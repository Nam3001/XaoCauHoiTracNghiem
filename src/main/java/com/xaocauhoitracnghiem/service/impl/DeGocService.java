package com.xaocauhoitracnghiem.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.xaocauhoitracnghiem.model.AnswerModel;
import com.xaocauhoitracnghiem.model.ExamModel;
import com.xaocauhoitracnghiem.model.QuestionGroupModel;
import com.xaocauhoitracnghiem.model.QuestionModel;
import com.xaocauhoitracnghiem.service.IDeGocService;
import com.xaocauhoitracnghiem.utils.ReadDeGoc;

import javax.servlet.http.HttpSession;

public class DeGocService implements IDeGocService {
	@Override
	public boolean isQuestionParagraph(XWPFParagraph p) {
		String paragraphContent = p.getText();
		String regex = "^(Câu|Question)\\s\\d+[:.].*";
		return paragraphContent.trim().matches(regex);
	}

	@Override
	// kiem tra xem doan do co phải là đoạn đề cập đến đáp án không
	public boolean isAnswerParagraph(XWPFParagraph p) {
		if (p.getNumFmt() == "upperLetter") {
			return true;
		}

		String paragraphContent = p.getText();
		String regex = "^[A-Z]\\..*";
		return paragraphContent.trim().matches(regex);
	}

	@Override
	public boolean isRightAnswerParagraph(XWPFParagraph p) {
		if (p.getNumFmt() == "upperLetter") {
			return false; // đáp án đúng là đáp án có gạch chân ở tên đáp án "A.", "B.", khi dùng
							// numbering thì chắc chắn sẽ không gạch chân nên đáp án đó sẽ ko phải là đáp án
							// đúng
		}

		if (!isAnswerParagraph(p))
			return false;

		XWPFRun run = p.getRuns().get(0);
		if (run.getUnderline().toString() == "SINGLE") {
			return true;
		}
		return false;
	}

	@Override
	public boolean isGroupQuestionParagraph(XWPFParagraph p) {
		return p.getText().trim().matches("^(<g[0-3]>).*");
	}

	@Override
	public ExamModel getExamData(String path) throws FileNotFoundException, IOException {
		ExamModel exam = new ExamModel();
		XWPFDocument doc = new XWPFDocument(new FileInputStream(path));

		// dùng để sử dụng khi paragraph không phải là group/question/answer. thì sẽ
		// thêm paragraph này vào group/question gần nhấn
		// nên chỉ dùng cho questionGroup hoặc question
		XWPFParagraph lastestElement = null;

		// để kiểm tra dòng đầu tiên
		// nếu dòng đầu tiên thì cho dù dòng đó có phải là group hay không thì cũng sẽ
		// tạo 1 group
		int countRow = 1;
		int questionCount = 1;
		int answerCount = 0;
		for (XWPFParagraph p : doc.getParagraphs()) {
//			if(p.getText().length() == 0) continue;
			if (countRow == 1 && !isGroupQuestionParagraph(p)) {
				QuestionGroupModel group = new QuestionGroupModel();
				group.setGroupType(0);
				exam.groupList.add(group);
			}

			if (isGroupQuestionParagraph(p)) {
				QuestionGroupModel group = new QuestionGroupModel();

				if (!p.getText().trim().matches("^(<g[0-3]>)")) {
					group.groupInfoList.add(p);
				}

				if (p.getText().trim().matches("^(<g0>)"))
					group.setGroupType(0);
				else if (p.getText().trim().matches("^(<g1>)"))
					group.setGroupType(1);
				else if (p.getText().trim().matches("^(<g2>)"))
					group.setGroupType(2);
				else if (p.getText().trim().matches("^(<g3>)")) {
					group.setGroupType(3);
				}

				exam.groupList.add(group);
				lastestElement = p;
			} else if (isQuestionParagraph(p)) {
				if (exam.groupList.size() > 0) {
					QuestionModel question = new QuestionModel();

					question.setQuestionOrder(questionCount);
					question.questionContentList.add(p);

					QuestionGroupModel lastGroup = exam.groupList.get(exam.groupList.size() - 1);
					lastGroup.questionList.add(question);

					lastestElement = p;
					answerCount = 0;
				}
			} else if (isAnswerParagraph(p)) {
				if (exam.groupList.size() > 0) {
					answerCount++;
					QuestionGroupModel lastGroup = exam.groupList.get(exam.groupList.size() - 1);
					QuestionModel lastQuestionOfLastGroup;
					if (lastGroup.questionList.size() > 0) {
						lastQuestionOfLastGroup = lastGroup.questionList.get(lastGroup.questionList.size() - 1);
						AnswerModel answer = new AnswerModel();
						answer.content = p;

						boolean rightAnswer = isRightAnswerParagraph(p);
						if (rightAnswer) {
							answer.isRightAnswer = true;
							lastQuestionOfLastGroup.setRightAnswerIndex(answerCount);
						}

						lastQuestionOfLastGroup.answerList.add(answer);

						int questionMultiRightAnsSize = exam.getQuestionHaveMultiRightAnswerlist().size();
						if ((rightAnswer && questionMultiRightAnsSize > 0 && exam.getQuestionHaveMultiRightAnswerlist().get(questionMultiRightAnsSize - 1) != lastQuestionOfLastGroup) || (rightAnswer && questionMultiRightAnsSize == 0)) {
							if(lastQuestionOfLastGroup.checkMultiRightAnswer()) {
								exam.getQuestionHaveMultiRightAnswerlist().add(lastQuestionOfLastGroup);
							}
						}
					}
				}

			} else {
				if (countRow == 1 || lastestElement == null) {
					exam.groupList.get(0).groupInfoList.add(p);
				} else {
					if (exam.groupList.size() > 0) {
						QuestionGroupModel lastGroup = exam.groupList.get(exam.groupList.size() - 1);
						if (isGroupQuestionParagraph(lastestElement)) {
							lastGroup.groupInfoList.add(p);
						} else if (isQuestionParagraph(lastestElement)) {
							QuestionModel lastQuestionOfLastGroup = lastGroup.questionList
									.get(lastGroup.questionList.size() - 1);
							lastQuestionOfLastGroup.questionContentList.add(p);
						}
					}
				}

			}
			// tang so dong len;
			countRow++;
		}

		for(QuestionModel question : exam.getQuestionHaveMultiRightAnswerlist()) {
			question.removeMultiRightAnswer();
		}
		doc.close();
		return exam;
	}

	@Override
	public int getQuestionCount(ExamModel exam) {
		// TODO Auto-generated method stub
		int res = 0;
		for (QuestionGroupModel group : exam.groupList) {
			res += group.questionList.size();
		}
		return res;
	}

	@Override
	public int getQuestionGroupCount(ExamModel exam) {
		return exam.groupList.size();
	}
}
