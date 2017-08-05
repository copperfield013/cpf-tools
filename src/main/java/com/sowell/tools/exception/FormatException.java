package com.sowell.tools.exception;

/**
 * <p>Title:FormatException</p>
 * <p>Description:</p>
 * 数据类型格式转换错误
 * @author 张荣波
 * @date 2015年2月5日 下午2:15:56
 */
public class FormatException extends Exception {

	/** 
	 *serialVersionUID
	*/
	private static final long serialVersionUID = 5935191811601795872L;

	public FormatException(String str) {
		super(str);
	}
	
}
