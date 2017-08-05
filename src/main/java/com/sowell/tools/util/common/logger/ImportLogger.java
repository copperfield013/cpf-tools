package com.sowell.tools.util.common.logger;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sowell.tools.exception.FormatException;
import com.sowell.tools.imp.common.excel.ExcelReader;
import com.sowell.tools.util.StringUtils;
import com.sowell.tools.util.common.logger.jsonAdapter.LogMessageAdapter;
import com.sowell.tools.util.common.logger.jsonAdapter.LogTagAdapter;

/**
 * 
 * <p>Title: ImportLogger</p>
 * <p>Description: </p><p>
 * 导入日志记录器类，继承于阻塞队列LinkedBlockingQueue
 * </p>
 * @author 张荣波
 * @date 2015年9月25日 上午10:36:08
 */
public class ImportLogger {

	private LinkedBlockingQueue<LogElement> msg;
	
	/**
	 * 构造一个日志记录器对象
	 */
	public ImportLogger() {
		super();
		this.msg = new LinkedBlockingQueue<LogElement>();
	}
	
	/**
	 * 添加日志的方法。直接添加一个空的日志对象，然后将日志对象拿出来处理
	 * 这个方法不能给开放出去的原因是，日志对象的记录时间属性是根据构造函数时来的，
	 * 因此逻辑上构造后是不应该进行处理的
	 * @return
	 */
	private LogElement addLog(){
		LogElement log = new LogElement("");
		try {
			this.msg.put(log);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			//因为是用put来添加元素,并且LinkedBlockingQueue是线程安全的,一旦队列已经满了,那么会进行阻塞
			//直到有空间可以把元素放入.但是如果此时线程被打断,那么就会返回InterruptedException错误
			//前期先不处理这个问题
			e.printStackTrace();
		}
		return log;
	}
	
	public LogElement addLog(String msg, LogTag...tags){
		LogElement log = this.addLog();
		if(log != null){
			log.setMessage(msg);
			for (LogTag tag : tags) {
				log.addTag(tag);
			}
		}
		return log;
	}
	
	public LogElement addLog(int rowNum, String msg, LogTag...tags){
		return this.addLog(this.formaLog(rowNum, msg), tags);
	}
	
	
	public LogElement addLog(int rowNum, int cellNum, String msg, LogTag...tags){
		try {
			return this.addLog(rowNum, ExcelReader.toColumnPosition(cellNum), msg,tags);
		} catch (FormatException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public LogElement addLog(int rowNum, String colPosition, String msg, LogTag...tags){
		return this.addLog(this.formaLog(rowNum, colPosition, msg), tags);
	}
	
	
	private String formaLog(int rowNum, String msg) {
		return "<" + rowNum + "行>" + (msg == null ? "" : msg);
	}
	
	private String formaLog(int rowNum, String colPosition, String msg) {
		return "<" + rowNum + "行" + colPosition + "列>" + (msg == null ? "" : msg);
	}




	/**
	 * 格式化日志内容
	 * @param rowNum
	 * @param colPos
	 * @param logMsg
	 * @param isError
	 * @return
	 */
	private String formatLog(boolean checkMode, int rowNum, String colPos, String logMsg, boolean isError){
		String ret = "";
		ret = (checkMode ? "[检测]" : "" ) + (isError ? "错误:" : "提示:") + "<" + rowNum + "行" + colPos + "列>" + logMsg;
		return ret;
	}
	/**
	 * 格式化针对某行的错误信息
	 * @param rowNum
	 * @param logMsg
	 * @param isError
	 * @return
	 */
	private String formaLog(boolean checkMode, int rowNum, String logMsg, boolean isError){
		String ret = "";
		ret = (checkMode ? "[检测]" : "" ) + (isError ? "错误:" : "提示:") + "<" + rowNum + "行>" + logMsg;
		return ret;
	}
	
	/**
	 * 将日志信息按顺序复制到一个数组中
	 * @return
	 */
	public ArrayList<LogElement> toArrayList(){
		ArrayList<LogElement> list = new ArrayList<LogElement>(this.msg);
		return list;
	}
	
	
	public final static String LOGGER_SPLITER = ";";
	
	/**
	 * 将日志内容全部转换成字符串形式，队列不变
	 */
	@Override
	public String toString() {
		ArrayList<LogElement> list = this.toArrayList();
		String ret = "";
		for (LogElement log : list) {
			ret += log.toString() + LOGGER_SPLITER;
		}
		
		return ret;
	}
	
	
	/**
	 * 将日志内容全部转换成Json形式数组，队列不变
	 */
	public String toJson() {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = 
				builder
					.registerTypeAdapter(LogTag.class, new LogTagAdapter())
					.registerTypeAdapter(LogMessage.class, new LogMessageAdapter())
					.create(); 
		ArrayList<LogElement> list = this.toArrayList();
		String json = "{\"msg\":";
		json += gson.toJson(list);
		json = StringUtils.trim(json, ",");
		
		json += "}";
		return json;
	}
	
	public String toText(){
		ArrayList<LogElement> list = this.toArrayList();
		String text = "";
		for (LogElement log : list) {
			text += log + "\r\n";
		}
		return text;
	}
	
	
}
