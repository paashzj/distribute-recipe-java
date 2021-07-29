package com.github.shoothzj.distribute.impl.mybatis;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hezhangjian
 */
@Data
public class LockPo {

    private String lockKey;

    private String lockId;

    private LocalDateTime expireTime;

    public LockPo() {
    }

}
