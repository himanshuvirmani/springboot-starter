package com.core.util.datetime;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.TimeZone;

/**
 * Created by himanshu.virmani on 01/10/16.
 */
public class DateTimeUtils {

    public static DateTime getISTDate(DateTime dateTime){
        if(dateTime!=null){
            DateTimeZone timeZone = DateTimeZone.forTimeZone(TimeZone.getTimeZone("IST"));
            return dateTime.toDateTime(timeZone);
        }
        return null;
    }

    public static final DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd");

    public static java.sql.Date toSqlDate(String date) throws UnsupportedOperationException, IllegalArgumentException {
        return toSqlDate(dateFormat, date);
    }

    public static java.sql.Date toSqlDate(DateTimeFormatter fmt, String date) throws UnsupportedOperationException,
            IllegalArgumentException {
        return new java.sql.Date(toJavaDate(fmt, date).getTime());
    }

    public static java.util.Date toJavaDate(DateTimeFormatter fmt, String date) throws UnsupportedOperationException,
            IllegalArgumentException {
        return date == null ? null : fmt.parseLocalDate(date).toDate();
    }
}
