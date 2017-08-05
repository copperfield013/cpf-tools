package com.sowell.tools.imp.common.main;

import java.sql.SQLException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.sowell.tools.exception.ConstructException;
import com.sowell.tools.exception.ImportDataException;
import com.sowell.tools.imp.common.excel.ExcelReader;
import com.sowell.tools.util.common.logger.ImportLogger;

public class ImportWork {
	//private String userName;

	private ExcelReader reader;
	
	private ImportDealer importDealer;

	private ImportLogger logger;

	private HSSFWorkbook ignoredWorkbook;
	
	
	/**
	 * 通过areaCode和要导入的人口excel表格对象，以及首个数据行索引和标识列索引构造一个导入工作
	 * @param areaCode 要导入的行政区域
	 * @param sheet 要导入的表格
	 * @param firstDataRowNumber 要导入的表格中，首个数据行的索引
	 * @param positionCellIndex 要导入的表格中，标志列的索引
	 * @throws ConstructException 
	 */
	public ImportWork(ImportDealer importDealer, ExcelReader reader, ImportLogger logger) throws ConstructException {
		super();
		if(importDealer != null){
			this.importDealer = importDealer;
		}else{
			throw new ConstructException("databaseDealer参数对象为null");
		}
		//this.userName = user.getAccount();
		this.logger = logger;
		this.reader = reader;
	}
	
	/**
	 * 实现Hibernate的Work接口的execute方法。在普通的jdbc环境下，也可以传入Connection对象直接进行导入工作
	 */
	public void execute() throws SQLException {
		ImportDealer handler = this.importDealer;
		try {
			
			//根据handler处理excel的表头
			this.reader.detectHeads(handler);
			
			//根据handler导入excel的人口数据
			this.reader.importData(handler);
			//根据hander检测导入到内存的人口对象
			this.reader.checkInfo(handler);
			//导入数据到数据库
			if(!handler.checkmode()){
			   this.reader.importInfo(handler);
			}
		} catch (ImportDataException e) {
			e.printStackTrace();
			//如果出现问题,那么说明数据是全部都没有导入的,那么应该是忽略所有数据
			this.reader.ignoreAllData();
			throw new SQLException(e); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//执行导入收尾动作
			try {
				this.reader.end(handler);
			} catch (ImportDataException e) {
				e.printStackTrace();
				throw new SQLException(e); 
			}finally{
				this.ignoredWorkbook = reader.getIgnoredDataWorkbook();
			}
		}
	}
	public ImportLogger getLogger() {
		return logger;
	}
	
	public HSSFWorkbook getIgnoredWorkbook(){
		return this.ignoredWorkbook;
	}
	

}
