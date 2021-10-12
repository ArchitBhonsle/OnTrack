package com.puipuituipui.ontrack.checkpoints;

import android.util.Log;

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
    public Calendar date;
    public Calendar marked;

    public Checkpoint(String name, String description, Calendar date) {
        this.state = false;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public boolean completed() {
        if(this.date == null) {
            Log.i("Checkpoint:completed", "null case");
            Log.i("Checkpoint:completed",this.toString());
            return false;
        }

        Calendar today = Calendar.getInstance();
        if (date.compareTo(today) < 0) {
            Log.i("Checkpoint:completed", "check case");
            Log.i("Checkpoint:completed", this.toString());
            this.state = true;
        } else {
            Log.i("Checkpoint:completed", "do nothing");
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Checkpoint{" +
                "id=" + id +
                ", state=" + state +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", marked=" + marked +
                '}';
    }
}
