package com.xaocauhoitracnghiem.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import com.xaocauhoitracnghiem.model.ExamModel;
import com.xaocauhoitracnghiem.model.QuestionGroupModel;
import com.xaocauhoitracnghiem.service.IExamService;

public class ExamService implements IExamService {
	@Override
	public List<QuestionGroupModel> getExamQuestionFromFile(String filePath) {
		// TODO Auto-generated method stub
		List<QuestionGroupModel> list = new ArrayList<QuestionGroupModel>();
		
		try {
			XWPFDocument document = new XWPFDocument(new FileInputStream(filePath));
			for (XWPFParagraph paragraph : document.getParagraphs()) {
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
}
