package com.sowell.tools.imp.common.select;

import com.sowell.tools.imp.common.pojo.ImportInfo;
/**
 * 
 * <p>Title: SelectCondition</p>
 * <p>Description: </p><p>
 * 用于筛选ImportInfo的接口
 * </p>
 * @author 张荣波
 * @date 2015年10月14日 下午7:34:57
 */
public interface ImportInfoFilterCondition {
	/**
	 * 实现这个方法,来判断info是否符合规则
	 * 如果返回true,那么在ImportInfoFilter中进行筛选时,可以被添加到结果中
	 * @param info
	 * @return
	 */
	public boolean check(ImportInfo info);
}
