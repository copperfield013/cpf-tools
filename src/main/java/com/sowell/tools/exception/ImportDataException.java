package com.sowell.tools.exception;

import com.sowell.tools.imp.common.excel.ExcelReader;
import com.sowell.tools.imp.enums.ImportDataErrorControl;
import com.sowell.tools.util.FormatUtils;

public class ImportDataException extends Exception {
	
	private static final long serialVersionUID = 6770469508408906600L;
	
	//用于存放发生导入错误的位置到行索引
	private Integer rowIndex;
	//用于存放发生导入错误的位置的列索引
	private Integer cellIndex;
	//用于判断导入时是否是错误，或者还是普通的异常
	private Boolean isError;
	
	private ImportDataErrorControl control;
	
	/**
	 * 
	 * @param rowIndex
	 * @param cellIndex
	 * @param isError
	 * @param log
	 */
	public ImportDataException(Integer rowIndex, Integer cellIndex, Boolean isError, String log){
		super(log);
		this.rowIndex = rowIndex;
		this.cellIndex = cellIndex;
		this.isError = isError;
	}
	/**
	 * 
	 * @param rowIndex
	 * @param cellPostion
	 * @param isError
	 * @param log
	 * @throws FormatException
	 */
	public ImportDataException(Integer rowIndex, String cellPostion, Boolean isError, String log) throws FormatException{
		this(rowIndex, ExcelReader.toColumnIndex(cellPostion), isError, log);
	}
	
	/**
	 * 根据错误所在行号行号和是否是错误，以及错误信息构造一个异常对象
	 * @param rowIndex
	 * @param isError
	 * @param log
	 */
	public ImportDataException(Integer rowIndex, Boolean isError, String log){
		this(rowIndex, FormatUtils.toInteger(null), isError, log);
	}
	/**
	 * 
	 * @param log
	 */
	public ImportDataException(String log) {
		this(FormatUtils.toInteger(null), FormatUtils.toInteger(null), null, log);
	}
	
	/**
	 * 创建一个无提示的异常,对导入过程进行控制
	 * @param continue2
	 */
	public ImportDataException(ImportDataErrorControl control) {
		this(control, control + "异常");
	}
	/**
	 * 
	 * @param control
	 * @param log
	 */
	public ImportDataException(ImportDataErrorControl control, String log){
		this(FormatUtils.toString(log));
		// TODO 自动生成的构造函数存根
		this.control = control;
	}
	/**
	 * 
	 * @param control
	 * @param rowIndex
	 * @param cellIndex
	 * @param isError
	 * @param log
	 */
	public ImportDataException(ImportDataErrorControl control, Integer rowIndex, Integer cellIndex, Boolean isError, String log){
		this(rowIndex, cellIndex, isError, log);
		this.control = control;
	}
	/**
	 * 
	 * @param control
	 * @param rowIndex
	 * @param cellPosition
	 * @param isError
	 * @param log
	 * @throws FormatException
	 */
	public ImportDataException(ImportDataErrorControl control, Integer rowIndex, String cellPosition, Boolean isError, String log) throws FormatException{
		this(rowIndex, cellPosition, isError, log);
		this.control = control;
	}
	/**
	 * 根据错误所在行号行号和是否是错误，以及错误信息构造一个异常对象
	 * @param rowIndex
	 * @param isError
	 * @param log
	 */
	public ImportDataException(ImportDataErrorControl control, Integer rowIndex, Boolean isError, String log){
		this(rowIndex, FormatUtils.toInteger(null), isError, log);
		this.control = control;
	}
	/**
	 * 判断当前的异常是不是一个表格单元的错误，即行号、列号、以及是否是错误都不为null
	 * @return
	 */
	public boolean isTableCellException(){
		if(this.rowIndex != null && this.cellIndex != null && this.isError != null){
			return true;
		}
		return false;
	}
	/**
	 * 判断当前的异常是不是一个以表格行的错误，即行号和是否是错误都不为null， 但是列号为null
	 * 当符合的时候返回true
	 * @return
	 */
	public boolean isTableRowException(){
		if(this.rowIndex != null && this.cellIndex == null && this.isError != null){
			return true;
		}
		return false;
	}


	public Integer getRowIndex() {
		return rowIndex;
	}


	public Integer getCellIndex() {
		return cellIndex;
	}


	public Boolean getIsError() {
		return isError;
	}
	/**
	 * 获得异常控制类型
	 * @return
	 */
	public ImportDataErrorControl getControl() {
		return control;
	}
}
