package com.megacitycabservice.persistence.dao;

import com.megacitycabservice.model.Booking;
import com.megacitycabservice.model.Vehicle;

import java.util.List;

public interface BookingDAO extends SuperDAO{
    String generateBookId();
    String getLatestBookId();
    String addBooking(Booking booking,String[] vehicles,String username);
    List<Booking> getAllBookings();
    String updateBooking(Booking booking);
    boolean doesBookingExist(int bookingId);
    Integer doesBookingExistCheck(String bookingNumber);
    void updateBookingStatus(int bookingId, String status);
    String getBookingStatus(int bookingId);
    List<Vehicle> getVehiclesByBookingNumber(String bookingNumber);
    List<Booking> getBookingsByCustomerId(String customerId);
    List<Booking>getBookingsByStatus(String status);
    Integer getBookingsCountByStatus(String status);
}
