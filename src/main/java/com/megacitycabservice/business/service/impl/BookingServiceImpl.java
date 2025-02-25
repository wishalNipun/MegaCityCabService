package com.megacitycabservice.business.service.impl;

import com.megacitycabservice.business.service.BookingService;
import com.megacitycabservice.model.Booking;
import com.megacitycabservice.persistence.dao.BookingDAO;
import com.megacitycabservice.persistence.dao.impl.BookingDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class BookingServiceImpl implements BookingService {

    BookingDAO bookingDAO;

    public BookingServiceImpl() throws SQLException, ClassNotFoundException {
        this.bookingDAO = new BookingDAOImpl();
    }


    @Override
    public String addBooking(Booking booking, String[] vehicles, String username) {
        return bookingDAO.addBooking(booking,vehicles,username);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingDAO.getAllBookings();
    }

    @Override
    public String updateBooking(Booking booking) {
        return bookingDAO.updateBooking(booking);
    }

    @Override
    public Boolean deleteBooking(int id) {
        return bookingDAO.deleteBooking(id);
    }
}
