package com.codose.idearepo.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.codose.idearepo.models.Archive;
import com.codose.idearepo.models.ArchiveDao;
import com.codose.idearepo.models.Idea;
import com.codose.idearepo.models.IdeaDao;

@Database(entities = {Archive.class}, version = 2)
public abstract class ArchiveDatabase extends RoomDatabase {

    private static ArchiveDatabase instance;

    public abstract ArchiveDao archiveDao();

    public static synchronized ArchiveDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ArchiveDatabase.class, "archive_database")
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
        private ArchiveDao archiveDao;

        private PopulateAsyncTask(ArchiveDatabase db){
            archiveDao = db.archiveDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
