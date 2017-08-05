package com.sowell.tools.imp.common.handler;

import com.sowell.tools.imp.common.excel.ExcelHeaderRow;
import com.sowell.tools.imp.common.main.ImportDealer;
import com.sowell.tools.util.common.logger.ImportLogger;

public class HeaderValidateHandler extends ValidateHandler {
	public HeaderValidateHandler(ImportLogger logger, ImportDealer dealer) {
		super(logger, dealer);
		// TODO 自动生成的构造函数存根
	}

	private ExcelHeaderRow headerRow;
}
