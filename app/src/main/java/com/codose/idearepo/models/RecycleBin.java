package com.codose.idearepo.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "recycle_bin")
public class RecycleBin {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String colorId;

    private String title;


    private String description;

    public RecycleBin(String title, String description, String colorId) {
        this.title = title;
        this.description = description;
        this.colorId = colorId;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }
}
