package com.github.shoothzj.distribute.impl.mybatis;

import com.github.shoothzj.distribute.impl.common.db.LockDbConst;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

/**
 * @author hezhangjian
 */
public interface LockMapper {

    @Insert("INSERT INTO " + LockDbConst.TABLE + " (lock_key, lock_id, expire_time) VALUES ( #{lockKey}, #{lockId}, #{expireTime})")
    Integer insertLockPo(@Param("lockKey") String key, @Param("lockId") String lockId, @Param("expireTime") LocalDateTime expireTime);

    @Select("SELECT * FROM " + LockDbConst.TABLE + " WHERE lock_key = #{lockKey}")
    @Results(value = {@Result(property = "lockKey", column = "lock_key"),
            @Result(property = "lockId", column = "lock_id"),
            @Result(property = "expireTime", column = "expire_time")})
    LockPo findLockPo(@Param("lockKey") String key);

    @Delete("DELETE FROM " + LockDbConst.TABLE + " WHERE lock_key = #{lockKey} AND lock_id = #{lockId}")
    Integer deleteByKeyAndLockId(@Param("lockKey") String key, @Param("lockId") String lockId);

    @Update("UPDATE " + LockDbConst.TABLE + " set lock_id = #{lockId}, expire_time = #{expireTime} WHERE lock_key = #{lockKey} and lock_id = #{oldLockId}")
    int findAndModify(@Param("lockKey") String key, @Param("oldLockId") String oldLockId, @Param("lockId") String lockId, @Param("expireTime") LocalDateTime expireTime);

}
