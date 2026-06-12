package com.rentcar.model;

public class Customer {
    private int id;
    private String nik;
    private String name;
    private String phone;
    private String address;
    private String licenseNumber;

    public Customer() {}

    public Customer(int id, String nik, String name, String phone, String address, String licenseNumber) {
        this.id = id;
        this.nik = nik;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.licenseNumber = licenseNumber;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}
