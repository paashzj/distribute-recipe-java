package com.github.shoothzj.distribute.impl.redis.spring;

import com.github.shoothzj.distribute.api.LockException;
import com.github.shoothzj.distribute.impl.common.BaseLockImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

/**
 * @author hezhangjian
 */
@Slf4j
@Service
public class RedisLockImpl extends BaseLockImpl {

    @Autowired
    private RedisTemplate<String, String> template;

    Resource setLockSource = new ClassPathResource("lua/set_lock.lua");

    RedisScript<Boolean> lockScript = RedisScript.of(setLockSource, Boolean.class);

    Resource releaseLockSource = new ClassPathResource("lua/release_lock.lua");

    RedisScript<Boolean> releaseLockScript = RedisScript.of(releaseLockSource, Boolean.class);

    @Override
    public String requireLock(String key, long nanoseconds) throws LockException {
        final String lockId = UUID.randomUUID().toString();
        try {
            int aux = (int) (nanoseconds / 1000_000_000);
            final Boolean result = template.execute(lockScript, Collections.singletonList(key), String.valueOf(aux), lockId);
            if (result == null || !result) {
                throw new LockException("lock failed");
            }
        } catch (Exception e) {
            throw new LockException(e);
        }
        return lockId;
    }

    @Override
    public void releaseLock(String lockKey, String lockId) {
        try {
            template.execute(releaseLockScript, Collections.singletonList(lockKey), lockId);
        } catch (Exception e) {
            log.error("release lock failed ", e);
        }
    }

}
