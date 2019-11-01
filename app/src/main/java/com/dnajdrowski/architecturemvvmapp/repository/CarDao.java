package com.dnajdrowski.architecturemvvmapp.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dnajdrowski.architecturemvvmapp.model.Car;

import java.util.List;

@Dao
public interface CarDao {

    @Insert
    void insert(Car car);

    @Update
    void update(Car car);

    @Delete
    void delete(Car car);

    @Query("DELETE FROM cars")
    void deleteAllCars();

    @Query("SELECT * FROM cars ORDER BY mileage DESC")
    LiveData<List<Car>> getAllCarsSortedByMileage();
}
