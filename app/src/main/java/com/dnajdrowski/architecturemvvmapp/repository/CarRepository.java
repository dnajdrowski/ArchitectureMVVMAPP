package com.dnajdrowski.architecturemvvmapp.repository;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.dnajdrowski.architecturemvvmapp.model.Car;

import java.util.List;

import timber.log.Timber;

public class CarRepository {
    private CarDao carDao;
    private LiveData<List<Car>> allCars;

    public CarRepository(Application application) {
        CarDatabase carDatabase = CarDatabase.getInstance(application);
        carDao = carDatabase.carDao();
        allCars = carDao.getAllCarsSortedByMileage();
    }

    public void insert(Car car) {
        new InsertCarAsyncTask(carDao).execute(car);
    }

    public void update(Car car) {
        new UpdateCarAsyncTask(carDao).execute(car);
    }

    public void delete(Car car) {
        new DeleteCarAsyncTask(carDao).execute(car);
    }

    public void deleteAllCars() {
        new DeleteAllCarsCarAsyncTask(carDao).execute();
    }

    public LiveData<List<Car>> getAllCarsOrderByMileage() {
        return allCars;
    }

    private static class InsertCarAsyncTask extends AsyncTask<Car, Void, Void> {
        private CarDao carDao;

        private InsertCarAsyncTask(CarDao carDao) {
            this.carDao = carDao;
        }
        @Override
        protected Void doInBackground(Car... cars) {
            carDao.insert(cars[0]);
            return null;
        }
    }

    private static class UpdateCarAsyncTask extends AsyncTask<Car, Void, Void> {
        private CarDao carDao;

        private UpdateCarAsyncTask(CarDao carDao) {
            this.carDao = carDao;
        }
        @Override
        protected Void doInBackground(Car... cars) {
            carDao.update(cars[0]);
            return null;
        }
    }

    private static class DeleteCarAsyncTask extends AsyncTask<Car, Void, Void> {
        private CarDao carDao;

        private DeleteCarAsyncTask(CarDao carDao) {
            this.carDao = carDao;
        }
        @Override
        protected Void doInBackground(Car... cars) {
            carDao.delete(cars[0]);
            return null;
        }
    }

    private static class DeleteAllCarsCarAsyncTask extends AsyncTask<Car, Void, Void> {
        private CarDao carDao;

        private DeleteAllCarsCarAsyncTask(CarDao carDao) {
            this.carDao = carDao;
        }
        @Override
        protected Void doInBackground(Car... cars) {
            carDao.deleteAllCars();
            return null;
        }
    }
}
