package com.inventory.middle.domain.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
/**
 * @description DateUtils
 * @author holmes
 * @date 2021-06-16
 */
@Slf4j
public class DateUtils {

    public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_COMMON = "yyyy-MM-dd HH:mm:ss";

    /**
     * Date 转换成 LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (Objects.isNull(date)) {
            date = new Date();
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Date 转换成 LocalDate
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        if (Objects.isNull(date)) {
            date = new Date();
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * LocalDateTime 转换成 Date
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            localDateTime = LocalDateTime.now();
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDate 转换成 Date
     * @param localDate
     * @return
     */
    public static Date localDateToDate(LocalDate localDate) {
        if (Objects.isNull(localDate)) {
            localDate = LocalDate.now();
        }
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String dateToString(Date date, String format) {
        // Date 转换成 LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.now();
        if (date != null) {
            localDateTime = dateToLocalDateTime(date);
        }
        if (StringUtils.isEmpty(format)){
            format = DATE_FORMAT_DEFAULT;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return df.format(localDateTime);
    }

    public static String dateToString(LocalDateTime date, String format) {
        // Date 转换成 LocalDateTime

        if (StringUtils.isEmpty(format)){
            format = DATE_FORMAT_DEFAULT;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return df.format(date);
    }
    /**
     * 日期转字符串 yyyy-MM-dd
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        if(date == null){
            return "";
        }
        return dateToString(date, DATE_FORMAT_DEFAULT);
    }


    /**
     * 字符串转换成日期 日期格式为 yyyy-MM-dd HH:mm:ss
     * @param dateStr
     * @return
     */
    public static Date stringToDateCommon(String dateStr) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_FORMAT_COMMON);
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, df);
        return localDateTimeToDate(localDateTime);
    }

    /**
     * 字符串转换成日期 日期格式为 yyyy-MM-dd
     * @param dateStr
     * @return
     */
    public static Date stringToDateDefault(String dateStr) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_FORMAT_DEFAULT);
        LocalDate localDate = LocalDate.parse(dateStr, df);
        return localDateToDate(localDate);
    }

    /**
     * 计算前后的日期 (yyyy-dd-MM HH:mm:ss 步长：分钟)
     * @return
     */
    public static Date getBeforeOrAfterDateByMinutes(LocalDateTime localDateTime, Integer minutes) {
        if (Objects.isNull(localDateTime)) {
            localDateTime = LocalDateTime.now();
        }
        if (Objects.isNull(minutes)) {
            return localDateTimeToDate(localDateTime);
        }
        localDateTime = localDateTime.plusMinutes(minutes);
        return localDateTimeToDate(localDateTime);
    }

    /**
     * 计算前后的日期 (yyyy-dd-MM HH:mm:ss 步长：月)
     * @return
     */
    public static Date getBeforeOrAfterDateByMonths(LocalDateTime localDateTime, Integer months) {
        if (Objects.isNull(localDateTime)) {
            localDateTime = LocalDateTime.now();
        }
        if (Objects.isNull(months)) {
            return localDateTimeToDate(localDateTime);
        }
        localDateTime = localDateTime.plusMonths(months);
        return localDateTimeToDate(localDateTime);
    }

    /**
     * 计算前后的日期 (yyyy-dd-MM HH:mm:ss 步长：天)
     * @return
     */
    public static Date getBeforeOrAfterDateByDays(LocalDateTime localDateTime, Integer days) {
        if (Objects.isNull(localDateTime)) {
            localDateTime = LocalDateTime.now();
        }
        if (Objects.isNull(days)) {
            return localDateTimeToDate(localDateTime);
        }
        localDateTime = localDateTime.plusDays(days);
        return localDateTimeToDate(localDateTime);
    }

    /**
     * 获得当前日期截止时间 (yyyy-MM-ddT23:59:59.999)
     * @return
     */
    public static LocalDateTime getCurrentLocalDateTimeToEnd() {
        String nowTime = LocalDate.now().toString() + "T23:59:59.999";
        return  LocalDateTime.parse(nowTime);
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDate asLocalDate(String str, String format) {
        return LocalDate.parse(str, DateTimeFormatter.ofPattern(format));
    }

    public static LocalDateTime asLocalDateTime(String str, String format) {
        return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(format));
    }
}
