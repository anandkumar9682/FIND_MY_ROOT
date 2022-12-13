
package com.asuni.assignment.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "loc_table")
public class LocModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private int prioriry;

    private String address;

    private double distance;

    private String lat;

    private String log;



    public LocModel( String name, double distance, String address,int prioriry, String lat, String log  ) {

        this.id=id;
        this.name=name;
        this.address=address;
        this.distance=distance;
        this.prioriry=prioriry;
        this.lat=lat;
        this.log=log;
    }

    public LocModel(){

    }

    public void setPrioriry(int prioriry) {
        this.prioriry = prioriry;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getLat() {
        return lat;
    }

    public String getLog() {
        return log;
    }

    public String getName() {
        return name;
    }

    public int getPrioriry() {
        return prioriry;
    }
}