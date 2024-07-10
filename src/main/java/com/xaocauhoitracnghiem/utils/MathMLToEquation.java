package com.xaocauhoitracnghiem.utils;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathPara;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;

import com.xaocauhoitracnghiem.controller.XaoCauHoiController;

public class MathMLToEquation {
	static String projectDir = XaoCauHoiController.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	static File stylesheet = new File(projectDir+"../../assets/MML2OMML.XSL");
	static TransformerFactory tFactory = TransformerFactory.newInstance();
	static StreamSource stylesource = new StreamSource(stylesheet);

	public static CTOMath getOMML(String mathML) throws Exception {
		Transformer transformer = tFactory.newTransformer(stylesource);

		StringReader stringreader = new StringReader(mathML);
		StreamSource source = new StreamSource(stringreader);

		StringWriter stringwriter = new StringWriter();
		StreamResult result = new StreamResult(stringwriter);
		transformer.transform(source, result);

		String ooML = stringwriter.toString();
		stringwriter.close();
		CTOMathPara ctOMathPara = CTOMathPara.Factory.parse(ooML);
		CTOMath ctOMath = ctOMathPara.getOMathArray(0);

		// for making this to work with Office 2007 Word also, special font settings are
		// necessary
		XmlCursor xmlcursor = ctOMath.newCursor();
		while (xmlcursor.hasNextToken()) {
			XmlCursor.TokenType tokentype = xmlcursor.toNextToken();
			if (tokentype.isStart()) {
				if (xmlcursor.getObject() instanceof CTR) {
					CTR cTR = (CTR) xmlcursor.getObject();
					cTR.addNewRPr2().addNewRFonts().setAscii("Cambria Math");
//		     cTR.getRPr2().getRFonts().setHAnsi("Cambria Math"); // up to apache poi 4.1.2
					cTR.getRPr2().getRFontsArray(0).setHAnsi("Cambria Math"); // since apache poi 5.0.0
				}
			}
		}

		return ctOMath;
	}

	public static void CopyParagraphWithEquation(XWPFParagraph srcParagraph, XWPFParagraph desParagraph)
			throws Exception {
		// using a cursor to go through the paragraph from top to down
		XmlCursor xmlcursor = srcParagraph.getCTP().newCursor();

		while (xmlcursor.hasNextToken()) {
			XmlCursor.TokenType tokentype = xmlcursor.toNextToken();
			if (tokentype.isStart()) {
				if (xmlcursor.getName().getPrefix().equalsIgnoreCase("w")
						&& xmlcursor.getName().getLocalPart().equalsIgnoreCase("r")) {
//					System.out.println(xmlcursor.getTextValue());
					// elements w:r are text runs within the paragraph
					// simply append the text data
					XWPFRun run = desParagraph.createRun();
					run.setText(xmlcursor.getTextValue());
				} else if (xmlcursor.getName().getLocalPart().equalsIgnoreCase("oMath")) {
					// we have oMath
					// append the oMath as MathML
					String mathML = EquationToMathML.getMathML((CTOMath) xmlcursor.getObject());
					CTOMath ctOMath = getOMML(mathML);
					CTP ctp = desParagraph.getCTP();
					ctp.setOMathArray(new CTOMath[] { ctOMath });
				}
			} else if (tokentype.isEnd()) {
				// we have to check whether we are at the end of the paragraph
				xmlcursor.push();
				xmlcursor.toParent();
				if (xmlcursor.getName().getLocalPart().equalsIgnoreCase("p")) {
					break;
				}
				xmlcursor.pop();
			}
		}

	}
}
