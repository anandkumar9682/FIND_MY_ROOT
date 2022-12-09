package com.asuni.assignment.db.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.asuni.assignment.db.entity.EmpModal;

import java.util.List;


@androidx.room.Dao
public interface EmpDao {

    @Insert
    void insert(EmpModal model);

    @Update
    void update(EmpModal model);

    @Delete
    void delete(EmpModal model);

    @Query("DELETE FROM emp_table")
    void deleteAllCourses();

    @Query("SELECT * FROM emp_table ORDER BY id ASC")
    LiveData<List<EmpModal>> getAllCourses();

}

