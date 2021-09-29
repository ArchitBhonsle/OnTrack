package com.puipuituipui.ontrack;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.puipuituipui.ontrack.habits.Habit;
import com.puipuituipui.ontrack.habits.HabitDao;

@Database(entities = {Habit.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HabitDao userDao();
}