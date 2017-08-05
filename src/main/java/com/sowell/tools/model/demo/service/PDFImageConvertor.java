package com.sowell.tools.model.demo.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.springframework.util.Assert;

import com.sowell.tools.util.ProgressRecorder;

public class PDFImageConvertor {
	private PDDocument document;
	private int dpi = 300;
	private int[] converteIndex;
	private PDFRenderer pdfRenderer;
	private ProgressRecorder progressRecorder;
	
	public PDFImageConvertor(InputStream inputStream, int dpi, int[] converteIndex) throws IOException{
		this.document = PDDocument.load(inputStream);
		this.pdfRenderer = new PDFRenderer(document);
		this.dpi = dpi;
		if(converteIndex == null){
			converteIndex = new int[this.document.getPages().getCount()];
			for (int i = 0; i < converteIndex.length; i++) {
				converteIndex[i] = i;
			}
		}
		this.converteIndex = converteIndex;
	}
	
	public PDFImageConvertor(InputStream inputStream, int dpi, int[] converteIndex, ProgressRecorder progressRecorder) throws IOException{
		this(inputStream, dpi, converteIndex);
		this.progressRecorder = progressRecorder;
	}
	
	public void converte(OutputStream[] outputs) throws IOException{
		Assert.notNull(outputs);
		Assert.isTrue(outputs.length == this.converteIndex.length);
		for (int i = 0; i < this.converteIndex.length; i++) {
			BufferedImage bim = pdfRenderer.renderImageWithDPI(converteIndex[i], this.dpi,
					ImageType.RGB);
			this.progressRecorder.setProgressMsg("正在转换第" + (i + 1) + "页").incStep();
			System.out.println("正在转换第" + (i + 1) + "页");
			ImageIOUtil.writeImage(bim, "png", outputs[i], this.dpi);
		}
	}

	public int[] getConverteIndex() {
		return converteIndex;
	}

	public void setConverteIndex(int[] converteIndex) {
		this.converteIndex = converteIndex;
	}

	public PDDocument getDocument() {
		return document;
	}
	
}
