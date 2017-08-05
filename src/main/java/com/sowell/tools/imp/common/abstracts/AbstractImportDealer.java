package com.sowell.tools.imp.common.abstracts;

import java.util.HashMap;

import com.sowell.tools.imp.common.main.ImportDealer;
import com.sowell.tools.util.common.logger.ImportLogger;

public abstract class AbstractImportDealer implements ImportDealer{
	protected ImportLogger logger;
	protected HashMap<Object, Object> map;
	
	public AbstractImportDealer(ImportLogger logger) {
		this.logger = logger;
	}
}
