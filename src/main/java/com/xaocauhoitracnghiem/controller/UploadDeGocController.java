package com.xaocauhoitracnghiem.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


@WebServlet(name="UploadDeGoc", urlPatterns= {"/upload-de-goc"})
@MultipartConfig(
	fileSizeThreshold= 1024*1024*1, // 1MB 
	maxFileSize= 1024*1024*10,   // 10MB
	maxRequestSize=1024*1024*100   // 100MB
)
public class UploadDeGocController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Receive file uploaded to the Servlet from the HTML5 form */
	    Part filePart = request.getPart("de-goc");
	    String realPath = request.getServletContext().getRealPath("/de-goc");
	    String fileName = filePart.getSubmittedFileName();
	    if (!Files.exists(Paths.get(realPath))) {
	    	Files.createDirectory(Paths.get(realPath));
	    }
	    
	    String fullPath = realPath + File.separator + fileName;
	    filePart.write(fullPath);
	    String contextPath = request.getServletContext().getContextPath();
	    HttpSession session = request.getSession();
	    
	    session.setAttribute("PathOfDeGoc", fullPath);
	    response.sendRedirect(contextPath + "/xao-cau-hoi");
	}
}
