package com.xaocauhoitracnghiem.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import com.xaocauhoitracnghiem.model.AnswerModel;
import com.xaocauhoitracnghiem.model.ExamModel;
import com.xaocauhoitracnghiem.model.QuestionGroupModel;
import com.xaocauhoitracnghiem.model.QuestionModel;
import com.xaocauhoitracnghiem.service.IDeGocService;
import com.xaocauhoitracnghiem.utils.ReadDeGoc;

public class DeGocService implements IDeGocService {
	@Override
	public ExamModel getExamData(String path) throws FileNotFoundException, IOException {
		ExamModel exam = new ExamModel();
		XWPFDocument doc = new XWPFDocument(new FileInputStream(path));

		// dùng để sử dụng khi paragraph không phải là group/question/answer. thì sẽ
		// thêm paragraph này vào group/question gần nhấn
		// nên chỉ dùng cho questionGroup hoặc question
		XWPFParagraph lastestElement = null;

		int countRow = 1; // để kiểm tr
		for (XWPFParagraph p : doc.getParagraphs()) {
			
			if (countRow == 1 && !ReadDeGoc.isGroupQuestionParagraph(p)) {
				QuestionGroupModel group = new QuestionGroupModel();
				exam.groupList.add(group);
			}

			if (ReadDeGoc.isGroupQuestionParagraph(p)) {
				QuestionGroupModel group = new QuestionGroupModel();
				group.groupInfo.add(p);
				exam.groupList.add(group);

				lastestElement = p;
			} else if (ReadDeGoc.isQuestionParagraph(p)) {
				if (exam.groupList.size() > 0) {
					QuestionModel question = new QuestionModel();
					question.questionContent.add(p);

					QuestionGroupModel lastGroup = exam.groupList.get(exam.groupList.size() - 1);
					lastGroup.questionList.add(question);

					lastestElement = p;
				}
			} else if (ReadDeGoc.isAnswerParagraph(p)) {
				if (exam.groupList.size() > 0) {
					QuestionGroupModel lastGroup = exam.groupList.get(exam.groupList.size() - 1);
					QuestionModel lastQuestionOfLastGroup;
					if (lastGroup.questionList.size() > 0) {
						lastQuestionOfLastGroup = lastGroup.questionList.get(lastGroup.questionList.size() - 1);
						AnswerModel answer = new AnswerModel();
						answer.content = p;

						if (ReadDeGoc.isRightAnswerParagraph(p)) {
							answer.isRightAnswer = true;
						}

						lastQuestionOfLastGroup.answerList.add(answer);
					}
				}

			} else {
				if (countRow == 1 || lastestElement == null) {
					exam.groupList.get(0).groupInfo.add(p);
				} else {
					if (exam.groupList.size() > 0) {
						QuestionGroupModel lastGroup = exam.groupList.get(exam.groupList.size() - 1);
						if (ReadDeGoc.isGroupQuestionParagraph(lastestElement)) {
							lastGroup.groupInfo.add(p);
						} else if (ReadDeGoc.isQuestionParagraph(lastestElement)) {
							QuestionModel lastQuestionOfLastGroup = lastGroup.questionList
									.get(lastGroup.questionList.size() - 1);
							lastQuestionOfLastGroup.questionContent.add(p);
						}
					}
				}

			}

			// tang so dong len;
			countRow++;
		}

		doc.close();
		return exam;
	}

	@Override
	public int getQuestionCount(ExamModel exam) {
		// TODO Auto-generated method stub
		int res = 0;
		for(QuestionGroupModel group : exam.groupList) {
			res += group.questionList.size();
		}
		return res;
	}
	
	@Override
	public int getQuestionGroupCount(ExamModel exam) {
		return exam.groupList.size();
	}
}
