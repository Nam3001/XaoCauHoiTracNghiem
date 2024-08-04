package com.xaocauhoitracnghiem.utils;

import com.xaocauhoitracnghiem.model.AnswerModel;
import com.xaocauhoitracnghiem.model.ExamModel;
import com.xaocauhoitracnghiem.model.QuestionModel;
import com.xaocauhoitracnghiem.service.impl.DeGocService;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GenerateExamDocxUtils {
    public static void setPageMarin(XWPFDocument document) {
        // set page margin
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        pageMar.setLeft(BigInteger.valueOf(1080L));
        pageMar.setTop(BigInteger.valueOf(720L));
        pageMar.setRight(BigInteger.valueOf(1080L));
        pageMar.setBottom(BigInteger.valueOf(1440L));
    }

    public static void generateHeader(XWPFDocument document, ExamModel exam) throws IOException {
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

    public static void generateSubHeader(XWPFDocument document, ExamModel exam) throws IOException {
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

    public static void generatePageNumber(XWPFDocument doc, String maDe) {
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


}
