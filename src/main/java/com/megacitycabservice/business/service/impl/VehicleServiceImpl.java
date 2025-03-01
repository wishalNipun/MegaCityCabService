package com.megacitycabservice.business.service.impl;

import com.megacitycabservice.business.service.VehicleService;
import com.megacitycabservice.model.Vehicle;
import com.megacitycabservice.persistence.dao.DAOFactory;
import com.megacitycabservice.persistence.dao.VehicleDAO;
import java.sql.SQLException;
import java.util.List;

public class VehicleServiceImpl implements VehicleService {

    private VehicleDAO vehicleDAO;

    public VehicleServiceImpl() throws SQLException, ClassNotFoundException {
       vehicleDAO = (VehicleDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.VEHICLE);
    }
    @Override
    public String addVehicle(Vehicle vehicle) {
        return vehicleDAO.insert(vehicle);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleDAO.getAll();
    }

    @Override
    public String updateVehicle(Vehicle vehicle) {
        return vehicleDAO.update(vehicle);
    }

    @Override
    public Boolean deleteVehicle(int id) {
        return vehicleDAO.delete(id);
    }

    @Override
    public List<Vehicle> getAvailableVehicles() {
        return vehicleDAO.getAvailableVehicles();
    }

    @Override
    public Integer getVehicleCountByStatus(String status) {
        return vehicleDAO.getVehicleCountByStatus(status);
    }
}
