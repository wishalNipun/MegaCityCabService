package com.megacitycabservice.persistence.dao;

import java.util.List;

public interface BookingVehicleDAO {
    List<Integer> getVehicleIdsByBookingId(int bookingId);
}
