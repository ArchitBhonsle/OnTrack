package com.puipuituipui.ontrack;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.puipuituipui.ontrack.todos.Todo;

import java.util.Calendar;
import java.util.List;

public class NotificationsHelper {
    public static String getChannelId(Context context) {
        return context.getPackageName() + "-notifs";
    }

    public static void createNotificationChannel(
            Context context,
            int importance,
            boolean showBadge,
            String description
    ) {
        String channelId = getChannelId(context);
        NotificationChannel notificationChannel = new NotificationChannel(channelId, "notifs", importance);
        notificationChannel.setDescription(description);
        notificationChannel.setShowBadge(showBadge);

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    public static void fireTodoNotifs(Context context) {
        AppDatabase db = Room.databaseBuilder(
                context.getApplicationContext(), AppDatabase.class, "db")
                .allowMainThreadQueries()
                .build();

        Calendar today = Utils.todayEnd();
        List<Todo> todos = db.todoDao().getOnDate(today);

        int todosColor = ContextCompat.getColor(context, R.color.todos);

        todos.forEach((todo) -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            Notification.Builder notifBuilder = new Notification.Builder(context, getChannelId(context))
                    .setSmallIcon(R.drawable.ic_baseline_check_circle_outline_24)
                    .setContentTitle(todo.name)
                    .setAutoCancel(false)
                    .setColor(todosColor)
                    .setContentIntent(pendingIntent);

            if (todo.description.length() != 0) notifBuilder.setContentText(todo.description);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(Math.toIntExact(todo.id), notifBuilder.build());
        });
    }
}
