package com.megacitycabservice.persistence.dao;

import com.megacitycabservice.model.Driver;

import java.sql.SQLException;
import java.util.List;

public interface DriverDAO {

    String addDriver(Driver driver);
    List<Driver> getAllDrivers();
    String updateDriver(Driver driver);
    Boolean deleteDriver(int id);
    List<Driver> getAllAvailableDrivers();
    Driver getDriverById(int driverId);
}
