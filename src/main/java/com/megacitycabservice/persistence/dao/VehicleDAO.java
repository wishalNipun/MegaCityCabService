package com.megacitycabservice.persistence.dao;

import com.megacitycabservice.model.Vehicle;

import java.util.List;

public interface VehicleDAO extends CrudDAO<Vehicle,Integer> , SuperDAO{
    List<Vehicle> getAvailableVehicles();
    String getVehicleStatus(int vehicleId);
    void updateVehicleStatus(List<Integer> vehicleIds, String status);
}
