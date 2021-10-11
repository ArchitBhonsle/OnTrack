package com.puipuituipui.ontrack.reminders;

import android.util.Log;

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

    public boolean completed() {
        if (this.time == null) {
            Log.i("Reminder:time", "null case");
            Log.i("Reminder:time",this.toString());
            this.state = true;
            return false;
        }

        Calendar now = Calendar.getInstance();
        if (time.compareTo(now) <= 0) {
            Log.i("Reminder:completed", "deadline passed case");
            Log.i("Reminder:completed", this.toString());
            this.state = true;
            return false;
        } else {
            Log.i("Reminder:completed", "Safe case");
//            int differenceInHours = (int) ((time.getTimeInMillis() - now.getTimeInMillis()) / (1000 * 60 * 60)) ;
//            if (differenceInHours < 24) {
//                Log.i("Reminder:completed", "urgent case");
//            }
            return true;
        }
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
