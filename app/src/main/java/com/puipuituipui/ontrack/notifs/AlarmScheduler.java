package com.puipuituipui.ontrack.notifs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmScheduler {
    public static String ALARM_DAILY_TYPE = "DAILY";

    public static PendingIntent createPendingIntent(Context context, String type) {
        Intent intent = new Intent(context.getApplicationContext(), AlarmReciever.class);
        intent.setType(type);

        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void setRepeatingAlarm(Context context, Calendar time, PendingIntent pendingIntent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                time.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent);
    }
}
