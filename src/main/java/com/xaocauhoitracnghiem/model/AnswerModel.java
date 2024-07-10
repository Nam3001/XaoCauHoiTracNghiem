package com.xaocauhoitracnghiem.model;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class AnswerModel {
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public XWPFParagraph getContent() {
		return content;
	}
	public void setContent(XWPFParagraph content) {
		this.content = content;
	}
	public boolean getIsRightAnswer() {
		return isRightAnswer;
	}
	public void setRightAnswer(boolean isRightAnswer) {
		this.isRightAnswer = isRightAnswer;
	}
	public String id;  // dap an A. B. C. D. ...
	public XWPFParagraph content; // noi dung cua dap an
	public boolean isRightAnswer; // check xem co phai la dap an dung khong
}
