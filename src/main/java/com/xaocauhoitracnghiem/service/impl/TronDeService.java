package com.xaocauhoitracnghiem.service.impl;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.xaocauhoitracnghiem.utils.GenerateExamDocxUtils;
import com.xaocauhoitracnghiem.utils.LoadPictureToExamDocxElementUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;

import com.xaocauhoitracnghiem.model.AnswerModel;
import com.xaocauhoitracnghiem.model.ExamModel;
import com.xaocauhoitracnghiem.model.QuestionGroupModel;
import com.xaocauhoitracnghiem.model.QuestionModel;
import com.xaocauhoitracnghiem.service.ITronDeService;
import com.xaocauhoitracnghiem.utils.CommonUtils;

public class TronDeService implements ITronDeService {
    @Override
    public List<ExamModel> tronDe(ExamModel deGoc, int soLuongDe, boolean coDinhNhom) {
        // TODO Auto-generated method stub
        List<ExamModel> dsDe = new ArrayList<ExamModel>();

        deGoc.setMaDe("000");
        dsDe.add(deGoc);

        for (int i = 1; i <= soLuongDe; i++) {
            ExamModel exam = new ExamModel();
            exam.clone(deGoc);

            String maDe = "";

            if (i < 10)
                maDe = "10" + i;
            else maDe = "1" + i;

            exam.setMaDe(maDe);

            for (QuestionGroupModel group : exam.getGroupList()) {
                if (group.getGroupType() == 0)
                    continue;

                for (QuestionModel question : group.getQuestionList()) {
                    if (group.getGroupType() == 3 || group.getGroupType() == 2) {
//						Collections.shuffle(question.getAnswerList());
                        CommonUtils.shuffle(question.getAnswerList());

                        // get right answer index
                        for (int j = 0; j < question.getAnswerList().size(); j++) { // loop through answerList
                            AnswerModel ans = question.getAnswerList().get(j);
                            if (ans.getIsRightAnswer()) {
                                question.setRightAnswerIndex(j + 1);
                                break;
                            }
                        }
                    }
                }

                if (group.getGroupType() == 3 || group.getGroupType() == 1) {
                    CommonUtils.shuffle(group.getQuestionList());
                }
            }

            if (!coDinhNhom) {
                CommonUtils.shuffle(exam.getGroupList());
            }
            dsDe.add(exam);
        }
        return dsDe;
    }

