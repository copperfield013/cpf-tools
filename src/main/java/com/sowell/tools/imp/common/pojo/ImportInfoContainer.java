package com.sowell.tools.imp.common.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import com.sowell.tools.imp.common.select.ImportInfoFilter;
import com.sowell.tools.imp.common.select.ImportInfoFilterCondition;

/**
 * 
 * <p>Title: ImportInfoContainer</p>
 * <p>Description: </p><p>
 * 
 * </p>
 * @author 张荣波
 * @date 2015年10月14日 下午8:06:22
 */
public class ImportInfoContainer {
	private ImportInfoFilter filter;
	
	private LinkedHashMap<ImportInfo, ImportInfoAttribute> importInfoAttrs;
	
	public ImportInfoContainer() {
		this.importInfoAttrs = new LinkedHashMap<ImportInfo, ImportInfoAttribute>();
		this.filter = new ImportInfoFilter();
	}
	
	public void put(ImportInfo info){
		this.importInfoAttrs.put(info, new ImportInfoAttribute(info));
	}
	/**
	 * 禁用该对象的导入
	 * @param info
	 */
	public void disableInfo(ImportInfo info){
		ImportInfoAttribute attr = this.getInfoAttribute(info);
		if(attr != null){
			attr.disableInfo();
		}
	}
	/**
	 * 启用该对象的导入
	 * @param info
	 */
	public void enableInfo(ImportInfo info){
		ImportInfoAttribute attr = this.getInfoAttribute(info);
		if(attr != null){
			attr.enableInfo();
		}
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public Set<ImportInfo> getImportInfoSet(){
		return this.importInfoAttrs.keySet();
	}
	/**
	 * 根据对象获得其参数对象
	 * @param info
	 * @return
	 */
	public ImportInfoAttribute getInfoAttribute(ImportInfo info){
		return this.importInfoAttrs.get(info);
	}
	/**
	 * 根据对象和自定义参数名,获得对应参数值
	 * @param info
	 * @param attrName
	 * @return
	 */
	public Object getInfoAttribute(ImportInfo info, String attrName){
		ImportInfoAttribute attr = this.getInfoAttribute(info);
		return attr != null ? attr.getAttribute(attrName) : null; 
	}
	
	/**
	 * 判断该对象是否被禁用如果启用,返回true
	 * @param info
	 * @return
	 */
	public boolean isEnabled(ImportInfo info){
		ImportInfoAttribute attr = this.getInfoAttribute(info);
		if(attr != null){
			return attr.isEnbaled();
		}
		return true;
	}
	/**
	 * 根据条件condition对当前存放的所有ImportInfo进行筛选,并按顺序存放到LinkedHashSet中
	 * 返回的LinkedHashSet不为null
	 * @param condition
	 * @return
	 */
	public LinkedHashSet<ImportInfo> filter(ImportInfoFilterCondition condition){
		LinkedHashSet<ImportInfo> set = new LinkedHashSet<ImportInfo>();
		this.filter(condition, set);
		return set;
	}
	/**
	 * 筛选当前存放的所有ImportInfo,并存放到result参数当中
	 * @param condition 参选条件接口
	 * @param result 要返回的result集合对象
	 */
	public void filter(ImportInfoFilterCondition condition, Collection<ImportInfo> result){
		this.filter.filter(this.getImportInfoSet(), condition, result);
	}
	
	public Iterator<ImportInfo> iterateFrom(Integer index){
		ArrayList<ImportInfo> importInfos = new ArrayList<ImportInfo>(this.importInfoAttrs.keySet());
		return importInfos.listIterator(index);
	}
	
	
	public Iterator<ImportInfo> iterateFrom(ImportInfo importInfo){
		ArrayList<ImportInfo> importInfos = new ArrayList<ImportInfo>(this.importInfoAttrs.keySet());
		Integer index = importInfos.indexOf(importInfo);
		return importInfos.listIterator(index + 1);
	}
	
}
