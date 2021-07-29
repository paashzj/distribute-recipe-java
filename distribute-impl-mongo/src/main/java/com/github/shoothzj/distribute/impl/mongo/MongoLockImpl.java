package com.github.shoothzj.distribute.impl.mongo;

import com.github.shoothzj.distribute.api.LockException;
import com.github.shoothzj.distribute.impl.common.BaseLockImpl;
import com.github.shoothzj.distribute.impl.mongo.dao.MongoLock;
import com.github.shoothzj.distribute.impl.mongo.repo.MongoLockRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

/**
 * @author hezhangjian
 */
@Slf4j
@Service
public class MongoLockImpl extends BaseLockImpl {

    @Autowired
    private MongoLockRepo mongoLockRepo;

    @Override
    public String requireLock(String key, long nanoseconds) throws LockException {
        String lockId = UUID.randomUUID().toString();
        MongoLock mongoLock = mongoLockRepo.findMongoLockByLockKey(key);
        final LocalDateTime nowTime = LocalDateTime.now(ZoneId.of("UTC"));
        final LocalDateTime expireTime = nowTime.plusNanos(nanoseconds);
        if (mongoLock == null) {
            // 如果mongoLock不存在，尝试插入，插入成功即获得了锁
            mongoLockRepo.insert(new MongoLock(key, lockId, expireTime));
            return lockId;
        } else {
            if (mongoLock.getExpireTime().isBefore(nowTime)) {
                //原来的锁过期，可以覆盖
                final MongoLock newMongoLock = mongoLockRepo.findAndModify(key, mongoLock.getLockId(), lockId, expireTime);
                if (newMongoLock != null) {
                    return lockId;
                }
            }
        }
        throw new LockException("get lock failed");
    }

    @Override
    public void releaseLock(String lockKey, String lockId) {
        Long result = mongoLockRepo.deleteByLockKeyAndLockId(lockKey, lockId);
        log.debug("delete end result is [{}]", result);
    }

}
