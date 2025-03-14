package com.megacitycabservice.business.service.impl;

import com.megacitycabservice.business.service.BillService;
import com.megacitycabservice.model.Bill;
import com.megacitycabservice.persistence.dao.*;
import com.megacitycabservice.util.Validation.BillValidation;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class BillServiceImpl implements BillService {
    private final BillDAO billDAO;
    private final BookingDAO bookingDAO;
    private final BookingVehicleDAO bookingVehicleDAO;
    private final VehicleDAO vehicleDAO;
    public BillServiceImpl() throws SQLException, ClassNotFoundException {
        this.billDAO = (BillDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.BILL);
        this.bookingDAO = (BookingDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.BOOKING);
        this.bookingVehicleDAO = (BookingVehicleDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.BOOKING_VEHICLE);
        this.vehicleDAO = (VehicleDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.VEHICLE);
    }

    @Override
    public String addBill(String bookingId, double fee,double taxP,double discount, double totalAmount) {
        String validationMessage = BillValidation.validateBill(taxP, discount);
        if (validationMessage != null) {
            return validationMessage;
        }

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

        System.out.println("---- Mega City Cab - Invoice ----");
        System.out.println("Booking ID: " + bill.getBookingId());
        System.out.println("Base Fee: " + bill.getBaseFee());
        System.out.println("Tax Percentage: " + bill.getTaxPercentage() + "%");
        System.out.println("Tax Price: " + bill.getTaxPrice());
        System.out.println("Discount: " + bill.getDiscount());
        System.out.println("Total Amount: " + bill.getTotalAmount());
        System.out.println("Thank you for choosing Mega City Cab!");

        return billDAO.insert(bill);
    }

    @Override
    public List<Bill> getAllBills() {
        return billDAO.getAll();
    }

}
