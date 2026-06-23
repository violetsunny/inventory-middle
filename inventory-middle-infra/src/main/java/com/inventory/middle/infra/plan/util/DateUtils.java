package com.inventory.middle.infra.plan.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期工具类
 *
 * @author xiaokang
 */
public class DateUtils {
    public static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    private DateUtils() {
    }

    final static long EIGHT_HOURS = 8 * 60 * 60 * 1000L;
    public final static long ONE_HOUR = 60 * 60 * 1000L;
    public final static long DAY = 24 * 60 * 60 * 1000L;
    public final static long WEEK = 7 * 24 * 60 * 60 * 1000L;
    public final static long ONE_MINUTE = 60 * 1000L;

    public final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public final static String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public final static String yyyy_MM_dd = "yyyy-MM-dd";
    public final static String yyyyMMdd = "yyyyMMdd";
    public final static String yyyyMMddHH = "yyyyMMddHH";
    public final static String YYYY_MM_DD_SLASH = "yyyy/MM/dd";

    public static String dateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date stringToDate(String dateString, String pattern) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.parse(dateString);
    }

    public static Date getStartOfDay(Date date) {
        Calendar dateStart = Calendar.getInstance();
        dateStart.setTime(date);
        dateStart.set(Calendar.HOUR_OF_DAY, 0);
        dateStart.set(Calendar.MINUTE, 0);
        dateStart.set(Calendar.SECOND, 0);
        dateStart.set(Calendar.MILLISECOND, 0);
        return dateStart.getTime();
    }

    public static Date getEndOfDay(Date date) {
        Calendar dateEnd = Calendar.getInstance();
        dateEnd.setTime(date);
        dateEnd.set(Calendar.HOUR_OF_DAY, 23);
        dateEnd.set(Calendar.MINUTE, 59);
        dateEnd.set(Calendar.SECOND, 59);
        return dateEnd.getTime();
    }

    public static long getFirstDayThisMonth(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DATE, 1);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTimeInMillis();
    }

    public static long getFirstDayLastMonth(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1);
        ca.set(Calendar.DATE, 1);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTimeInMillis();
    }

    public static long getFirstDayNextMonth(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) + 1);
        ca.set(Calendar.DATE, 1);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTimeInMillis();
    }

    public static long getFirstDayThisWeek(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        // 如果当前时间是星期天，则向上移动一天，再取本周的星期一，老外用周日到周六为一周，向前移动一天，则是中国人的本周
        if (ca.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            ca.add(Calendar.DATE, -1);
        }
        ca.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);

        return ca.getTimeInMillis();
    }

    public static long getFirstDayLastWeek(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DATE, -7);

        // 如果当前时间是星期天，则向上移动一天，再取本周的星期一,老外用周日到周六为一周，向前移动一天，则是中国人的本周
        if (ca.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            ca.add(Calendar.DATE, -1);
        }
        ca.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);

        return ca.getTimeInMillis();
    }

    public static long getFirstDayNextWeek(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DATE, 7);

        // 如果当前时间是星期天，则向上移动一天，再取本周的星期一，老外用周日到周六为一周，向前移动一天，则是中国人的本周
        if (ca.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            ca.add(Calendar.DATE, -1);
        }
        ca.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);

        return ca.getTimeInMillis();
    }

    /**
     * 判断是周几,
     *
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 校验是否是周几
     *
     * @param date
     * @param day
     * @return
     */
    public static boolean checkIsDayOfWeek(Date date, int day) {
        return getDayOfWeek(date) == day;
    }


    /**
     * 是否是月初第一天
     *
     * @param date
     * @return
     */
    public static boolean checkFirstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DATE) == cal.getActualMinimum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 是否是月末最后一天
     *
     * @param date
     * @return
     */
    public static boolean checkLastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DATE) == cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 对时间进行天加减运算
     *
     * @param time
     * @param num
     * @return
     */
    public static long addDate(long time, int num) {
        return DateUtils.addDate(new Date(time), num).getTime();
    }

    /**
     * 对时间进行天加减运算
     *
     * @param date
     * @param num
     * @return
     */
    public static Date addDate(Date date, int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, num);
        return cal.getTime();
    }

    /**
     * 对小时加减运算
     *
     * @param time
     * @param num
     * @return
     */
    public static long addHour(long time, int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        cal.add(Calendar.HOUR_OF_DAY, num);
        return cal.getTime().getTime();
    }

    public static long addMonth(long time, int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        cal.add(Calendar.MONTH, num);
        return cal.getTime().getTime();
    }

    /**
     * 对分钟加减运算
     *
     * @param time
     * @param num
     * @return
     */
    public static long addMinute(long time, int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        cal.add(Calendar.MINUTE, num);
        return cal.getTime().getTime();
    }

    /**
     * @param date
     * @return 获取当前天
     */
    public static int getDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * @param date1
     * @param date2
     * @return 判断对否同一天
     */
    public static Boolean isOneDay(Date date1, Date date2) {
        int lastDay = DateUtils.getDayOfYear(date1);
        int nowDay = DateUtils.getDayOfYear(date2);
        if (lastDay == nowDay) {
            return true;
        }
        return false;
    }

    /**
     * 获取当天0点的时间戳
     *
     * @param
     * @return
     */
    public static long getTodayZeroHour(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");//NOSONAR
        try {
            return formatter.parse(formatter.format(new Date(time))).getTime();
        } catch (ParseException e) {
            LOGGER.error("DateUtils.getTodayZeroHour error", e);
            return 0;
        }
    }

    /**
     * 获取当天0点的时间戳(使用calendar)
     *
     * @param
     * @return
     */
    public static Date getTodayZeroHourByCalendar(long time, String timeZone) {
        String tz = StringUtils.isEmpty(timeZone) ? "GMT+8" : timeZone;
        TimeZone curTimeZone = TimeZone.getTimeZone(tz);
        Calendar calendar = Calendar.getInstance(curTimeZone);
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param startTime   开始的时间
     * @param currentTime 当前的时间
     * @return 相差天数
     * @throws ParseException
     */

    public static int daysBetween(Date startTime, Date currentTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);
        startTime = sdf.parse(sdf.format(startTime));
        currentTime = sdf.parse(sdf.format(currentTime));
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        long time1 = cal.getTimeInMillis();
        cal.setTime(currentTime);
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(betweenDays)) + 1;
    }

    /**
     * 间隔小时数
     *
     * @param startTime
     * @param currentTime
     * @return
     * @throws ParseException
     */
    public static int hoursBetween(Date startTime, Date currentTime) throws ParseException {
        long time1 = startTime.getTime();
        long time2 = currentTime.getTime();
        long betweenHours = (time2 - time1) / (1000 * 3600);
        return Integer.parseInt(String.valueOf(betweenHours));
    }


    public static String getSecondDayString(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        Date date;
        try {
            date = stringToDate(dateStr, yyyy_MM_dd);
        } catch (ParseException e) {
            LOGGER.error("Parse Date error,dateStr :{}", dateStr, e);
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.AM_PM, 0);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date nextDate = cal.getTime();
        return dateToString(nextDate, yyyy_MM_dd);
    }

    public static String getSecondDayString(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.AM_PM, 0);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date nextDate = cal.getTime();
        return dateToString(nextDate, yyyy_MM_dd);
    }

    public static Date getLastDateOfOneDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date getStartDateOfOneDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getYesterdayDate(Date today) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }


    /**
     * 获取昨天0点的时间戳
     *
     * @param
     * @return
     */
    public static Date getYesterdayZeroHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * 获取昨天0点的时间戳
     *
     * @param
     * @return
     */
    public static Date getTodayZeroHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取明天0点的时间戳
     *
     * @param
     * @return
     */
    public static Date getTomorrowZeroHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String getTodayByFormat(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }

    public static Calendar getTodayEnd() {
        return getDayEnd(Calendar.getInstance());
    }

    public static Calendar getDayEnd(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar;
    }

    public static Long getStartOfMonth(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static Long getEndOfMonth(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    public static String getDateString(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//NOSONAR
        return simpleDateFormat.format(date);
    }

    public static String getDateString_yyyyMMddHHmmss(Date date) {//NOSONAR
        if (date == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//NOSONAR
        return simpleDateFormat.format(date);
    }

    public static String getDateString_yyyyMMdd(Date date) {//NOSONAR
        if (date == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");//NOSONAR
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前时间到明天凌晨秒数
     *
     * @return
     */
    public static int getSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }

    public static LocalDate toLocalDate(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date toDate(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}
