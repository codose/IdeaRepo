package com.codose.idearepo.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "idea_table")
public class Idea {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private List<String> features;

    private String description;

    public Idea(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Idea(String title, String description, List<String> features) {
        this.title = title;
        this.features = features;
        this.description = description;
    }

    public List<String> getFeatures() {
        return features;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description;
    }
}
