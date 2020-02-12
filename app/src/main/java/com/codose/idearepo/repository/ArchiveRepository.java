package com.codose.idearepo.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.codose.idearepo.database.ArchiveDatabase;
import com.codose.idearepo.database.IdeaDatabase;
import com.codose.idearepo.models.Archive;
import com.codose.idearepo.models.ArchiveDao;
import com.codose.idearepo.models.Idea;
import com.codose.idearepo.models.IdeaDao;

import java.util.List;

public class ArchiveRepository {
    private ArchiveDao archiveDao;
    private LiveData<List<Archive>> allArchives;


    public ArchiveRepository(Application application){
        ArchiveDatabase database = ArchiveDatabase.getInstance(application);
        archiveDao = database.archiveDao();
        allArchives = archiveDao.getAllArchives();
    }
    public void insert(Archive archive){
        new InsertArchiveAsyncTask(archiveDao).execute(archive);
    }

    public void update(Archive archive){
        new UpdateArchiveAsyncTask(archiveDao).execute(archive);
    }

    public void delete(Archive archive){
        new DeleteArchiveAsyncTask(archiveDao).execute(archive);
    }

    public void deleteAllArchives(){
        new DeleteAllArchiveAsyncTask(archiveDao).execute();
    }

    public LiveData<List<Archive>> getAllArchives(){
        return allArchives;
    }

    private static class InsertArchiveAsyncTask extends AsyncTask<Archive, Void, Void>{
        private ArchiveDao archiveDao;

        private InsertArchiveAsyncTask(ArchiveDao archiveDao){
            this.archiveDao = archiveDao;
        }

        @Override
        protected Void doInBackground(Archive... archives) {
            archiveDao.insert(archives[0]);
            return null;
        }
    }
    private static class UpdateArchiveAsyncTask extends AsyncTask<Archive, Void, Void>{
        private ArchiveDao archiveDao;

        private UpdateArchiveAsyncTask(ArchiveDao archiveDao){
            this.archiveDao = archiveDao;
        }

        @Override
        protected Void doInBackground(Archive... archives) {
            archiveDao.update(archives[0]);
            return null;
        }
    }
    private static class DeleteArchiveAsyncTask extends AsyncTask<Archive, Void, Void>{
        private ArchiveDao archiveDao;

        private DeleteArchiveAsyncTask(ArchiveDao archiveDao){
            this.archiveDao = archiveDao;
        }

        @Override
        protected Void doInBackground(Archive... archives) {
            archiveDao.delete(archives[0]);
            return null;
        }
    }

    private static class DeleteAllArchiveAsyncTask extends AsyncTask<Void, Void, Void>{
        private ArchiveDao archiveDao;

        private DeleteAllArchiveAsyncTask(ArchiveDao archiveDao){
            this.archiveDao = archiveDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            archiveDao.deleteAllArchives();
            return null;
        }
    }
}
