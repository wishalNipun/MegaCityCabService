package com.megacitycabservice.presentation.controllers;
import com.megacitycabservice.business.service.BOFactory;
import com.megacitycabservice.business.service.CustomerService;
import com.megacitycabservice.business.service.impl.CustomerServiceImpl;
import com.megacitycabservice.business.service.impl.DriverServiceImpl;
import com.megacitycabservice.model.Customer;
import com.megacitycabservice.model.Driver;
import com.megacitycabservice.util.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/customers")
public class CustomerServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init() {

        try {
            customerService = (CustomerService) BOFactory.getInstance().getBO(BOFactory.BOTypes.CUSTOMER);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if ("save".equals(action)) {
            addCustomer(request,response);
        } else if ("delete".equals(action)) {
            deleteCustomer(request,response);
        } else if ("update".equals(action)) {
            updateCustomer(request,response);
        } else if("register".equals(action)){
            registerCustomer(request,response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("getCustomerDetail".equals(action)) {
            getCustomerDetail(request, response);
        } else {
            try {
                List<Customer> customerList = customerService.getAllCustomers();
                request.setAttribute("customerList", customerList);
                request.getRequestDispatcher("/pages/CustomerManagement.jsp").forward(request, response);
            } catch (ServletException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void getCustomerDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String customerId = request.getParameter("customerId");

        if (customerId == null || customerId.isEmpty()) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"error\": \"Customer ID is missing\"}");
            return;
        }

        Customer customer = customerService.getCustomerById(customerId);

        if (customer != null) {
            String json = "{"
                    + "\"customerId\":\"" + customer.getCustomerId() + "\","
                    + "\"name\":\"" + customer.getName() + "\","
                    + "\"address\":\"" + customer.getAddress() + "\","
                    + "\"contactNumber\":\"" + customer.getContactNumber() + "\","
                    + "\"nic\":\"" + customer.getNic() + "\","
                    + "\"username\":\"" + customer.getUsername() + "\""
                    + "}";

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } else {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"error\": \"Customer not found\"}");
        }
    }

    public void addCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String nic = request.getParameter("nic");
        String address = request.getParameter("address");
        String contactNumber = request.getParameter("contactNumber");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isRegistered = customerService.registerCustomerWithUser(name, nic, address, contactNumber, username, password);



        if (isRegistered) {
            request.setAttribute("message", "Customer registered successfully.");
            response.sendRedirect(request.getContextPath() + "/customers");
        } else {
            request.setAttribute("message", "Registration failed. Please try again.");
            response.sendRedirect(request.getContextPath() + "/customers");
        }
    }

    public void registerCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String nic = request.getParameter("nic");
        String address = request.getParameter("address");
        String contactNumber = request.getParameter("contactNumber");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isRegistered = customerService.registerCustomerWithUser(name, nic, address, contactNumber, username, password);

        if (isRegistered) {
            request.setAttribute("message", "Customer registered successfully.");
            request.getRequestDispatcher("pages/customerDashboard.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "Registration failed. Please try again.");
            request.getRequestDispatcher("pages/register.jsp").forward(request, response);

        }
    }

    public void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerId = request.getParameter("customerId");

        if (customerId != null && !customerId.isEmpty()) {

            boolean isDeleted = customerService.deleteCustomer(customerId);

            if (isDeleted) {
                ResponseUtil.setResponseMessage(request, "success", "Customer deleted successfully!");
            } else {
                ResponseUtil.setResponseMessage(request, "error", "Failed to delete Customer.");

            }
            response.sendRedirect(request.getContextPath() + "/customers");
        }
    }

    public void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String customerId = request.getParameter("customerId");
        String name = request.getParameter("name");
        String nic = request.getParameter("nic");
        String address = request.getParameter("address");
        String contactNumber = request.getParameter("contactNumber");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("Updating Customer: " + customerId + ", Name: " + name + ", Contact: " + contactNumber);

        if (customerId == null || customerId.isEmpty()) {
            ResponseUtil.setResponseMessage(request, "error", "Error: Customer ID is missing.");
            response.sendRedirect(request.getContextPath() + "/customers");
            return;
        }

        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setName(name);
        customer.setAddress(address);
        customer.setNic(nic);
        customer.setContactNumber(contactNumber);
        customer.setUsername(username);
        customer.setPassword(password);
        String message = customerService.updateCustomer(customer);

        if ("success".equals(message)) {
            ResponseUtil.setResponseMessage(request, "success", "Customer updated successfully!");
        } else {
            ResponseUtil.setResponseMessage(request, "error", message);
        }
        response.sendRedirect(request.getContextPath() + "/customers");
    }
}
