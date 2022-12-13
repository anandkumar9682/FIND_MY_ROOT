package com.asuni.assignment.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.asuni.assignment.db.entity.LocModel;
import com.asuni.assignment.db.repository.LocRepository;

import java.util.List;


public class ViewModal extends AndroidViewModel {

    private LocRepository repository;

    private LiveData<List<LocModel>> allLocs;

    Application application;

    public ViewModal(@NonNull Application application) {

        super(application);

        this.application = application;

        repository = new LocRepository(application);
        allLocs = repository.getAllLocsAsc();

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

    public void deleteAllLocs() {
        repository.deleteAllLocs();
    }

    public LiveData<List<LocModel>> getAllLocsAsc() {
        allLocs = repository.getAllLocsAsc();
        return allLocs;
    }
    public LiveData<List<LocModel>> getAllLocsDesc() {
        allLocs = repository.getAllLocsDesc();
        return allLocs;
    }

    public LiveData<List<LocModel>> getAllLocsByPriority() {
        allLocs = repository.getAllLocsByPriority();
        return allLocs;
    }




}
