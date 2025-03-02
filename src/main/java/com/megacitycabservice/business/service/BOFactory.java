package com.megacitycabservice.business.service;

import com.megacitycabservice.business.service.impl.*;

import java.sql.SQLException;

public class BOFactory {

    private static BOFactory boFactory;

    private static BillService billService;
    private static BookingService bookingService;
    private static CustomerService customerService;
    private static DriverService driverService;
    private static UserService userService;
    private static VehicleService vehicleService;

    private BOFactory() {
    }

    public static BOFactory getInstance() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public enum BOTypes {
        BILL, BOOKING, CUSTOMER, DRIVER, USER, VEHICLE
    }

    public SuperBOService getBO(BOTypes boType) throws SQLException, ClassNotFoundException {
        switch (boType) {
            case BILL:
                if (billService == null) {
                    billService = new BillServiceImpl();
                }
                return billService;
            case BOOKING:
                if (bookingService == null) {
                    bookingService = new BookingServiceImpl();
                }
                return bookingService;
            case CUSTOMER:
                if (customerService == null) {
                    customerService = new CustomerServiceImpl();
                }
                return customerService;
            case DRIVER:
                if (driverService == null) {
                    driverService = new DriverServiceImpl();
                }
                return driverService;
            case USER:
                if (userService == null) {
                    userService = new UserServiceImpl();
                }
                return userService;
            case VEHICLE:
                if (vehicleService == null) {
                    vehicleService = new VehicleServiceImpl();
                }
                return vehicleService;
            default:
                return null;
        }
    }
}
