package com.dnajdrowski.architecturemvvmapp.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.dnajdrowski.architecturemvvmapp.model.Car;

@Database(entities = Car.class, version = 1)
public abstract class CarDatabase extends RoomDatabase {

    private static CarDatabase instance;

    public abstract CarDao carDao();

    public static synchronized CarDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CarDatabase.class, "car_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private CarDao carDao;

        public PopulateDbAsyncTask(CarDatabase carDatabase) {
            this.carDao = carDatabase.carDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            carDao.insert(new Car("GA155EP",15434, "Opel","Astra"));
            carDao.insert(new Car("GA145EL",6334, "Mercedes","GLA"));
            carDao.insert(new Car("GD033EN",4334, "BWM","X5"));
            return null;
        }
    }
}
