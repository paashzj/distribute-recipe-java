package com.github.shoothzj.distribute.impl.mongo.repo;

import com.github.shoothzj.distribute.impl.mongo.dao.MongoLock;

import java.time.LocalDateTime;

/**
 * @author hezhangjian
 */
public interface MongoLockRepoCustom {

    /**
     * 查找并更新
     * @param key
     * @param oldLockId
     * @param lockId
     * @param expireTime
     */
    MongoLock findAndModify(String key, String oldLockId, String lockId, LocalDateTime expireTime);

}
