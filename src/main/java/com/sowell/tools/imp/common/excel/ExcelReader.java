package com.sowell.tools.imp.common.excel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.sowell.tools.exception.ConstructException;
import com.sowell.tools.exception.FormatException;
import com.sowell.tools.exception.ImportDataException;
import com.sowell.tools.imp.common.handler.CheckInfoHandler;
import com.sowell.tools.imp.common.handler.ImportInfoHandler;
import com.sowell.tools.imp.common.main.ImportDealer;
import com.sowell.tools.imp.common.pojo.ImportInfo;
import com.sowell.tools.imp.common.pojo.ImportInfoContainer;
import com.sowell.tools.imp.enums.ImportDataErrorControl;
import com.sowell.tools.util.POIUtils;
import com.sowell.tools.util.poi.PoiCopyTarget;
import com.sowell.tools.util.poi.PoiCopyTargetImpl;


public class ExcelReader {
	private int firstDataRowNumber ;
	private int headRowNumber ;
	
	private ImportInfoContainer infoContainer;
	
	private Sheet sheet;
	
	//被忽略不导入的行号,1-base
	private TreeSet<Integer> ignoredRowNums;
	//如果要求忽略所有的数据,那么会直接将原表格返回.默认为false
	private boolean isIgnoreAllData;
	//读取的数据行总数
	private int readRowCount;
	
	
	//用于判断是否到达结尾的标记列索引,如果某行这些列对应的单元格值都为空,那么说明遍历表格结束
	private int[] markIndexs;
	private int maxJumpRow;
	
	/**
	 * 构造一个Excel数据读取器
	 * @param sheet 读取数据的表格对象
	 * @param headerRowNumber 表头所在行号1-base
	 * @param firstDataRowNumber 数据行首行的行号1-base
	 * @param markIndexs 数组,代表用于标记是否有数据的列索引0-base
	 * @param maxJumpRow 代表最多能跳过的行的阈值,如果遍历到这么多行的markIndexs的单元都没有数据,那么说明可以跳出数据读取
	 * @throws FileNotFoundException 
	 * @throws IOException
	 */
	public ExcelReader(Sheet sheet, int headerRowNumber, int firstDataRowNumber,
			int[] markIndexs, int maxJumpRow) throws FileNotFoundException, IOException {
		this.headRowNumber = headerRowNumber;
		this.firstDataRowNumber = firstDataRowNumber;
		this.markIndexs = markIndexs;
		this.maxJumpRow = maxJumpRow;
		this.sheet = sheet;
		//this.infoList = new ArrayList<ImportInfo>();
		this.ignoredRowNums = new TreeSet<Integer>();
		//默认不忽略所有数据,只有调用ignoreAllData()方法后才会变为忽略全部
		this.isIgnoreAllData = false;
		this.infoContainer = new ImportInfoContainer();
		//设置读取行数的初始值为0
		this.readRowCount = 0;
	}
	
	
	/**
	 * 获得当前数据行的下一行行号(1-base)，
	 * 如果是有下一行，那么返回值的绝对值|ret|>1；
	 * 如果当前行可用，那么返回值ret>0；
	 * 如果参数错误，那么返回0。<br/>
	 * 即，
	 * 如果返回值<-1，那么有下一行,并且当前为空行<b>跳过行</b><br/>
	 * 如果返回值>1,那么有下一行,并且当前不为空行<b>正常行</b><br/>
	 * 如果返回值=-1,那么没有下一行,并且当前行为空行<b>尾行</b><br/>
	 * 如果返回值=1,那么没有下一行,并且当前行不为空行<b>游离行</b><br/>
	 * @param currentRow
	 * @return
	 * @throws ConstructException 
	 * @throws Exception
	 */
	private int getNextRowNum(Row currentRow){
		ExcelRow row;
		try {
			row = new ExcelRow(currentRow);
		} catch (ConstructException e) {
			return 0;
		}
		boolean allEmpty = true;
		for (int i = 0; i < this.markIndexs.length; i++) {
			int markIndex = this.markIndexs[i];
			String value = row.getString(markIndex);
			if(value != null && !value.isEmpty()){
				allEmpty = false;
				break;
			}
		}
		Integer currentRowNum = currentRow.getRowNum();
		Row nextRow = currentRow.getSheet().getRow(++ currentRowNum);
		int desc = this.maxJumpRow;
		while(nextRow != null && --desc >= 0){
			try {
				row = new ExcelRow(nextRow);
				for (int i = 0; i < this.markIndexs.length; i++) {
					int markIndex = this.markIndexs[i];
					String value = row.getString(markIndex);
					if(value != null && !value.isEmpty()){
						return (currentRowNum + 1) * (allEmpty ? -1 : 1);
					}
				}
			} catch (ConstructException e) {
				break;
			}
			nextRow = nextRow.getSheet().getRow(++ currentRowNum);
		}
		return (allEmpty ? -1 : 1);
	}

