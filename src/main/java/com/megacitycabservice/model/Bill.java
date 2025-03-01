package com.megacitycabservice.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Bill {
    private int id;
    private int bookingId;
    private BigDecimal baseFee;
    private BigDecimal taxPercentage;
    private BigDecimal taxPrice;
    private BigDecimal discount;
    private BigDecimal totalAmount;
    private java.sql.Timestamp createdDate;

    public Bill() {
    }

    public Bill(int id, int bookingId, BigDecimal baseFee, BigDecimal taxPercentage, BigDecimal discount, BigDecimal totalAmount, Timestamp createdDate) {
        this.id = id;
        this.bookingId = bookingId;
        this.baseFee = baseFee;
        this.taxPercentage = taxPercentage;
        this.discount = discount;
        this.totalAmount = totalAmount;
        this.createdDate = createdDate;
    }

    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public BigDecimal getBaseFee() {
        return baseFee;
    }

    public void setBaseFee(BigDecimal baseFee) {
        this.baseFee = baseFee;
    }

    public BigDecimal getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(BigDecimal taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
