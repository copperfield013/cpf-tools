package cn.sowell.cpftools.main.controller.pimport;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.sowell.copframe.utils.TreeTable;
import cn.sowell.copframe.utils.excel.ExcelReader;
import cn.sowell.copframe.utils.excel.SheetHeader;
import cn.sowell.copframe.utils.excel.SheetReader;
import cn.sowell.copframe.utils.excel.poi.PoiExcelReader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/pimport")
public class ImportToolAction {
	@RequestMapping("/replace_typeint")
	public String replaceTypeint(){
		return "/pimport/replace_typeint.jsp";
	}
	
	
	String[] typeintParam = new String[]{
			"性别", "户籍性质", "民族", "人口类型",
			"政治面貌", "婚姻状况", "文化程度", "兵役状况",
			"工作状态", "健康状况", "血型", "关系"
	};
	
	@RequestMapping("/do_replace_typeint")
	public String doReplaceTypeint(
			@RequestParam("file") CommonsMultipartFile file, 
			@RequestParam String typeintMap,
			Model model){
		JSONObject tMap = JSON.parseObject(typeintMap);
		try {
			ExcelReader reader = PoiExcelReader.createReader(file.getOriginalFilename(), file.getInputStream());
			SheetReader sheet = reader.getSheet(0);
			SheetHeader header = new SheetHeader(sheet.getRow(1));
			TreeTable<String> table = new TreeTable<String>();
			sheet.iterateRow(()->{return row-> !row.getCell(0).isEmpty();}, (row, e) -> {
				table.put(row.getRowNum(), 0, row.getString(0));
				header.handleCells(row, typeintParam, cell->{
					JSONObject typeint = tMap.getJSONObject("typeint_" + cell.getString());
					if(typeint != null){
						table.put(cell.getRowNum(), cell.getColumnIndex(), typeint.getString("cname"));
					}
				});
			});
			model.addAttribute("table", table.asMatrix());
		} catch (IOException e) {
		}
		return "/pimport/replace_typeint_result.jsp";
	}
}
