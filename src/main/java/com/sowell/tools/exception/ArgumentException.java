package com.sowell.tools.exception;

import com.sowell.tools.util.FormatUtils;
import com.sowell.tools.util.StringUtils;

public class ArgumentException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2591444558023855285L;

	public ArgumentException(String log) {
		super(log);
	}
	public ArgumentException(String log, String[] argNames, Object[] argValues){
		this(log + to(argNames, argValues));
	}
	
	private static String to(String[] argNames, Object[] argValues){
		String str = "[";
		for(int i  = 0 ; i < argNames.length ; i ++){
			String value = "Unknow";
			if(i < argValues.length){
				value = FormatUtils.toString(argValues[i]);
			}
			str += argNames[i] + "=" + value + ",";
		}
		str = StringUtils.trim(str, ",");
		return str;
	}
	
}
