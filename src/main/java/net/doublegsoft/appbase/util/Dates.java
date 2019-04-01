/*
 * Copyright 2017 doublegsoft.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.doublegsoft.appbase.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 2.0
 */
public class Dates {

    public static final long MILLIS_OF_SECOND = 1000;

    public static final long MILLIS_OF_MINUTE = MILLIS_OF_SECOND * 60;

    public static final long MILLIS_OF_HOUR = MILLIS_OF_MINUTE * 60;

    public static final long MILLIS_OF_DAY = MILLIS_OF_HOUR * 24;

    public static Date valueOf(String str) {
        Date retVal = null;
        try {
            retVal = Timestamp.valueOf(str);
        } catch (Exception ex) {

        }
        if (retVal != null) {
            return retVal;
        }
        try {
            retVal = java.sql.Date.valueOf(str);
        } catch (Exception ex) {

        }
        return retVal;
    }

    /**
     * Gets the {@link Date} instance with the year, month, day and hour.
     * 
     * @param year
     *            the year
     * 
     * @param month
     *            the month, one-based
     * 
     * @param day
     *            the day of month
     * 
     * @param hour
     *            the hour of day
     * 
     * @return the date
     */
    public static Date valueOf(int year, int month, int day, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Gets this month {@link Date} object with the given date.
     * 
     * @param date
     *            any date
     * 
     * @return the month {@link Date} object
     */
    public static Date month(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Gets the {@link Date} object before the given date.
     * 
     * @param date
     *            any date
     * 
     * @param offset
     *            the time offset
     * 
     * @param field
     *            the time field
     * 
     * @return the {@link Date} object before the given date
     */
    public static Date before(Date date, int offset, int field) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, -offset);
        return cal.getTime();
    }

    public static String today() {
        return stringify(new Date(System.currentTimeMillis()));
    }

    public static String yesterday() {
        return stringify(increment(new Date(System.currentTimeMillis()), Calendar.DAY_OF_MONTH, -1));
    }

    /**
     * It is the default function to convert data to string.
     * 
     * @param date
     *            the date
     * 
     * @return the date string
     */
    public static String stringify(Date date) {
        return stringify(date, Calendar.DATE);
    }

    /**
     * Stringifies the given date.
     * <p>
     * Example:
     * 
     * <pre>
     * {@code
     * Dates.stringify(Timestamp.valueOf("2017-01-01 12:05:00")); // 2017-01-01
     * Dates.stringify(Timestamp.valueOf("2017-01-01 12:05:00"), Calendar.YEAR); // 2017
     * Dates.stringify(Timestamp.valueOf("2017-01-01 12:05:00"), Calendar.MONTH); // 2017-01
     * Dates.stringify(Timestamp.valueOf("2017-01-01 12:05:00"), Calendar.DATE); // 2017-01-01
     * Dates.stringify(Timestamp.valueOf("2017-01-01 12:05:00"), Calendar.HOUR); // 2017-01-01 12:00
     * Dates.stringify(Timestamp.valueOf("2017-01-01 12:05:00"), Calendar.MINUTE); // 2017-01-01 12:05
     * }
     * </pre>
     * 
     * @param date
     *            the date
     * 
     * @param field
     *            the calendar field
     * 
     * @return the date string
     * 
     * @see Calendar
     */
    public static String stringify(Date date, int field) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        switch (field) {
        case Calendar.YEAR:
            return String.format("%d", cal.get(Calendar.YEAR));
        case Calendar.MONTH:
            return String.format("%d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
        case Calendar.DATE:
            return String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.DAY_OF_MONTH));
        case Calendar.HOUR_OF_DAY:
            return String.format("%d-%02d-%02d %02d:00", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.HOUR_OF_DAY));
        case Calendar.MINUTE:
            return String.format("%d-%02d-%02d %02d:%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE));
        case Calendar.SECOND:
            return String.format("%d-%02d-%02d %02d:%02d:%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    cal.get(Calendar.SECOND));
        case Calendar.MILLISECOND:
            return String.format("%d-%02d-%02d %02d:%02d:%02d.%03d", cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    cal.get(Calendar.SECOND),
                    cal.get(Calendar.MILLISECOND));
        default:
            return String.format("%d-%02d-%02d %02d:00", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.HOUR_OF_DAY));
        }

    }

    /**
     * Increments the value by the given date.
     * <p>
     * Example:
     * 
     * <pre>
     * {@code
     * Dates.zero(Timestamp.valueOf("2017-01-01 12:05:00"), Calendar.HOUR, Calendar.MINUTE); // 2017-01-01 00:00:00
     * }
     * </pre>
     * 
     * @param date
     *            the date
     * 
     * @param field
     *            the calendar field
     * 
     * @param value
     *            the operating value, could be negative
     * 
     * @return the date after being operated
     */
    public static Date increment(Date date, int field, int value) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, value);
        return cal.getTime();
    }

    /**
     * Fills zero value to the fields of the given date.
     * 
     * @param date
     *            the date
     * 
     * @param fields
     *            the calendar fields
     * 
     * @return the zeroed date
     */
    public static Date zero(Date date, int... fields) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        for (int f : fields) {
            cal.set(f, 0);
        }
        return cal.getTime();
    }

    /**
     * Gets the calendar field value of the given date.
     * 
     * @param date
     *            the date
     * 
     * @param field
     *            the calendar field
     * 
     * @return the time field value
     */
    public static int get(Date date, int field) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(field);
    }

    /**
     * Calculates the difference value with two dates and calendar field.
     * 
     * @param base
     *            the base date
     * 
     * @param sub
     *            the other date
     * 
     * @param field
     *            the calendar field
     * 
     * @return the difference value
     */
    public static double diff(Date base, Date sub, int field) {
        double diff = base.getTime() - sub.getTime();
        switch (field) {
        case Calendar.HOUR:
        case Calendar.HOUR_OF_DAY:
            return diff / MILLIS_OF_HOUR;
        case Calendar.SECOND:
            return diff / MILLIS_OF_SECOND;
        case Calendar.MINUTE:
            return diff / MILLIS_OF_MINUTE;
        case Calendar.DAY_OF_MONTH:
            return diff / MILLIS_OF_DAY;
        case Calendar.MILLISECOND:
        default:
            return diff;
        }
    }

    private Dates() {

    }

}