    public static void repairQuestionAndAnswerLabel(String path, boolean isEnglishExam, String tienToCauHoi) {
        if(!Files.exists(Paths.get(path))) return;
        // chỉnh sửa label cho câu hỏi và đáp án
        try {
            XWPFDocument document = new XWPFDocument(new FileInputStream(new File(path)));
            DeGocService deGocService = new DeGocService();
            int questionCount = 0;
            for (XWPFParagraph p : document.getParagraphs()) {
                boolean isQuesstion = deGocService.isQuestionParagraph(p);
                if (isQuesstion) {
                    questionCount++;
                    String content = "";

                    for (int i = 0; i < p.getRuns().size(); i++) {
                        XWPFRun run = p.getRuns().get(i);
                        if (!content.trim().matches("^(Câu|Question)\\s\\d+[:.].*")) {
                            content += run.getText(0);
                        } else {
                            content = content.replaceAll("^(Câu|Question)\\s\\d+[:.]", "");
                            if (content.trim().length() == 0) {
                                XWPFRun labelRun = p.getRuns().get(0);
                                String labelText = (isEnglishExam ? "Question " : "Câu ") + questionCount + tienToCauHoi + " ";
                                labelRun.setText(labelText, 0);
                                labelRun.setBold(true);

                                for (int j = 0; j < i; j++) {
                                    if (j == 0) continue;
                                    p.removeRun(j);
                                    j--;
                                    i--;
                                }

                            } else {
                                XWPFRun remainRun = p.getRuns().get(0);
                                String labelText = (isEnglishExam ? "Question " : "Câu ") + questionCount + tienToCauHoi + " ";
                                remainRun.setText(content, 0);

                                for (int j = 0; j < i; j++) {
                                    if (j == 0) continue;
                                    p.removeRun(j);
                                    j--;
                                    i--;
                                }
                                XWPFRun labelRun = p.insertNewRun(0);
                                labelRun.setText(labelText);
                                labelRun.setBold(true);
                            }

                            break;
                        }
                    }
                }
            }

            for (XWPFTable tbl : document.getTables()) {
                int answerCount = 0;
                for (XWPFTableRow row : tbl.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph p : cell.getParagraphs()) {
                            boolean isAnswerPara = deGocService.isAnswerParagraph(p);
                            if (isAnswerPara) {
                                answerCount++;
                                String content = "";

                                for (int i = 0; i < p.getRuns().size(); i++) {
                                    XWPFRun run = p.getRuns().get(i);
                                    if (!content.trim().matches("^[A-Z]\\..*")) {
                                        content += run.getText(0);
                                    } else {
                                        content = content.replaceAll("^[A-Z]\\.", "");
                                        if (content.trim().length() == 0) {
                                            XWPFRun labelRun = p.getRuns().get(0);
                                            String labelText = CommonUtils.numToLetterBySubstr(answerCount) + ". ";
                                            labelRun.setText(labelText, 0);
                                            labelRun.setBold(true);
                                            labelRun.setUnderline(UnderlinePatterns.NONE);

                                            for (int j = 0; j < i; j++) {
                                                if (j == 0) continue;
                                                p.removeRun(j);
                                                j--;
                                                i--;
                                            }
                                        } else {
                                            XWPFRun remainRun = p.getRuns().get(0);
                                            String labelText = CommonUtils.numToLetterBySubstr(answerCount) + ". ";
                                            remainRun.setText(content, 0);

                                            for (int j = 0; j < i; j++) {
                                                if (j == 0) continue;
                                                p.removeRun(j);
                                                j--;
                                                i--;
                                            }
                                            XWPFRun labelRun = p.insertNewRun(0);
                                            labelRun.setText(labelText);
                                            labelRun.setBold(true);
                                            labelRun.setUnderline(UnderlinePatterns.NONE);
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            document.write(new FileOutputStream(new File(path)));
            document.close();

        } catch (
                IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void generateAnswer(XWPFDocument document, QuestionModel question, int numberAnswerEachRow) {
        try {
            List<AnswerModel> answerList = question.getAnswerList();

            int amountOfRow = answerList.size() % numberAnswerEachRow == 0 ? answerList.size() / numberAnswerEachRow
                    : answerList.size() / numberAnswerEachRow + 1;

            XWPFTable tbl = document.createTable();
            tbl.setWidth("100%");
            tbl.getCTTbl().getTblPr().unsetTblBorders(); // hide table border

            int countAnswer = 0;

            tbl.removeRow(0);
            for (int i = 0; i < amountOfRow; i++) {
                XWPFTableRow row = tbl.createRow();
                if (i == 0) {
                    row = tbl.getRow(0);

                    row.removeCell(0);
                    for (int j = 0; j < numberAnswerEachRow; j++) {
                        if (countAnswer >= answerList.size())
                            break;

                        XWPFTableCell cell = row.createCell();

                        float cellWithPercent = 100 / numberAnswerEachRow;
                        cell.getParagraphArray(0).getCTP().set(answerList.get(countAnswer).getContent().getCTP());
                        cell.setWidth(cellWithPercent + "%");

                        countAnswer++;
                    }
                } else {
                    row = tbl.getRow(i);

                    for (int j = 0; j < numberAnswerEachRow; j++) {
                        if (countAnswer >= answerList.size())
                            break;

                        XWPFTableCell cell = row.getCell(j);

                        cell.getParagraphArray(0).getCTP().set(answerList.get(countAnswer).getContent().getCTP());
                        countAnswer++;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void generateExamWord(ExamModel exam, String path, String tienToCauHoi, boolean isEnglishExam) {
        List<List<XWPFPicture>> picListOfParagraphs = new ArrayList<List<XWPFPicture>>();
        List<List<List<XWPFPicture>>> picListOfTables = new ArrayList<List<List<XWPFPicture>>>();
        LoadPictureToExamDocxElementUtils.addXWPFPictureToList(exam, picListOfParagraphs, picListOfTables);

        // tạo ra file word exam mới
        try {
            XWPFDocument document = new XWPFDocument();

            GenerateExamDocxUtils.setPageMarin(document);
            GenerateExamDocxUtils.generateHeader(document, exam);
            document.createParagraph().createRun().setText("");
            GenerateExamDocxUtils.generateSubHeader(document, exam);
            int questionCount = 1;

            int paragraphIndex = 0;
            for (QuestionGroupModel group : exam.getGroupList()) {
                for (Object groupInfo : group.getGroupInfoList()) {
                    if (groupInfo instanceof XWPFParagraph) {
                        XWPFParagraph p = document.createParagraph();
                        p.getCTP().set(((XWPFParagraph) groupInfo).getCTP());
                    }
                    else if (groupInfo instanceof XWPFTable) {
                        XWPFTable tbl = document.createTable();
                        tbl.getCTTbl().set(((XWPFTable) groupInfo).getCTTbl());

                        tbl.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "333333");
                        tbl.setRightBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "333333");
                        tbl.setTopBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "333333");
                        tbl.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "333333");
                        tbl.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "333333");
                        tbl.setInsideVBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "333333");
                        tbl.setCellMargins(0, 100, 0, 0);
                    }
                }
                for (QuestionModel question : group.getQuestionList()) {
                    for (Object questionContent : question.getQuestionContentList()) {
                        if (questionContent instanceof XWPFParagraph) {
                            XWPFParagraph p = document.createParagraph();
                            p.getCTP().set(((XWPFParagraph) questionContent).getCTP());

                            p.setSpacingBefore(15);
                            p.setSpacingAfter(15);

                            for (XWPFRun run : p.getRuns()) {
                                run.setFontFamily("Times New Roman");
                                run.setFontSize(12);
                            }
                        }
                        else if (questionContent instanceof XWPFTable) {
                            XWPFTable tbl = document.createTable();
                            tbl.getCTTbl().set(((XWPFTable) questionContent).getCTTbl());

                            tbl.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "333333");
                            tbl.setRightBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "333333");
                            tbl.setTopBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "333333");
                            tbl.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "333333");
                            tbl.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "333333");
                            tbl.setInsideVBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "333333");
                            tbl.setCellMargins(0, 100, 0, 0);
                        }
                    }
                    int answerEachRow = 4;
                    for (AnswerModel ans : question.getAnswerList()) {
                        if (ans.getContent().getText().length() > 43) {
                            answerEachRow = 1;  // 1 dap an 1 hang
                            break;
                        } else if (ans.getContent().getText().length() > 20) {
                            answerEachRow = 2;
                        }
                    }
                    generateAnswer(document, question, answerEachRow);
                }
            }

            document.createParagraph().createRun().setText("");
            XWPFParagraph endExamParagraph = document.createParagraph();
            endExamParagraph.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun endExamRun = endExamParagraph.createRun();
            endExamRun.setText("___Hết___");
            endExamRun.setFontFamily("Times New Roman");
            endExamRun.setFontSize(14);
            endExamRun.setItalic(true);
            GenerateExamDocxUtils.generatePageNumber(document, exam.getMaDe());

            if (!Files.exists(Paths.get(path))) {
                Files.createFile(Paths.get(path));
            }
            File f = new File(path);
            FileOutputStream out = new FileOutputStream(f);
            document.write(out);

            out.close();
            document.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // chỉnh sửa label cho câu hỏi và đáp án
        repairQuestionAndAnswerLabel(path, isEnglishExam, tienToCauHoi);

        // write hình ảnh
        LoadPictureToExamDocxElementUtils.writePictureToDocxElement(path, picListOfParagraphs, picListOfTables);
    }

    @Override
    public void generateRightAnswerExcel(List<ExamModel> dsDe, String excelPath) {
        Workbook workbook;

        if (excelPath.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelPath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("file khong phai excel");
        }

        Sheet sheet = workbook.createSheet();
        CellStyle cellStyleCenter = workbook.createCellStyle();
        cellStyleCenter.setAlignment(HorizontalAlignment.CENTER);

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i <= dsDe.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(cellStyleCenter);
            if (i == 0) {
                cell.setCellValue("Câu");
            } else {
                cell.setCellValue(dsDe.get(i - 1).getMaDe());
            }
        }

        int questionCount = 1;
        int rowCount = 1;
        for (QuestionGroupModel group : dsDe.get(0).getGroupList()) {
            for (QuestionModel question : group.getQuestionList()) {
                Row row = sheet.createRow(rowCount);
                Cell questionOrderCell = row.createCell(0);
                questionOrderCell.setCellValue("Câu " + questionCount + ":");
//                questionOrderCell.setCellStyle(cellStyleCenter);

                Cell dapAnDeGocCell = row.createCell(1);
                if (question.getRightAnswerIndex() == 0)
                    dapAnDeGocCell.setCellValue("_");
                else
                    dapAnDeGocCell.setCellValue(CommonUtils.numToLetterBySubstr(question.getRightAnswerIndex()));
                dapAnDeGocCell.setCellStyle(cellStyleCenter);

                questionCount++;
                rowCount++;
            }
        }

        for (int i = 1; i < dsDe.size(); i++) {
            rowCount = 1;
            ExamModel exam = dsDe.get(i);
            for (QuestionGroupModel group : exam.getGroupList()) {
                for (QuestionModel question : group.getQuestionList()) {
                    Row row = sheet.getRow(rowCount);
                    Cell answerCell = row.createCell(i + 1);
                    answerCell.setCellStyle(cellStyleCenter);

                    if (question.getRightAnswerIndex() == 0) {
                        answerCell.setCellValue("_");
                    } else {
                        answerCell.setCellValue(CommonUtils.numToLetterBySubstr(question.getRightAnswerIndex()));
                    }
                    rowCount++;
                }
            }
        }

        if (!Files.exists(Paths.get(excelPath))) {
            try {
                Files.createFile(Paths.get(excelPath));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            FileOutputStream out = new FileOutputStream(new File(excelPath));
            workbook.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            workbook.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
