package com.sowell.tools.imp.common.excel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import com.sowell.tools.exception.ConstructException;
import com.sowell.tools.exception.FormatException;
import com.sowell.tools.util.FormatUtils;
import com.sowell.tools.util.StringUtils;

public class ExcelRow{
	private int rowIndex;
	private ArrayList<ExcelCell> cells;
	
	private HashMap<Integer, String> coverValue;
	
	private Row row;
	private static DateFormat defaultDateFormat = new SimpleDateFormat("yyyyMMdd");
	
	public ExcelRow(Row row) throws ConstructException {
		if(row != null){
			this.row = row;
		}else{
			throw new ConstructException("构造ExcelRow对象失败，传入的row参数为null");
		}
		this.coverValue = new HashMap<Integer, String>();
	}
	/**
	 * 根据列序列的字母索引获得对应的字符串值，并清除字符串内的所有空格
	 * @param position
	 * @return
	 * @throws FormatException
	 */
	public String getString(String columnPosition) throws FormatException{
		return this.getString(ExcelReader.toColumnIndex(columnPosition));
	}
	/**
	 * 根据列序列的索引获得对应的字符串值，并清除字符串内的所有空格
	 * @param columnIndex
	 * @return
	 */
	public String getString(Integer columnIndex){
		return StringUtils.removeBlank(this.getStringWithBlank(columnIndex));
	}
	
	private String getStringWithBlank(Cell cell){
		//如果有覆盖至
		if(cell == null){
			return null;
		}
		int cellType = cell.getCellType();
		switch (cellType) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_NUMERIC:
			return FormatUtils.toString(FormatUtils.toLong(cell.getNumericCellValue()));
		case Cell.CELL_TYPE_FORMULA:
			 FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
			CellValue cellValue = evaluator.evaluate(cell);
			switch (cellValue.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				return cellValue.getStringValue();
			case Cell.CELL_TYPE_NUMERIC:
				return FormatUtils.toString(FormatUtils.toLong(cellValue.getNumberValue()));
			default:
				return null;
			}
		default:
			return null;
		}
	}
	
	/**
	 * 根据列序列的索引获得对应的字符串值
	 * @param columnIndex
	 * @return
	 */
	public String getStringWithBlank(Integer columnIndex){
		if(columnIndex != null){
			Cell cell = row.getCell(columnIndex);
			if(this.coverValue.containsKey(columnIndex)){
				return this.coverValue.get(columnIndex);
			}
			return this.getStringWithBlank(cell);
		}
		return null;
	}
	/**
	 * 根据列序列的字母索引获得对应的字符串值
	 * @param columnIndex
	 * @return
	 * @throws FormatException 
	 */
	public String getStringWithBlank(String columnPosition) throws FormatException{
		return this.getStringWithBlank(ExcelReader.toColumnIndex(columnPosition));
	}
	/**
	 * 通过传入一个列序列的索引以及可以用于解析其格式的格式化对象，优先级从前到后
	 * 如果不传入任何格式化对象，那么就按照默认的格式yyyyMMdd来进行格式化
	 * 如果没有任何一个格式化对象可以解析该字符串，那么就返回一个错误
	 * 如果表格值为空，那么就返回一个null
	 * @param columnIndex
	 * @param df
	 * @return
	 * @throws ParseException
	 */
	public Long getDateLong(Integer columnIndex, DateFormat...dfs) throws ParseException{
		String value = this.getString(columnIndex);
		Date date = null;
		if(StringUtils.hasText(value)){
			if(dfs.length > 0){
				boolean hasParsed = false;
				for (DateFormat df : dfs) {
					try {
						date = df.parse(value);
						hasParsed = true;
						break;
					} catch (ParseException e) {
					}
				}
				if(hasParsed){
					throw new ParseException("传入的所有日期格式都不能成功转换时间字符串[" + value + "]", 0);
				}
			}else{
				date = defaultDateFormat.parse(value);
			}
			return date.getTime();
		}
		return null;
	}
	/**
	 * 通过传入一个列序列的索引以及可以用于解析其格式的格式化对象，优先级从前到后
	 * 如果不传入任何格式化对象，那么就按照默认的格式yyyyMMdd来进行格式化
	 * 如果没有任何一个格式化对象可以解析该字符串，那么就返回一个错误
	 * 如果表格值为空，那么就返回一个null
	 * @param columnIndex
	 * @param df
	 * @return
	 * @throws ParseException
	 */
	public Long getDateLong(String columnPosition, DateFormat...dfs) throws ParseException, FormatException{
		return this.getDateLong(ExcelReader.toColumnIndex(columnPosition), dfs);
	}
	/**
	 * 通过列序列的索引获得对应的数字值。
	 * @param columnIndex
	 * @return
	 */
	public Integer getInt(Integer columnIndex){
		String value = this.getString(columnIndex);
		return FormatUtils.toInteger(value);
	}
	/**
	 * 通过列序列的字母索引获得对应的数字值。
	 * @param columnIndex
	 * @return
	 */
	public Integer getInt(String columnPostion) throws FormatException{
		return this.getInt(ExcelReader.toColumnIndex(columnPostion));
	}
	
	/**
	 * 获得当前行的行数（第一行为1）
	 * @return
	 */
	public Integer getRowNum(){
		return this.row.getRowNum() + 1;
	}
	
	/**
	 * 返回当前行对象的所有单元对象的值
	 * @return
	 */
	public ArrayList<String> listString(){
		ArrayList<String> list = new ArrayList<String>();
		Iterator<Cell> it = this.row.cellIterator();
		while(it.hasNext()){
			Cell cell = it.next();
			String value = null;
			if(this.coverValue.containsKey(cell.getColumnIndex())){
				value = this.coverValue.get(cell.getColumnIndex());
			}else{
				value = this.getStringWithBlank(cell);
			}
			if(StringUtils.hasText(value)){
				list.add(value);
			}
		}
		return list;
	}
	
	/**
	 * 覆盖某个单元的值(并不覆盖原Row对象的值,覆盖的值保存到当前ExcelRow对象中)
	 * @param index
	 * @param value
	 */
	public void setValue(Integer index, String value){
		this.coverValue.put(index, value);
	}
	
	/**
	 * 重置某个单元的值
	 * @param index
	 */
	public void resetValue(Integer index){
		this.coverValue.remove(index);
	}
	
	/**
	 * 将所有单元的值重置
	 */
	public void resetAllValue(){
		this.coverValue.clear();
	}
}
