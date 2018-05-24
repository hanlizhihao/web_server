package com.hlz.util;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Administrator
 * @created 2018/5/23
 */
public class TimeUtil {
    @NotNull
    public static Date getTodayBegin() {
        int zeroToNowSecond = LocalTime.now().toSecondOfDay();
        return Date.from(Instant.now().minusSeconds(zeroToNowSecond));
    }

    @NotNull
    public static Date getTodayEnd() {
        Instant instant = getTodayBegin().toInstant();
        instant = instant.plusSeconds(24*3600);
        return Date.from(instant);
    }

    public static Instant getMouthBegin() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime mouth = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), 1,0,0, 1);
        return mouth.atZone(ZoneId.systemDefault()).toInstant();
    }

}
