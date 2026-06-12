package com.rentcar.model;

public class Rental {
    private int id;
    private int carId;
    private int customerId;
    private String rentDate; // YYYY-MM-DD
    private String dueDate;  // YYYY-MM-DD
    private String returnDate; // YYYY-MM-DD, nullable
    private double pricePerDay;
    private double totalPrice;
    private double fine;
    private String status; // 'Rented', 'Returned'
    
    // Joint Object Associations for easy display
    private Car car;
    private Customer customer;

    public Rental() {}

    public Rental(int id, int carId, int customerId, String rentDate, String dueDate, String returnDate, 
                  double pricePerDay, double totalPrice, double fine, String status) {
        this.id = id;
        this.carId = carId;
        this.customerId = customerId;
        this.rentDate = rentDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.pricePerDay = pricePerDay;
        this.totalPrice = totalPrice;
        this.fine = fine;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
