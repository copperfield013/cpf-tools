package com.sowell.tools.imp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * <p>Title: CheckInDataFormat</p>
 * <p>Description: </p><p>
 * 用于在数据导入时的数据格式和操作
 * </p>
 * @author 张荣波
 * @date 2015年8月4日
 */
public class CheckInDataFormat {
	
	
	//=======static属性区=============
	private static CheckInDataFormat instance;
	private static final Pattern chinesePattern = Pattern.compile("[\u4e00-\u9fa5]+");
	
	//==============================
	
	
	
	
	//=======static方法区=============
	/**
	 * 获取一个单例
	 * @return
	 */
	public static CheckInDataFormat getInstance(){
		if(instance == null){
			instance = new CheckInDataFormat();
		}
		return instance;
	}
	
	public static void main(String[] args){
		CheckInDataFormat f = CheckInDataFormat.getInstance();
		System.out.println(f.checkChineseName(null));
	}
	//==============================
	
	
	
	
	
	
	
	//========对象方法区===============
	/**
	 * 禁止外部实例化
	 */
	private CheckInDataFormat() {
	}
	/**
	 * 检测中文名称，即名字全为中文，不允许其他符号存在
	 * @param name
	 * @return
	 */
	public boolean checkChineseName(String name){
		if(name != null){
			name = new String(name.getBytes());//用GBK编码
			Matcher result = chinesePattern.matcher(name);                  
			return result.matches(); //是否全部中文字符 
		}
		return false;
	}
	
	//==============================
	
}
