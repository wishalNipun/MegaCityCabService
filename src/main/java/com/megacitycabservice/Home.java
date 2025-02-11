package com.megacitycabservice;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "/")
public class Home extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
     response.sendRedirect("pages/login.jsp");
    }

    public void destroy() {
    }
}