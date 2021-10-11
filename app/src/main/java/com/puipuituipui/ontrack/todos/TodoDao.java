package com.puipuituipui.ontrack.todos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Calendar;
import java.util.List;

@Dao
public interface TodoDao {
    @Query("SELECT * FROM todo ORDER BY state, due")
    List<Todo> getAll();

    @Query("SELECT * FROM todo WHERE (NOT state) AND due <= (:today)")
    List<Todo> getOnDate(Calendar today);

    @Insert
    List<Long> insertAll(Todo... todos);

    @Update
    int updateTodos(Todo... todos);

    @Delete
    void delete(Todo todo);
}
