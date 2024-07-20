package com.xaocauhoitracnghiem.model;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class OriginalExamModel extends ExamModel {
    private XWPFDocument documentOfExam;

    public XWPFDocument getDocumentOfExam() {
        return documentOfExam;
    }

    public void setDocumentOfExam(XWPFDocument documentOfExam) {
        this.documentOfExam = documentOfExam;
    }

    public OriginalExamModel() {
        super();
        this.documentOfExam = new XWPFDocument();
    }
}
