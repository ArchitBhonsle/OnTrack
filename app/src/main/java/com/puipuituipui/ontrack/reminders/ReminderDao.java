package com.puipuituipui.ontrack.reminders;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.puipuituipui.ontrack.todos.Todo;

import java.util.List;

@Dao
public interface ReminderDao {
    @Query("SELECT * FROM reminder")
    List<Reminder> getAll();

    @Query("SELECT * FROM reminder WHERE id IN (:ids)")
    List<Reminder> loadAllByIds(int[] ids);

    @Insert
    List<Long> insertAll(Reminder... reminders);

    @Update
    int updateReminders(Reminder... reminders);

    @Delete
    void delete(Reminder reminder);
}
