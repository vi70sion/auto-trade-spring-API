package com.example.myapi.demo.model;

import java.math.BigDecimal;

public class CarAd {
    private int adId, clientId;
    private String name, make, model, description;
    private int year, mileage;
    private BigDecimal price;
    private byte[] photo;

    public CarAd() {
    }

    //public CarAd(int adId, int clientId, String name, String make, String model, int year, BigDecimal price, int mileage, String description, byte[] photo) {
    public CarAd(int adId, int clientId, String name, String make, String model, int year, BigDecimal price, int mileage, String description) {
        this.adId = adId;
        this.clientId = clientId;
        this.name = name;
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
        this.mileage = mileage;
        this.description = description;
        //this.photo = photo;
    }

    public int getAdId() { return adId; }
    public int getClientId() { return clientId; }
    public String getName() { return name; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public String getDescription() { return description; }
    public int getYear() { return year; }
    public int getMileage() { return mileage; }
    public BigDecimal getPrice() { return price; }
    public byte[] getPhoto() { return photo; }
    public void setPhoto(byte[] photo) { this.photo = photo; }


}
