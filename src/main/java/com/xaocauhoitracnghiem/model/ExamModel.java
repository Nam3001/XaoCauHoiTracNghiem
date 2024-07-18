package com.xaocauhoitracnghiem.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class ExamModel {
	public List<QuestionGroupModel> groupList = new ArrayList<QuestionGroupModel>();
	
	// exam information
	public String tenSo_PhongGD; // tên sở / phòng giáo dục
	public List<QuestionGroupModel> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<QuestionGroupModel> groupList) {
		this.groupList = groupList;
	}
	public String getTenSo_PhongGD() {
		return tenSo_PhongGD;
	}
	public void setTenSo_PhongGD(String tenSo_PhongGD) {
		this.tenSo_PhongGD = tenSo_PhongGD;
	}
	public String getKiKiemTra() {
		return kiKiemTra;
	}
	public void setKiKiemTra(String kiKiemTra) {
		this.kiKiemTra = kiKiemTra;
	}
	public String getHocKy() {
		return hocKy;
	}
	public void setHocKy(String hocKy) {
		this.hocKy = hocKy;
	}
	public String getNamHoc() {
		return namHoc;
	}
	public void setNamHoc(String namHoc) {
		this.namHoc = namHoc;
	}
	public String getMonHoc() {
		return monHoc;
	}
	public void setMonHoc(String monHoc) {
		this.monHoc = monHoc;
	}
	public String getThoiGianLamBai() {
		return thoiGianLamBai;
	}
	public void setThoiGianLamBai(String thoiGianLamBai) {
		this.thoiGianLamBai = thoiGianLamBai;
	}
	
	public void setTenTruong(String tenTruong) {
		this.tenTruong = tenTruong;
	}
	public String getTenTruong() {
		return this.tenTruong;
	}
	
	public void setMaDe(String maDe) {
		this.maDe = maDe;
	}
	public String getMaDe() {
		return this.maDe;
	}
	
	public String kiKiemTra; // kì kiểm tra
	public String hocKy;
	public String namHoc; 
	public String monHoc; // mon hoc
	public String thoiGianLamBai; // thoi gian lam bai
	public String tenTruong;
	public String maDe;

	private List<QuestionModel> questionHaveMultiRightAnswerlist = new ArrayList<QuestionModel>();

	public List<QuestionModel> getQuestionHaveMultiRightAnswerlist() {
		return questionHaveMultiRightAnswerlist;
	}

	public void setQuestionHaveMultiRightAnswerlist(List<QuestionModel> questionHaveMultiRightAnswerlist) {
		this.questionHaveMultiRightAnswerlist = questionHaveMultiRightAnswerlist;
	}

	public void setExamInfo(String soGD, String tenTruong, String namHoc, String hocKy, String monKT, String thoiGian) {
		this.tenSo_PhongGD = soGD;
		this.tenTruong = tenTruong;
		this.namHoc = namHoc;
		this.hocKy = hocKy;
		this.monHoc = monKT;
		this.thoiGianLamBai = thoiGian;
	}
	
	public void clone(ExamModel another) {
		this.hocKy = another.getHocKy();
		this.kiKiemTra = another.getKiKiemTra();
		this.monHoc = another.getMonHoc();
		this.namHoc = another.getNamHoc();
		this.thoiGianLamBai = another.getThoiGianLamBai();
		this.tenTruong = another.getTenTruong();
		this.tenSo_PhongGD = another.getTenSo_PhongGD();
		
		
		
		for(QuestionGroupModel group : another.getGroupList()) {
			QuestionGroupModel newGroup = new QuestionGroupModel();
			newGroup.setGroupType(group.getGroupType());
			
			for(QuestionModel question : group.getQuestionList()) {
				QuestionModel newQuestion = new QuestionModel();
				newQuestion.setQuestionOrder(question.getQuestionOrder());
				newQuestion.setRightAnswerIndex(question.getRightAnswerIndex());
				
				for(AnswerModel answer : question.getAnswerList()) {
					AnswerModel newAnswer = new AnswerModel();
					newAnswer.setId(answer.getId());
					newAnswer.setRightAnswer(answer.getIsRightAnswer());
					newAnswer.setContent(answer.getContent());
					
					// add newAnswer to newQuestion answerList
					newQuestion.getAnswerList().add(newAnswer);
				}
				
				for(XWPFParagraph questionContent : question.getQuestionContentList()) {
					newQuestion.getQuestionContentList().add(questionContent);
				}
				
				// add newQuestion to newGroup questionList
				newGroup.getQuestionList().add(newQuestion);
			}
			
			for(XWPFParagraph groupInfo : group.getGroupInfoList()) {
				newGroup.getGroupInfoList().add(groupInfo);
			}
			
			// add newGroup to groupList
			this.groupList.add(newGroup);
		}
		
		// 1 exam: group list
		// 1 group: question list, groupInfo list
		// 1 question: answerList, content list
		// question content -> paragraph, groupInfo -> paragrap, 
		
	}
}
