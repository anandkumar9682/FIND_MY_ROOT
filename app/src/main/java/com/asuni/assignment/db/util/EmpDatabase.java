package com.asuni.assignment.db.util;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.asuni.assignment.db.DAO.EmpDao;
import com.asuni.assignment.db.entity.EmpModal;


@Database(entities = { EmpModal.class }, version = 1)
public abstract class EmpDatabase extends RoomDatabase {


    private static EmpDatabase instance;

    public abstract EmpDao Dao();

    public static synchronized EmpDatabase getInstance(Context context) {

        if (instance == null) {

            instance =Room.databaseBuilder( context.getApplicationContext(),EmpDatabase.class, "employee_database")
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
        PopulateDbAsyncTask(EmpDatabase instance) {
            EmpDao dao = instance.Dao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
