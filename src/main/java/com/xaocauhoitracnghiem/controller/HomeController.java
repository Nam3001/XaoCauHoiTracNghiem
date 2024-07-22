package com.xaocauhoitracnghiem.controller;

import com.xaocauhoitracnghiem.model.SampleExamModel;
import com.xaocauhoitracnghiem.utils.CommonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/trang-chu"})
public class HomeController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathToSampleExam = getServletContext().getRealPath("assets/de-mau");
		List<SampleExamModel> sampleExamList = CommonUtils.getSampleExamList(pathToSampleExam);
		req.setAttribute("dsDeMau", sampleExamList);
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/home.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}

