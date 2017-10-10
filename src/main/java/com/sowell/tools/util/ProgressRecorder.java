package com.sowell.tools.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.hssf.record.Record;

import com.alibaba.fastjson.JSONObject;


public class ProgressRecorder {
	private String progressMsg;
	private int progress = 0;
	private int total = 100;
	private int stepLength = 1;
	private Map<String, Object> data = new LinkedHashMap<String, Object>();
	private boolean ended = false;
	private boolean breakFlag = false;
	
	private String errorMsg;
	
	public void checkBreaked(){
		if(breakFlag) throw new ProgressBreakException();
	}
	public ProgressRecorder setProgressMsg(String progress) throws ProgressBreakException{
		checkBreaked();
		this.progressMsg = progress;
		return this;
	}
	public String getProgressMsg() {
		return this.progressMsg;
	}
	public synchronized ProgressRecorder incStep() throws ProgressBreakException{
		checkBreaked();
		if(this.progress < this.total){
			this.progress += this.stepLength;
			if(this.progress > this.total){
				this.progress = this.total;
			}
		}
		return this;
	}
	public int getTotal() {
		return total;
	}
	public synchronized ProgressRecorder setTotal(int total) throws ProgressBreakException {
		checkBreaked();
		this.total = total;
		return this;
	}
	public int getStepLength() {
		return stepLength;
	}
	public ProgressRecorder setStepLength(int stepLength) throws ProgressBreakException {
		checkBreaked();
		this.stepLength = stepLength;
		return this;
	}
	public int getProgress() {
		return progress;
	}
	public int getRemainProgress(){
		return this.total - this.progress;
	}
	public synchronized ProgressRecorder fullProgress() throws ProgressBreakException {
		checkBreaked();
		this.progress = this.total;
		return this;
	}
	
	public JSONObject toJSON(){
		JSONObject jo = new JSONObject();
		jo.put("msg", this.progressMsg);
		jo.put("progress", this.getProgress());
		jo.put("total", this.getTotal());
		jo.putAll(data);
		return jo;
	}
	
	public String toJSONString(){
		return toJSON().toJSONString();
	}
	public void setData(String key, Object value) {
		data.put(key, value);
	}
	public Object getData(String key){
		return data.get(key);
	}
	public synchronized void setEnded(){
		this.ended = true;
	}
	
	public boolean isEnded(){
		return this.ended;
	}
	
	public synchronized void breakProgress(){
		this.breakFlag = true;
	}
	public void setError(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String getError(){
		return errorMsg;
	}
	
}
