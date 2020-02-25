package com.codose.idearepo.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.codose.idearepo.models.RecycleBin;
import com.codose.idearepo.models.RecycleDao;

@Database(entities = {RecycleBin.class}, version = 2)
public abstract class RecycleDatabase extends RoomDatabase {

    private static RecycleDatabase instance;

    public abstract RecycleDao recycleDao();

    public static synchronized RecycleDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    RecycleDatabase.class, "recycled_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static Callback roomCallback = new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateAsyncTask(instance).execute();
        }
    };

    private static class PopulateAsyncTask extends AsyncTask<Void, Void, Void> {
        private RecycleDao recycleDao;

        private PopulateAsyncTask(RecycleDatabase db){
            recycleDao = db.recycleDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
