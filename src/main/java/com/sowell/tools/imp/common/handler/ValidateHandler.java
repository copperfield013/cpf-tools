package com.sowell.tools.imp.common.handler;

import com.sowell.tools.imp.common.main.ImportDealer;
import com.sowell.tools.util.common.logger.ImportLogger;

/**
 * 
 * <p>Title: ValidateHandler</p>
 * <p>Description: </p><p>
 * 检测器的参数对象
 * </p>
 * @author 张荣波
 * @date 2015年10月12日 上午9:38:24
 */
public abstract class ValidateHandler extends AbstractHandler{

	public ValidateHandler(ImportLogger logger, ImportDealer dealer) {
		super(logger, dealer);
	}
	
}
