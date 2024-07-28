package com.xaocauhoitracnghiem.service.impl;

import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import com.xaocauhoitracnghiem.model.AnswerModel;
import com.xaocauhoitracnghiem.model.ExamModel;
import com.xaocauhoitracnghiem.model.QuestionGroupModel;
import com.xaocauhoitracnghiem.model.QuestionModel;
import com.xaocauhoitracnghiem.service.ITronDeService;
import com.xaocauhoitracnghiem.utils.CommonUtils;

import javax.imageio.ImageIO;
public class TronDeService implements ITronDeService {
    private void setPageMarin(XWPFDocument document) {
        // set page margin
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        pageMar.setLeft(BigInteger.valueOf(1080L));
        pageMar.setTop(BigInteger.valueOf(720L));
        pageMar.setRight(BigInteger.valueOf(1080L));
        pageMar.setBottom(BigInteger.valueOf(1440L));
    }

    private void generateHeader(XWPFDocument document, ExamModel exam) throws IOException {
        XWPFTable tbl = document.createTable();
        tbl.setWidth("100%");
        tbl.getCTTbl().getTblPr().unsetTblBorders(); // hide table border

        XWPFTableRow row1 = tbl.getRow(0);
        XWPFTableCell cell1 = row1.getCell(0);
        XWPFParagraph p11 = cell1.addParagraph();
        XWPFParagraph p12 = cell1.addParagraph();
        XWPFParagraph p13 = cell1.addParagraph();
        XWPFParagraph p14 = cell1.addParagraph();

        cell1.setText(exam.tenSo_PhongGD);

        XWPFRun runOfP11 = p11.createRun();
        runOfP11.setText(exam.getTenTruong());
        runOfP11.setBold(true);

        p12.createRun().setText("");
        p13.createRun().setText("--------------------");

        XWPFRun runOfP4 = p14.createRun();
        runOfP4.setText("(Đề thi có ____ trang)");
        runOfP4.setItalic(true);

        XWPFTableCell cell2 = row1.createCell();
        cell2.setText(exam.kiKiemTra);
        for (XWPFRun run : cell2.getParagraphs().get(0).getRuns()) {
            run.setBold(true);
        }

        XWPFParagraph p21 = cell2.addParagraph();
        XWPFParagraph p22 = cell2.addParagraph();
        XWPFParagraph p23 = cell2.addParagraph();
        XWPFParagraph p24 = cell2.addParagraph();

        XWPFRun runOfP21 = p21.createRun();
        runOfP21.setText("NĂM HỌC " + exam.getNamHoc());
        runOfP21.setBold(true);

        XWPFRun runOfP22 = p22.createRun();
        runOfP22.setText("MÔN: " + exam.getMonHoc());
        runOfP22.setBold(true);

        XWPFRun runOfP23 = p23.createRun();
        runOfP23.setText("Thời gian làm bài: " + exam.getThoiGianLamBai());
        runOfP23.setItalic(true);

        XWPFRun runOfP24 = p24.createRun();
        runOfP24.setText("(không kể thời gian phát đề)");
        runOfP24.setItalic(true);

        for (XWPFTableCell cell : row1.getTableCells()) {
            cell.setWidth("50%");
            for (XWPFParagraph p : cell.getParagraphs()) {
                p.setAlignment(ParagraphAlignment.CENTER);
                p.setSpacingBefore(15);
                p.setSpacingAfter(15);

                for (XWPFRun run : p.getRuns()) {
                    run.setFontFamily("Times New Roman");
                    run.setFontSize(12);
                }

            }
        }
    }

