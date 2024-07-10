package com.xaocauhoitracnghiem.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class QuestionGroupModel {
	public List<QuestionModel> questionList = new ArrayList<QuestionModel>();
	public List<QuestionModel> getQuestionList() {
		return questionList;
	}
	public void setQuestionList(List<QuestionModel> questionList) {
		this.questionList = questionList;
	}
	public List<XWPFParagraph> getGroupInfo() {
		return groupInfo;
	}
	public void setGroupInfo(List<XWPFParagraph> groupInfo) {
		this.groupInfo = groupInfo;
	}
	public List<XWPFParagraph> groupInfo = new ArrayList<XWPFParagraph>();
}
