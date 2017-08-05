package com.sowell.tools.imp.common.validator;

import com.sowell.tools.imp.common.handler.CellValidateHandler;

public class CommonValidator {
	public void requiredValidate(CellValidateHandler handler){
		String value = handler.getCellValue();
		if(value == null || value.isEmpty()){
		}
	}
}
