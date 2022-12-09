package com.asuni.assignment.viewmodels;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.asuni.assignment.retro.RetrofitClient;
import com.asuni.assignment.db.entity.EmpModal;
import com.asuni.assignment.db.repository.EmpRepository;
import com.asuni.assignment.models.EmpResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class ViewModal extends AndroidViewModel {

    private EmpRepository repository;

    private LiveData<List<EmpModal>> allCourses;

    EmpResponse model;

    Application application;

    public ViewModal(@NonNull Application application) {

        super(application);

        this.application = application;

        repository = new EmpRepository(application);
        allCourses = repository.getAllCourses();

        retroFetch();



    }


    public void insert(EmpModal model) {
        repository.insert(model);
    }

    public void update(EmpModal model) {
        repository.update(model);
    }

    public void delete(EmpModal model) {
        repository.delete(model);
    }

    public void deleteAllCourses() {
        repository.deleteAllCourses();
    }

    public LiveData<List<EmpModal>> getAllCourses() {
        return allCourses;
    }


    public void retroFetch() {

        Call<EmpResponse> call = RetrofitClient.getInstance().getMyApi().getData();

        call.enqueue( new Callback<EmpResponse>() {
            @Override
            public void onResponse(Call<EmpResponse> call, retrofit2.Response<EmpResponse> response) {

                model= response.body();

                Toast.makeText( application , model.getMessage() , Toast.LENGTH_LONG).show();

                deleteAllCourses();

                try{

                    for( EmpModal modal : model.getData() )
                        insert( modal );

                }catch (Exception e){ }

            }

            @Override
            public void onFailure(Call<EmpResponse> call, Throwable t) {

                if( model != null )
                    Toast.makeText( application , model.getMessage() , Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText( application , "Server data fetching failed." , Toast.LENGTH_SHORT).show();

            }

        });

    }


}
