package com.megacitycabservice.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ResponseUtil {
    public static void setResponseMessage(HttpServletRequest request, String type, String message) {
        HttpSession session = request.getSession();
        session.setAttribute("alert", type);
        session.setAttribute("message", message);
    }
}
