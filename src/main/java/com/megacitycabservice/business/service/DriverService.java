package com.megacitycabservice.business.service;

import com.megacitycabservice.model.Driver;

import java.util.List;

public interface DriverService {
    String addDriver(Driver driver);
    List<Driver> getAllDrivers();
    String updateDriver(Driver driver);
    Boolean deleteDriver(int id);
}
