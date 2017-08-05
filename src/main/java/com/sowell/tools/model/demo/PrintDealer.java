package com.sowell.tools.model.demo;

import com.sowell.tools.exception.ImportDataException;
import com.sowell.tools.imp.common.excel.ExcelRow;
import com.sowell.tools.imp.common.handler.CheckInfoHandler;
import com.sowell.tools.imp.common.handler.ImportInfoHandler;
import com.sowell.tools.imp.common.main.ImportDealer;
import com.sowell.tools.imp.common.pojo.ImportInfo;

public class PrintDealer implements ImportDealer {

	@Override
	public void readHeader(ExcelRow row) throws ImportDataException {
		// TODO Auto-generated method stub

	}

	@Override
	public void validateData(ExcelRow row) throws ImportDataException {
		// TODO Auto-generated method stub

	}

	@Override
	public ImportInfo buildInfo(ExcelRow row) throws ImportDataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkInfo(CheckInfoHandler handler) throws ImportDataException {
		// TODO Auto-generated method stub

	}

	@Override
	public void importInfo(ImportInfoHandler handler)
			throws ImportDataException {
		// TODO Auto-generated method stub

	}

	@Override
	public void end() throws ImportDataException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkmode() {
		// TODO Auto-generated method stub
		return false;
	}

}
