package com.megacitycabservice.business.service.impl;

import com.megacitycabservice.model.Customer;
import com.megacitycabservice.persistence.dao.CustomerDAO;
import com.megacitycabservice.persistence.dao.DAOFactory;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerServiceImplTest {

    private static CustomerServiceImpl customerService;
    private static CustomerDAO customerDAO;

    @BeforeAll
    public static void setUp() throws SQLException, ClassNotFoundException {

        customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CUSTOMER);
        customerService = new CustomerServiceImpl();
    }

    @Test
    @Order(1)
    public void testRegisterCustomerWithUser() {
        String name = "TestName";
        String nic = "200021343212";
        String address = "Kaluthara";
        String contactNumber = "0771234567";
        String username = "test_customer";
        String password = "password123";

        String result = customerService.registerCustomerWithUser(name, nic, address, contactNumber, username, password);
        assertEquals("success", result, "Customer should be registered successfully");

        String id = customerDAO.getCustomerIdByUsername(username);
        assertNotNull(id, "Customer should be found in the database");
        System.out.println("Customer added successfully");
    }

    @Test
    @Order(2)
    public void testGetAllCustomers() {

        List<Customer> customers = customerService.getAllCustomers();
        assertNotNull(customers, "The Customer List should not be null");
        assertTrue(customers.size() > 0, "There should be at least one customer in the database");
        System.out.println("Get All Customers successfully");
     }

    @Test
    @Order(3)
    public void testDeleteCustomer() throws SQLException {
        boolean result = customerService.deleteCustomer("C0000007");
        assertEquals(true,result, "Customer should be deleted successfully");
        System.out.println("User Deleted successfully");
    }

    @Test
    @Order(4)
    public void testUpdateCustomer() throws SQLException {

        Customer customer = new Customer();
        customer.setCustomerId("C0000006");
        customer.setName("Ayesh");
        customer.setAddress("Ampara");
        customer.setNic("200066556565");
        customer.setContactNumber("0771234567");
        customer.setUsername("ayesh_sss");
        customer.setPassword("123456saa");
        String result = customerService.updateCustomer(customer);

        assertEquals("success", result, "Customer should be updated successfully");
        System.out.println("Customer Updated successfully");

    }


    @Test
    @Order(5)
    public void testGetCustomerById() throws SQLException {

        Customer result = customerService.getCustomerById("C0000006");

        assertNotNull(result, "The result should not be null");
        System.out.println("Get Customer successfully");

    }
}