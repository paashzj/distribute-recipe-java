package com.github.shoothzj.distribute.impl.redis.lettuce;

import org.junit.Assert;
import org.junit.Test;

public class RedisLockImplTest {

    @Test
    public void testCanConstruct() {
        final RedisLockImpl redisLock = new RedisLockImpl(null);
        Assert.assertNotNull(redisLock);
    }

}