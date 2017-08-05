package com.sowell.tools.util.poi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.Assert;

import com.sowell.tools.util.FileUtils;
import com.sowell.tools.util.FormatUtils;
import com.sowell.tools.util.POIUtils;

public class PoiCopyTargetImpl implements PoiCopyTarget{

	private Map<CellStyle, CellStyle> cellStyleMap;
	
	private Workbook target;
	
	private int maxRowIndex;
	
	public PoiCopyTargetImpl() {
		this.target = new HSSFWorkbook();
		this.cellStyleMap = new HashMap<CellStyle, CellStyle>();
		this.maxRowIndex = -1;
	}
	/**
	 * 获得目标Workbook中对应sheetName的sheet，如果不存在就创建
	 * 总之返回值不会为null
	 * @param targetSheetName
	 * @return
	 */
	private Sheet getTargetSheet(String targetSheetName){
		Sheet sheet = this.target.getSheet(targetSheetName);
		if(sheet == null){
			sheet = this.target.createSheet(targetSheetName);
		}
		return sheet;
	}
	
	private Row getTargetRow(String targetSheetName, int targetRowIndex){
		Sheet targetSheet = this.getTargetSheet(targetSheetName);
		Row row = targetSheet.getRow(targetRowIndex);
		if(row == null){
			row = targetSheet.createRow(targetRowIndex);
		}
		return row;
	}
	
	private Cell getTargetCell(String targetSheetName, int targetRowIndex, int targetColumnIndex){
		Row targetRow = this.getTargetRow(targetSheetName, targetRowIndex);
		Cell cell = targetRow.getCell(targetColumnIndex);
		if(cell == null){
			cell = targetRow.createCell(targetColumnIndex);
		}
		return cell;
	}
	
	
	@Override
	public void copySheet(String targetSheetName, Sheet srcSheet) {
		Assert.hasText(targetSheetName);
		Assert.notNull(srcSheet);
		Sheet targetSheet = this.getTargetSheet(targetSheetName);
		
		POIUtils.mergerRegion(srcSheet, targetSheet);
		
		for (Iterator<Row> rowIt = srcSheet.rowIterator();rowIt.hasNext();) {
			Row srcRow = (Row) rowIt.next();
			this.copyRow(targetSheetName, srcRow.getRowNum(), srcRow);
			this.maxRowIndex = srcRow.getRowNum() > this.maxRowIndex ? srcRow.getRowNum() : this.maxRowIndex;
		}
		
	}

	
	@Override
	public void copyRow(String targetSheetName, Integer targetRowIndex, Row srcRow) {
		Assert.hasText(targetSheetName);
		if(srcRow != null){
			Row targetRow = this.getTargetRow(targetSheetName, targetRowIndex);
			
			targetRow.setHeight(srcRow.getHeight());
			
			
			int j = 0;
			for (Iterator<Cell> cellIt = srcRow.cellIterator(); cellIt.hasNext() && j < HSSFCell.LAST_COLUMN_NUMBER; j++) {
				Cell srcCell = cellIt.next();
				this.copyCell(targetSheetName, targetRowIndex, j, srcCell);
			}
		}
	}

	@Override
	public void copyRow(String targetSheetName, Row srcRow) {
		this.copyRow(targetSheetName, this.maxRowIndex + 1, srcRow);
	}

	@Override
	public void copyCell(String targetSheetName, Integer targetRowIndex,
			Integer targetColumnIndex, Cell srcCell) {
		Assert.hasText(targetSheetName);
		if(srcCell != null){
			Cell targetCell = this.getTargetCell(targetSheetName, targetRowIndex, targetColumnIndex);
			//复制cellStyle
			CellStyle srcCellStyle = srcCell.getCellStyle(), targetCellStyle = null,
					existCellStyle = this.cellStyleMap.get(srcCellStyle);
			if(existCellStyle == null) {
				targetCellStyle = this.target.createCellStyle();
				POIUtils.copyCellStyle(srcCellStyle, targetCellStyle);
				this.cellStyleMap.put(srcCellStyle, targetCellStyle);
			}else{
				targetCellStyle = existCellStyle;
			}
			targetCell.setCellStyle(targetCellStyle);
			//复制CellComment
			if(srcCell.getCellComment() != null){
				targetCell.setCellComment(srcCell.getCellComment());
			}
			
			//复制Value
			this.copyCellValue(targetCell, srcCell);
		}
		
	}
	/**
	 * 复制srcCell的值到targetCell中，包括设置cellType
	 * @param targetCell
	 * @param srcCell
	 */
	private void copyCellValue(Cell targetCell, Cell srcCell) {
		//复制cellType
		int srcCellType = srcCell.getCellType();
		targetCell.setCellType(srcCellType);
		switch (srcCellType) {
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(srcCell)) {
				targetCell.setCellValue(srcCell.getDateCellValue());
			} else {
				targetCell.setCellValue(srcCell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_STRING:
			if(targetCell.getClass() == srcCell.getClass()){
				targetCell.setCellValue(srcCell.getRichStringCellValue());
			}else{
				targetCell
						.setCellValue(srcCell.getRichStringCellValue() == null ? null
								: srcCell.getRichStringCellValue().getString());
				;
			}
			break;
		case Cell.CELL_TYPE_BLANK:
			//将目标的值设置为空
			targetCell.setCellValue(FormatUtils.toString(null));
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			targetCell.setCellValue(srcCell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			targetCell.setCellErrorValue(srcCell.getErrorCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			targetCell.setCellFormula(srcCell.getCellFormula());
			break;
		default:
			break;
		}
		
	}
	@Override
	public Workbook getWorkbook() {
		return this.target;
	}
	
	public static void main(String...strings) throws FileNotFoundException, IOException{
		Workbook wb = FileUtils.importFile(FileUtils.desktopPath, "临江花园家庭信息.xls");
		Sheet sheet = wb.getSheetAt(0);
		PoiCopyTarget pt = new PoiCopyTargetImpl();
		pt.copySheet("复制", sheet);
		FileUtils.exportExcelTo(FileUtils.desktopPath, "target.xls", pt.getWorkbook());
	}
	
}
