package com.megacitycabservice.business.service.impl;

import com.megacitycabservice.business.service.VehicleService;
import com.megacitycabservice.model.Vehicle;
import com.megacitycabservice.persistence.dao.VehicleDAO;
import com.megacitycabservice.persistence.dao.impl.VehicleDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class VehicleServiceImpl implements VehicleService {

    private VehicleDAO vehicleDAO;

    public VehicleServiceImpl() throws SQLException, ClassNotFoundException {
       vehicleDAO = new VehicleDAOImpl();
    }
    @Override
    public String addVehicle(Vehicle vehicle) {
        return vehicleDAO.addVehicle(vehicle);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleDAO.getAllVehicles();
    }

    @Override
    public String updateVehicle(Vehicle vehicle) {
        return vehicleDAO.updateVehicle(vehicle);
    }

    @Override
    public Boolean deleteVehicle(int id) {
        return vehicleDAO.deleteVehicle(id);
    }

    @Override
    public List<Vehicle> getAvailableVehicles() {
        return vehicleDAO.getAvailableVehicles();
    }
}
