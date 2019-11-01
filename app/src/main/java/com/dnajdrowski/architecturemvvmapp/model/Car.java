package com.dnajdrowski.architecturemvvmapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Entity(tableName = "cars")
public class Car {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String registrationNumber;
    private int mileage;
    private String brand;
    private String model;

    public Car(String registrationNumber, int mileage, String brand, String model) {
        this.registrationNumber = registrationNumber;
        this.mileage = mileage;
        this.brand = brand;
        this.model = model;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public int getMileage() {
        return mileage;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }
}
