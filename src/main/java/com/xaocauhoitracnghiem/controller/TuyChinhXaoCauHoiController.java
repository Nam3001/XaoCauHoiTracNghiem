package com.xaocauhoitracnghiem.controller;

import java.io.IOException;
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

import com.xaocauhoitracnghiem.model.AnswerModel;
import com.xaocauhoitracnghiem.model.ExamModel;
import com.xaocauhoitracnghiem.model.QuestionGroupModel;
import com.xaocauhoitracnghiem.model.QuestionModel;
import com.xaocauhoitracnghiem.service.impl.DeGocService;


@WebServlet(urlPatterns= {"/tuy-chinh-xao-cau-hoi"})
public class TuyChinhXaoCauHoiController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		
		String path = (String)session.getAttribute("PathOfDeGoc");
//		String path = "D:\\workspace\\java_eclipse2018\\ThucTapVietNienLuan\\XaoCauHoiTracNghiem\\src\\main\\webapp\\assets\\test.docx";
		DeGocService degocservice = new DeGocService();
		ExamModel exam = degocservice.getExamData(path);
		session.setAttribute("exam", exam);
		
		session.setAttribute("deGocFileName", Paths.get(path).getFileName());
		req.setAttribute("questionAmount", degocservice.getQuestionCount(exam));
		req.setAttribute("groupAmount", degocservice.getQuestionGroupCount(exam));
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/tuyChinhXaoCauHoi.jsp");
		rd.forward(req, resp);
	}
}
