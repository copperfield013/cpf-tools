package com.sowell.tools.imp.utils;

import org.apache.poi.ss.usermodel.Workbook;

public class ExcelCheckResult{
	private Workbook wb;
	private String[] sheetNames;
	private String suffix;
	private String msg;
	private boolean isError;
	public ExcelCheckResult() {
		super();
		this.setError(false);
	}
	public String[] getSheetNames() {
		return sheetNames;
	}
	public void setSheetNames(String[] sheetNames) {
		this.sheetNames = sheetNames;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
	public Workbook getWorkbook() {
		return wb;
	}
	public void setWorkbook(Workbook wb) {
		this.wb = wb;
	}
	
}