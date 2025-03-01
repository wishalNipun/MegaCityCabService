package com.megacitycabservice.persistence.dao;

import com.megacitycabservice.persistence.dao.impl.*;

import java.sql.SQLException;

public class DAOFactory {
    public static BillDAO getBillDAO() throws SQLException, ClassNotFoundException {
        return new BillDAOImpl();
    }

    public static BookingDAO getBookingDAO() throws SQLException, ClassNotFoundException {
        return new BookingDAOImpl();
    }

    public static BookingVehicleDAO getBookingVehicleDAO() {
        return new BookingVehicleDAOImpl();
    }

    public static CustomerDAO getCustomerDAO() throws SQLException, ClassNotFoundException {
        return new CustomerDAOImpl();
    }

    public static DriverDAO getDriverDAO() throws SQLException, ClassNotFoundException {
        return new DriverDAOImpl();
    }

    public static UserDAO getUserDAO() throws SQLException, ClassNotFoundException {
        return new UserDAOImpl();
    }

    public static VehicleDAO getVehicleDAO() throws SQLException, ClassNotFoundException {
        return new VehicleDAOImpl();
    }
}
