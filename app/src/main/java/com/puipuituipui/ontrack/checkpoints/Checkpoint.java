package com.puipuituipui.ontrack.checkpoints;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity
public class Checkpoint {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public boolean state;
    public String name;
    public String description;
    public Calendar due;
    public Calendar marked;

    public Checkpoint(String name, String description, Calendar due) {
        this.state = false;
        this.name = name;
        this.description = description;
        this.due = due;
    }

    @Override
    public String toString() {
        return "Checkpoint{" +
                "id=" + id +
                ", state=" + state +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", due=" + due +
                ", marked=" + marked +
                '}';
    }
}
