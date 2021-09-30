package com.puipuituipui.ontrack.todos;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Todo {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public boolean state;
    public String name;
    public String description;
    public Date due;

    public Todo(String name, String description, Date due) {
        this.state = false;
        this.name = name;
        this.description = description;
        this.due = due;
    }
}
