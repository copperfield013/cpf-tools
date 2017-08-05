package com.sowell.tools.util;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class XMLReader {
	private Document document;
	
	/**
	 * 通过File类型构造类
	 * @param file
	 */
	public XMLReader(File file) {
		SAXReader reader = new SAXReader();
		try {
			document = reader.read(file);
		} catch (DocumentException e) {
			//System.out.println("XML读取失败");
			System.out.println(file.getAbsolutePath());
			e.printStackTrace();
		}
	}
	public XMLReader(String path) {
		SAXReader reader = new SAXReader();
		try {
			document = reader.read(new File(path));
		} catch (DocumentException e) {
			//System.out.println("XML读取失败");
			//System.out.println(path );
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过XML文件所在文件夹路径和XML文件名构造类
	 * @param path XML文件所在文件夹的路径
	 * @param fileName XML文件名，只需输入文件名不需要输入xml后缀
	 */
	public XMLReader(String path,String fileName) {
		SAXReader reader = new SAXReader();
		if(!path.endsWith("/")){
			path += "/";
		}
		try {
			document = reader.read(new File(path + fileName + ".xml"));
		} catch (DocumentException e) {
			//System.out.println("XML读取失败");
			//System.out.println(path + fileName + ".xml");
			e.printStackTrace();
		}
		
	}
	public Document getDocument() {
		return document;
	}
	
	
	
	
	
}
