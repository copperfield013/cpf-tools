package com.sowell.tools.util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;

public class FormatUtils {

	public static Integer toInteger(Object o){
		try {
			return Double.valueOf(o.toString()).intValue();
		} catch (Exception e) {
		}
		return null;
	}
	
	public static Long toLong(Object o){
		try {
			return BigDecimal.valueOf(toDouble(o)).longValue();
		} catch (Exception e) {
		}
		return null;
	}
	
	public static String toString(Object o){
		try {
			return o.toString();
		} catch (Exception e) {
		}
		return null;
	}
	
	public static BigDecimal toBigDecimal(Object o){
		try {
			return BigDecimal.valueOf(toLong(o));
		} catch (Exception e) {
		}
		return null;
	}
	
	public static Double toDouble(Object o){
		try {
			return Double.valueOf(o.toString());
		} catch (Exception e) {
		}
		return null;
	}
	/**
	 * 将对象转换成布尔型
	 * 如果对象是字符串类型，就根据Boolean.value()方法转换，
	 * 只有传入"true"的时候才为真,
	 * 如果传入其它类型的对象，则仅当对象不为空时返回真
	 * @param o
	 * @return
	 */
	public static Boolean toBoolean(Object o){
		try {
			if(o != null){
				if(o instanceof String){
					return Boolean.valueOf(o.toString());
				}else{
					return true;
				}
			}
		} catch (Exception e) {}
		return false;
	}
	
	public static LinkedHashMap<Object, Object> toMap(Object[] objs){
		if(objs != null){
			LinkedHashMap<Object, Object> ret = new LinkedHashMap<Object, Object>();
			for (Object obj : objs) {
				ret.put(obj, obj);
			}
			return ret;
		}
		return null;
	}
	
	public static LinkedHashMap<Object, Object> toMap(Collection<Object> objs){
		if(objs != null){
			return toMap(objs.toArray(new Object[objs.size()]));
		}
		return null;
	}
	
	
	public static void main(String[] args){
		System.out.println(FormatUtils.toLong("1e15"));
	}
}
