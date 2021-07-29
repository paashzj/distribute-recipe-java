package com.github.shoothzj.distribute.impl.mybatis;

import com.github.shoothzj.javatool.util.CommonUtil;
import com.github.shoothzj.javatool.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

@Slf4j
public class MybatisLockImplTest {

    final MybatisLockImpl mybatisLock = new MybatisLockImpl();

    @Test
    public void testLock() throws Exception {
        LogUtil.configureLog();
        final String lockId = mybatisLock.requireLock("lock_key", 10_000);
        log.info("lock id is {}", lockId);
        CommonUtil.sleep(TimeUnit.SECONDS, 1);
        mybatisLock.requireLock("lock_key");
    }

}