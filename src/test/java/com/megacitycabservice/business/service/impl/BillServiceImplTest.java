package com.megacitycabservice.business.service.impl;

import com.megacitycabservice.model.Bill;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BillServiceImplTest {
    private static BillServiceImpl billService;

    @BeforeAll
    public static void setUp() throws SQLException, ClassNotFoundException {
        billService = new BillServiceImpl();
    }

    @Test
    @Order(1)
    public void testAddBill() {
        String result = billService.addBill("BOOK-00017", 5000.0, 10.0, 500.0, 5500.0);
        assertEquals("success", result, "Bill should be added successfully");
        System.out.println("Bill added successfully");
    }

    @Test
    @Order(2)
    public void testGetAllBills() {
        List<Bill> bills = billService.getAllBills();
        assertNotNull(bills, "Bill list should not be null");
        assertTrue(bills.size() > 0, "There should be at least one bill in the database");
        System.out.println("Retrieved all bills successfully");
    }
}