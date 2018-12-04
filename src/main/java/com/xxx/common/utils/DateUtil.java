package com.xxx.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理工具类
 *
 * @author xujingyang
 * @date 2016年5月30日
 */
public class DateUtil {

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    private final static String HHMMSS = "HHmmss";
    private final static String HH_MM_SS = "HH:mm:ss";
    private final static String YYYYMMDD = "yyyyMMdd";
    private final static String YYYY_MM_DD = "yyyy-MM-dd";
    private final static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    private final static String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
    private final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式化日期 YYYY_MM_DD_HH_MM_SS 格式
     *
     * @param date
     * @return
     */
    public static final String format(Object date) {
        return format(date, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param pattern 不填默认为yyyy-MM-dd HH:mm:ss 格式
     * @return
     */
    public static final String format(Object date, String pattern) {
        if (date == null) {
            return null;
        }
        if (pattern == null) {
            return format(date);
        }
        return new SimpleDateFormat(pattern).format(date);
    }


    /**
     * 获取当前日期格式为yyyy-MM-dd
     *
     * @return
     */
    public static final String getDate() {
        return format(new Date());
    }

    /**
     * 获取日期时间 yyyy-MM-dd HH:mm:ss 格式
     *
     * @return
     */
    public static final String getDateTime() {
        return format(new Date(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取日期
     *
     * @param pattern 传入定义格式参数
     * @return
     */
    public static final String getDateTime(String pattern) {
        return format(new Date(), pattern);
    }


    /**
     * 获取当前时间，格式：YYYYMMDDHHMMSS
     *
     * @return
     */
    public static final String getDateSequence() {
        return new SimpleDateFormat(YYYYMMDDHHMMSS).format(new Date());
    }


    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static final long getCurrentTime() {
        return System.currentTimeMillis();
    }


    /**
     * 返回置顶日期与当前时间的距离，格式：XX年前、XX个月前、XX天前、XX小时前、XX分钟前、刚刚
     *
     * @param date
     * @return
     */
    public static final String getTimeFormatText(Long date) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date;
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

    /**
     * 间隔天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static final Integer getDayBetween(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);

        long n = end.getTimeInMillis() - start.getTimeInMillis();
        return (int) (n / (60 * 60 * 24 * 1000l));
    }


    /**
     * 日期加减计算
     *
     * @param date
     * @param field  常量 日:Calendar.DATE  月:Calendar.MONTH  年:Calendar.YEAR
     * @param amount 加减数量
     * @return Date
     */
    public static final Date addDate(Date date, int field, int amount) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    public static void main(String[] args) {
//        System.out.println(getDateSequence());
//        System.out.println(getDateSequence());

        System.out.println(format(addDate(new Date(), Calendar.DATE, 2)));

        System.out.println(getTimeFormatText(addDate(new Date(), Calendar.DATE, -42).getTime()));
    }
}
