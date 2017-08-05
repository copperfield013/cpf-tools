package com.sowell.tools.imp.utils;

import com.sowell.tools.util.common.logger.ImportLogger;
import com.sowell.tools.util.common.logger.LogTag;

public class ImportUtils {
	
	/**
	 * 将跳过的行进行记录,如果prevRowNum不比currentRowNum小,或者四个参数中有任意一个为null
	 * 那么就不记录
	 * @param prevRowNum 前一行进行导入的行
	 * @param currentRowNum 当前进行导入的行
	 * @param logger 日志记录器
	 * @param msg 用于记录的信息模板,用$代表跳过行的行号
	 * @param tags 
	 * @return 返回所有跳过的行号(1-base),如果没有跳过任何行,或者参数错误,则返回一个长度为0的数组
	 */
	public static Integer[] logJumpedRow(Integer prevRowNum, Integer currentRowNum, ImportLogger logger, String msg, LogTag... tags){
		if(prevRowNum != null && currentRowNum != null && logger != null && msg != null && prevRowNum < currentRowNum - 1){
			Integer[] jumpedRowNum = new Integer[currentRowNum - prevRowNum - 1];
			for(Integer rowNum = prevRowNum + 1, i = 0; rowNum < currentRowNum; rowNum++, i++){
				jumpedRowNum[i] = rowNum;
				String loggerMsg = msg.replaceAll("\\$", rowNum.toString());
				logger.addLog(loggerMsg, tags);
			}
			return jumpedRowNum;
		}
		return new Integer[0];
	}
	
	
	
}
