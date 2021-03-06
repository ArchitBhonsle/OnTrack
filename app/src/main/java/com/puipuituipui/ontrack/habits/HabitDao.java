package com.puipuituipui.ontrack.habits;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Calendar;
import java.util.List;

@Dao
public interface HabitDao {
    @Query("SELECT * FROM habit ORDER BY lastMarked")
    List<Habit> getAll();

    @Query("SELECT * FROM habit WHERE lastMarked < (:cal)")
    List<Habit> getMarkedBefore(Calendar cal);

    @Insert
    List<Long> insertAll(Habit... habits);

    @Update
    int updateHabits(Habit... habits);

    @Delete
    void delete(Habit habit);
}
