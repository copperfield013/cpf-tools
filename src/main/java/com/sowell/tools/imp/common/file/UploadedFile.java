package com.sowell.tools.imp.common.file;

import java.io.File;

public class UploadedFile {
	private File file;
	private String originFileName;
	
	public UploadedFile(File file, String originFileName) {
		super();
		this.file = file;
		this.originFileName = originFileName;
	}
	public File getFile() {
		return file;
	}
	public String getOriginFileName() {
		return originFileName;
	}
	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}
	
}
