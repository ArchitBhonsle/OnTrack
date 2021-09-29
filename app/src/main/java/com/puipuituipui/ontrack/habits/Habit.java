package com.puipuituipui.ontrack.habits;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Habit {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public int streak;
    public String name;
    public String description;

    public Habit(String name, String description) {
        this.name = name;
        this.streak = 0;
        this.description = description;
    }
}

