package com.dnajdrowski.architecturemvvmapp.viemodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dnajdrowski.architecturemvvmapp.model.Car;
import com.dnajdrowski.architecturemvvmapp.repository.CarRepository;

import java.util.List;

public class CarViewModel extends AndroidViewModel {

    private CarRepository carRepository;
    private LiveData<List<Car>> allCars;

    public CarViewModel(@NonNull Application application) {
        super(application);
        carRepository = new CarRepository(application);
        allCars = carRepository.getAllCarsOrderByMileage();
    }

    public void insert(Car car) {
        carRepository.insert(car);
    }

    public void update(Car car) {
        carRepository.update(car);
    }

    public void delete(Car car) {
        carRepository.delete(car);
    }

    public void deleteAllCars() {
        carRepository.deleteAllCars();
    }

    public LiveData<List<Car>> getAllCars() {
        return allCars;
    }


}
