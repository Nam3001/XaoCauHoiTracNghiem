package com.xaocauhoitracnghiem.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;

import com.xaocauhoitracnghiem.model.AnswerModel;
import com.xaocauhoitracnghiem.model.ExamModel;
import com.xaocauhoitracnghiem.model.QuestionGroupModel;
import com.xaocauhoitracnghiem.model.QuestionModel;
import com.xaocauhoitracnghiem.service.ITronDeService;
import com.xaocauhoitracnghiem.utils.CommonUtils;
import com.xaocauhoitracnghiem.utils.MathMLToEquation;

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

	private void generateAnswer(XWPFDocument document, QuestionModel question, int numberQuestionEachRow) {
		try {
			List<AnswerModel> answerList = question.answerList;

			int amountOfRow = answerList.size() % numberQuestionEachRow == 0 ? answerList.size() / numberQuestionEachRow
					: answerList.size() / numberQuestionEachRow + 1;

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
					for (int j = 0; j < numberQuestionEachRow; j++) {
						if (countAnswer >= answerList.size())
							break;

						XWPFTableCell cell = row.createCell();

						XWPFRun labelRun = cell.getParagraphs().get(0).createRun();
						labelRun.setText(CommonUtils.numToLetterBySubstr(countAnswer + 1) + ". ");
						labelRun.setBold(true);
						MathMLToEquation.CopyParagraphWithEquation(answerList.get(countAnswer).getContent(),
								cell.getParagraphs().get(0));
						countAnswer++;

						for (XWPFParagraph p : cell.getParagraphs()) {
							for (XWPFRun run : p.getRuns()) {
								run.setFontFamily("Times New Roman");
								run.setFontSize(12);
								p.setSpacingBefore(10);
								p.setSpacingAfter(10);
							}

						}
					}
				} else {
					row = tbl.getRow(i);

					for (int j = 0; j < numberQuestionEachRow; j++) {
						if (countAnswer >= answerList.size())
							break;

						XWPFTableCell cell = row.getCell(j);

						XWPFRun labelRun = cell.getParagraphs().get(0).createRun();
						labelRun.setText(CommonUtils.numToLetterBySubstr(countAnswer + 1) + ". ");
						labelRun.setBold(true);
						MathMLToEquation.CopyParagraphWithEquation(answerList.get(countAnswer).getContent(),
								cell.getParagraphs().get(0));
						countAnswer++;

						for (XWPFParagraph p : cell.getParagraphs()) {
							for (XWPFRun run : p.getRuns()) {
								run.setFontFamily("Times New Roman");
								run.setFontSize(12);
								p.setSpacingBefore(10);
								p.setSpacingAfter(10);
							}

						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
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
	public String generateExamWord(ExamModel exam, String path, String tienToCauHoi, boolean isEnglishExam) throws IOException {
		XWPFDocument document = new XWPFDocument();

		setPageMarin(document);
		generateHeader(document, exam);
		document.createParagraph().createRun().setText("");
		generateSubHeader(document, exam);
		int questionCount = 1;

		for (QuestionGroupModel group : exam.getGroupList()) {
			String info = "";
			for (XWPFParagraph groupInfo : group.getGroupInfoList()) {
				info += groupInfo.getText();
				XWPFParagraph p = document.createParagraph();
				MathMLToEquation.CopyParagraphWithEquation(groupInfo, p);
				p.setSpacingBefore(130);
				p.setSpacingAfter(15);

				for (XWPFRun run : p.getRuns()) {
					run.setFontFamily("Times New Roman");
					run.setFontSize(12);
					run.setBold(true);
				}
			}
			for (QuestionModel question : group.getQuestionList()) {
				// chỉ thêm chữ Câu x: cho phần đầu nên dùng biến đếm để kiểm tra
				int count = 1;
				for (XWPFParagraph questionContent : question.getQuestionContentList()) {
					XWPFParagraph p = document.createParagraph();
					XWPFRun Questionrun = p.createRun();
					Questionrun.setBold(true);
					if (count == 1) {
						if (isEnglishExam)
							Questionrun.setText("Question " + questionCount + tienToCauHoi + " ");
						else
							Questionrun.setText("Câu " + questionCount + tienToCauHoi + " ");

						questionCount++;
						count++;
					}
					MathMLToEquation.CopyParagraphWithEquation(questionContent, p);

					p.setSpacingBefore(15);
					p.setSpacingAfter(15);

					for (XWPFRun run : p.getRuns()) {
						run.setFontFamily("Times New Roman");
						run.setFontSize(12);
					}
				}
				generateAnswer(document, question, 2);
			}
		}
		
//		document.createParagraph().createRun().setText("");
		XWPFParagraph endExamParagraph = document.createParagraph();
		endExamParagraph.setAlignment(ParagraphAlignment.CENTER);
		
		XWPFRun endExamRun = endExamParagraph.createRun();
		endExamRun.setText("___Hết___");
		endExamRun.setFontSize(14);
		endExamRun.setItalic(true);
		generatePageNumber(document, exam.getMaDe());

		if (!Files.exists(Paths.get(path))) {
			Files.createFile(Paths.get(path));
		}
		File f = new File(path);
		FileOutputStream out = new FileOutputStream(f);
		document.write(out);
		document.close();
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
			
			if(i < 10)
				maDe = "10" + i;
			else maDe = "1" + i;
			
			exam.setMaDe(maDe);

			for (QuestionGroupModel group : exam.getGroupList()) {
				if (group.getGroupType() == 0)
					continue;

				for (QuestionModel question : group.getQuestionList()) {
					if (group.getGroupType() == 3 || group.getGroupType() == 2) {
						Collections.shuffle(question.getAnswerList());

						// get right answer index
						for(int j = 0; j < question.getAnswerList().size(); j++) { // loop through answerList
							AnswerModel ans = question.getAnswerList().get(j);
							if(ans.getIsRightAnswer()) {
								question.setRightAnswerIndex(j+1);
								break;
							}
						}
					}
				}

				if (group.getGroupType() == 3 || group.getGroupType() == 1)
					Collections.shuffle(group.getQuestionList());
			}
			
			if(!coDinhNhom) {
				Collections.shuffle(exam.getGroupList());
			}

			dsDe.add(exam);
		}
		return dsDe;
	}

	@Override
	public void generateRightAnswerExcel(List<ExamModel> dsDe, String excelPath) {
		Workbook workbook;

		if(excelPath.endsWith(".xlsx")) {
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
		for(int i = 0; i <= dsDe.size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellStyle(cellStyleCenter);
			if(i == 0) {
				cell.setCellValue("Câu");
			} else {
				cell.setCellValue(dsDe.get(i-1).getMaDe());
			}
		}

		int questionCount = 1;
		int rowCount = 1;
		for(QuestionGroupModel group : dsDe.get(0).getGroupList()) {
			for(QuestionModel question : group.getQuestionList()) {
				Row row = sheet.createRow(rowCount);
				Cell questionOrderCell = row.createCell(0);
				questionOrderCell.setCellValue(questionCount);
				questionOrderCell.setCellStyle(cellStyleCenter);

				Cell dapAnDeGocCell = row.createCell(1);
				if(question.getRightAnswerIndex() == 0)
					dapAnDeGocCell.setCellValue("_");
				else
					dapAnDeGocCell.setCellValue(CommonUtils.numToLetterBySubstr(question.getRightAnswerIndex()));
				dapAnDeGocCell.setCellStyle(cellStyleCenter);

				questionCount++;
				rowCount++;
			}
		}

		for(int i = 1; i < dsDe.size(); i++) {
			rowCount = 1;
			ExamModel exam = dsDe.get(i);
			for(QuestionGroupModel group : exam.getGroupList()) {
				for(QuestionModel question : group.getQuestionList()) {
					Row row = sheet.getRow(rowCount);
					Cell answerCell = row.createCell(i+1);
					answerCell.setCellStyle(cellStyleCenter);

					if(question.getRightAnswerIndex() == 0) {
						answerCell.setCellValue("_");
					} else {
						answerCell.setCellValue(CommonUtils.numToLetterBySubstr(question.getRightAnswerIndex()));
					}
					rowCount++;
				}
			}
		}

		if(!Files.exists(Paths.get(excelPath))) {
			try {
				Files.createFile(Paths.get(excelPath));
			} catch (IOException e) {
				System.out.println(e.getStackTrace());
			}
		}

		try {
			FileOutputStream out = new FileOutputStream(new File(excelPath));
			workbook.write(out);
		} catch(FileNotFoundException e) {
			System.out.println(e.getStackTrace());
		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		}
	}
}
