package com.xaocauhoitracnghiem.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.apache.commons.io.FileUtils;
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

        // Đặt cookie để báo hiệu hoàn tất tải file
        Cookie cookie = new Cookie("fileDownload", "true");
        cookie.setPath("/");
        cookie.setMaxAge(60); // Cookie sẽ tồn tại trong 60 giây
        response.addCookie(cookie);
        
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
        FileUtils.deleteDirectory(ExamListFolder);
	}
}
