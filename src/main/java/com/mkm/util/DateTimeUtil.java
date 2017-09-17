package com.mkm.util;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class DateTimeUtil {

    public static boolean isTransactionOlder(long timeStamp,int older){
       return (timeStamp < Instant.now().minusSeconds(older).toEpochMilli())? true : false;
    }
    public static Long secondsAgoTime(long seconds){
        return Instant.now().minusSeconds(seconds).toEpochMilli();
    }

 }
