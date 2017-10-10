package com.sowell.tools.model.demo.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
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
	
	Logger logger = Logger.getLogger(PDFImageConvertor.class);
	
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
	
	public void converte(int[] indexs, OutputStream[] outputs) throws IOException{
		Assert.notNull(outputs);
		Assert.isTrue(outputs.length == indexs.length);
		for (int i = 0; i < indexs.length; i++) {
			BufferedImage bim = pdfRenderer.renderImageWithDPI(indexs[i], this.dpi,
					ImageType.RGB);
			this.progressRecorder.setProgressMsg("正在转换第" + (indexs[i] + 1) + "页，当前进度" + (i + 1) + "/" + indexs.length + "页").incStep();
			logger.debug("正在转换第" + (indexs[i] + 1) + "/" + outputs.length + "页，当前进度" + (i + 1) + "/" + indexs.length + "页");
			ImageIOUtil.writeImage(bim, "png", outputs[i], this.dpi);
		}
	}

	public int[] getConverteIndex(Set<Integer> filter) {
		if(filter == null){
			return converteIndex;
		}else{
			List<Integer> list = new ArrayList<Integer>(); 
			for(int i : converteIndex){
				if(filter.contains(i)){
					list.add(i);
				}
			}
			int[] result = new int[list.size()];
			for (int i = 0; i < result.length; i++) {
				result[i] = list.get(i);
			}
			return result;
		}
	}

	public void setConverteIndex(int[] converteIndex) {
		this.converteIndex = converteIndex;
	}

	public PDDocument getDocument() {
		return document;
	}
	
}
