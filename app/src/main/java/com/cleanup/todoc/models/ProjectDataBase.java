package com.cleanup.todoc.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ProjectDataBase {
    @PrimaryKey
    private final long id;
    private final String name;
    private final int color;

    public ProjectDataBase(long id, String name, int color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public int getColor() { return color; }

}
