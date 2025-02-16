package com.megacitycabservice.service.impl;

import com.megacitycabservice.dao.DriverDAO;
import com.megacitycabservice.dao.impl.DriverDAOImpl;
import com.megacitycabservice.model.Driver;
import com.megacitycabservice.service.DriverService;

import java.sql.SQLException;
import java.util.List;

public class DriverServiceImpl implements DriverService {

    private final DriverDAO driverDAO;

    public DriverServiceImpl() throws SQLException, ClassNotFoundException {
        this.driverDAO = new DriverDAOImpl();
    }

    @Override
    public List<Driver> getAllDrivers() {
        return driverDAO.getAllDrivers();
    }

    @Override
    public String addDriver(Driver driver) {
        return driverDAO.addDriver(driver);
    }

    @Override
    public String updateDriver(Driver driver) {
        return null;
    }

    @Override
    public Boolean deleteDriver(int id) {
        return null;
    }
}
