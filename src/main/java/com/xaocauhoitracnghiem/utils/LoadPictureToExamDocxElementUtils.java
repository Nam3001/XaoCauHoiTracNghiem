package com.xaocauhoitracnghiem.utils;

import com.xaocauhoitracnghiem.model.AnswerModel;
import com.xaocauhoitracnghiem.model.ExamModel;
import com.xaocauhoitracnghiem.model.QuestionGroupModel;
import com.xaocauhoitracnghiem.model.QuestionModel;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoadPictureToExamDocxElementUtils {
    // sử dụng XWPFPicture data để write hình ảnh vào element(các paragraphs và tables) của file docx(đề được tạo ra)
    public static void writePictureToDocxElement(String path, List<List<XWPFPicture>> picListOfParagraphs, List<List<List<XWPFPicture>>> picListOfTables) {
        // write hình ảnh
        if(!Files.exists(Paths.get(path))) return;

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
            System.out.println(e.getMessage());
        } catch (InvalidFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addXWPFPictureToList(ExamModel exam, List<List<XWPFPicture>> picListOfParagraphs, List<List<List<XWPFPicture>>> picListOfTables) {
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
    }
}
