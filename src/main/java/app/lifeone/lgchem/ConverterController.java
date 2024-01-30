package app.lifeone.lgchem;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.core.office.OfficeManager;
import org.jodconverter.local.LocalConverter;
import org.jodconverter.local.office.LocalOfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ConverterController {

	private static final Logger logger = LoggerFactory.getLogger(ConverterController.class);

	/**
	 * JODConverter 사용 office -> PDF -> jpg 변환
	 *
	 * @param HttpServletRequest  req
	 * @param HttpServletResponse rep
	 * @return ModelAndView
	 * @throws Exception
	 * @since 2024. 01. 31
	 * @author 김영우
	 */
	@RequestMapping(value = "/jodConverter", method = { RequestMethod.GET })
	public void retrieveJodConverter(HttpServletRequest req, HttpServletResponse res) throws OfficeException {

		logger.info("##### Converter Test #####");

		OfficeManager officeManager = LocalOfficeManager.make();
		DocumentConverter converter = LocalConverter.make(officeManager);

		try {
			officeManager.start();

			// 오피스 To Pdf
			logger.info("##### Convert Start #####");
			File excelFile = new File("C:\\fileupload\\convert_after\\a.xlsx");
			File pdfFile = new File("C:\\fileupload\\convert_before\\a.pdf");

			converter.convert(excelFile).to(pdfFile).execute();
			logger.info("##### Convert End #####");

			// Pdf To jpg
			logger.info("##### Converter image #####");
			if (pdfFile.isFile() && getFileExtension(pdfFile.getName()).equalsIgnoreCase("pdf")) {
				this.convert(pdfFile);
			}
			logger.info("##### Converter image #####");

			// logger.info("##### Base64 Encoding Start #####");
			// String sEncodeString = this.convertImageToBase64Decoding(pdfFile);
			// logger.info("##### Base64 Encoding End #####");

			// logger.info("##### Base64 Decoding Start #####");
			// this.convertBase64Decoding(sEncodeString);
			// logger.info("##### Base64 Decoding End #####");

		} finally {
			officeManager.stop();
		}
	}

	private String getFileExtension(String fileName) {
		if (fileName == null || fileName.equals(""))
			return "undefined";
		int dotIndex = fileName.lastIndexOf(".");
		return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
	}

	private void convert(File sourceFile) {
		try {
			String destinationDir = "C:\\fileupload\\convert_image\\"; // converted images from pdf

			File destinationFile = new File(destinationDir);
			if (!destinationFile.exists()) {
				boolean fileCreated = destinationFile.mkdir();
				if (fileCreated){
					logger.info("Folder Created -> {}", destinationFile.getAbsolutePath());
				}
			}
			if (sourceFile.exists()) {
				logger.info("Images copied to Folder: {} ", destinationFile.getName());
				PDDocument document = PDDocument.load(sourceFile);
				PDPageTree pdPageTree = document.getDocumentCatalog().getPages();
				logger.info("Total files to be converted -> {}", pdPageTree.getCount());

				PDFRenderer pdfRenderer = new PDFRenderer(document);

				String fileName = sourceFile.getName().replace(".pdf", "");
				int pageNumber = 1;
				for (int page = 0; page < document.getNumberOfPages(); ++page) {
					BufferedImage image = pdfRenderer.renderImageWithDPI(page, 150, ImageType.RGB);
					File outputFile = new File(destinationDir + fileName + "_" + pageNumber + ".jpg");
					logger.info("Image Created -> {}", outputFile.getName());
					ImageIO.write(image, "jpg", outputFile);
					pageNumber++;
				}
				document.close();
				logger.info("Converted Images are saved at -> {}", destinationFile.getAbsolutePath());
			} else {
				logger.info(sourceFile.getName() + " File not exists");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * PDF를 Base64로 변환(Encoding)
	 *
	 * @param File pdfFile
	 * @return String
	 * @throws Exception
	 * @since 2024. 01. 31
	 * @author 김영우
	 */
	private String convertPdfToBase64(File pdfFile) {

		String sEncodeString = "";

		try {
			byte[] imageBytes = FileUtils.readFileToByteArray(pdfFile);

			sEncodeString = Base64.getEncoder().encodeToString(imageBytes);

			logger.info("##### Base64 Value : {}{} #####", "data:image/png;base64,", sEncodeString);

		} catch (IOException e) {
			e.printStackTrace();
			logger.error(">>>>>>>>>>  convertPdfToBase64 ERROR !!  {}", e.getMessage());
		}

		return sEncodeString;
	}

	/**
	 * Image를 Base64로 변환(Decoding)
	 *
	 * @param String sBase64Encoded
	 * @return
	 * @throws Exception
	 * @since 2024. 01. 31
	 * @author 김영우
	 */
	private void convertImageToBase64Decoding(String sBase64Encoded) {
		try {
			byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(sBase64Encoded);

			FileOutputStream fos = new FileOutputStream("C:\\fileupload\\convert_image\\a.pdf");
			fos.write(decoded);
			fos.flush();
			fos.close();

		} catch (IOException e) {
			e.printStackTrace();
			logger.error(">>>>>>>>>>  convertBase64Decoding ERROR !!  {}", e.getMessage());
		}
	}
}
