package com.puipuituipui.ontrack.reminders;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity
public class Reminder {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public boolean state;
    public String name;
    public String description;
    public Calendar time;
    public Calendar marked;

    public Reminder(String name, String description,Calendar time) {
        this.state = false;
        this.name = name;
        this.description = description;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "id=" + id +
                ", state=" + state +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", time=" + time +
                ", marked=" + marked +
                '}';
    }

}
