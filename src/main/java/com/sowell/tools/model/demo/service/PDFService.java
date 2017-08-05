package com.sowell.tools.model.demo.service;

//import java.io.File;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
//import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;

public class PDFService {
	public static void main(String[] args) throws IOException{
		PDDocument document = PDDocument.load(new File("f://智慧社区项目实施日报 2016.7.22AM.pdf"));
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		int pageCounter = 0;
		for (PDPage page : document.getPages())
		{
		    // note that the page number parameter is zero based
		    BufferedImage bim = pdfRenderer.renderImageWithDPI(pageCounter, 500, ImageType.RGB);

		    // suffix in filename will be used as the file format
		    ImageIOUtil.writeImage(bim, "f://sss" + "-" + (pageCounter++) + ".png", 300);
		}
		document.close();
	}
}
