package com.megacitycabservice.business.service.impl;

import com.megacitycabservice.business.service.BookingService;
import com.megacitycabservice.model.Booking;
import com.megacitycabservice.persistence.dao.BookingDAO;
import com.megacitycabservice.persistence.dao.BookingVehicleDAO;
import com.megacitycabservice.persistence.dao.VehicleDAO;
import com.megacitycabservice.persistence.dao.impl.BookingDAOImpl;
import com.megacitycabservice.persistence.dao.impl.BookingVehicleDAOImpl;
import com.megacitycabservice.persistence.dao.impl.VehicleDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class BookingServiceImpl implements BookingService {

    private final BookingDAO bookingDAO;
    private final BookingVehicleDAO bookingVehicleDAO;
    private final VehicleDAO vehicleDAO;

    public BookingServiceImpl() throws SQLException, ClassNotFoundException {
        this.bookingDAO = new BookingDAOImpl();
        this.bookingVehicleDAO = new BookingVehicleDAOImpl();
        this.vehicleDAO = new VehicleDAOImpl();
    }


    @Override
    public String addBooking(Booking booking, String[] vehicles, String username) {
        return bookingDAO.addBooking(booking, vehicles, username);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingDAO.getAllBookings();
    }


    @Override
    public String updateBooking(Booking booking) {
        int bookingId = booking.getId();

        if (!bookingDAO.doesBookingExist(bookingId)) {
            return "Error: Booking not found.";
        }


        String currentBookingStatus = bookingDAO.getBookingStatus(bookingId);


        if ("CANCELLED".equals(currentBookingStatus)) {
            return "Error: This booking has already been cancelled and cannot be changed.";
        }

        if ("CONFIRMED".equals(booking.getStatus())) {
            if ("PENDING".equals(currentBookingStatus)) {
                List<Integer> vehicleIds = bookingVehicleDAO.getVehicleIdsByBookingId(bookingId);
                for (int vehicleId : vehicleIds) {
                    if ("BUSY".equals(vehicleDAO.getVehicleStatus(vehicleId))) {
                        return "Error: One or more vehicles are currently busy. Cannot confirm booking.";
                    }
                }

                bookingDAO.updateBookingStatus(bookingId, "CONFIRMED");
                vehicleDAO.updateVehicleStatus(vehicleIds, "BUSY");
                return "success";
            }
        }


        if ("CANCELLED".equals(booking.getStatus())) {
            if ("CONFIRMED".equals(currentBookingStatus)) {
                List<Integer> vehicleIds = bookingVehicleDAO.getVehicleIdsByBookingId(bookingId);
                vehicleDAO.updateVehicleStatus(vehicleIds, "AVAILABLE");
            }
            bookingDAO.updateBookingStatus(bookingId, "CANCELLED");
            return "success";
        }

        if ("PENDING".equals(booking.getStatus())) {
            if ("CONFIRMED".equals(currentBookingStatus)) {
                List<Integer> vehicleIds = bookingVehicleDAO.getVehicleIdsByBookingId(bookingId);
                vehicleDAO.updateVehicleStatus(vehicleIds, "AVAILABLE");
            }
            bookingDAO.updateBookingStatus(bookingId, "PENDING");
            return "success";
        }

        return "Error: Invalid status update.";
    }
}
