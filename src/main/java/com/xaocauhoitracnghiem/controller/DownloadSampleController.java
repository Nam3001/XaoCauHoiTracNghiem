package com.xaocauhoitracnghiem.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Paths;

@WebServlet(urlPatterns = {"/download-de-mau"})
public class DownloadSampleController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String selectedValue = req.getParameter("de-mau");

        FileInputStream inStream = new FileInputStream(new File(selectedValue));

        if (inStream == null) {
            // Handle case where file not found (e.g., send error message)
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String fileName = Paths.get(selectedValue).getFileName().toString();

        resp.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        OutputStream outStream = resp.getOutputStream();
        byte[] buffer =  new byte[1024];
        int bytesRead = -1;
        while((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        inStream.close();
        outStream.close();
    }
}
