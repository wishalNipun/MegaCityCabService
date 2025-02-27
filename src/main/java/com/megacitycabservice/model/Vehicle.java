package com.megacitycabservice.model;

import com.megacitycabservice.util.DateUtil;

import java.sql.Timestamp;
import java.util.Arrays;

public class Vehicle {
    private int id;
    private String vehicleType;
    private String model;
    private String plateNumber;
    private int driverId;
    private int numberOfPassengers;
    private double pricePerKm;
    private String status;
    private byte[] img;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private String base64Img;
    private Timestamp assignedDate;
    private int driver_id;

    public int getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id = driver_id;
    }

    public Vehicle() {
    }

    public Vehicle(int id, String vehicleType, String model, String plateNumber, int driverId, int numberOfPassengers, double pricePerKm, String status, byte[] img, Timestamp createdDate, Timestamp updatedDate) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.model = model;
        this.plateNumber = plateNumber;
        this.driverId = driverId;
        this.numberOfPassengers = numberOfPassengers;
        this.pricePerKm = pricePerKm;
        this.status = status;
        this.img = img;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public double getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(double pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
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

    public String getFormattedCreatedDate() {
        return DateUtil.formatDate(createdDate);
    }

    public String getFormattedUpdatedDate() {
        return DateUtil.formatDate(updatedDate);
    }

    public String getBase64Img() {
        return base64Img;
    }

    public void setBase64Img(String base64Img) {
        this.base64Img = base64Img;
    }

    public Timestamp getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Timestamp assignedDate) {
        this.assignedDate = assignedDate;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", vehicleType='" + vehicleType + '\'' +
                ", model='" + model + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", driverId=" + driverId +
                ", numberOfPassengers=" + numberOfPassengers +
                ", pricePerKm=" + pricePerKm +
                ", status='" + status + '\'' +
                ", img=" + Arrays.toString(img) +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
