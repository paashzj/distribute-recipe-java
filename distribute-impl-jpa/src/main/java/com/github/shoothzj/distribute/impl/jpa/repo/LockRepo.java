package com.github.shoothzj.distribute.impl.jpa.repo;

import com.github.shoothzj.distribute.impl.jpa.domain.LockPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author hezhangjian
 */
@Repository
public interface LockRepo extends JpaRepository<LockPo, Long> {

    @Modifying
    @Transactional
    @Query("update LockPo lock set lock.lockId = :lockId, lock.expireTime = :expireTime WHERE lock.lockKey = :lockKey and lock.lockId = :oldLockId")
    int findAndModify(@Param("lockKey") String lockKey, @Param("oldLockId") String oldLockId, @Param("lockId") String lockId, @Param("expireTime") LocalDateTime expireTime);

    /**
     * 根据Key和锁Id释放锁
     *
     * @param key
     * @param lockId
     */
    @Modifying
    @Transactional
    void deleteByLockKeyAndLockId(String key, String lockId);

    /**
     * 根据key查找
     *
     * @param key
     * @return
     */
    Optional<LockPo> findByLockKey(String key);

}
