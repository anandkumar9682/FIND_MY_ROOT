package com.asuni.assignment.db.util;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.asuni.assignment.db.DAO.LocDoa;
import com.asuni.assignment.db.entity.LocModel;


@Database(entities = { LocModel.class }, version = 1)
public abstract class LocDatabase extends RoomDatabase {


    private static LocDatabase instance;

    public abstract LocDoa Dao();

    public static synchronized LocDatabase getInstance(Context context) {

        if (instance == null) {

            instance =Room.databaseBuilder( context.getApplicationContext(), LocDatabase.class, "loc_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }


    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(LocDatabase instance) {
            LocDoa dao = instance.Dao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
