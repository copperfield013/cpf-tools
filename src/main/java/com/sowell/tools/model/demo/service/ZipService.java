package com.sowell.tools.model.demo.service;

import java.io.File;
import java.io.OutputStream;

import com.sowell.tools.util.ProgressRecorder;

public interface ZipService {
	public void zip(File[] files, OutputStream outputStream) ;

	void zip(File[] files, OutputStream outputStream, String prefix,
			String extName, ProgressRecorder record);
}
