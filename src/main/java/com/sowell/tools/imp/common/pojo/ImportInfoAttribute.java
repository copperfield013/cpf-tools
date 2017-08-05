package com.sowell.tools.imp.common.pojo;

import java.util.HashMap;

/**
 * 
 * <p>Title: ImportInfoAttribute</p>
 * <p>Description: </p><p>
 * 用于外部配置某个对象的属性
 * </p>
 * @author 张荣波
 * @date 2015年10月14日 下午8:05:13
 */
public class ImportInfoAttribute {
	//代表的ImportInfo
	private ImportInfo info;
	//自定义参数
	private HashMap<String, Object> attributes;
	//这个对象在后面的步骤是否还要进行导入
	private boolean enabled; 
	
	public ImportInfoAttribute(ImportInfo info) {
		this.info = info;
		this.attributes = new HashMap<String, Object>();
		this.enabled = true;
	}
	/**
	 * 获得对应对象的自定义属性
	 * @param name
	 * @return
	 */
	public Object getAttribute(String name){
		return this.attributes.get(name);
	}
	/**
	 * 设置自定义属性值,返回值为原本的属性值
	 * @param name
	 * @param value
	 * @return
	 */
	public Object setAttribute(String name, Object value){
		return this.attributes.put(name, value);
	}
	/**
	 * 启用对应ImportInfo对象的导入
	 */
	public void enableInfo(){
		this.enabled = true;
	}
	/**
	 * 禁用对应ImportInfo对象的导入
	 */
	public void disableInfo(){
		this.enabled = false;
	}
	/**
	 * 获得对应的属性值
	 * @return
	 */
	public ImportInfo getImportInfo(){
		return this.info;
	}
	
	public boolean isEnbaled(){
		return this.enabled;
	}
}
