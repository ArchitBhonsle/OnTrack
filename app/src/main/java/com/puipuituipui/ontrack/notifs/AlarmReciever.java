package com.puipuituipui.ontrack.notifs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (AlarmScheduler.ALARM_DAILY_TYPE.equals(intent.getType())) {
            NotificationsHelper.fireTodoNotifs(context);
            NotificationsHelper.fireHabitNotifs(context);
        }
    }
}
