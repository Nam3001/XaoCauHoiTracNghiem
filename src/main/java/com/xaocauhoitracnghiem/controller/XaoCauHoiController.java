package com.xaocauhoitracnghiem.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns= {"/xao-cau-hoi"})
public class XaoCauHoiController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		HttpSession session = req.getSession();
//		resp.getWriter().print(session.getAttribute("PathOfDeGoc"));
		
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/tuyChinhXaoCauHoi.jsp");
		rd.forward(req, resp);
	}
}
