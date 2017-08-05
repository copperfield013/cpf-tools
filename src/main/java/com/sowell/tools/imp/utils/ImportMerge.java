package com.sowell.tools.imp.utils;

import com.sowell.tools.imp.common.pojo.ImportInfo;

/**
 * 
 * <p>Title: ImportMerge</p>
 * <p>Description: </p><p>
 * 用于合并两个类的接口.通过实现这个接口,将两个导入类的对象进行合并
 * </p>
 * @author 张荣波
 * @date 2015年10月19日 下午12:52:37
 * @param <T1>
 * @param <T2>
 */
public interface ImportMerge<T1 extends ImportInfo, T2 extends ImportInfo> {
	/**
	 * 实现并调用这个方法,用于合并两个对象,并将合并对象返回到T1
	 * @param info1
	 * @param info2
	 */
	public void merge(T1 info1, T2 info2);
}
