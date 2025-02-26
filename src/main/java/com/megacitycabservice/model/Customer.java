package com.megacitycabservice.model;

import com.megacitycabservice.util.DateUtil;

import java.sql.Timestamp;

public class Customer {
    private String customerId;
    private int userId;
    private String name;
    private String nic;
    private String address;
    private String contactNumber;
    private String username;
    private String password;
    private Timestamp createdDate;
    private Timestamp updatedDate;

    public Customer() {
    }

    public Customer(String name, String nic, String address, String contactNumber, String username, String password) {
        this.name = name;
        this.nic = nic;
        this.address = address;
        this.contactNumber = contactNumber;
        this.username = username;
        this.password = password;
    }

    public Customer(String customerId, int userId, String name, String nic, String address, String contactNumber, String username, String password) {
        this.customerId = customerId;
        this.userId = userId;
        this.name = name;
        this.nic = nic;
        this.address = address;
        this.contactNumber = contactNumber;
        this.username = username;
        this.password = password;
    }

    public Customer(String customerId, int userId, String name, String nic, String address, String contactNumber, String username, String password, Timestamp createdDate, Timestamp updatedDate) {
        this.customerId = customerId;
        this.userId = userId;
        this.name = name;
        this.nic = nic;
        this.address = address;
        this.contactNumber = contactNumber;
        this.username = username;
        this.password = password;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFormattedCreatedDate() {
        return DateUtil.formatDate(createdDate);
    }

    public String getFormattedUpdatedDate() {
        return DateUtil.formatDate(updatedDate);
    }
}
