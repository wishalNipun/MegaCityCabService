package com.megacitycabservice.persistence.dao;

import com.megacitycabservice.model.Booking;
import com.megacitycabservice.model.Driver;

import java.util.List;

public interface BookingDAO {
    String generateBookId();
    String getLatestBookId();
    String addBooking(Booking booking,String[] vehicles,String username);
    List<Booking> getAllBookings();
    String updateBooking(Booking booking);
    Boolean deleteBooking(int id);
}
