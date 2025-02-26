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
        int id = booking.getId();
        boolean doesBookingExist = bookingDAO.doesBookingExist(id);
        if (doesBookingExist) {
            if (booking.getStatus().equals("CONFIRMED")) {

                List<Integer> vehicleIds = bookingVehicleDAO.getVehicleIdsByBookingId(booking.getId());
                for (int vehicleId : vehicleIds) {
                    String vehicleStatus = vehicleDAO.getVehicleStatus(vehicleId);
                    if ("BUSY".equals(vehicleStatus)) {
                        return "Error: One or more vehicles are currently busy. Cannot confirm booking.";
                    }
                }
                bookingDAO.updateBookingStatus(booking.getId(), "CONFIRMED");
                vehicleDAO.updateVehicleStatus(vehicleIds, "BUSY");
                return "success";


            } else if (booking.getStatus().equals("CANCELLED")) {

                String currentBookingStatus = bookingDAO.getBookingStatus(booking.getId());

                if ("CONFIRMED".equals(currentBookingStatus)) {
                    List<Integer> vehicleIds = bookingVehicleDAO.getVehicleIdsByBookingId(booking.getId());
                    vehicleDAO.updateVehicleStatus(vehicleIds, "AVAILABLE");
                }

                bookingDAO.updateBookingStatus(booking.getId(), "CANCELLED");
                return "success";
            }
            return bookingDAO.updateBooking(booking);
        } else {
            return "Error: Booking not found.";
        }
    }
}
