package com.sowell.tools.exception;

import org.dom4j.Element;

public class TagConstructException extends ConstructException {

	/** 
	 *serialVersionUID
	*/
	private static final long serialVersionUID = 1L;
	public TagConstructException(String log, Element e) {
		super(log + (e == null ? "" : ("[" + e.getPath() + "]")));
	}
}
