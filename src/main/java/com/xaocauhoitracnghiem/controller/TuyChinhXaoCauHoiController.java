package com.xaocauhoitracnghiem.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xaocauhoitracnghiem.model.*;
import com.xaocauhoitracnghiem.service.impl.DeGocService;


@WebServlet(urlPatterns= {"/tuy-chinh-xao-cau-hoi"})
public class TuyChinhXaoCauHoiController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		
		String path = session.getAttribute("PathOfDeGoc") != null ? session.getAttribute("PathOfDeGoc").toString() : "";
		DeGocService degocservice = new DeGocService();
		ExamModel exam = new ExamModel();
		if(!path.equals("")) {
			exam = degocservice.getExamData(path);
		}
		session.setAttribute("exam", exam);
		
		session.setAttribute("deGocFileName", Paths.get(path).getFileName());
		req.setAttribute("questionAmount", degocservice.getQuestionCount(exam));
		req.setAttribute("groupAmount", degocservice.getQuestionGroupCount(exam));

		req.setAttribute("contextPath", getServletContext().getContextPath());
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/tuyChinhXaoCauHoi.jsp");
		rd.forward(req, resp);

		if(!path.isEmpty()) {
			Files.deleteIfExists(Paths.get(path));
			session.removeAttribute("PathOfDeGoc");
		}
	}
}
