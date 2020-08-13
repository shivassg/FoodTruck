package com.example.foodtruck;

import java.io.Serializable;

/**
 * POJO Class for FoodTruck Data
 * Implements Serializalble for sending the data to mapview
 */

public class FoodTruck implements Serializable , Comparable<FoodTruck>{

    private String foodTruckName;
    private String foodTruckAddress;
    private String foodTruckAdditionalText;
    private String startTime;
    private String endTime;
    private String dayOfWeek;
    private String latitude;
    private String longitude;

    public FoodTruck(String foodTruckName, String foodTruckAddress, String foodTruckAdditionalText, String startTime, String endTime, String dayOfWeek, String lat, String logt) {
        this.foodTruckName = foodTruckName;
        this.foodTruckAddress = foodTruckAddress;
        this.foodTruckAdditionalText = foodTruckAdditionalText;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.latitude = lat;
        this.longitude= logt;
    }

    public void setFoodTruckName(String foodTruckName) {
        this.foodTruckName = foodTruckName;
    }

    public void setFoodTruckAddress(String foodTruckAddress) {
        this.foodTruckAddress = foodTruckAddress;
    }

    public void setFoodTruckAdditionalText(String foodTruckAdditionalText) {
        this.foodTruckAdditionalText = foodTruckAdditionalText;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFoodTruckName() {
        return foodTruckName;
    }

    public String getFoodTruckAddress() {
        return foodTruckAddress;
    }

    public String getFoodTruckAdditionalText() {
        return foodTruckAdditionalText;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public int compareTo(FoodTruck foodTruckObj) {
        return this.foodTruckName.compareTo( foodTruckObj.foodTruckName );
    }
}
