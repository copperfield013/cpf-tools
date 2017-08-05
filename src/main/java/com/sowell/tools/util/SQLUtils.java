package com.sowell.tools.util;


public class SQLUtils {
	
	public static String toString(Object[] array){
		String ret = "";
		if(array != null){
			for (Object object : array) {
				ret += "'" + FormatUtils.toString(object) + "',";
			}
			ret = StringUtils.trim(ret, ",");
		}
		return ret;
	}
	
	public static String toReservedSQL(String word){
		return "`" + word + "`";
	}
	
}
