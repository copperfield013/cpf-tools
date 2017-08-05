package com.sowell.tools.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * <p>Title:FileUtils</p>
 * <p>Description:</p>
 * 文件工具类
 * @author 张荣波
 * @date 2015年2月28日 下午3:11:43
 */
public class FileUtils {
	
	public static final String desktopPath = "C:\\Users\\Administrator\\Desktop";
	
	
	public static void exportToDesktop(String fileName, String content) throws IOException{
		exportTo(desktopPath, fileName, content);
	}
	
	/**
	 * 将字符串保存在path路径下,文件名为fileName，如果已经存在同名文件，那么就覆盖
	 * @param path
	 * @param fileName
	 * @param content
	 * @throws IOException
	 */
	public static File exportTo(String path, String fileName, String content) throws IOException{
		File dir = new File(path);
		if(!dir.exists() && !dir.isDirectory()){
			if(!dir.mkdirs()){
				throw new IOException("路径构造失败[" + dir.toString() + "]");
			}
		}
		File f = new File(path, fileName);
		f.createNewFile();
		FileWriter writer = new FileWriter(f);
		writer.write(content);
		writer.flush();
		writer.close();
		return f;
	}
	
	
	public static File exportExcelTo(String filePath, String fileName, Workbook workbook) throws IOException{
		File dir = new File(filePath);
		if(!dir.exists() && !dir.isDirectory()){
			if(!dir.mkdirs()){
				throw new IOException("路径构造失败[" + dir.toString() + "]");
			}
		}
		File importLoggerFile = new File(filePath, fileName);
		//构造文件
		importLoggerFile.createNewFile();
		FileOutputStream stream = new FileOutputStream(importLoggerFile);
		//将Excel写入文件
		workbook.write(stream);
		stream.flush();
		stream.close();
		return importLoggerFile;
	}
	
	public static List<String> importLinesFromDesktop(String fileName) throws IOException{
		List<String> ret = new ArrayList<String>();
		File f = new File(desktopPath, fileName);
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line = reader.readLine();
		while(line != null){
			ret.add(line);
			line = reader.readLine();
		}
		reader.close();
		return ret;
	}
	
	public static Workbook  importFile(String excelPath) throws FileNotFoundException, IOException{
		if(excelPath != null){
			Class<? extends Workbook> wbClass = null;
			if(excelPath.endsWith(".xls")){
				wbClass = HSSFWorkbook.class;
			}else if(excelPath.endsWith(".xlsx")){
				wbClass = XSSFWorkbook.class;
			}
			return importFile(new FileInputStream(new File(excelPath)), wbClass);
		}
		return null;
	}
	
	public static Workbook  importFile(InputStream input, Class<? extends Workbook> wbClass) throws FileNotFoundException, IOException{
		if(input != null && wbClass != null){
			if(HSSFWorkbook.class.isAssignableFrom(wbClass)){
				try {
					return new HSSFWorkbook(input);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(XSSFWorkbook.class.isAssignableFrom(wbClass)){
				try {
					return new XSSFWorkbook(input);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	public static Workbook  importFile(String path, String fileName) throws FileNotFoundException, IOException{
		return importFile(path + "/" + fileName);
	}
	
	public static String getFileTypeWithDot(String fileName){
		if(fileName.contains(".")){
			String[] split = fileName.split("\\.");
			return "." + split[split.length - 1];
		}else{
			return "";
		}
	}
	
}