	public int getFirstDataRowNumber() {
		return firstDataRowNumber;
	}
	
	private Sheet getCurrentSheet(){
		return this.sheet;
	}
	
	/**
	 * 检测Excel的表头
	 * @param handler
	 * @throws ImportDataException 检测到不可忽视的导入问题时，将会把该错误抛出
	 */
	public void detectHeads(ImportDealer handler) throws ImportDataException {
		Sheet sheet = getCurrentSheet();
		Row row = sheet.getRow(this.headRowNumber - 1);
		if(row != null){
			ExcelRow excelRow;
			try {
				excelRow = new ExcelRow(row);
				handler.readHeader(excelRow);
			} catch (ConstructException e1) {
				e1.printStackTrace();
			} catch (ImportDataException e) {
				throw e;
			}
			
		}
	}
	
	/**
	 * 读取表格里的数据
	 * @param handler
	 * @return
	 * @throws ImportDataException
	 */
	public void importData(ImportDealer handler) throws ImportDataException{
		Sheet sheet = getCurrentSheet();
		if(sheet != null){
			int firstDataRow = this.firstDataRowNumber;
			int nextRowNum, prevRowNum = firstDataRow - 1;
			Row currentRow = sheet.getRow(prevRowNum)
					;
			do {
				ExcelRow row = null;
				nextRowNum = this.getNextRowNum(currentRow);
				if(nextRowNum > 0){
					//只有nextRowNum大于0的情况下,才可以读取当前行
					try {
						//封装行对象
						row = new ExcelRow(currentRow);
						//读取数据的行数自增
						//this.readRowCount ++;
						//控制台显示
						//System.out.println("============" + row.getRowNum());
						try {
							//只要不出现错误行(即currentRow为null),那么就可以将这些行设置为已读.
							//因为没有没有数据的行是直接跳过的,因此要联系上下文来添加这些行
							//TODO: 在这里进行重置读取行数
							int gap = row.getRowNum() - prevRowNum;
							this.readRowCount += gap;
							//添加忽略行
							while(gap > 1) this.ignoredRowNums.add(prevRowNum + --gap);
							handler.validateData(row);
							//对行进行检测
							//根据handler的validateData方法检测每一行数据，
							//如果抛出异常，那么就会记录这个异常
							//如果返回返回null值，那么不记录异常，也不添加
							ImportInfo info = handler.buildInfo(row);
							if (info != null) {
								this.infoContainer.put(info);
							}
						} catch (ImportDataException e) {
							//如果是BREAK异常,那么直接抛出,终止导入
							if (e.getControl() == ImportDataErrorControl.BREAK) {
								throw e;
							}
							//将导入时抛出错误的行号放入数组中
							this.ignoredRowNums.add(row.getRowNum());
						}
					} catch (ConstructException e) {
					}
					//将当前有效行的行号赋给prevRowNum
					prevRowNum = currentRow.getRowNum() + 1;
				}
				int absNextRowNum = Math.abs(nextRowNum);
				if(absNextRowNum > 1){
					//如果有下一行,那么就获得下一行，因为nexrRowNum是1-base,所以要减1
					currentRow = sheet.getRow(absNextRowNum - 1);
				}else{
					//如果没有下一行,那么就直接跳出读取数据的循环
					break;
				}
			} while (currentRow != null);
			System.out.println("=======遍历结束=======");
		}
	}
	
