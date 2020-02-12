package com.codose.idearepo.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.codose.idearepo.models.Archive;
import com.codose.idearepo.models.Idea;
import com.codose.idearepo.repository.ArchiveRepository;
import com.codose.idearepo.repository.IdeaRepository;

import java.util.List;

public class ArchiveViewModel extends AndroidViewModel {
    private ArchiveRepository repository;
    private LiveData<List<Archive>> allArchives;

    public ArchiveViewModel(@NonNull Application application) {
        super(application);
        repository = new ArchiveRepository(application);
        allArchives = repository.getAllArchives();
    }
    public void insert(Archive archive){
        repository.insert(archive);
    }
    public void delete(Archive archive){
        repository.delete(archive);
    }
    public void update(Archive archive){
        repository.update(archive);
    }
    public void deleteAllArchives(){
        repository.deleteAllArchives();
    }
    public LiveData<List<Archive>> getAllArchives(){
        return allArchives;
    }
}
