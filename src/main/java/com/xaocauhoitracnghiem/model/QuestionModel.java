package com.xaocauhoitracnghiem.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class QuestionModel {
	public int questionOrder; // cau 1. cau 2. ...
	public int getQuestionOrder() {
		return questionOrder;
	}
	public void setQuestionOrder(int questionOrder) {
		this.questionOrder = questionOrder;
	}
	
	public List<XWPFParagraph> getQuestionContentList() {
		return questionContentList;
	}
	public void setQuestionContent(List<XWPFParagraph> questionContentList) {
		this.questionContentList = questionContentList;
	}
	public List<AnswerModel> getAnswerList() {
		return answerList;
	}
	public void setAnswerList(List<AnswerModel> answerList) {
		this.answerList = answerList;
	}
	public List<XWPFParagraph> questionContentList = new ArrayList<XWPFParagraph>();
	public List<AnswerModel> answerList = new ArrayList<AnswerModel>();  // danh sach dap an
}
