package com.github.shoothzj.distribute.impl.common;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author hezhangjian
 */
public class TimeUtil {

    public static LocalDateTime futureTime(long time) {
        return LocalDateTime.now(ZoneId.of("UTC")).plusNanos(time);
    }

}
