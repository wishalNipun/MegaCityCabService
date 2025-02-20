package com.megacitycabservice.model;

import com.megacitycabservice.util.DateUtil;

import java.sql.Date;
import java.sql.Timestamp;

public class Driver {
    private int id;
    private String name;
    private String nic;
    private Date dateOfBirth;
    private String address;
    private String licenseNumber;
    private String contactNumber;
    private String status;
    private Timestamp createdDate;
    private Timestamp updatedDate;

    public Driver() {
    }

    public Driver(int id, String name, String nic, Date dateOfBirth, String address, String licenseNumber, String contactNumber, String status, Timestamp createdDate, Timestamp updatedDate) {
        this.id = id;
        this.name = name;
        this.nic = nic;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.licenseNumber = licenseNumber;
        this.contactNumber = contactNumber;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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

    public String getFormattedCreatedDate() {
        return DateUtil.formatDate(createdDate);
    }

    public String getFormattedUpdatedDate() {
        return DateUtil.formatDate(updatedDate);
    }
}
