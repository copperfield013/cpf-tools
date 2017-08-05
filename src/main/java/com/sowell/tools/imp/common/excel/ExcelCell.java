package com.sowell.tools.imp.common.excel;

import org.apache.poi.ss.usermodel.Cell;

public class ExcelCell {
	//
	private ExcelHeaderCell header;
	//
	private Cell srcCell;
	//
	private String value;
	//
	private int cellIndex;
	
	private ExcelRow row;
	
	public ExcelCell(Cell cell) {
		this.srcCell = cell;
	}

	public Cell getSrcCell() {
		return srcCell;
	}

	public String getValue() {
		return value;
	}

	public int getCellIndex() {
		return cellIndex;
	}

	public ExcelHeaderCell getHeader() {
		return header;
	}

	public ExcelRow getRow() {
		return row;
	}
	
}
