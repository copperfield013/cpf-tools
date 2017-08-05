package com.sowell.tools.imp.common.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * <p>Title: ExcelHeader</p>
 * <p>Description: </p><p>
 * 
 * </p>
 * @author 张荣波
 * @date 2015年10月13日 上午9:07:40
 */
public class ExcelHeaders {
	private HashMap<String, Integer> headerIndexMap;
	private ArrayList<String> headerList;
	//保存无法识别的表头
	private LinkedHashMap<Integer, String> unrecognizedHeader;
	private ExcelHeaders(){
		this.headerIndexMap = new HashMap<String, Integer>();
		this.headerList = new ArrayList<String>();
	}
	/**
	 * 根据表头标识-表头名的map和实际的 完整表头list构造一个表头对象
	 * @param headerNameMap
	 * @param headerList
	 */
	public ExcelHeaders(Map<String, String> headerNameMap, List<String> headerList) {
		this();
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		for (Entry<String, String> entry : headerNameMap.entrySet()) {
			String name = entry.getKey(),
					cname = entry.getValue()
					;
			if(name != null && cname != null){
				Integer headerIndex = headerList.indexOf(cname);
				//如果是必需字段的表头,后面会带有"*"号
				if(headerIndex < 0){
					headerIndex = headerList.indexOf(cname + "*");
				}
				if(headerIndex >= 0){
					this.headerIndexMap.put(name, headerIndex);
					int i = 0;
					for (; i < indexList.size(); i++) {
						Integer index = indexList.get(i);
						if(index > headerIndex){
							break;
						}
					}
					this.headerList.add(i, name);
					indexList.add(i, headerIndex);
				}
			}
		}
		//保存无法识别的表头名
		this.unrecognizedHeader = new LinkedHashMap<Integer, String>();
		for (int i = 0; i < headerList.size(); i++) {
			String cname = headerList.get(i);
			if(!this.headerIndexMap.containsValue(i)){
				if(!"序号".equals(cname) && !"序号*".equals(cname)){
					//将没有放到header
					this.unrecognizedHeader.put(i, cname);
				}
			}
		}
		System.out.println(unrecognizedHeader);
	}
	
	/**
	 * 获得所有可以导入的表头标识
	 * @return
	 */
	public String[] getHeaderList(){
		return this.headerList.toArray(new String[this.headerList.size()]);
	}
	
	/**
	 * 根据表头标识获得表头在表头的位置
	 * 如果没有找到对应表头,则返回null
	 * @param headerName
	 * @return
	 */
	public Integer getHeaderIndex(String headerName){
		return this.headerIndexMap.get(headerName);
	}
	public LinkedHashMap<Integer, String> getUnrecognizedHeader() {
		return unrecognizedHeader;
	}
	
	
}
