package com.megacitycabservice.model;

import java.sql.Timestamp;

public class Booking {
    private int id;
    private String bookingNumber;
    private String customerId;
    private String pickupLocation;
    private String dropLocation;
    private double distanceKm;
    private double fee;
    private String status;
    private Timestamp createdDate;
    private Timestamp updatedDate;

    public Booking() {
    }

    public Booking(int id, String bookingNumber, String customerId, String pickupLocation, String dropLocation, double distanceKm, double fee, String status, Timestamp createdDate, Timestamp updatedDate) {
        this.id = id;
        this.bookingNumber = bookingNumber;
        this.customerId = customerId;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.distanceKm = distanceKm;
        this.fee = fee;
        this.status = status;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }
}
