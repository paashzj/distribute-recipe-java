package com.github.shoothzj.distribute.api;

import java.util.concurrent.TimeUnit;

/**
 * @author hezhangjian
 */
public interface ILock {

    /**
     * 获取锁
     * @param lockKey 锁的Key
     * @throws LockException 获取锁异常
     */
    default void requireLock(String lockKey) throws LockException {
        requireLock(lockKey, TimeUnit.SECONDS, LockConst.LOCK_TIME);
    }

    /**
     * 获取锁，返回锁ID
     * @param lockKey 锁Key
     * @param timeUnit 时间单位
     * @param number 时间
     * @return lockId
     * @throws LockException 获取锁异常
     */
    String requireLock(String lockKey, TimeUnit timeUnit, long number) throws LockException;

    /**
     * 根据锁Key和锁ID释放锁
     * @param lockKey 锁Key
     * @param lockId 锁Id
     */
    void releaseLock(String lockKey, String lockId);

}
