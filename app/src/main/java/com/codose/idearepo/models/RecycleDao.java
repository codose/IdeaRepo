package com.codose.idearepo.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecycleDao {

    @Insert
    void insert(RecycleBin recycleBin);

    @Update
    void update(RecycleBin recycleBin);

    @Delete
    void delete(RecycleBin recycleBin);

    @Query("DELETE FROM recycle_bin")
    void deleteAllRecycled();

    @Query("SELECT * FROM recycle_bin ORDER BY id DESC")
    LiveData<List<RecycleBin>> getAllRecycled();
}
