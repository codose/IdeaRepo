package com.codose.idearepo.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ArchiveDao {

    @Insert
    void insert(Archive archive);

    @Update
    void update(Archive archive);

    @Delete
    void delete(Archive archive);

    @Query("DELETE FROM archive_table")
    void deleteAllArchives();

    @Query("SELECT * FROM archive_table ORDER BY id DESC")
    LiveData<List<Archive>> getAllArchives();
}
