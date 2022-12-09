package com.asuni.assignment.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.asuni.assignment.db.DAO.EmpDao;
import com.asuni.assignment.db.entity.EmpModal;
import com.asuni.assignment.db.util.EmpDatabase;

import java.util.List;


public class EmpRepository {

    private EmpDao dao;
    private LiveData<List<EmpModal>> allCourses;


    public EmpRepository(Application application) {

        EmpDatabase database = EmpDatabase.getInstance(application);
        dao = database.Dao();
        allCourses = dao.getAllCourses();
    }


    public void insert(EmpModal model) {
        new InsertCourseAsyncTask(dao).execute(model);
    }

    public void update(EmpModal model) {
        new UpdateCourseAsyncTask(dao).execute(model);
    }

    public void delete(EmpModal model) {
        new DeleteCourseAsyncTask(dao).execute(model);
    }


    public void deleteAllCourses() {
        new DeleteAllCoursesAsyncTask(dao).execute();
    }


    public LiveData<List<EmpModal>> getAllCourses() {
        return allCourses;
    }

    private static class InsertCourseAsyncTask extends AsyncTask<EmpModal, Void, Void> {
        private EmpDao dao;

        private InsertCourseAsyncTask(EmpDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(EmpModal... model) {
            dao.insert(model[0]);
            return null;
        }
    }

    private static class UpdateCourseAsyncTask extends AsyncTask<EmpModal, Void, Void> {
        private EmpDao dao;

        private UpdateCourseAsyncTask(EmpDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(EmpModal... models) {
            dao.update(models[0]);
            return null;
        }
    }

    private static class DeleteCourseAsyncTask extends AsyncTask<EmpModal, Void, Void> {
        private EmpDao dao;

        private DeleteCourseAsyncTask(EmpDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(EmpModal... models) {
            dao.delete(models[0]);
            return null;
        }
    }


    private static class DeleteAllCoursesAsyncTask extends AsyncTask<Void, Void, Void> {

        private EmpDao dao;
        private DeleteAllCoursesAsyncTask(EmpDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllCourses();
            return null;
        }
    }

}
