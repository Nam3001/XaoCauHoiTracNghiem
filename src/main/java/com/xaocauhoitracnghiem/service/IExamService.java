package com.xaocauhoitracnghiem.service;

import java.util.List;

import com.xaocauhoitracnghiem.model.QuestionGroupModel;

public interface IExamService {
	public List<QuestionGroupModel> getExamQuestionFromFile(String filePath);
}
