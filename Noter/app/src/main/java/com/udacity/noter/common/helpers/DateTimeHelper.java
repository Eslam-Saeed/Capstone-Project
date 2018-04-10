package com.udacity.noter.common.helpers;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateUtils;
import com.udacity.noter.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by eslam on 10/19/2017.
 */
public class DateTimeHelper {
    //2018-04-10 02:46:55
    public static final String MM_DD_YYYY = "mm/dd/yyyy";
    public static final String FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String YYYY_MM_DD = "yyyy-mm-dd";
    public static final String YYYY_MMM_DD = "dd MMM yyyy";
    public static final String SERVER_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String formatDate(String oldFormat, String newFormat, String dateStr) {
        if (TextUtils.isEmpty(dateStr)) {
            return "";
        }
        SimpleDateFormat fmt = new SimpleDateFormat(oldFormat, Locale.ENGLISH);
        Date date;
        try {
            date = fmt.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr;
        }
        SimpleDateFormat fmtOut = new SimpleDateFormat(newFormat, Locale.ENGLISH);
        return fmtOut.format(date);
    }

    public static String formatDate(String oldFormat, String newFormat, String dateStr, Locale oldLocale, Locale newLocale) {
        if (oldLocale == null) {
            return formatDate(oldFormat, newFormat, dateStr);
        }
        if (TextUtils.isEmpty(dateStr)) {
            return "";
        }
        SimpleDateFormat fmt = new SimpleDateFormat(oldFormat, oldLocale);
        Date date;
        try {
            date = fmt.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr;
        }
        SimpleDateFormat fmtOut = new SimpleDateFormat(newFormat, newLocale);
        return fmtOut.format(date);
    }

    public static String convertDateToString(Date date, String format) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(format, Locale.ENGLISH);
        return dateFormatter.format(date);
    }

    public static String convertDateToStringForServer(Date date, String format) {
        Locale localeObject = Locale.ENGLISH;
        SimpleDateFormat dateFormatter = new SimpleDateFormat(format, localeObject);
        return dateFormatter.format(date);
    }

    public static String timestampToDateString(long milliSeconds, String dateFormat) {
        try {
            // Create a DateFormatter object for displaying date in specified format.
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

            // Create a calendar object that will convert the date and time value in milliseconds to date.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSeconds);
            return formatter.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Date convertStringToDate(String oldFormat, String dateStr, Locale oldLocale) {
        if (oldLocale == null) {
            oldLocale = Locale.ENGLISH;
        }

        if (TextUtils.isEmpty(dateStr)) {
            return null;
        }
        SimpleDateFormat fmt = new SimpleDateFormat(oldFormat, oldLocale);
        fmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            return fmt.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean isToday(Date date) {
        Calendar otherCalendar = Calendar.getInstance();
        otherCalendar.setTime(date);
        return Calendar.getInstance().get(Calendar.DATE) == otherCalendar.get(Calendar.DATE);
    }

    public static boolean isSameYear(Date date) {
        Calendar otherCalendar = Calendar.getInstance();
        otherCalendar.setTime(date);
        return Calendar.getInstance().get(Calendar.YEAR) == otherCalendar.get(Calendar.YEAR);
    }

    public static String getRelativeTime(Context context, String dateStr, String oldFormat, Locale oldLocale, String newFormat) {
        try {
            if (oldLocale == null) {
                oldLocale = Locale.ENGLISH;
            }
            Date date = convertStringToDate(oldFormat, dateStr, oldLocale);
            if (date == null)
                return dateStr;
            if (date.compareTo(new Date()) > -1) {
                return context.getString(R.string.now);
            }
            if (isToday(date)) {
                return DateUtils.getRelativeTimeSpanString(date.getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS).toString();
            } else {
                return convertDateToString(date, newFormat);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return dateStr;
        }
    }

}