    private void generateSubHeader(XWPFDocument document, ExamModel exam) throws IOException {
        XWPFTable tbl = document.createTable();
        tbl.setWidth("100%");
        tbl.getCTTbl().getTblPr().unsetTblBorders();

        XWPFTableRow row = tbl.getRow(0);
        XWPFTableCell cell1 = row.getCell(0);
        XWPFTableCell cell2 = row.addNewTableCell();
        XWPFTableCell cell3 = row.addNewTableCell();

        cell1.setText("Họ và tên: ................................................................");
        cell2.setText("Số báo danh: ...............");
        cell3.setText("Mã đề " + exam.getMaDe());

        // bold mã đề
        for (XWPFRun run : cell3.getParagraphs().get(0).getRuns()) {
            run.setBold(true);
        }

        for (XWPFTableCell cell : row.getTableCells()) {
            for (XWPFParagraph p : cell.getParagraphs()) {
                for (XWPFRun run : p.getRuns()) {
                    run.setFontFamily("Times New Roman");
                    run.setFontSize(12);
                }

            }
        }

        tbl.setTopBorder(null, 0, 0, null);
        tbl.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE, 10, 0, "000000");
        tbl.setLeftBorder(null, 0, 0, null);
        tbl.setRightBorder(null, 0, 0, null);
        tbl.setInsideHBorder(null, 0, 0, null);
        tbl.setInsideVBorder(null, 0, 0, null);
    }

    private void generateAnswer(XWPFDocument document, QuestionModel question, int numberAnswerEachRow) {
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
                        cell.setWidth(cellWithPercent + "%");

                        cell.getParagraphArray(0).getCTP().set(answerList.get(countAnswer).getContent().getCTP());
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
            System.out.println(e.getStackTrace());
        }
    }


    public void generatePageNumber(XWPFDocument doc, String maDe) {
        XWPFFooter footer = doc.createFooter(HeaderFooterType.DEFAULT);

        XWPFTable tbl = footer.createTable(1, 2);
        tbl.setWidth("100%");
        tbl.getCTTbl().getTblPr().unsetTblBorders();
        XWPFTableRow row = tbl.getRow(0);

        XWPFParagraph pageParagraph = row.getCell(0).getParagraphs().get(0);
        pageParagraph.setAlignment(ParagraphAlignment.START);

        // Add page number field
        XWPFRun footerRun = pageParagraph.createRun();
        footerRun.setText("Trang ");
        footerRun.getCTR().addNewFldChar().setFldCharType(STFldCharType.BEGIN);
        footerRun.getCTR().addNewInstrText().setStringValue("PAGE \\* MERGEFORMAT");
        footerRun.getCTR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);
        footerRun = pageParagraph.createRun();
        footerRun.setText("1");
        footerRun.getCTR().addNewFldChar().setFldCharType(STFldCharType.END);

        footerRun = pageParagraph.createRun();
        footerRun.setText("/");
        footerRun.getCTR().addNewFldChar().setFldCharType(STFldCharType.BEGIN);
        footerRun.getCTR().addNewInstrText().setStringValue("NUMPAGES \\* MERGEFORMAT");
        footerRun.getCTR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);
        footerRun = pageParagraph.createRun();
        footerRun.setText("1");
        footerRun.getCTR().addNewFldChar().setFldCharType(STFldCharType.END);

        XWPFParagraph maDeParagraph = row.createCell().getParagraphs().get(0);
        maDeParagraph.setAlignment(ParagraphAlignment.END);
        XWPFRun maDeRun = maDeParagraph.createRun();
        maDeRun.setBold(true);
        maDeRun.setText("Mã đề " + maDe);
    }

    @Override
    public String generateExamWord(ExamModel exam, String path, String tienToCauHoi, boolean isEnglishExam) {
        List<List<XWPFPicture>> picListOfParagraphs = new ArrayList<List<XWPFPicture>>();
        List<List<List<XWPFPicture>>> picListOfTables = new ArrayList<List<List<XWPFPicture>>>();

        // picture của các đáp án, và các table vào 1 list, picture của các phần còn lại (groupInfo, questionContent) vào 1 list
        for (int i = 0; i < exam.getGroupList().size(); i++) {
            QuestionGroupModel group = exam.getGroupList().get(i);
            for (int j = 0; j < group.getGroupInfoList().size(); j++) {
                Object grpInfo = group.getGroupInfoList().get(j);
                if (grpInfo instanceof XWPFParagraph) {
                    List<XWPFRun> runList = ((XWPFParagraph) grpInfo).getRuns();
                    List<XWPFPicture> lst = new ArrayList<XWPFPicture>();
                    for (XWPFRun run : runList) {
                        lst.addAll(run.getEmbeddedPictures());
                    }
                    if (lst.size() > 0) {
                        picListOfParagraphs.add(lst);
                    }
                }
                else if (grpInfo instanceof XWPFTable) {
                    XWPFTable tbl = (XWPFTable) grpInfo;
                    List<List<XWPFPicture>> picListOfATable = new ArrayList<>();

                    for(XWPFTableRow row : tbl.getRows()) {
                        for(XWPFTableCell cell : row.getTableCells()) {
                            List<XWPFPicture> pictureOfEachCell = new ArrayList<>();
                            for(XWPFParagraph p : cell.getParagraphs()) {
                                for(XWPFRun run : p.getRuns()) {
                                    pictureOfEachCell.addAll(run.getEmbeddedPictures());
                                }
                            }
                            picListOfATable.add(pictureOfEachCell);
                        }
                    }

                    picListOfTables.add(picListOfATable);
                }
            }

            for (int j = 0; j < group.getQuestionList().size(); j++) {
                QuestionModel question = group.getQuestionList().get(j);
                for (int k = 0; k < question.getQuestionContentList().size(); k++) {
                    Object questionContent = question.getQuestionContentList().get(k);
                    if (questionContent instanceof XWPFParagraph) {
                        List<XWPFRun> runList = ((XWPFParagraph) questionContent).getRuns();
                        List<XWPFPicture> lst = new ArrayList<XWPFPicture>();
                        for (XWPFRun run : runList) {
                            lst.addAll(run.getEmbeddedPictures());
                        }
                        if (lst.size() > 0) {
                            picListOfParagraphs.add(lst);
                        }
                    }
                    else if (questionContent instanceof XWPFTable) {
                        XWPFTable tbl = (XWPFTable) questionContent;
                        List<List<XWPFPicture>> picListOfATable = new ArrayList<>();

                        for(XWPFTableRow row : tbl.getRows()) {
                            for(XWPFTableCell cell : row.getTableCells()) {
                                List<XWPFPicture> pictureOfEachCell = new ArrayList<>();
                                for(XWPFParagraph p : cell.getParagraphs()) {
                                    for(XWPFRun run : p.getRuns()) {
                                        pictureOfEachCell.addAll(run.getEmbeddedPictures());
                                    }
                                }
                                picListOfATable.add(pictureOfEachCell);
                            }
                        }

                        picListOfTables.add(picListOfATable);
                    }
                }
                // add picture to pictureList Of AnswerTable
                List<List<XWPFPicture>> picListOfAQuestionAnswers = new ArrayList<>();
                // loop answer
                for (AnswerModel ans : question.getAnswerList()) {
                    XWPFParagraph AnswerContent = ans.getContent();
                    List<XWPFPicture> picListOfEachAnswer = new ArrayList<>();
                    for (XWPFRun run : AnswerContent.getRuns()) {
                        picListOfEachAnswer.addAll(run.getEmbeddedPictures());
                    }
                    picListOfAQuestionAnswers.add(picListOfEachAnswer);
                }
                picListOfTables.add(picListOfAQuestionAnswers);
            }
        }


        // tạo ra file word exam mới
        try {
            XWPFDocument document = new XWPFDocument();

            setPageMarin(document);
            generateHeader(document, exam);
            document.createParagraph().createRun().setText("");
            generateSubHeader(document, exam);
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
                    // chỉ thêm chữ Câu x: cho phần đầu nên dùng biến đếm để kiểm tra
                    int count = 1;
                    for (Object questionContent : question.getQuestionContentList()) {
                        if (questionContent instanceof XWPFParagraph) {
                            XWPFParagraph p = document.createParagraph();
                            p.getCTP().set(((XWPFParagraph) questionContent).getCTP());

                            XWPFRun Questionrun = p.createRun();
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
                        if (ans.getContent().getText().length() > 40) {
                            answerEachRow = 1;  // 1 dap an 1 hang
                            break;
                        } else if (ans.getContent().getText().length() > 20) {
                            answerEachRow = 2;
                        } else answerEachRow = 4;
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
            generatePageNumber(document, exam.getMaDe());

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


        // write hình ảnh
        try {
            XWPFDocument document = new XWPFDocument(new FileInputStream(new File(path)));

            int countParagraphHavePicture = 0;
            int countParagraph = 0;
            int countTable = 0;
            for (Object element : document.getBodyElements()) {
                if (element instanceof XWPFParagraph) {
                    XWPFParagraph p = (XWPFParagraph) element;
                    if (countParagraph < 1) {
                        countParagraph++;
                        continue;
                    }

                    int pictureCount = 0;
                    for (int i = 0; i < p.getRuns().size(); i++) {
                        if (p.getRuns().get(i).getText(0) == null && p.getRuns().get(i).getEmbeddedPictures().size() > 0) {
                            // trường hợp 1 paragraph có nhiều picture
                            if (pictureCount <= 0) {
                                countParagraphHavePicture++;
                            }

                            // tăng pictureCount trước, khi lấy ảnh thì trừ lui 1
                            List<XWPFPicture> picLst = picListOfParagraphs.get(countParagraphHavePicture - 1);

                            if (picLst.size() <= 0) continue;

                            XWPFRun run = p.getRuns().get(i);
                            CTR ctr = run.getCTR();

                            // Check for CTDrawing elements and remove them
                            for (int j = 0; j < ctr.sizeOfDrawingArray(); j++) {
                                if (ctr.getDrawingArray(j) != null) {
                                    ctr.removeDrawing(j);
                                }
                            }

                            for (int j = 0; j < ctr.sizeOfPictArray(); j++) {
                                if (ctr.getPictArray(j) != null) {
                                    ctr.removePict(j);
                                }
                            }


                            XWPFPictureData picData = picLst.get(pictureCount).getPictureData();
                            InputStream rawPicData = new ByteArrayInputStream(picData.getData());
                            int pictureType = picData.getPictureType();

                            // set default width and height
                            double ratio = (double) 500 / 300;
                            int imageWidth = 400;
                            int imageHeight = (int) (imageWidth * ratio);

                            BufferedImage imgBuffer = ImageIO.read(new ByteArrayInputStream(picData.getData()));

                            imageWidth = (int) picLst.get(pictureCount).getCTPicture().getSpPr().getXfrm().getExt().getCx() / 14000;
                            imageHeight = (int) picLst.get(pictureCount).getCTPicture().getSpPr().getXfrm().getExt().getCy() / 14000;
//
                            File tempImageFile = File.createTempFile("image", "jpg");
                            FileOutputStream tempOut = new FileOutputStream(tempImageFile);
                            tempOut.write(picData.getData());
                            tempOut.close();



                            run.addPicture(rawPicData, pictureType, "image.jpg", Units.toEMU(imageWidth),
                                    Units.toEMU(imageHeight));
                            pictureCount++;
                        }
                    }

                    countParagraph++;
                }
                else if (element instanceof XWPFTable) {
                    XWPFTable tbl = (XWPFTable) element;
                    if (countTable < 2) {
                        countTable++;
                        continue;
                    }

                    List<List<XWPFPicture>> picListOfEachTable = picListOfTables.get(countTable - 2);

                    if(picListOfEachTable.size() == 0) {
                        countTable++;
                        continue;
                    }

                    int countCell = 0;
                    for (XWPFTableRow row : tbl.getRows()) {
                        for (XWPFTableCell cell : row.getTableCells()) {

//                            if(picListOfEachTable.size() == 0) {
//                                continue;
//                            }
                            if(countCell >= picListOfEachTable.size()) continue;
                            List<XWPFPicture> picListOfEachCell = picListOfEachTable.get(countCell); // mỗi cell chứa 1 đáp án

                            if (picListOfEachCell.size() <= 0) {
                                countCell++;
                                continue;
                            }
                            int countPictureOfEachCell = 0;
                            for(XWPFParagraph paragraph : cell.getParagraphs()) {
                                for (int i = 0; i < paragraph.getRuns().size(); i++) {
                                    XWPFRun runOfAnswerContent = paragraph.getRuns().get(i);

                                    if (runOfAnswerContent.getText(0) == null && runOfAnswerContent.getEmbeddedPictures().size() > 0 && countPictureOfEachCell < picListOfEachCell.size()) {

                                        CTR ctr = runOfAnswerContent.getCTR();

                                        // Check for CTDrawing elements and remove them
                                        for (int j = 0; j < ctr.sizeOfDrawingArray(); j++) {
                                            if (ctr.getDrawingArray(j) != null) {
                                                ctr.removeDrawing(j);
                                            }
                                        }

                                        for (int j = 0; j < ctr.sizeOfPictArray(); j++) {
                                            if (ctr.getPictArray(j) != null) {
                                                ctr.removePict(j);
                                            }
                                        }

                                        XWPFPictureData picData = picListOfEachCell.get(countPictureOfEachCell).getPictureData();
                                        InputStream rawPicData = new ByteArrayInputStream(picData.getData());
                                        int pictureType = picData.getPictureType();

                                        // set default width and height
                                        double ratio = (double) 500 / 300;
                                        int imageWidth = 400;
                                        int imageHeight = (int) (imageWidth * ratio);

                                        BufferedImage imgBuffer = ImageIO.read(new ByteArrayInputStream(picData.getData()));

                                        imageWidth = (int) picListOfEachCell.get(countPictureOfEachCell).getCTPicture().getSpPr().getXfrm().getExt().getCx() / 14000;
                                        imageHeight = (int) picListOfEachCell.get(countPictureOfEachCell).getCTPicture().getSpPr().getXfrm().getExt().getCy() / 14000;

                                        File tempImageFile = File.createTempFile("image", "jpg");
                                        FileOutputStream tempOut = new FileOutputStream(tempImageFile);
                                        tempOut.write(picData.getData());
                                        tempOut.close();



                                        runOfAnswerContent.addPicture(rawPicData, pictureType, "image.jpg", Units.toEMU(imageWidth),
                                                Units.toEMU(imageHeight));

                                        countPictureOfEachCell++;
                                    }
                                }
                            }
                            countCell++;
                        }
                    }

                    countTable++;
                }
            }

            document.write(new FileOutputStream(new File(path)));
            document.close();
        } catch (IOException e) {
            System.out.println(e.getStackTrace().toString());
        } catch (InvalidFormatException e) {
            System.out.println(e.getStackTrace().toString());
        }

        return null;
    }

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
                questionOrderCell.setCellValue(questionCount);
                questionOrderCell.setCellStyle(cellStyleCenter);

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
                System.out.println(e.getStackTrace());
            }
        }

        try {
            FileOutputStream out = new FileOutputStream(new File(excelPath));
            workbook.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getStackTrace());
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
        try {
            workbook.close();
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }
}
