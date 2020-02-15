package com.codose.idearepo.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.codose.idearepo.database.RecycleDatabase;
import com.codose.idearepo.models.RecycleBin;
import com.codose.idearepo.models.RecycleDao;

import java.util.List;

public class RecycleRepository {
    private RecycleDao recycleDao;
    private LiveData<List<RecycleBin>> allRecycled;


    public RecycleRepository(Application application){
        RecycleDatabase database = RecycleDatabase.getInstance(application);
        recycleDao = database.recycleDao();
        allRecycled = recycleDao.getAllRecycled();
    }
    public void insert(RecycleBin recycleBin){
        new InsertRecycleAsyncTask(recycleDao).execute(recycleBin);
    }

    public void update(RecycleBin recycleBin){
        new UpdateRecycleAsyncTask(recycleDao).execute(recycleBin);
    }

    public void delete(RecycleBin recycleBin){
        new DeleteRecycleAsyncTask(recycleDao).execute(recycleBin);
    }

    public void deleteAllRecycled(){
        new DeleteAllRecycleAsyncTask(recycleDao).execute();
    }

    public LiveData<List<RecycleBin>> getAllRecycled(){
        return allRecycled;
    }

    private static class InsertRecycleAsyncTask extends AsyncTask<RecycleBin, Void, Void>{
        private RecycleDao recycleDao;

        private InsertRecycleAsyncTask(RecycleDao recycleDao){
            this.recycleDao = recycleDao;
        }

        @Override
        protected Void doInBackground(RecycleBin... recycleBins) {
            recycleDao.insert(recycleBins[0]);
            return null;
        }
    }
    private static class UpdateRecycleAsyncTask extends AsyncTask<RecycleBin, Void, Void>{
        private RecycleDao recycleDao;

        private UpdateRecycleAsyncTask(RecycleDao recycleDao){
            this.recycleDao = recycleDao;
        }

        @Override
        protected Void doInBackground(RecycleBin... recycleBins) {
            recycleDao.update(recycleBins[0]);
            return null;
        }
    }
    private static class DeleteRecycleAsyncTask extends AsyncTask<RecycleBin, Void, Void>{
        private RecycleDao recycleDao;

        private DeleteRecycleAsyncTask(RecycleDao recycleDao){
            this.recycleDao = recycleDao;
        }

        @Override
        protected Void doInBackground(RecycleBin... recycleBins) {
            recycleDao.delete(recycleBins[0]);
            return null;
        }
    }

    private static class DeleteAllRecycleAsyncTask extends AsyncTask<Void, Void, Void>{
        private RecycleDao recycleDao;

        private DeleteAllRecycleAsyncTask(RecycleDao recycleDao){
            this.recycleDao = recycleDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            recycleDao.deleteAllRecycled();
            return null;
        }
    }
}
