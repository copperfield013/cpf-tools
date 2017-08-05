package com.sowell.tools.util.common.logger;


import java.util.HashSet;

import com.google.gson.Gson;

public class LogElement {
	private LogMessage message;
	private Long logTime;
	private HashSet<LogTag> tags;
	
	public LogElement(String message) {
		super();
		this.message = new LogMessage(message, this);
		this.logTime = System.currentTimeMillis();
		this.tags = new HashSet<LogTag>();
	}
	public LogMessage getMessage() {
		return message;
	}
	LogElement setMessage(String message) {
		this.message = new LogMessage(message, this);
		return this;
	}
	public Long getLogTime() {
		return logTime;
	}
	/**
	 * 添加标签.如果tag为null或LogTag.NULL,那么不进行添加
	 * @param tag
	 * @return
	 */
	public LogElement addTag(LogTag tag){
		if(tag != null && tag != LogTag.NULL){
			this.tags.add(tag);
		}
		return this;
	}
	public LogElement removeTag(LogTag tag){
		this.tags.remove(tag);
		return this;
	}
	
	public boolean hasTag(LogTag tag){
		return this.tags.contains(tag);
	}
	
	public HashSet<LogTag> getTags(){
		return this.tags;
	}
	/**
	 * 重写toString方法,根据tag来设置内容
	 */
	@Override
	public String toString() {
		if(this.message != null){
			return this.message.toString();
		}
		return null;
	}
	
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	public void setLogTime(Long logTime) {
		this.logTime = logTime;
	}
	public void setTags(HashSet<LogTag> tags) {
		this.tags = tags;
	}
}
