package com.megacitycabservice.business.service.impl;

import com.megacitycabservice.persistence.dao.DriverDAO;
import com.megacitycabservice.persistence.dao.impl.DriverDAOImpl;
import com.megacitycabservice.model.Driver;
import com.megacitycabservice.business.service.DriverService;

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
        return driverDAO.updateDriver(driver);
    }

    @Override
    public Boolean deleteDriver(int id) {
        return driverDAO.deleteDriver(id);
    }
}
