package com.github.shoothzj.distribute.impl.redis.lettuce;

import com.github.shoothzj.distribute.api.LockException;
import com.github.shoothzj.distribute.impl.common.BaseLockImpl;
import com.github.shoothzj.javatool.util.FileUtil;
import com.github.shoothzj.javatool.util.IoUtil;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.util.UUID;

/**
 * @author hezhangjian
 */
@Slf4j
public class RedisLockImpl extends BaseLockImpl {

    RedisCommands<String, String> redisCommands;

    private final String lockScriptSha1;

    private final String unlockScriptSha1;

    public RedisLockImpl(RedisCommands<String, String> redisCommands) {
        this.redisCommands = redisCommands;
        try {
            final byte[] setLockBytes = IoUtil.read2Byte(new FileInputStream(FileUtil.getFilePath("lua/set_lock.lua")));
            final byte[] releaseLockBytes = IoUtil.read2Byte(new FileInputStream(FileUtil.getFilePath("lua/release_lock.lua")));
            lockScriptSha1 = redisCommands.scriptLoad(setLockBytes);
            unlockScriptSha1 = redisCommands.scriptLoad(releaseLockBytes);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void releaseLock(String lockKey, String lockId) {
        try {
            redisCommands.evalsha("", ScriptOutputType.BOOLEAN, auxArray(lockKey), lockId);
        } catch (Exception e) {
            log.error("release lock failed ", e);
        }
    }

    @Override
    public String requireLock(String key, long nanoseconds) throws LockException {
        final String lockId = UUID.randomUUID().toString();
        try {
            int aux = (int) (nanoseconds / 1000_000_000);
            final Boolean result = redisCommands.evalsha("", ScriptOutputType.BOOLEAN, auxArray(key), String.valueOf(aux), lockId);
            if (result == null || !result) {
                throw new LockException("lock failed");
            }
        } catch (LockException e) {
            throw e;
        } catch (Exception e) {
            log.error("release lock failed ", e);
        }
        return lockId;
    }

    private String[] auxArray(String key) {
        final String[] strings = new String[1];
        strings[0] = key;
        return strings;
    }

}
