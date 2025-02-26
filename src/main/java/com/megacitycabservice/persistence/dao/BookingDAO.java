package com.megacitycabservice.persistence.dao;

import com.megacitycabservice.model.Booking;

import java.util.List;

public interface BookingDAO {
    String generateBookId();
    String getLatestBookId();
    String addBooking(Booking booking,String[] vehicles,String username);
    List<Booking> getAllBookings();
    String updateBooking(Booking booking);
    boolean doesBookingExist(int bookingId);
    void updateBookingStatus(int bookingId, String status);
    String getBookingStatus(int bookingId);
}
