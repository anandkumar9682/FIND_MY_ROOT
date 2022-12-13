package com.asuni.assignment.viewmodels;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.asuni.assignment.db.entity.LocModel;
import com.asuni.assignment.db.repository.LocRepository;

import java.util.List;


public class MapViewModel extends AndroidViewModel {

    private LocRepository repository;

    private LiveData<List<LocModel>> allCourses;

    Application application;

    public MapViewModel(@NonNull Application application) {

        super(application);

        this.application = application;

        repository = new LocRepository(application);

        allCourses = repository.getAllLocsAsc();

    }

    public void insert(LocModel model) {
        repository.insert(model);
    }

    public void update(LocModel model) {
        repository.update(model);
    }

    public void delete(LocModel model) {
        repository.delete(model);
    }

    public void deleteAllCourses() {
        repository.deleteAllLocs();
    }

    public LiveData<List<LocModel>> getAllLocsAcs() {
        allCourses = repository.getAllLocsAsc();
        return allCourses;
    }

    public LiveData<List<LocModel>> getAllLocsDesc() {
        allCourses = repository.getAllLocsDesc();
        return allCourses;
    }


}
