package com.puipuituipui.ontrack.checkpoints;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Calendar;
import java.util.List;

@Dao
public interface CheckpointDao {
    @Query("SELECT * FROM checkpoint")
    List<Checkpoint> getAll();

    @Query("SELECT * FROM checkpoint WHERE date >= (:todayStart) AND date <= (:todayEnd)")
    List<Checkpoint> getOnDate(Calendar todayStart, Calendar todayEnd);

    @Query("SELECT * FROM checkpoint WHERE id IN (:ids)")
    List<Checkpoint> loadAllByIds(int[] ids);

    @Insert
    List<Long> insertAll(Checkpoint... checkpoints);

    @Update
    int updateCheckpoints(Checkpoint... checkpoints);

    @Delete
    void delete(Checkpoint checkpoint);

}
