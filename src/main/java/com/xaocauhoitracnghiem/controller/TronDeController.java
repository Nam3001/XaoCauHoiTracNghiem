package com.xaocauhoitracnghiem.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xaocauhoitracnghiem.model.ExamModel;
import com.xaocauhoitracnghiem.model.QuestionGroupModel;
import com.xaocauhoitracnghiem.service.impl.DeGocService;
import com.xaocauhoitracnghiem.service.impl.TronDeService;

@WebServlet(urlPatterns = { "/tron-de" })
public class TronDeController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.getWriter().print("hello");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		int soLuongDe = Integer.parseInt(req.getParameter("so-luong-de"));
		String tienToCauHoi = req.getParameter("tien-to-cau-hoi");
		boolean isEnglishExam = req.getParameter("de-tieng-anh") == null ? false : true;
		boolean coDinhNhom = req.getParameter("co-dinh-nhom") == null ? false : true;

		String soGD = req.getParameter("so-gd");
		String tenTruong = req.getParameter("ten-truong");
		String kyKiemTra = req.getParameter("ky-kiem-tra");
		String monThi = req.getParameter("mon-thi");
		String thoiGian = req.getParameter("thoi-gian");
		String namHoc = req.getParameter("nam-hoc");

		HttpSession session = req.getSession();
		ExamModel deGoc = (ExamModel) session.getAttribute("exam");
		
		 String deGocFileName = session.getAttribute("deGocFileName").toString();
		deGocFileName = deGocFileName.substring(0, deGocFileName.length() - 5);
		
		deGoc.tenSo_PhongGD = soGD;
		deGoc.tenTruong = tenTruong;
		deGoc.namHoc = namHoc;
		deGoc.kiKiemTra = kyKiemTra;
		deGoc.monHoc = monThi;
		deGoc.thoiGianLamBai = thoiGian;

		TronDeService tronDeService = new TronDeService();
		List<ExamModel> dsDe = tronDeService.tronDe(deGoc, soLuongDe, coDinhNhom);


		String rootPath = getServletContext().getRealPath("/de-vua-tao");
		if (!Files.exists(Paths.get(rootPath))) {
			Files.createDirectory(Paths.get(rootPath));
		}


		String realPath = rootPath + File.separator + deGocFileName;
		session.setAttribute("pathToExamShuffledExam", realPath);
		if (!Files.exists(Paths.get(realPath))) {
			Files.createDirectory(Paths.get(realPath));
		}
		
		for (ExamModel de : dsDe) {
			tronDeService.generateExamWord(de, realPath + File.separator + "Ma_de_" + de.getMaDe() + ".docx", tienToCauHoi, isEnglishExam);
		}
		req.setAttribute("path", dsDe.get(0).getTenSo_PhongGD());
		req.setAttribute("examList", dsDe);

		session.setAttribute("daTronDe", true);
		resp.sendRedirect(getServletContext().getContextPath()+"/thong-bao");
	}
}
