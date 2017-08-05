package com.sowell.tools.util.common.logger;


/**
 * 
 * <p>Title: LogTag</p>
 * <p>Description: </p><p>
 * 导入日志的标签，构造函数对应的是
 * </p>
 * @author 张荣波
 * @date 2015年11月10日 下午4:18:02
 */
public enum LogTag {
    	CHECK_MODE("checkMode"),
    	SUC("suc") ,
    	ERROR("error"),
    	//代表空的标签,值也为Null
    	NULL(null), 
    	WARNING("warning")
    	;
    	
    	private String value;
    	private LogTag(String val) {
			this.value = val;
		}
    	@Override
    	public String toString() {
    		return this.value;
    	}
    	public String getValue() {
			return value;
		}
}
