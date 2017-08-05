package com.sowell.tools.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * <p>Title: ExcelUtils</p>
 * <p>Description: </p><p>
 * 处理Excel文件的工具类
 * </p>
 * @author 张荣波
 * @date 2015年10月29日 下午4:40:18
 */
public class ExcelUtils {
	
	/**
	 * 复制单独的sheet表格到新的Workbook中
	 * @param sheet
	 * @return
	 */
	public static Workbook extract(Sheet sheet){
		if(sheet != null){
			Workbook wb = sheet.getWorkbook(),
					newWb = null
					;
			if(wb instanceof HSSFWorkbook){
				newWb = new HSSFWorkbook();
			}else if(wb instanceof XSSFWorkbook){
				newWb = new XSSFWorkbook();
			}
			Sheet newSheet = newWb.createSheet(sheet.getSheetName());
			
			POIUtils.copySheet(sheet, newSheet, true);
			return newWb;
		}
		return null;
	}
}
