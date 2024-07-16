package com.xaocauhoitracnghiem.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.zeroturnaround.zip.ZipUtil;

@WebServlet(urlPatterns = {"/download-exam"})
public class DownloadExamController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String pathToExamShuffledExam = session.getAttribute("pathToExamShuffledExam").toString();
		
		String zipPath = pathToExamShuffledExam+"_trondetracnghiem"+".zip";
		ZipUtil.pack(new File(pathToExamShuffledExam), new File(zipPath));
		
		
		File downloadFile = new File(zipPath);
		FileInputStream inStream = new FileInputStream(downloadFile);
		
		// Đặt các thuộc tính cho phản hồi HTTP
        response.setContentType("application/zip");
        response.setContentLength((int) downloadFile.length());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFile.getName() + "\"");

        
        OutputStream outStream = response.getOutputStream();

        // Tạo buffer và ghi file vào output stream
        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inStream.close();
        outStream.close();
        
        File zipFile = new File(zipPath);
        File ExamListFolder = new File(pathToExamShuffledExam);
        
        zipFile.delete();
        ExamListFolder.delete();
        
	}
}
