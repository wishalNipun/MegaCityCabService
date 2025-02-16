package com.megacitycabservice.model;

import java.sql.Date;

public class Driver {
    private int id;
    private String name;
    private String nic;
    private Date dateOfBirth;
    private String address;
    private String licenseNumber;
    private String contactNumber;
    private String status;
    private Date createdDate;
    private Date updatedDate;

    public Driver() {
    }


    public Driver(int id, String name, String nic, Date dateOfBirth, String address,
                  String licenseNumber, String contactNumber, String status,
                  Date createdDate, Date updatedDate) {
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

    // Getters and Setters
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
