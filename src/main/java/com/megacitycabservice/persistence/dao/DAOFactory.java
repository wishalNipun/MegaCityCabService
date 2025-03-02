package com.megacitycabservice.persistence.dao;

import com.megacitycabservice.persistence.dao.impl.*;

import java.sql.SQLException;

public class DAOFactory {
    private static DAOFactory daoFactory;


    private static BillDAO billDAO;
    private static BookingDAO bookingDAO;
    private static BookingVehicleDAO bookingVehicleDAO;
    private static CustomerDAO customerDAO;
    private static DriverDAO driverDAO;
    private static UserDAO userDAO;
    private static VehicleDAO vehicleDAO;


    private DAOFactory() {}

    public static DAOFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public enum DAOTypes {
        BILL, BOOKING, BOOKING_VEHICLE, CUSTOMER, DRIVER, USER, VEHICLE
    }

    public SuperDAO getDAO(DAOTypes daoType) throws SQLException, ClassNotFoundException {
        switch (daoType) {
            case BILL:
                if (billDAO == null) {
                    billDAO = new BillDAOImpl();
                }
                return billDAO;
            case BOOKING:
                if (bookingDAO == null) {
                    bookingDAO = new BookingDAOImpl();
                }
                return bookingDAO;
            case BOOKING_VEHICLE:
                if (bookingVehicleDAO == null) {
                    bookingVehicleDAO = new BookingVehicleDAOImpl();
                }
                return bookingVehicleDAO;
            case CUSTOMER:
                if (customerDAO == null) {
                    customerDAO = new CustomerDAOImpl();
                }
                return customerDAO;
            case DRIVER:
                if (driverDAO == null) {
                    driverDAO = new DriverDAOImpl();
                }
                return driverDAO;
            case USER:
                if (userDAO == null) {
                    userDAO = new UserDAOImpl();
                }
                return userDAO;
            case VEHICLE:
                if (vehicleDAO == null) {
                    vehicleDAO = new VehicleDAOImpl();
                }
                return vehicleDAO;
            default:
                return null;
        }
    }
}
