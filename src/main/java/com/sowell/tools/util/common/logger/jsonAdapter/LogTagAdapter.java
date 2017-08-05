package com.sowell.tools.util.common.logger.jsonAdapter;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.sowell.tools.util.common.logger.LogTag;

public class LogTagAdapter extends TypeAdapter<LogTag>{

	@Override
	public void write(JsonWriter out, LogTag value) throws IOException {
		if(value == null){
			out.nullValue();
		}else{
			out.value(value.toString());
		}
	}

	@Override
	public LogTag read(JsonReader in) throws IOException {
		return null;
	}
	
}