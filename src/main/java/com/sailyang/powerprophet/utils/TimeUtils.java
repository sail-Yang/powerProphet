package com.sailyang.powerprophet.utils;

import java.sql.Timestamp;

public class TimeUtils {
    public static Timestamp getNowTime(){
        return new Timestamp(System.currentTimeMillis());
    }
}
