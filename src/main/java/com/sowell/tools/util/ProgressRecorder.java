package com.sowell.tools.util;

import com.google.gson.JsonObject;


public class ProgressRecorder {
	private String progressMsg;
	private int progress = 0;
	private int total = 100;
	private int stepLength = 1;
	public ProgressRecorder setProgressMsg(String progress){
		this.progressMsg = progress;
		return this;
	}
	public String getProgressMsg() {
		return this.progressMsg;
	}
	public ProgressRecorder incStep(){
		this.progress += this.stepLength;
		return this;
	}
	public int getTotal() {
		return total;
	}
	public ProgressRecorder setTotal(int total) {
		this.total = total;
		return this;
	}
	public int getStepLength() {
		return stepLength;
	}
	public ProgressRecorder setStepLength(int stepLength) {
		this.stepLength = stepLength;
		return this;
	}
	public int getProgress() {
		return progress;
	}
	public ProgressRecorder fullProgress() {
		this.progress = this.total;
		return this;
	}
	
	public String toJSON(){
		JsonObject jo = new JsonObject();
		jo.addProperty("msg", this.progressMsg);
		jo.addProperty("progress", this.getProgress());
		jo.addProperty("total", this.getTotal());
		return jo.toString();
	}
}
