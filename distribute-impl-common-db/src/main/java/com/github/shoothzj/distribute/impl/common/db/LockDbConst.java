package com.github.shoothzj.distribute.impl.common.db;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class LockDbConst {

    public static final String TABLE = "distribute_lock";

    public static final String LOCK_KEY_FIELD = "lock_key";

    public static final String LOCK_ID_FIELD = "lock_id";

    public static final String EXPIRE_TIME_FIELD = "expire_time";

}
