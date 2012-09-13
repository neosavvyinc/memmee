package com.memmee.util;

import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return org.apache.commons.lang3.time.DateUtils.truncate(calendar.getTime(), Calendar.DAY_OF_MONTH);
    }

    public static Date hourPrecision(Date date) {
        return org.apache.commons.lang3.time.DateUtils.truncate(date, Calendar.HOUR);
    }

    public static String toSqlDate(Date date) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static Date safeParse(String date) {
        try {
            return DateUtils.parseDate(date);
        } catch (DateParseException e) {
            return null;
        }
    }

    public static Date updateHoursMinsSecondsFromAnotherDate(Date toBeUpdated, Date updateFrom) {
        if (toBeUpdated != null && updateFrom != null) {
            Calendar calendarToBeUpdated = calendarFromDate(toBeUpdated);
            Calendar calendarUpdateFrom = calendarFromDate(updateFrom);

            calendarToBeUpdated.set(Calendar.HOUR_OF_DAY, calendarUpdateFrom.get(Calendar.HOUR_OF_DAY));
            calendarToBeUpdated.set(Calendar.MINUTE, calendarUpdateFrom.get(Calendar.MINUTE));
            calendarToBeUpdated.set(Calendar.SECOND, calendarUpdateFrom.get(Calendar.SECOND));

            return calendarToBeUpdated.getTime();
        }
        return null;
    }

    public static Calendar calendarFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

}
