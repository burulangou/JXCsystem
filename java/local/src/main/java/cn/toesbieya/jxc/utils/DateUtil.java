package cn.toesbieya.jxc.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    //设置默认的时间格式
    public static final DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //当没有传递参数时返回本地的时间和格式
    public static String dateFormat() {
        return dateFormat(null, null);
    }

    public static String dateFormat(Long time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        return dateFormat(localDateTime, null);
    }

    public static String dateFormat(LocalDateTime localDateTime) {
        return dateFormat(localDateTime,null);
    }

    public static String dateFormat(DateTimeFormatter formatter) {
        return dateFormat(null,formatter);
    }

    //
    public static String dateFormat(LocalDateTime localDateTime, DateTimeFormatter formatter) {
        LocalDateTime t = localDateTime == null ? LocalDateTime.now() : localDateTime;
        DateTimeFormatter f = formatter == null ? defaultFormatter : formatter;
        return t.format(f);
    }

    //获取今日零点的时间戳
    /**
     * currentTimestamps：当前时间戳
     * 60 * 60 * 8 * 1000：8小时的毫秒数（28800000ms）
     * 60 * 60 * 24 * 1000：24小时的毫秒数（86400000ms）
     * 8小时偏移是针对中国时区设计的，可以自己添加时区来确保时间准确
     * */
    public static long getTimestampNow() {
        long currentTimestamps = System.currentTimeMillis();
        return currentTimestamps - (currentTimestamps + 60 * 60 * 8 * 1000) % (60 * 60 * 24 * 1000);
    }

    //获取n天前零点的时间戳
    public static long getTimestampBeforeNow(int before) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -before);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().getTime();
    }
}
