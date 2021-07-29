package com.github.shoothzj.distribute.impl.mybatis;

import com.github.shoothzj.distribute.api.LockException;
import com.github.shoothzj.distribute.impl.common.BaseLockImpl;
import com.github.shoothzj.distribute.impl.common.db.SqlConnConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

/**
 * @author hezhangjian
 */
@Slf4j
public class MybatisLockImpl extends BaseLockImpl {

    private final SqlSessionFactory sessionFactory;

    public MybatisLockImpl() {
        this(SqlConnConfig.JDBC_URL);
    }

    public MybatisLockImpl(String jdbcUrl) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        HikariDataSource ds = new HikariDataSource(config);
        SqlSessionFactoryBuilder sessionFactoryBuilder = new SqlSessionFactoryBuilder();
        Environment environment = new Environment("production", new JdbcTransactionFactory(), ds);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(LockMapper.class);
        sessionFactory = sessionFactoryBuilder.build(configuration);
    }

    public MybatisLockImpl(HikariDataSource ds) {
        SqlSessionFactoryBuilder sessionFactoryBuilder = new SqlSessionFactoryBuilder();
        Environment environment = new Environment("production", new JdbcTransactionFactory(), ds);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(LockMapper.class);
        sessionFactory = sessionFactoryBuilder.build(configuration);
    }

    @Override
    public String requireLock(String key, long nanoseconds) throws LockException {
        String lockId = UUID.randomUUID().toString();
        final LocalDateTime nowTime = LocalDateTime.now(ZoneId.of("UTC"));
        final LocalDateTime expireTime = nowTime.plusNanos(nanoseconds);
        try (SqlSession sqlSession = sessionFactory.openSession()) {
            final LockMapper lockMapper = sqlSession.getMapper(LockMapper.class);
            final LockPo lockPo = lockMapper.findLockPo(key);
            if (lockPo == null) {
                lockMapper.insertLockPo(key, lockId, expireTime);
                sqlSession.commit();
                return lockId;
            } else {
                if (lockPo.getExpireTime().isBefore(nowTime)) {
                    //原来的锁过期，可以覆盖
                    final int affectedRows = lockMapper.findAndModify(key, lockPo.getLockId(), lockId, expireTime);
                    sqlSession.commit();
                    if (affectedRows == 1) {
                        return lockId;
                    }
                }
            }
        }
        throw new LockException("get lock failed");
    }

    @Override
    public void releaseLock(String lockKey, String lockId) {
        try (SqlSession sqlSession = sessionFactory.openSession()) {
            final LockMapper lockMapper = sqlSession.getMapper(LockMapper.class);
            lockMapper.deleteByKeyAndLockId(lockKey, lockId);
        } catch (Exception e) {
            log.error("release lock failed, do nothing ", e);
        }
    }

}
