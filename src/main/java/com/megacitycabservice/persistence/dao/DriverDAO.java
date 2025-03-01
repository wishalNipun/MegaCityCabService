package com.megacitycabservice.persistence.dao;

import com.megacitycabservice.model.Driver;

import java.sql.SQLException;
import java.util.List;

public interface DriverDAO extends CrudDAO<Driver,Integer>,SuperDAO{
    List<Driver> getAllAvailableDrivers();
    Driver getDriverById(int driverId);
}
