package com.puipuituipui.ontrack.habits;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.puipuituipui.ontrack.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

@Entity
public class Habit {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public int streak;
    public String name;
    public String description;
    public Calendar lastMarked;

    public Habit(String name, String description) {
        this.name = name;
        this.streak = 0;
        this.description = description;
        this.lastMarked = null;
    }

    public boolean ping() {
        if (this.lastMarked == null) {
            Log.i("Habit:ping", "null case");
            Log.i("Habit:ping",this.toString());
            this.streak = 0;
            return false;
        }

        Calendar expected = (Calendar) this.lastMarked.clone();
        expected.add(Calendar.DATE, 1);
        Calendar today = Calendar.getInstance();

        Log.i("Habit:ping", String.valueOf(expected.compareTo(today)));
        Log.i("Habit:ping", Utils.formatCalendarLong(expected));
        Log.i("Habit:ping", Utils.formatCalendarLong(today));
        if (expected.compareTo(today) < 0) {
            Log.i("Habit:ping", "reset case");
            Log.i("Habit:ping",this.toString());
            this.streak = 0;
            return false;
        } else {
            Log.i("Habit:ping", "nothing case");
            Log.i("Habit:ping",this.toString());
            return true;
        }
    }

    public boolean mark() {
        if (this.lastMarked == null) {
            Log.i("Habit:mark", "null case");
            Log.i("Habit:mark",this.toString());
            this.streak = 1;
            this.setLastMarked(Calendar.getInstance());
            return false;
        }

        Calendar expected = (Calendar) this.lastMarked.clone();
        expected.add(Calendar.DATE, 1);
        Calendar today = Calendar.getInstance();
        this.setLastMarked(today);

        if (expected.compareTo(today) <= 0) {
            Log.i("Habit:mark", "on time case");
            Log.i("Habit:mark",this.toString());
            this.streak += 1;
            return true;
        } else {
            Log.i("Habit:mark", "reset case");
            Log.i("Habit:mark", this.toString());
            this.streak = 1;
            return false;
        }
    }

    public void setLastMarked(Calendar setTo) {
        setTo.set(Calendar.HOUR_OF_DAY, 23);
        setTo.set(Calendar.MINUTE, 59);
        setTo.set(Calendar.SECOND, 59);
        setTo.set(Calendar.MILLISECOND, 99);

        this.lastMarked = setTo;
    }

    @Override
    public String toString() {
        return "Habit{" +
                "id=" + id +
                ", streak=" + streak +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", lastMarked="
                + (lastMarked != null ? LocalDateTime.ofInstant(
                lastMarked.toInstant(),
                lastMarked.getTimeZone().toZoneId()
        ).toLocalDate().toString() : "null") +
                '}';
    }
}

