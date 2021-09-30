package com.puipuituipui.ontrack.todos;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

@Entity
public class Todo {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public boolean state;
    public String name;
    public String description;
    public Calendar due;
    public Calendar marked;

    public Todo(String name, String description, Calendar due) {
        this.state = false;
        this.name = name;
        this.description = description;
        this.due = due;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", state=" + state +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", due=" + due +
                ", marked=" + marked +
                '}';
    }
}
