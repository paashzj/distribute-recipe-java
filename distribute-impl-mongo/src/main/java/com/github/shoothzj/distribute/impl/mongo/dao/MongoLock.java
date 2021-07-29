package com.github.shoothzj.distribute.impl.mongo.dao;

import com.github.shoothzj.distribute.impl.common.db.LockDbConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * @author hezhangjian
 */
@Slf4j
@Document(collection = "MongoLock")
@Data
@AllArgsConstructor
public class MongoLock {

    @Indexed(unique = true)
    @Field(name = LockDbConst.LOCK_KEY_FIELD)
    private String lockKey;

    @Field(name = LockDbConst.LOCK_ID_FIELD)
    private String lockId;

    @Field(name = LockDbConst.EXPIRE_TIME_FIELD)
    private LocalDateTime expireTime;

    public MongoLock() {
    }
    
}
