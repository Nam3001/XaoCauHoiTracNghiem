package com.xaocauhoitracnghiem.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.xaocauhoitracnghiem.model.OriginalExamModel;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import com.xaocauhoitracnghiem.model.ExamModel;

import javax.servlet.http.HttpSession;

public interface IDeGocService {
	// đọc file .docx và lấy dữ liệu trong đề gốc
	OriginalExamModel getExamData(String path) throws FileNotFoundException, IOException;
	
	boolean isQuestionParagraph(XWPFParagraph p);
	boolean isAnswerParagraph(XWPFParagraph p);
	boolean isRightAnswerParagraph(XWPFParagraph p);
	boolean isGroupQuestionParagraph(XWPFParagraph p);
	
	int getQuestionCount(ExamModel exam);
	int getQuestionGroupCount(ExamModel exam);
}
