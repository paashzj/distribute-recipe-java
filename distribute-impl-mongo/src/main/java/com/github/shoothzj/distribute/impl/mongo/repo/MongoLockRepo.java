package com.github.shoothzj.distribute.impl.mongo.repo;

import com.github.shoothzj.distribute.impl.mongo.dao.MongoLock;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author hezhangjian
 */
public interface MongoLockRepo extends MongoRepository<MongoLock, String>, MongoLockRepoCustom {

    /**
     * 根据唯一主键查询MongoLock对象
     * @param key
     * @return
     */
    MongoLock findMongoLockByLockKey(String key);

    /**
     * 根据key和lockId释放锁
     * @param key
     * @param lockId
     * @return
     */
    Long deleteByLockKeyAndLockId(String key, String lockId);

}
