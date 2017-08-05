package com.sowell.tools.exception;

import com.sowell.tools.util.StringUtils;

/**
 * 
 * <p>Title: SetValueException</p>
 * <p>Description: </p><p>
 * 设置属性值时出现问题的错误类
 * </p>
 * @author 张荣波
 * @date 2015年6月28日
 */
public class SetValueException extends Exception {
	public SetValueException() {
		// TODO 自动生成的构造函数存根
	}
	/**
	 * 当值不在range内的时候，构造的错误信息
	 * @param log
	 * @param range
	 */
	public SetValueException(String ValueInFact, String...ranges){
		super("设置的值有问题，只能设置为[" + StringUtils.toString(ranges) + "], 而实际值为" + ValueInFact);
	}
	/**
	 * 设置值时返回错误信息
	 * @param log
	 */
	public SetValueException(String log){
		super(log);
	}
}
