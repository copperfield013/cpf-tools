package com.sowell.tools.imp.common.main;

import com.sowell.tools.exception.ImportDataException;
import com.sowell.tools.imp.common.excel.ExcelRow;
import com.sowell.tools.imp.common.handler.CheckInfoHandler;
import com.sowell.tools.imp.common.handler.ImportInfoHandler;
import com.sowell.tools.imp.common.pojo.ImportInfo;

public interface ImportDealer {
	
	/**
	 * 
	 * @param row
	 */
	public void readHeader(ExcelRow row) throws ImportDataException;
	
	/**
	 * 
	 * @param row
	 */
	public void validateData(ExcelRow row) throws ImportDataException;
	
	
	/**
	 * 从Excel表格导入数据，ExcelRow参数表示一行数据
	 * 最后返回一个继承ImportInfo接口的对象，表示导入的对象
	 * 如果返回返回null值，那么不记录异常，也不添加
	 * 如果抛出异常，那么就会记录这个异常
	 * @param row
	 * @return
	 * @throws Exception 抛出异常会进行记录
	 */
	public ImportInfo buildInfo(final ExcelRow row) throws ImportDataException;
	
	/**
	 * 检测导入的数据对象，并返回一个检测结果对象
	 * 返回的检测结果会自动赋给ImportInfo参数对象的checkResult属性
	 * 不能返回null值
	 * @param people
	 * @return
	 * @throws ImportDataException
	 */
	public void checkInfo(final CheckInfoHandler handler) throws ImportDataException;
	
	/**
	 * 对所有导入的ImportInfo对象逐一执行导入。这里表示对其中一个对象进行导入时的情况
	 * 可以通过info的的checkResult属性进行判断，并执行不同的分支时的不同操作
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public void importInfo(final ImportInfoHandler handler) throws ImportDataException;
	
	public void end() throws ImportDataException;
	
	public boolean checkmode();
	
}
