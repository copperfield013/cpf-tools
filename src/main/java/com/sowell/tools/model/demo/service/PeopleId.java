package com.sowell.tools.model.demo.service;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import com.sowell.tools.exception.ImportDataException;
import com.sowell.tools.imp.common.excel.ExcelReader;
import com.sowell.tools.imp.common.excel.ExcelRow;
import com.sowell.tools.imp.common.handler.CheckInfoHandler;
import com.sowell.tools.imp.common.handler.ImportInfoHandler;
import com.sowell.tools.imp.common.main.ImportDealer;
import com.sowell.tools.imp.common.pojo.ImportInfo;
import com.sowell.tools.imp.utils.Row;
import com.sowell.tools.imp.utils.Table;
import com.sowell.tools.util.StringUtils;

public class PeopleId {
	
	@Test
	public Table deal(Workbook wb, String sheetName) throws Exception {
		final Integer firstDataRowNum = 2;
		ExcelReader reader = new ExcelReader(wb.getSheet(sheetName), 1, firstDataRowNum, new int[1], 2);
		
		Table table = new Table();
		
		final LinkedHashMap<String, Integer> indexMap = new LinkedHashMap<String, Integer>();
		
		final LinkedHashMap<Integer, Integer> similarMap = new LinkedHashMap<Integer, Integer>();
		
		final Map<Integer, String> peopleIdMap = new HashMap<Integer, String>();
		
		ImportDealer dealer = new ImportDealer() {
			
			int rowMax = 0;
			
			@Override
			public void validateData(ExcelRow row) throws ImportDataException {
				String peopleId = row.getString(5);
				Integer rowNum = row.getRowNum();
				peopleIdMap.put(rowNum, peopleId);
				if(StringUtils.hasText(peopleId)){
					if(indexMap.containsKey(peopleId)){
						similarMap.put(rowNum, indexMap.get(peopleId));
						similarMap.put(indexMap.get(peopleId), null);
					}else{
						indexMap.put(peopleId, rowNum);
					}
				}
				this.rowMax = rowNum;
			}
			
			
			@Override
			public void readHeader(ExcelRow row) throws ImportDataException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void importInfo(ImportInfoHandler handler)
					throws ImportDataException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void end() throws ImportDataException {
				for (int i = firstDataRowNum; i <= this.rowMax; i++) {
					Row row = new Row();
					table.getRows().add(row);
					row.getTds().add(String.valueOf(i));
					if(similarMap.containsKey(i)){
						Integer index = similarMap.get(i);
						if(index == null){
							row.getTds().add("存在相同");
						}else{
							row.getTds().add(index.toString());
							row.getTds().add(peopleIdMap.get(index));
						}
					}
				}
			}
			
			@Override
			public boolean checkmode() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void checkInfo(CheckInfoHandler handler) throws ImportDataException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public ImportInfo buildInfo(ExcelRow row) throws ImportDataException {
				// TODO Auto-generated method stub
				return null;
			}
		};
		reader.importData(dealer);
		reader.end(dealer);
		return table;
	}
	
	
	
}
