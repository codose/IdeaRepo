package com.codose.idearepo.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.codose.idearepo.models.Archive;
import com.codose.idearepo.models.RecycleBin;
import com.codose.idearepo.repository.ArchiveRepository;
import com.codose.idearepo.repository.RecycleRepository;

import java.util.List;

public class RecycleViewModel extends AndroidViewModel {
    private RecycleRepository repository;
    private LiveData<List<RecycleBin>> allRecycled;

    public RecycleViewModel(@NonNull Application application) {
        super(application);
        repository = new RecycleRepository(application);
        allRecycled = repository.getAllRecycled();
    }
    public void insert(RecycleBin recycleBin){
        repository.insert(recycleBin);
    }
    public void delete(RecycleBin recycleBin){
        repository.delete(recycleBin);
    }
    public void update(RecycleBin recycleBin){
        repository.update(recycleBin);
    }
    public void deleteAllRecycled(){
        repository.deleteAllRecycled();
    }
    public LiveData<List<RecycleBin>> getAllRecycled(){
        return allRecycled;
    }
}
