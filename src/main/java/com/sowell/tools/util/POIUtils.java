package com.sowell.tools.util;

import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POIUtils {
//	/**
//	 * 把一个excel中的cellstyletable复制到另一个excel，这里会报错，不能用这种方法，不明白呀？？？？？
//	 * @param fromBook
//	 * @param toBook
//	 */
//	public static void copyBookCellStyle(HSSFWorkbook fromBook,HSSFWorkbook toBook){
//		for(short i=0;i<fromBook.getNumCellStyles();i++){
//			HSSFCellStyle fromStyle=fromBook.getCellStyleAt(i);
//			HSSFCellStyle toStyle=toBook.getCellStyleAt(i);
//			if(toStyle==null){
//				toStyle=toBook.createCellStyle();
//			}
//			copyCellStyle(fromStyle,toStyle);
//		}
//	}
	/**
	 * 复制一个单元格样式到目的单元格样式
	 * @param fromStyle
	 * @param toStyle
	 */
	public static void copyCellStyle(CellStyle fromStyle,
			CellStyle toStyle) {
		toStyle.setAlignment(fromStyle.getAlignment());
		//边框和边框颜色
		toStyle.setBorderBottom(fromStyle.getBorderBottom());
		toStyle.setBorderLeft(fromStyle.getBorderLeft());
		toStyle.setBorderRight(fromStyle.getBorderRight());
		toStyle.setBorderTop(fromStyle.getBorderTop());
		toStyle.setTopBorderColor(fromStyle.getTopBorderColor());
		toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());
		toStyle.setRightBorderColor(fromStyle.getRightBorderColor());
		toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());
		
		//背景和前景
		toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());
		toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());
		
		toStyle.setDataFormat(fromStyle.getDataFormat());
		toStyle.setFillPattern(fromStyle.getFillPattern());
//		toStyle.setFont(fromStyle.getFont(null));
		toStyle.setHidden(fromStyle.getHidden());
		toStyle.setIndention(fromStyle.getIndention());//首行缩进
		toStyle.setLocked(fromStyle.getLocked());
		toStyle.setRotation(fromStyle.getRotation());//旋转
		toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());
		toStyle.setWrapText(fromStyle.getWrapText());
		
	}
	/**
	 * Sheet复制
	 * @param fromSheet
	 * @param toSheet
	 * @param copyValueFlag 是否复制单元格值
	 */
	public static void copySheet(Sheet fromSheet, Sheet toSheet,
			boolean copyValueFlag) {
		//合并区域处理
		mergerRegion(fromSheet, toSheet);
		for (Iterator rowIt = fromSheet.rowIterator(); rowIt.hasNext();) {
			Row tmpRow = (Row) rowIt.next();
			Row newRow = toSheet.createRow(tmpRow.getRowNum());
			//行复制
			copyRow(tmpRow,newRow,copyValueFlag);
		}
	}
	/**
	 * 行复制功能
	 * @param fromRow
	 * @param toRow
	 */
	public static void copyRow(Row fromRow,Row toRow,boolean copyValueFlag){
		toRow.setHeight(fromRow.getHeight());
		for (Iterator cellIt = fromRow.cellIterator(); cellIt.hasNext();) {
			Cell tmpCell = (Cell) cellIt.next();
			Cell newCell = toRow.createCell(tmpCell.getColumnIndex());
			copyCell(tmpCell, newCell, copyValueFlag);
		}
	}
	/**
	* 复制原有sheet的合并单元格到新创建的sheet
	* 
	* @param sheetCreat 新创建sheet
	* @param sheet      原有的sheet
	*/
	public static void mergerRegion(Sheet fromSheet, Sheet toSheet) {
	   int sheetMergerCount = fromSheet.getNumMergedRegions();
	   for (int i = 0; i < sheetMergerCount; i++) {
			CellRangeAddress  mergedRegionAt = fromSheet.getMergedRegion(i);
		    toSheet.addMergedRegion(mergedRegionAt);
	   }
	}
	/**
	 * 复制单元格
	 * 
	 * @param srcCell
	 * @param distCell
	 * @param copyValueFlag
	 *            true则连同cell的内容一起复制
	 */
	public static void copyCell(Cell srcCell, Cell distCell,
			boolean copyValueFlag) {
		Workbook distWb = distCell.getSheet().getWorkbook();
		CellStyle newstyle= distWb.createCellStyle();
		copyCellStyle(srcCell.getCellStyle(), newstyle);
		//distCell.setEncoding(srcCell.getEncoding());
		//样式
		distCell.setCellStyle(newstyle);
		//评论
		if (srcCell.getCellComment() != null) {
			distCell.setCellComment(srcCell.getCellComment());
		}
		// 不同数据类型处理
		int srcCellType = srcCell.getCellType();
		distCell.setCellType(srcCellType);
		if (copyValueFlag) {
			if (srcCellType == Cell.CELL_TYPE_NUMERIC) {
				if (DateUtil.isCellDateFormatted(srcCell)) {
					distCell.setCellValue(srcCell.getDateCellValue());
				} else {
					distCell.setCellValue(srcCell.getNumericCellValue());
				}
			} else if (srcCellType == Cell.CELL_TYPE_STRING) {
				if(distCell.getClass() == srcCell.getClass()){
					distCell.setCellValue(srcCell.getRichStringCellValue());
				}else{
					distCell.setCellValue(srcCell.getRichStringCellValue() == null ? null : srcCell.getRichStringCellValue().getString());;
				}
			} else if (srcCellType == Cell.CELL_TYPE_BLANK) {
				// nothing21
			} else if (srcCellType == Cell.CELL_TYPE_BOOLEAN) {
				distCell.setCellValue(srcCell.getBooleanCellValue());
			} else if (srcCellType == Cell.CELL_TYPE_ERROR) {
				distCell.setCellErrorValue(srcCell.getErrorCellValue());
			} else if (srcCellType == Cell.CELL_TYPE_FORMULA) {
				distCell.setCellFormula(srcCell.getCellFormula());
			} else { // nothing29
			}
		}
	}
	public static Workbook newInstance(Class<? extends Workbook> clazz) {
		if(HSSFWorkbook.class.isAssignableFrom(clazz)){
			return new HSSFWorkbook();
		}else if(XSSFWorkbook.class.isAssignableFrom(clazz)){
			return new XSSFWorkbook();
		}
		throw new IllegalArgumentException("clzz只能是HSSFWorkbook或者XSSFWorkbook");
	}
}
