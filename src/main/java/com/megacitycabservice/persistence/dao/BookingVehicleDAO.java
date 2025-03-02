package com.megacitycabservice.persistence.dao;

import java.util.List;

public interface BookingVehicleDAO extends SuperDAO {
    List<Integer> getVehicleIdsByBookingId(int bookingId);
}
