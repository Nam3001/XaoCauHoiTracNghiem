package com.xaocauhoitracnghiem.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;



@WebServlet(urlPatterns = {"/huong-dan-convert-mathtype", "/huong-dan-tat-auto-list"})
public class GuideController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        String contextPath = req.getContextPath();

        RequestDispatcher rd;
        if(requestUri.equals(contextPath + "/huong-dan-convert-mathtype")) {
            rd = req.getRequestDispatcher("/views/web/guidelines/convertMathtype.jsp");
        } else {
            rd = req.getRequestDispatcher("/views/web/guidelines/disableAutoList.jsp");
        }
        rd.forward(req, resp);
    }
}
