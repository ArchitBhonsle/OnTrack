package com.puipuituipui.ontrack.todos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {
    @Query("SELECT * FROM todo")
    List<Todo> getAll();

    @Query("SELECT * FROM todo WHERE id IN (:ids)")
    List<Todo> loadAllByIds(int[] ids);

    @Insert
    List<Long> insertAll(Todo... todos);

    @Update
    int updateTodos(Todo... todos);

    @Delete
    void delete(Todo todo);
}
