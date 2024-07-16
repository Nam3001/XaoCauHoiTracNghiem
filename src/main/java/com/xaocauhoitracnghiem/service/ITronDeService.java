package com.xaocauhoitracnghiem.service;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.xaocauhoitracnghiem.model.ExamModel;

public interface ITronDeService {
	List<ExamModel> tronDe(ExamModel deGoc, int soLuongDe);
	String generateExamWord(ExamModel exam, String path, String tienToCauHoi, boolean isEnglishExam)  throws Exception;
}
