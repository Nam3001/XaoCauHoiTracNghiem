package com.xaocauhoitracnghiem.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.xaocauhoitracnghiem.model.ExamModel;

public interface IDeGocService {
	// đọc file .docx và lấy dữ liệu trong đề gốc
	ExamModel getExamData(String path) throws FileNotFoundException, IOException;
	int getQuestionCount(ExamModel exam);
	int getQuestionGroupCount(ExamModel exam);
}
