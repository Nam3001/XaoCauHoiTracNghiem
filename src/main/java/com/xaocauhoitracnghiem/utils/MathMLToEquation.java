package com.xaocauhoitracnghiem.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathPara;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;

import com.xaocauhoitracnghiem.controller.TuyChinhXaoCauHoiController;

public class MathMLToEquation {
	static String projectDir = TuyChinhXaoCauHoiController.class.getProtectionDomain().getCodeSource().getLocation()
			.getPath();
	static File stylesheet = new File(projectDir + "../../assets/MML2OMML.XSL");
	static TransformerFactory tFactory = TransformerFactory.newInstance();
	static StreamSource stylesource = new StreamSource(stylesheet);

	public static CTOMath getOMML(String mathML) throws TransformerException, IOException, XmlException {
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

	public static void CopyParagraphWithEquation(XWPFParagraph srcParagraph, XWPFParagraph desParagraph) {
		// using a cursor to go through the paragraph from top to down
		try {
			// using a cursor to go through the paragraph from top to down
			XmlCursor xmlcursor = srcParagraph.getCTP().newCursor();

			List<XWPFRun> srcParagraphRuns = new ArrayList<XWPFRun>(srcParagraph.getRuns());
			for (int i = srcParagraphRuns.size() - 1; i >= 0; i--) {
				if (srcParagraphRuns.get(i).getText(0) == null) {
					srcParagraphRuns.remove(i);
				} else
					break;
			}
			

			String lastRunContent = srcParagraphRuns.size() > 0
					? srcParagraph.getRuns().get(srcParagraphRuns.size() - 1).getText(0)
					: "";

			int pictureCount = 0;
			String contentString = "";

			int questionLabelCount = 0;
			int answerLabelCount = 0;

			while (xmlcursor.hasNextToken()) {
				XmlCursor.TokenType tokentype = xmlcursor.toNextToken();
				if (tokentype.isStart()) {
					if (xmlcursor.getName().getPrefix().equalsIgnoreCase("w")
							&& xmlcursor.getName().getLocalPart().equalsIgnoreCase("r")) {
						contentString += xmlcursor.getTextValue();

						if ((xmlcursor.getTextValue().equals(lastRunContent))) {
							if (contentString.matches("^(Câu|Question)\\s\\d+[:.].*") && questionLabelCount == 0) {
								contentString = contentString.replaceAll("^(Câu|Question)\\s\\d+[:.]", "").trim();
								questionLabelCount++;
							} else if (contentString.matches("^[A-Z]\\..*") && answerLabelCount == 0) {
								contentString = contentString.replaceAll("^[A-Z]\\.", "").trim();
								answerLabelCount++;
							}
							XWPFRun destinationRun = desParagraph.createRun();
							destinationRun.setText(contentString);
							contentString = "";
						}

					} else if (xmlcursor.getName().getLocalPart().equalsIgnoreCase("oMath")) {
						// append contentString to desParagraph
//						if (desParagraph.getRuns().size() == 0) {
						if (contentString.matches("^(Câu|Question)\\s\\d+[:.].*") && questionLabelCount == 0) {
							contentString = contentString.replaceAll("^(Câu|Question)\\s\\d+[:.]", "").trim();
							questionLabelCount++;
						} else if (contentString.matches("^[A-Z]\\..*") && answerLabelCount == 0) {
							contentString = contentString.replaceAll("^[A-Z]\\.", "").trim();
							answerLabelCount++;
						}
//						}
						XWPFRun destinationRun = desParagraph.createRun();
						destinationRun.setText(contentString);
						contentString = "";

						// we have oMath
						// append the oMath as MathML
						String mathML = EquationToMathML.getMathML((CTOMath) xmlcursor.getObject());
						CTOMath ctOMath = getOMML(mathML);
						CTP ctp = desParagraph.getCTP();
						ctp.getOMathList().add(ctOMath);
//						ctp.setOMathArray(new CTOMath[] { ctOMath });
					} else if (xmlcursor.getName().getPrefix().equalsIgnoreCase("w")
							&& xmlcursor.getName().getLocalPart().equalsIgnoreCase("drawing")) {
						// append contentString to desParagraph
//						if (desParagraph.getRuns().size() == 0) {
						if (contentString.matches("^(Câu|Question)\\s\\d+[:.].*") && questionLabelCount == 0) {
							contentString = contentString.replaceAll("^(Câu|Question)\\s\\d+[:.]", "").trim();
							questionLabelCount++;
						} else if (contentString.matches("^[A-Z]\\..*") && answerLabelCount == 0) {
							contentString = contentString.replaceAll("^[A-Z]\\.", "").trim();
							answerLabelCount++;
						}
//						}
						XWPFRun destinationRun = desParagraph.createRun();
						destinationRun.setText(contentString);
						contentString = "";

						// Handle image element

						List<XWPFPicture> picLst = new ArrayList<XWPFPicture>();
						for (XWPFRun run : srcParagraph.getRuns()) {
							picLst.addAll(run.getEmbeddedPictures());
						}
						
						XWPFPictureData picData = picLst.get(pictureCount).getPictureData();
						InputStream rawPicData = new ByteArrayInputStream(picData.getData());
						int pictureType = picData.getPictureType();

						// set default width and height
						double ratio = (double) 500 / 300;
						int imageWidth = 400;
						int imageHeight = (int) (imageWidth * ratio);

						BufferedImage imgBuffer = ImageIO.read(new ByteArrayInputStream(picData.getData()));
//						if (imgBuffer != null) {
//							ratio = (double) imgBuffer.getHeight() / imgBuffer.getWidth();
//							imageHeight = (int) (imageWidth * ratio);
//						}
						imageWidth = (int) picLst.get(pictureCount).getCTPicture().getSpPr().getXfrm().getExt().getCx() / 14000;
						imageHeight = (int) picLst.get(pictureCount).getCTPicture().getSpPr().getXfrm().getExt().getCy() / 14000;

						File tempImageFile = File.createTempFile("image", "jpg");
						FileOutputStream tempOut = new FileOutputStream(tempImageFile);
						tempOut.write(picData.getData());
						tempOut.close();

						XWPFRun run = desParagraph.createRun();
						run.addPicture(rawPicData, pictureType, "image.jpg", Units.toEMU(imageWidth),
								Units.toEMU(imageHeight));
						pictureCount++;

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
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getLocalizedMessage());
		}

	}
}
