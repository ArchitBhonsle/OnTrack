package com.puipuituipui.ontrack.habits;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HabitDao {
    @Query("SELECT * FROM habit ORDER BY lastMarked")
    List<Habit> getAll();

    @Insert
    List<Long> insertAll(Habit... habits);

    @Update
    int updateHabits(Habit... habits);

    @Delete
    void delete(Habit habit);
}
