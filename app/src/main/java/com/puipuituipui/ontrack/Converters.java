package com.puipuituipui.ontrack;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class Converters {
    @TypeConverter
    public static Calendar fromTimestamp(Long value) {
        if (value == null) return null;

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(value);
        return cal;
    }

    @TypeConverter
    public static Long dateToTimestamp(Calendar cal) {
        return cal == null ? null: cal.getTime().getTime();
    }
}
