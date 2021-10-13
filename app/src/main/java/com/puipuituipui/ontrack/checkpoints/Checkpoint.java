package com.puipuituipui.ontrack.checkpoints;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.puipuituipui.ontrack.Utils;

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
            Log.i("Checkpoint:completed",Utils.formatCalendarLong(this.date));
            return false;
        }

        Calendar today = Calendar.getInstance();
        if (date.compareTo(today) < 0) {
            Log.i("Checkpoint:completed", "date passed case");
            Log.i("Checkpoint:completed", Utils.formatCalendarLong(this.date));
            this.state = true;
            return true;
        } else {
            Log.i("Checkpoint:not complete", "date not passed case");
            this.state = false;
            return false;
        }
    }

    @NonNull
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
