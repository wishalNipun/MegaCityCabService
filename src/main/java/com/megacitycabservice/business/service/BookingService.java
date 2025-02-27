package com.megacitycabservice.business.service;

import com.megacitycabservice.model.Booking;
import com.megacitycabservice.model.Vehicle;

import java.util.List;

public interface BookingService {
    String addBooking(Booking booking,String[] vehicles,String username);
    List<Booking> getAllBookings();
    String updateBooking(Booking booking);
    List<Vehicle> getVehiclesByBookingNumber(String bookingNumber);
}
