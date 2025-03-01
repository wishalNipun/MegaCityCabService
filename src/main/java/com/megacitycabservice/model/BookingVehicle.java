package com.megacitycabservice.model;

import java.sql.Timestamp;

public class BookingVehicle {
    private int id;
    private int bookingId;
    private int vehicleId;
    private Timestamp assignedDate;

    public BookingVehicle() {
    }

    public BookingVehicle(int id, int bookingId, int vehicleId, Timestamp assignedDate) {
        this.id = id;
        this.bookingId = bookingId;
        this.vehicleId = vehicleId;
        this.assignedDate = assignedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Timestamp getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Timestamp assignedDate) {
        this.assignedDate = assignedDate;
    }
}
