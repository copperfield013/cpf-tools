package com.sowell.tools.model.demo.service;


import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.poi.ss.usermodel.Workbook;

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

public class Excel {
	public Table deal(Workbook wb, String sheetName) throws Exception {
		ExcelReader reader = new ExcelReader(wb.getSheet(sheetName), 1, 2, new int[1], 2);
		
		Table table = new Table();
		
		
		final LinkedHashMap<String, SortedMap<Integer, SortedSet<Integer>>> buildingRoomMap 
			= new LinkedHashMap<String, SortedMap<Integer,SortedSet<Integer>>>();
		final LinkedHashMap<SortedMap, String> cellInfo = new LinkedHashMap<SortedMap, String>();
		ImportDealer dealer = new ImportDealer() {
			
			@Override
			public void validateData(ExcelRow row) throws ImportDataException {
				String buildingName = row.getString(1),
						cellName = row.getString(2),
						roomName = row.getString(3)
						;
				SortedMap<Integer, SortedSet<Integer>> cellMap = null;
				SortedSet<Integer> roomSet = null;
				
				if(buildingRoomMap.containsKey(buildingName)){
					cellMap = buildingRoomMap.get(buildingName);
					if(cellMap != null){
						if(StringUtils.hasText(cellName)){
							roomSet = cellMap.get(Integer.valueOf(cellName));
							if(roomSet == null){
								roomSet = new TreeSet<Integer>();
								cellMap.put(Integer.valueOf(cellName), roomSet);
							}
							try {
								roomSet.add(Integer.valueOf(roomName));
							} catch (NumberFormatException e) {
								table.getErrors().add("第" + row.getRowNum() + "行房间号错误");
							}
						}else{
							table.getErrors().add(buildingName + "到底是不是商品房！");
						}
					}else{
						if(StringUtils.hasText(cellName)){
							table.getErrors().add(buildingName + "到底是不是商品房！");
						}
					}
				}else{
					if(StringUtils.hasText(cellName) && StringUtils.hasText(roomName)){
						cellMap = new TreeMap<Integer, SortedSet<Integer>>();
						roomSet = new TreeSet<Integer>();
						roomSet.add(Integer.valueOf(roomName));
						cellMap.put(Integer.valueOf(cellName), roomSet);
					}
					buildingRoomMap.put(buildingName, cellMap);
				}
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
				for (SortedMap<Integer, SortedSet<Integer>> cellMap : buildingRoomMap.values()) {
					if(cellMap != null){
						Integer maxFloor = 0,
								maxRoomNumber = 0;
						for (Entry<Integer, SortedSet<Integer>> roomsEntry : cellMap.entrySet()) {
							for (Integer room : roomsEntry.getValue()) {
								Integer floor = room / 100
										,roomNo = room % 100;
								if(floor > maxFloor) maxFloor = floor;
								if(roomNo > maxRoomNumber) maxRoomNumber = roomNo;
							}
						}
						cellInfo.put(cellMap, maxFloor + "\t" + maxRoomNumber);
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
		table.getThs().add("楼栋名");
		table.getThs().add("单元数");
		table.getThs().add("各单元层数");
		table.getThs().add("单元楼层户数");
		for (String key : buildingRoomMap.keySet()) {
			Row row = new Row();
			table.getRows().add(row);
			row.getTds().add(key);
			SortedMap<Integer, SortedSet<Integer>> cellMap = buildingRoomMap.get(key);
			String cellStr = cellInfo.get(cellMap);
			row.getTds().add(cellMap == null ? "" : Collections.max(cellMap.keySet()).toString());
			if(cellStr != null){
				String[] split = cellStr.split("\\t");
				row.getTds().add(split[0]);
				row.getTds().add(split[1]);
			}else{
				row.getTds().add("");
				row.getTds().add("");
				
			}
		}
		return table;
	}
	
	
	
}
