package com.asuni.assignment.db.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.asuni.assignment.db.entity.LocModel;

import java.util.List;


@androidx.room.Dao
public interface LocDoa {

    @Insert
    void insert(LocModel model);

    @Update
    void update(LocModel model);

    @Delete
    void delete(LocModel model);

    @Query("DELETE FROM loc_table")
    void deleteAllLocs();

    @Query("SELECT * FROM loc_table ORDER BY name ASC")
    LiveData<List<LocModel>> getAllLocsAsc();

    @Query("SELECT * FROM loc_table ORDER BY name DESC")
    LiveData<List<LocModel>> getAllLocsDesc();

    @Query("SELECT * FROM loc_table ORDER BY prioriry ASC")
    LiveData<List<LocModel>> getAllLocsByPriority();


}

