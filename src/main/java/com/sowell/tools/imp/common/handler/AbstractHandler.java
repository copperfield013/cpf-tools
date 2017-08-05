package com.sowell.tools.imp.common.handler;

import com.sowell.tools.imp.common.main.ImportDealer;
import com.sowell.tools.util.common.logger.ImportLogger;

/**
 * 
 * <p>Title: AbstractHandler</p>
 * <p>Description: </p><p>
 * 
 * </p>
 * @author 张荣波
 * @date 2015年10月12日 上午9:34:31
 */
public abstract class AbstractHandler {
	//用于记录的日志对象
	private ImportLogger logger;
	//当前对象执行的dealer
	private ImportDealer dealer;
	
	public AbstractHandler(ImportLogger logger, ImportDealer dealer) {
		super();
		this.logger = logger;
		this.dealer = dealer;
	}

	public void addLog(String log) throws InterruptedException{
		this.logger.addLog(log);
	}
	
	
}
