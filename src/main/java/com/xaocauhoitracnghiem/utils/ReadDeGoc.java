package com.xaocauhoitracnghiem.utils;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class ReadDeGoc {
	// kiểm tra xem đoạn đó có phải là đoạn câu hỏi không
		public static boolean isQuestionParagraph(XWPFParagraph p) {
			String paragraphContent = p.getText();
			String regex = "^(Câu|Question)\\s\\d+[:.].*"; // hoặc  ^(Câu|Question)\\s\\d+\\(\\.|\\:) .*
			return paragraphContent.trim().matches(regex);
		}
		
		// kiem tra xem doan do co phải là đoạn đề cập đến đáp án không
		public static boolean isAnswerParagraph(XWPFParagraph p) {
			if (p.getNumFmt() == "upperLetter") {
				return true;
			}
			
			String paragraphContent = p.getText();
			String regex = "^[A-Z]\\..*"; 
			return paragraphContent.trim().matches(regex);
		}
		public static boolean isRightAnswerParagraph(XWPFParagraph p) {
			if (p.getNumFmt() == "upperLetter") {
				return false;   // đáp án đúng là đáp án có gạch chân ở tên đáp án "A.", "B.", khi dùng numbering thì chắc chắn sẽ không gạch chân nên đáp án đó sẽ ko phải là đáp án đúng
			}
			
			if(!isAnswerParagraph(p)) return false;
			
			XWPFRun run = p.getRuns().get(0);
			if(run.getUnderline().toString() == "SINGLE") {
				return true;
			}
			return false;
		}
		
		public static boolean isGroupQuestionParagraph(XWPFParagraph p) {
			return p.getText().trim().matches("^(<g>).*");
		}
}
