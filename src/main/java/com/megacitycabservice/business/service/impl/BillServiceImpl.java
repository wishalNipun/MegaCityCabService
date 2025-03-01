package com.megacitycabservice.business.service.impl;

import com.megacitycabservice.business.service.BillService;
import com.megacitycabservice.model.Bill;
import com.megacitycabservice.persistence.dao.BillDAO;
import com.megacitycabservice.persistence.dao.BookingDAO;
import com.megacitycabservice.persistence.dao.BookingVehicleDAO;
import com.megacitycabservice.persistence.dao.VehicleDAO;
import com.megacitycabservice.persistence.dao.impl.BillDAOImpl;
import com.megacitycabservice.persistence.dao.impl.BookingDAOImpl;
import com.megacitycabservice.persistence.dao.impl.BookingVehicleDAOImpl;
import com.megacitycabservice.persistence.dao.impl.VehicleDAOImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class BillServiceImpl implements BillService {
    private final BillDAO billDAO;
    private final BookingDAO bookingDAO;
    private final BookingVehicleDAO bookingVehicleDAO;
    private final VehicleDAO vehicleDAO;
    public BillServiceImpl() throws SQLException, ClassNotFoundException {
        this.billDAO = new BillDAOImpl();
        this.bookingDAO = new BookingDAOImpl();
        this.bookingVehicleDAO = new BookingVehicleDAOImpl();
        this.vehicleDAO = new VehicleDAOImpl();
    }

    @Override
    public String addBill(String bookingId, double fee,double taxP,double discount, double totalAmount) {
        try {
            Integer id = bookingDAO.doesBookingExistCheck(bookingId);
            if (id == null) {
                return "Error: Booking not found.";
            }

            bookingDAO.updateBookingStatus(id,"COMPLETED");
            List<Integer> vehicleIds = bookingVehicleDAO.getVehicleIdsByBookingId(id);
            for (int vehicleId : vehicleIds) {
                if ("BUSY".equals(vehicleDAO.getVehicleStatus(vehicleId))) {
                    vehicleDAO.updateVehicleStatus(vehicleIds, "AVAILABLE");
                }
            }

            Bill bill = new Bill();


            BigDecimal baseFee = BigDecimal.valueOf(fee);
            BigDecimal taxPercentage =  BigDecimal.valueOf(taxP);
            BigDecimal taxPrice = baseFee.multiply(taxPercentage).divide(new BigDecimal("100"));
            BigDecimal total = BigDecimal.valueOf(totalAmount);
            BigDecimal discounts = BigDecimal.valueOf(discount);

            bill.setBookingId(id);
            bill.setBaseFee(baseFee);
            bill.setTaxPercentage(taxPercentage);
            bill.setTaxPrice(taxPrice);
            bill.setDiscount(discounts);
            bill.setTotalAmount(total);
            bill.setTaxPrice(taxPrice);

            return billDAO.addBill(bill);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: Failed to add bill.";
        }
    }

    @Override
    public List<Bill> getAllBills() {
        try {
            return billDAO.getAllBills();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
