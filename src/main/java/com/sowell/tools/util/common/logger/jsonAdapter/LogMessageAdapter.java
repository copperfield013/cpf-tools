package com.sowell.tools.util.common.logger.jsonAdapter;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.sowell.tools.util.common.logger.LogMessage;

public class LogMessageAdapter extends TypeAdapter<LogMessage> {

	@Override
	public void write(JsonWriter out, LogMessage value) throws IOException {
		if(value == null){
			out.nullValue();
		}else{
			out.value(value.toString());
		}

	}

	@Override
	public LogMessage read(JsonReader in) throws IOException {
		// TODO 自动生成的方法存根
		return null;
	}

}
