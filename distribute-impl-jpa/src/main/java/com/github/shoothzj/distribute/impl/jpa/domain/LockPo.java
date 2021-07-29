package com.github.shoothzj.distribute.impl.jpa.domain;

import com.github.shoothzj.distribute.impl.common.db.LockDbConst;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author shoothzj
 */
@Entity
@Table(name = "lock")
public class LockPo {

    @Id
    @Column(name = LockDbConst.LOCK_KEY_FIELD)
    private String lockKey;

    @Column(name = LockDbConst.LOCK_ID_FIELD)
    private String lockId;

    @Column(name = LockDbConst.EXPIRE_TIME_FIELD)
    private LocalDateTime expireTime;

    public LockPo() {
    }

    public LockPo(String lockKey, String lockId, LocalDateTime expireTime) {
        this.lockKey = lockKey;
        this.lockId = lockId;
        this.expireTime = expireTime;
    }

    public String getLockKey() {
        return lockKey;
    }

    public void setLockKey(String scene) {
        this.lockKey = scene;
    }

    public String getLockId() {
        return lockId;
    }

    public void setLockId(String uniqId) {
        this.lockId = uniqId;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
