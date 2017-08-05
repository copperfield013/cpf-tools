package com.sowell.tools.imp.common.select;

import java.util.Collection;
import java.util.LinkedHashSet;

import com.sowell.tools.imp.common.pojo.ImportInfo;

/**
 * 
 * <p>Title: ImportInfoFilter</p>
 * <p>Description: </p><p>
 * 导入信息筛选器
 * </p>
 * @author 张荣波
 * @date 2015年10月14日 下午7:52:10
 */
public class ImportInfoFilter {
	/**
	 * 通过用筛选条件condition筛选ImportInfo集合src,并将筛选的集合add到result中
	 * @param collection
	 * @param condition
	 * @param result
	 * @return
	 */
	public ImportInfoFilter filter(Collection<ImportInfo> src, ImportInfoFilterCondition condition, Collection<ImportInfo> result){
		if(src != null && result != null){
			for (ImportInfo importInfo : src) {
				if(condition.check(importInfo)){
					result.add(importInfo);
				}
			}
		}
		return this;
	}
	
	public LinkedHashSet<ImportInfo> filter(Collection<ImportInfo> src, ImportInfoFilterCondition condition){
		LinkedHashSet<ImportInfo> result = new LinkedHashSet<ImportInfo>();
		this.filter(src, condition, result);
		return result;
	}
	
}
