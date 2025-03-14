package com.megacitycabservice.business.service;

import com.megacitycabservice.model.Driver;

import java.util.List;

public interface DriverService extends SuperBOService{
    String addDriver(Driver driver);
    List<Driver> getAllDrivers();
    String updateDriver(Driver driver);
    Boolean deleteDriver(int id);
    List<Driver> getAllAvailableDrivers();
    Driver getDriverById(int driverId);
}
