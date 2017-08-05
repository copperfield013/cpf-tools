package com.sowell.tools.imp.common.handler;

import com.sowell.tools.imp.common.excel.ExcelCell;

public class CellValidateHandler extends ValidateHandler {
	//检测的单元格对象，不能为空
	private ExcelCell cell;
	
	public CellValidateHandler(ExcelCell cell) {
		super(null, null);
		if(this.cell != null){
			this.cell = cell;
		}
	}
	
	public String getCellValue(){
		return this.cell.getValue();
	}
}
