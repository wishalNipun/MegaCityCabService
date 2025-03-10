package com.megacitycabservice.business.service.impl;

import com.megacitycabservice.model.Booking;
import com.megacitycabservice.model.Vehicle;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingServiceImplTest {
    private static BookingServiceImpl bookingService;

    @BeforeAll
    public static void setUp() throws SQLException, ClassNotFoundException {
        bookingService = new BookingServiceImpl();
    }

    @Test
    @Order(1)
    public void testAddBooking() {
        Booking booking = new Booking();
        booking.setPickupLocation("Colombo");
        booking.setDropLocation("Kandy");
        booking.setDistanceKm(120);
        booking.setFee(5000);
        String result = bookingService.addBooking(booking, new String[]{"1", "2"}, "test");
        assertEquals("success", result, "Booking should be added successfully");
        System.out.println("Booking added successfully");
    }

    @Test
    @Order(2)
    public void testGetAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        assertNotNull(bookings, "Booking list should not be null");
        assertTrue(bookings.size() > 0, "There should be at least one booking in the database");
        System.out.println("Retrieved all bookings successfully");
    }

    @Test
    @Order(3)
    public void testUpdateBooking() {
        Booking booking = new Booking();
        booking.setId(17);
        booking.setBookingNumber("BOOK-00017");
        booking.setStatus("CONFIRMED");
        String result = bookingService.updateBooking(booking);
        assertEquals("success", result, "Booking should be updated successfully");
        System.out.println("Booking updated successfully");
    }

    @Test
    @Order(4)
    public void testGetVehiclesByBookingNumber() {
        List<Vehicle> vehicles = bookingService.getVehiclesByBookingNumber("BOOK-00017");
        assertNotNull(vehicles, "Vehicle list should not be null");
        System.out.println("Retrieved Vehicles by booking number successfully");
    }

    @Test
    @Order(5)
    public void testGetBookingsByUserName() {
        List<Booking> bookings = bookingService.getBookingsByUsername("test");
        assertNotNull(bookings, "Booking list should not be null");
        System.out.println("Retrieved bookings by username successfully");
    }

    @Test
    @Order(6)
    public void testGetBookingsByStatus() {
        List<Booking> bookings = bookingService.getBookingsByStatus("PENDING");
        assertNotNull(bookings, "Booking list should not be null");
        System.out.println("Retrieved bookings by status successfully");
    }

    @Test
    @Order(7)
    public void testGetVehicleCountByStatus() {
        Integer count = bookingService.getBookingsCountByStatus("Available");
        assertNotNull(count, "Bookings count should not be null");
        assertTrue(count >= 0, "Bookings count should be non-negative");

        System.out.println("Bookings count by status retrieved successfully");
    }
}