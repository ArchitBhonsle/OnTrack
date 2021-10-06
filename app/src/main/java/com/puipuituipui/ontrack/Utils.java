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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy, hh:mm:ss");
        return formatter.format(cal.getTime());
    }

    public static String formatCalenderTime(Calendar cal) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        return  formatter.format(cal.getTime());
    }
}
