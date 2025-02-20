package com.megacitycabservice.persistence.dao;

import com.megacitycabservice.model.Driver;

import java.util.List;

public interface DriverDAO {

    String addDriver(Driver driver);
    List<Driver> getAllDrivers();
    String updateDriver(Driver driver);
    Boolean deleteDriver(int id);
}
