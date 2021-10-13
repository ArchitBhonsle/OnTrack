package com.puipuituipui.ontrack.reminders;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.puipuituipui.ontrack.Utils;

import java.util.Calendar;

@Entity
public class Reminder {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public boolean state;
    public String name;
    public String description;
    public Calendar scheduled;
    public Calendar marked;

    public Reminder(String name, String description,Calendar scheduled) {
        this.state = false;
        this.name = name;
        this.description = description;
        this.scheduled = scheduled;
        this.marked = null;
    }

    public boolean completed() {
        if (this.scheduled == null) {
            Log.i("Reminder:time", "null case");
            Log.i("Reminder:time", Utils.formatCalendarLong(this.scheduled));
            this.state = true;
            return false;
        }

        Calendar now = Calendar.getInstance();
        if (scheduled.compareTo(now) <= 0) {
            Log.i("Reminder:completed", "deadline passed case");
            Log.i("Reminder:completed", Utils.formatCalendarLong(this.scheduled));
            this.state = true;
            return false;
        } else {
            Log.i("Reminder:not complete", "deadline not passed");
            this.state = false;
//            int differenceInHours = (int) ((time.getTimeInMillis() - now.getTimeInMillis()) / (1000 * 60 * 60)) ;
//            if (differenceInHours < 24) {
//                Log.i("Reminder:completed", "urgent case");
//            }
            return true;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "Reminder{" +
                "id=" + id +
                ", state=" + state +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", time=" + scheduled +
                ", marked=" + marked +
                '}';
    }
}
