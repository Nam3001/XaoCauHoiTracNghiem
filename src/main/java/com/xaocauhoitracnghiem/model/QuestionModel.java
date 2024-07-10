package com.xaocauhoitracnghiem.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class QuestionModel {
	public String questionId; // cau 1. cau 2. ...
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public List<XWPFParagraph> getQuestionContent() {
		return questionContent;
	}
	public void setQuestionContent(List<XWPFParagraph> questionContent) {
		this.questionContent = questionContent;
	}
	public List<AnswerModel> getAnswerList() {
		return answerList;
	}
	public void setAnswerList(List<AnswerModel> answerList) {
		this.answerList = answerList;
	}
	public List<XWPFParagraph> questionContent = new ArrayList<XWPFParagraph>();
	public List<AnswerModel> answerList = new ArrayList<AnswerModel>();  // danh sach dap an
}
