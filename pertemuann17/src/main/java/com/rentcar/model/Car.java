package com.rentcar.model;

public class Car {
    private int id;
    private String plateNumber;
    private String brand;
    private String model;
    private int year;
    private double pricePerDay;
    private String status; // 'Available', 'Rented', 'Maintenance'

    public Car() {}

    public Car(int id, String plateNumber, String brand, String model, int year, double pricePerDay, String status) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.pricePerDay = pricePerDay;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getFullName() {
        return brand + " " + model + " (" + plateNumber + ")";
    }
}
