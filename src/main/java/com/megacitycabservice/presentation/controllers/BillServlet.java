package com.megacitycabservice.presentation.controllers;

import com.megacitycabservice.business.service.BOFactory;
import com.megacitycabservice.business.service.BillService;
import com.megacitycabservice.business.service.impl.BillServiceImpl;
import com.megacitycabservice.model.Bill;
import com.megacitycabservice.util.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet("/bills")
public class BillServlet extends HttpServlet {
    private BillService billService;
    @Override
    public void init() {
        try {
            billService = (BillService) BOFactory.getInstance().getBO(BOFactory.BOTypes.BILL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if ("payBook".equals(action)) {
            payBooking(request,response);
        }
    }

    private void payBooking(HttpServletRequest request, HttpServletResponse response) {

        String bookingId = request.getParameter("bookingId");
        double baseFee = Double.parseDouble(request.getParameter("baseFee"));
        double taxPercentage = Double.parseDouble(request.getParameter("tax"));
        double discount = Double.parseDouble(request.getParameter("discount"));
        double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));

        String message = null;
        try {
            message = billService.addBill(bookingId,baseFee,taxPercentage,discount,totalAmount);
            if ("success".equals(message)) {
                ResponseUtil.setResponseMessage(request, "success", "Payment Created successfully!");
            } else {
                ResponseUtil.setResponseMessage(request, "error", message);
            }
            response.sendRedirect(request.getContextPath() + "/bookings?action=availablePayBookings");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            List<Bill> billList = billService.getAllBills();
            request.setAttribute("billList", billList);
            request.getRequestDispatcher("/pages/adminBillDetailsManagement.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
