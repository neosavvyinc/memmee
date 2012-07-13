package com.memmee.util;

import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: trevorewen
 * Date: 7/12/12
 * Time: 10:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class DateUtil {

    public static Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public static Date safeParse(String date) {
        try {
            return DateUtils.parseDate(date);
        } catch (DateParseException e) {
            return null;
        }
    }

}
