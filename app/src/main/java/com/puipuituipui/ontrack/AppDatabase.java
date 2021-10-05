package com.puipuituipui.ontrack;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.puipuituipui.ontrack.checkpoints.Checkpoint;
import com.puipuituipui.ontrack.checkpoints.CheckpointDao;
import com.puipuituipui.ontrack.habits.Habit;
import com.puipuituipui.ontrack.habits.HabitDao;
import com.puipuituipui.ontrack.todos.Todo;
import com.puipuituipui.ontrack.todos.TodoDao;

@Database(entities = {Habit.class, Todo.class, Checkpoint.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract HabitDao habitDao();
    public abstract TodoDao todoDao();
    public abstract CheckpointDao checkpointDao();
}