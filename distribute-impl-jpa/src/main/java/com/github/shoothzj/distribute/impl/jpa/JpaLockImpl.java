package com.github.shoothzj.distribute.impl.jpa;

import com.github.shoothzj.distribute.api.LockException;
import com.github.shoothzj.distribute.impl.common.BaseLockImpl;
import com.github.shoothzj.distribute.impl.jpa.domain.LockPo;
import com.github.shoothzj.distribute.impl.jpa.repo.LockRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

/**
 * @author hezhangjian
 */
@Slf4j
@Service
public class JpaLockImpl extends BaseLockImpl {

    @Autowired
    private LockRepo lockRepo;

    @Override
    public String requireLock(String key, long nanoseconds) throws LockException {
        String lockId = UUID.randomUUID().toString();
        Optional<LockPo> lockOp = lockRepo.findByLockKey(key);
        final LocalDateTime nowTime = LocalDateTime.now(ZoneId.of("UTC"));
        final LocalDateTime expireTime = nowTime.plusNanos(nanoseconds);
        if (!lockOp.isPresent()) {
            lockRepo.save(new LockPo(key, lockId, expireTime));
            return lockId;
        } else {
            LockPo lockPo = lockOp.get();
            if (lockPo.getExpireTime().isBefore(nowTime)) {
                final int affectRow = lockRepo.findAndModify(key, lockPo.getLockId(), lockId, expireTime);
                if (affectRow == 1) {
                    return lockId;
                }
            }
        }
        throw new LockException("get lock failed");
    }

    @Override
    public void releaseLock(String lockKey, String lockId) {
        lockRepo.deleteByLockKeyAndLockId(lockKey, lockId);
    }
}
