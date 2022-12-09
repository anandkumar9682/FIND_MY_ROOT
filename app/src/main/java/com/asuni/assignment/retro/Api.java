package com.asuni.assignment.retro;

import com.asuni.assignment.models.EmpResponse;

import retrofit2.Call;

import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "https://dummy.restapiexample.com/api/v1/";

    @GET("employees")
    Call<EmpResponse> getData();

}