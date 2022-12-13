package com.asuni.assignment.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.asuni.assignment.db.DAO.LocDoa;
import com.asuni.assignment.db.entity.LocModel;
import com.asuni.assignment.db.util.LocDatabase;

import java.util.List;


public class LocRepository {

    private LocDoa dao;
    private LiveData<List<LocModel>> allLocs;


    public LocRepository(Application application) {

        LocDatabase database = LocDatabase.getInstance(application);
        dao = database.Dao();
        allLocs = dao.getAllLocsAsc();
    }


    public void insert(LocModel model) {
        new InsertLocAsyncTask(dao).execute(model);
    }

    public void update(LocModel model) {
        new UpdateLocAsyncTask(dao).execute(model);
    }

    public void delete(LocModel model) {
        new DeleteLocAsyncTask(dao).execute(model);
    }


    public void deleteAllLocs() {
        new DeleteAllLocsAsyncTask(dao).execute();
    }


    public LiveData<List<LocModel>> getAllLocsAsc() {
        allLocs = dao.getAllLocsAsc();
        return allLocs;
    }

    public LiveData<List<LocModel>> getAllLocsDesc() {
        allLocs = dao.getAllLocsDesc();
        return allLocs;
    }

    public LiveData<List<LocModel>> getAllLocsByPriority() {
        allLocs = dao.getAllLocsByPriority();
        return allLocs;
    }

    private static class InsertLocAsyncTask extends AsyncTask<LocModel, Void, Void> {
        private LocDoa dao;

        private InsertLocAsyncTask(LocDoa dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(LocModel... model) {
            dao.insert(model[0]);
            return null;
        }
    }

    private static class UpdateLocAsyncTask extends AsyncTask<LocModel, Void, Void> {
        private LocDoa dao;

        private UpdateLocAsyncTask(LocDoa dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(LocModel... models) {
            dao.update(models[0]);
            return null;
        }
    }

    private static class DeleteLocAsyncTask extends AsyncTask<LocModel, Void, Void> {
        private LocDoa dao;

        private DeleteLocAsyncTask(LocDoa dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(LocModel... models) {
            dao.delete(models[0]);
            return null;
        }
    }


    private static class DeleteAllLocsAsyncTask extends AsyncTask<Void, Void, Void> {

        private LocDoa dao;
        private DeleteAllLocsAsyncTask(LocDoa dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllLocs();
            return null;
        }
    }

}
