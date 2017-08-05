package com.sowell.tools.imp.common.handler;

import java.util.TreeSet;

import com.sowell.tools.imp.common.pojo.ImportInfo;
import com.sowell.tools.imp.common.pojo.ImportInfoContainer;
/**
 * 
 * <p>Title: CheckInfoHandler</p>
 * <p>Description: </p><p>
 * 检测对象的参数(临时版,后面完善要继承AbstractHandler)
 * </p>
 * @author 张荣波
 * @date 2015年10月15日 上午9:26:33
 */
public class CheckInfoHandler{
	private ImportInfo currentInfo;
	private ImportInfoContainer infoContainer;
	private TreeSet<Integer> ignoredRowNums;
	
	
	
	public CheckInfoHandler(ImportInfo currentInfo,
			ImportInfoContainer infoContainer, TreeSet<Integer> ignoredRowNums) {
		super();
		this.currentInfo = currentInfo;
		this.infoContainer = infoContainer;
		this.ignoredRowNums = ignoredRowNums;
	}
	
	/**
	 * 获得当前处理的ImportInfo对象
	 * @return
	 */
	public ImportInfo getCurrentInfo() {
		return currentInfo;
	}
	/**
	 * 获得包含所有ImportInfo的容器ImportInfoContainer对象
	 * @return
	 */
	public ImportInfoContainer getInfoContainer() {
		return infoContainer;
	}
	
	/**
	 * 禁用行,rownum为行号,从1开始
	 * @param rownum
	 * @return
	 */
	public CheckInfoHandler disableRow(Integer rownum){
		if(rownum != null){
			this.ignoredRowNums.add(rownum);
		}
		return this;
	}

	public void disableRow(Integer[] allMemberRowNum) {
		for (Integer rownum : allMemberRowNum) {
			this.disableRow(rownum);
		}
	}
	
}
