package com.github.shoothzj.distribute.impl.common;

import com.github.shoothzj.distribute.api.ILock;
import com.github.shoothzj.distribute.api.LockException;

import java.util.concurrent.TimeUnit;

/**
 * @author hezhangjian
 */
public abstract class BaseLockImpl implements ILock {

    @Override
    public String requireLock(String lockKey, TimeUnit timeUnit, long number) throws LockException {
        if (timeUnit.equals(TimeUnit.MINUTES)) {
            return requireLock(lockKey, number * 60 * 1000_000_000);
        }
        if (timeUnit.equals(TimeUnit.SECONDS)) {
            return requireLock(lockKey, number * 1000_000_000);
        }
        if (timeUnit.equals(TimeUnit.MILLISECONDS)) {
            return requireLock(lockKey, number * 1000_000);
        }
        if (timeUnit.equals(TimeUnit.NANOSECONDS)) {
            return requireLock(lockKey, number);
        }
        throw new LockException("lock unit not supported yet.");
    }

    public abstract String requireLock(String key, long nanoseconds) throws LockException;

}
