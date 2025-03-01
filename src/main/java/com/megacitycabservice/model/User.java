package com.megacitycabservice.model;

import com.megacitycabservice.util.DateUtil;

import java.sql.Timestamp;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private Timestamp createdDate;
    private Timestamp updatedDate;

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

    public User() {
    }

    public User(int id, String username, String password, String role, Timestamp createdDate, Timestamp updatedDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public User(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public String getFormattedCreatedDate() {
        return DateUtil.formatDate(createdDate);
    }

    public String getFormattedUpdatedDate() {
        return DateUtil.formatDate(updatedDate);
    }
}
