package com.asuni.assignment.models;

import com.asuni.assignment.db.entity.EmpModal;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EmpResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private ArrayList<EmpModal> data;

    @SerializedName("message")
    private String message;

    public void setData(ArrayList<EmpModal> data) {
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<EmpModal> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

}
