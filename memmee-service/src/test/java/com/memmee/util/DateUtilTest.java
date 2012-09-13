package com.memmee.util;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class DateUtilTest {

    @Test
    public void testUpdateHoursMinsSecondsFromAnotherDate() {
        //Null inputs
        assertThat(DateUtil.updateHoursMinsSecondsFromAnotherDate(null, new Date()), is(nullValue()));
        assertThat(DateUtil.updateHoursMinsSecondsFromAnotherDate(new Date(), null), is(nullValue()));
        assertThat(DateUtil.updateHoursMinsSecondsFromAnotherDate(null, null), is(nullValue()));

        Date now = new Date();
        Date christmas = DateUtil.getDate(2012, 11, 25);
        Calendar christMasWithTimeCalendar = DateUtil.calendarFromDate(christmas);

        assertThat(christMasWithTimeCalendar.get(Calendar.YEAR), is(equalTo(2012)));
        assertThat(christMasWithTimeCalendar.get(Calendar.MONTH), is(equalTo(11)));
        assertThat(christMasWithTimeCalendar.get(Calendar.DAY_OF_MONTH), is(equalTo(25)));
        assertThat(christMasWithTimeCalendar.get(Calendar.HOUR_OF_DAY), is(equalTo(0)));
        assertThat(christMasWithTimeCalendar.get(Calendar.MINUTE), is(equalTo(0)));
        assertThat(christMasWithTimeCalendar.get(Calendar.SECOND), is(equalTo(0)));

        Date christmasWithTime = DateUtil.updateHoursMinsSecondsFromAnotherDate(christmas, now);
        assertThat(christmasWithTime, is(not(nullValue())));

        Calendar nowCalendar = DateUtil.calendarFromDate(now);
        christMasWithTimeCalendar = DateUtil.calendarFromDate(christmasWithTime);

        assertThat(christMasWithTimeCalendar.get(Calendar.YEAR), is(equalTo(2012)));
        assertThat(christMasWithTimeCalendar.get(Calendar.MONTH), is(equalTo(11)));
        assertThat(christMasWithTimeCalendar.get(Calendar.DAY_OF_MONTH), is(equalTo(25)));
        assertThat(christMasWithTimeCalendar.get(Calendar.HOUR_OF_DAY), is(equalTo(nowCalendar.get(Calendar.HOUR_OF_DAY))));
        assertThat(christMasWithTimeCalendar.get(Calendar.MINUTE), is(equalTo(nowCalendar.get(Calendar.MINUTE))));
        assertThat(christMasWithTimeCalendar.get(Calendar.SECOND), is(equalTo(nowCalendar.get(Calendar.SECOND))));
    }

}
