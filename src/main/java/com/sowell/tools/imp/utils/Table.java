package com.sowell.tools.imp.utils;

import java.util.ArrayList;
import java.util.List;

public class Table {
	List<String> ths = new ArrayList<String>();
	List<Row> rows = new ArrayList<Row>();
	private List<String> errors = new ArrayList<String>();
	public List<String> getThs() {
		return ths;
	}
	public void setThs(List<String> ths) {
		this.ths = ths;
	}
	public List<Row> getRows() {
		return rows;
	}
	public void setRows(List<Row> rows) {
		this.rows = rows;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public String getText() {
		StringBuffer buffer = new StringBuffer();
		for (Row row : this.rows) {
			for (String td : row.getTds()) {
				buffer.append(td + "\t");
			}
			buffer.deleteCharAt(buffer.lastIndexOf("\t"));
			buffer.append("\n");
		}
		buffer.deleteCharAt(buffer.lastIndexOf("\n"));
		return buffer.toString();
	}
}
