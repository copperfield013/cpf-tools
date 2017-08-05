package com.sowell.tools.util.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * <p>Title: PoiCopyTarget</p>
 * <p>Description: </p><p>
 * Excel的Workbook对象复制器
 * </p>
 * @author Copperfield Zhang
 * @date 2015年12月15日 下午7:17:02
 */
public interface PoiCopyTarget {
	/**
	 * 将srcSheet的内容复制到targetSheetName对应的sheet中
	 * 如果sheet不存在，那么就创建一张
	 * 如果sheet已经存在，那么就在原有内容的基础上进行追加
	 * @param targetSheetName 复制目标的表格名称
	 * @param srcSheet 要复制的原始数据表格
	 */
	public void copySheet(String targetSheetName, Sheet srcSheet );
	/**
	 * 将srcRow的内容复制到targetSheetName对应的sheet中，覆盖位置是targetRowIndex
	 * @param targetSheetName
	 * @param targetRowIndex 0-base
	 * @param srcRow 要复制的原始数据行
	 */
	public void copyRow(String targetSheetName, Integer targetRowIndex, Row srcRow);
	/**
	 * 将srcRow的内容复制到targetSheetName对应的sheet中，复制形式是在最后一行追加
	 * @param targetSheetName
	 * @param srcRow
	 */
	public void copyRow(String targetSheetName, Row srcRow);
	/**
	 * 
	 * @param targetSheetName
	 * @param targetRowIndex 0-base
	 * @param targetColumnIndex 0-base
	 * @param srcCell
	 */
	public void copyCell(String targetSheetName, Integer targetRowIndex, Integer targetColumnIndex, Cell srcCell);
	/**
	 * 
	 * @return
	 */
	public Workbook getWorkbook();
}
