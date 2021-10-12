package com.puipuituipui.ontrack.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.puipuituipui.ontrack.checkpoints.Checkpoint;
import com.puipuituipui.ontrack.checkpoints.CheckpointDao;
import com.puipuituipui.ontrack.habits.Habit;
import com.puipuituipui.ontrack.habits.HabitDao;
import com.puipuituipui.ontrack.reminders.Reminder;
import com.puipuituipui.ontrack.reminders.ReminderDao;
import com.puipuituipui.ontrack.todos.Todo;
import com.puipuituipui.ontrack.todos.TodoDao;

@Database(entities = {Habit.class, Todo.class, Checkpoint.class, Reminder.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract HabitDao habitDao();
    public abstract TodoDao todoDao();
    public abstract CheckpointDao checkpointDao();
    public abstract ReminderDao reminderDao();
}