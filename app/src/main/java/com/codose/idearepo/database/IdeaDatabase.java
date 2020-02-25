package com.codose.idearepo.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.codose.idearepo.models.Idea;
import com.codose.idearepo.models.IdeaDao;


@Database(entities = {Idea.class}, version = 2)
public abstract class IdeaDatabase extends RoomDatabase {

    private static IdeaDatabase instance;

    public abstract IdeaDao ideaDao();

    public static synchronized IdeaDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    IdeaDatabase.class, "idea_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateAsyncTask(instance).execute();
        }
    };

    private static class PopulateAsyncTask extends AsyncTask<Void, Void, Void> {
        private IdeaDao ideaDao;

        private PopulateAsyncTask(IdeaDatabase db){
            ideaDao = db.ideaDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
