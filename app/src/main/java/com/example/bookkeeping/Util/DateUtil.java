package com.example.bookkeeping.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }
    public static String getDate(Calendar calendar){
        Date time = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time);
    }
    public static String getMonth(Calendar calendar){
//        拿到时间
        Date time = calendar.getTime();
//        时间的显示格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM");
        return sdf.format(time);
    }

}
