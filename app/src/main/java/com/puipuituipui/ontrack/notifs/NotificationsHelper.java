package com.puipuituipui.ontrack.notifs;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.puipuituipui.ontrack.checkpoints.Checkpoint;
import com.puipuituipui.ontrack.database.AppDatabase;
import com.puipuituipui.ontrack.MainActivity;
import com.puipuituipui.ontrack.R;
import com.puipuituipui.ontrack.Utils;
import com.puipuituipui.ontrack.habits.Habit;
import com.puipuituipui.ontrack.todos.Todo;

import java.util.Calendar;
import java.util.List;

public class NotificationsHelper {
    public static String NOTIFICATION_CHANNEL_TODO = "Todos";
    public static String NOTIFICATION_CHANNEL_DESC_TODO = "Notification channel for all your todos";
    public static String NOTIFICATION_CHANNEL_HABIT = "Habits";
    public static String NOTIFICATION_CHANNEL_DESC_HABIT = "Notification channel for all your habits";
    public static String NOTIFICATION_CHANNEL_REMINDER = "Reminders";
    public static String NOTIFICATION_CHANNEL_DESC_REMINDER = "Notification channel for all your reminders";
    public static String NOTIFICATION_CHANNEL_CHECKPOINT = "Checkpoints";
    public static String NOTIFICATION_CHANNEL_DESC_CHECKPOINT = "Notification channel for all your checkpoints";

    public static String getChannelId(Context context, String name) {
        return context.getPackageName() + "-" + name;
    }

    public static void createNotificationChannel(
            Context context,
            int importance,
            boolean showBadge,
            String name,
            String description
    ) {
        String channelId = getChannelId(context, name);
        NotificationChannel notificationChannel = new NotificationChannel(channelId, name, importance);
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

            Notification.Builder notifBuilder = new Notification.Builder(
                    context,
                    getChannelId(context, NOTIFICATION_CHANNEL_TODO))
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

    public static void fireHabitNotifs(Context context) {
        AppDatabase db = Room.databaseBuilder(
                context.getApplicationContext(), AppDatabase.class, "db")
                .allowMainThreadQueries()
                .build();

        Calendar today = Utils.todayStart();
        List<Habit> habits = db.habitDao().getMarkedBefore(today);

        int habitsColor = ContextCompat.getColor(context, R.color.habits);

        habits.forEach((habit) -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            Notification.Builder notifBuilder = new Notification.Builder(
                    context,
                    getChannelId(context, NOTIFICATION_CHANNEL_HABIT))
                    .setSmallIcon(R.drawable.ic_baseline_loop_24)
                    .setContentTitle(habit.name)
                    .setAutoCancel(false)
                    .setColor(habitsColor)
                    .setContentIntent(pendingIntent);

            if (habit.description.length() != 0) notifBuilder.setContentText(habit.description);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(Math.toIntExact(habit.id), notifBuilder.build());
        });
    }

    public static void fireCheckpointNotifs(Context context) {
        AppDatabase db = Room.databaseBuilder(
                context.getApplicationContext(), AppDatabase.class, "db")
                .allowMainThreadQueries()
                .build();

        Calendar todayStart = Utils.todayStart();
        Calendar todayEnd = Utils.todayEnd();
        List<Checkpoint> checkpoints = db.checkpointDao().getOnDate(todayStart, todayEnd);
        Log.i("NotificationsHelper:fireCheckpointNotifs", checkpoints.toString());

        int checkpointsColor = ContextCompat.getColor(context, R.color.checkpoints);

        checkpoints.forEach(checkpoint -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            Notification.Builder notifBuilder = new Notification.Builder(
                    context,
                    getChannelId(context, NOTIFICATION_CHANNEL_CHECKPOINT))
                    .setSmallIcon(R.drawable.ic_baseline_outlined_flag_24)
                    .setContentTitle(checkpoint.name)
                    .setAutoCancel(false)
                    .setColor(checkpointsColor)
                    .setContentIntent(pendingIntent);

            if (checkpoint.description.length() != 0) notifBuilder.setContentText(checkpoint.description);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(Math.toIntExact(checkpoint.id), notifBuilder.build());
        });
    }

}
