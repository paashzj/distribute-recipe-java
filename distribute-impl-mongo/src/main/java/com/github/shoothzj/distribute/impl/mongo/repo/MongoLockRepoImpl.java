package com.github.shoothzj.distribute.impl.mongo.repo;

import com.github.shoothzj.distribute.impl.common.db.LockDbConst;
import com.github.shoothzj.distribute.impl.mongo.dao.MongoLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author shoothzj
 */
@Service
public class MongoLockRepoImpl implements MongoLockRepoCustom {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public MongoLock findAndModify(String key, String oldLockId, String lockId, LocalDateTime expireTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where(LockDbConst.LOCK_KEY_FIELD).is(key))
                .addCriteria(Criteria.where(LockDbConst.LOCK_ID_FIELD).is(oldLockId));

        Update update = new Update();
        update.set(LockDbConst.LOCK_KEY_FIELD, key);
        update.set(LockDbConst.LOCK_ID_FIELD, lockId);
        update.set(LockDbConst.EXPIRE_TIME_FIELD, expireTime);

        return mongoOperations.findAndModify(query, update, MongoLock.class);
    }

}
