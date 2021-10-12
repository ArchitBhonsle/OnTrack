package com.puipuituipui.ontrack;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {
    public static String formatCalendarDateShort(Calendar cal) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM");
        return formatter.format(cal.getTime());
    }

    public static String formatCalendarDate(Calendar cal) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(cal.getTime());
    }

    public static String formatCalendarLong(Calendar cal) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy, hh:mm aa");
        return formatter.format(cal.getTime());
    }

    public static String formatCalenderTime(Calendar cal) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa");
        return  formatter.format(cal.getTime());
    }

    public static Calendar todayEnd() {
        Calendar today = Calendar.getInstance();
        setToEnd(today);
        return today;
    }

    public static Calendar todayStart() {
        Calendar today = Calendar.getInstance();
        setToStart(today);
        return today;
    }

    public static void setToStart(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
    }

    public static void setToEnd(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 23);
        date.set(Calendar.MINUTE, 59);
        date.set(Calendar.SECOND, 59);
        date.set(Calendar.MILLISECOND, 99);
    }

    public static Calendar tomorrow() {
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, 1);
        return tomorrow;
    }
}
