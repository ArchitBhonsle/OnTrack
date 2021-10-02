package com.puipuituipui.ontrack.habits;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HabitDao {
    @Query("SELECT * FROM habit")
    List<Habit> getAll();

    @Query("SELECT * FROM habit WHERE id IN (:ids)")
    List<Habit> loadAllByIds(int[] ids);

    @Insert
    List<Long> insertAll(Habit... habits);

    @Update
    int updateHabits(Habit... habits);

    @Delete
    void delete(Habit habit);
}
