package com.megacitycabservice.business.service.impl;

import com.megacitycabservice.persistence.dao.DAOFactory;
import com.megacitycabservice.persistence.dao.DriverDAO;
import com.megacitycabservice.model.Driver;
import com.megacitycabservice.business.service.DriverService;
import com.megacitycabservice.util.Validation.DriverValidation;

import java.sql.SQLException;
import java.util.List;

public class DriverServiceImpl implements DriverService {

    private final DriverDAO driverDAO;

    public DriverServiceImpl() throws SQLException, ClassNotFoundException {
        driverDAO = (DriverDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.DRIVER);
    }

    @Override
    public List<Driver> getAllDrivers() {
        return driverDAO.getAll();
    }

    @Override
    public String addDriver(Driver driver) {
        String validationMessage = DriverValidation.validateDriver(
                driver.getName(),
                driver.getNic(),
                driver.getAddress(),
                driver.getLicenseNumber(),
                driver.getContactNumber()
        );

        if (validationMessage != null) {
            System.out.println(validationMessage);
            return validationMessage;
        }
        return driverDAO.insert(driver);
    }

    @Override
    public String updateDriver(Driver driver) {
        String validationMessage = DriverValidation.validateDriver(
                driver.getName(),
                driver.getNic(),
                driver.getAddress(),
                driver.getLicenseNumber(),
                driver.getContactNumber()
        );
        if (validationMessage != null) {
            System.out.println(validationMessage);
            return validationMessage;
        }
        return driverDAO.update(driver);
    }

    @Override
    public Boolean deleteDriver(int id) {
        return driverDAO.delete(id);
    }

    @Override
    public List<Driver> getAllAvailableDrivers() {
        return driverDAO.getAllAvailableDrivers();
    }

    @Override
    public Driver getDriverById(int driverId) {
        return driverDAO.getDriverById(driverId);
    }
}
