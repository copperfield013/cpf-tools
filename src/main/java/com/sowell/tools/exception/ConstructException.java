package com.sowell.tools.exception;

public class ConstructException extends RuntimeException {
	
	/** 
	 *serialVersionUID
	*/
	private static final long serialVersionUID = 1089157257556038587L;

	public ConstructException() {
		super();
	}
	
	public ConstructException(String log) {
		super(log);
	}
	
}
