package com.xaocauhoitracnghiem.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class QuestionGroupModel {
	public List<QuestionModel> questionList = new ArrayList<QuestionModel>();
	public List<XWPFParagraph> groupInfoList = new ArrayList<XWPFParagraph>();
	
	// type = 0 => không cần xáo
	// typ = 1 => chỉ cần xáo câu hỏi
	// type = 2 => chỉ cần xáo đáp án
	// type = 3 => xáo cả câu hỏi và đáp án
	public int groupType;
	
	public int getGroupType() {
		return groupType;
	}
	public void setGroupType(int type) {
		this.groupType = type;
	}
	public List<QuestionModel> getQuestionList() {
		return questionList;
	}
	public void setQuestionList(List<QuestionModel> questionList) {
		this.questionList = questionList;
	}
	public List<XWPFParagraph> getGroupInfoList() {
		return groupInfoList;
	}
	public void setGroupInfo(List<XWPFParagraph> groupInfo) {
		this.groupInfoList = groupInfo;
	}
}