	/**
	 * 使用handler的checkInfo方法对当前导入的所有的Import对象进行检测
	 * @param handler
	 * @return
	 * @throws ImportDataException
	 * @throws ConstructException
	 */
	public void checkInfo( ImportDealer handler ) throws ImportDataException{
		System.out.println("===============checkInfo-begin=============");	
		//遍历导入的所有数据对象，逐一进行检测
		for ( ImportInfo info : this.infoContainer.getImportInfoSet() ) {
			CheckInfoHandler arg = new CheckInfoHandler(info, this.infoContainer, this.ignoredRowNums);
			//使用handler对象的checkInfo方法对当前数据对象进行检测
			try {
				if(this.infoContainer.isEnabled(info)){
					handler.checkInfo( arg );					
				}
			} catch (ImportDataException e) {
				System.err.println(e.getMessage());
				//如果是BREAK,那么直接抛出错误
				if(e.getControl() == ImportDataErrorControl.BREAK){
					throw e;
				}
			}
		}		
		//将阶段标志设置为已经检测完毕
		System.out.println("===============checkInfo-end=============");
	}
	
	
	/**
     * 使用handler的checkInfo方法对当前导入的所有的Import对象进行检测
     * @param handler
     * @return
     * @throws ImportDataException
	 * @throws InterruptedException 
     * @throws ConstructException
     */
    public void importInfo( ImportDealer handler ) throws ImportDataException, InterruptedException{
        System.out.println("===============importInfo-begin=============");  
        int i = 0;
            /*Session session = HibernateUtils.currentSession();
            handler.setSession(session);
            session.beginTransaction();*/
        //遍历导入的所有数据对象，进行导入
        for ( ImportInfo info : this.infoContainer.getImportInfoSet() ) {
            ImportInfoHandler importInfoHandler = new ImportInfoHandler(info, this.infoContainer, this.ignoredRowNums);
            try {
                if(this.infoContainer.isEnabled(info)){
                    handler.importInfo( importInfoHandler );                                           
                }
            } catch (ImportDataException e) {
                System.err.println(e.getMessage());
                //如果是BREAK,那么直接抛出错误
                if(e.getControl() == ImportDataErrorControl.BREAK){
                    //session.beginTransaction().rollback();
                    throw e;
                }
            }
            if((i+1)%1000==0){
                //session.flush();
                //session.beginTransaction().commit();                
               // HibernateUtils.closeSession();
                Thread.sleep(3000);
               // session = HibernateUtils.currentSession();
               // handler.setSession(session);
              //  session.beginTransaction();
            }
            i++;
        }        
        //session.beginTransaction().commit();  
        //将阶段标志设置为已经检测完毕
        System.out.println("===============importInfo-end=============");
    }
	
	
	public ExcelReader end(ImportDealer handler) throws ImportDataException{
		handler.end();
		System.out.println("===============importInfo-finish=============");
		return this;
	}
	
	public Sheet getSheet(){
		return this.sheet;
	}
	
	/**
	 * 获取读取的数据行总数,不包含空行,包含错误数据行
	 * @return
	 */
	public int getReadRowCount() {
		return readRowCount;
	}
	
	public int getIgnoredRowCount(){
		return this.ignoredRowNums.size();
	}
	
	/**
	 * 忽略所有数据,意味着所有数据都没进行导入
	 * 一般是在表头验证时出现问题,或者抛出BREAK异常时,会用到这个方法
	 */
	public void ignoreAllData() {
		this.isIgnoreAllData = true;
		
	}
	
	
	/**
	 * 获得没有被导入的行的表格,返回的工作簿对象是Excel2003版本的对象
	 * @return
	 */
	public HSSFWorkbook getIgnoredDataWorkbook(){
		PoiCopyTarget target = new PoiCopyTargetImpl();
		final String sheetName = "忽略行";
		if(this.isIgnoreAllData){
			target.copySheet(sheetName, this.sheet);
		}else{
			for (int i = 0; i < this.firstDataRowNumber - 1; i++) {
				Row srcRow = this.sheet.getRow(i);
				target.copyRow(sheetName, i	, srcRow);
			}
			
			//获得忽略的数据行
			Iterator<Integer> ignored = this.ignoredRowNums.iterator();
			int i = this.firstDataRowNumber - 1;
			while(ignored.hasNext()){
				Integer currentSrcIndex = ignored.next();
				Row srcRow = this.sheet.getRow(currentSrcIndex - 1);
				target.copyRow(sheetName, i, srcRow);
				i++;
			}
			POIUtils.mergerRegion(this.sheet, target.getWorkbook().getSheet(sheetName));
		}
		return (HSSFWorkbook) target.getWorkbook();
	}
	
	
	//======================静态方法区======================
	

	/**
	 * 将由英文字母组成的字符串转成Excel表格的列序列索引
	 * @param str
	 * @return
	 * @throws FormatException
	 */
	public static int toColumnIndex(String str) throws FormatException{
		int index = 0;
		if(str != null && str.matches("^[A-Za-z]*$")){
			char[] chars = str.toUpperCase().toCharArray();
			for(int i = chars.length - 1 ; i >= 0 ; --i){
				index += ( chars[i] - 'A' + ( i < chars.length - 1 ? 1 : 0) ) * Math.pow(26, chars.length - i -1);
			}
		}else{
			throw new FormatException("传入的字符串必须是由英文字母组成");
		}
		return index;
	}
	
	public static String toColumnPosition(int index) throws FormatException{
		if(index < 0) throw new FormatException("传入的索引必须大于等于0");
		String ret = "";
		int A = 'A';
		for(;;){
			int cur = index % 26;
			char c = (char) (A + cur);
			ret = c + ret;
			index = index / 26;
			if(index < 26){
				if(index == 0) break;
				ret = ( char )(A + index - 1) + ret;
				break;
			}
		}
		return ret;
	}
	
	
	public static void main(String[] args) throws FormatException{
		System.out.print('a');
		System.out.print('\b');
		System.out.print('b');
	}
	//==================================================


}