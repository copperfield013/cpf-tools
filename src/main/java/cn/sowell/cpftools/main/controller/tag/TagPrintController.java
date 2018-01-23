package cn.sowell.cpftools.main.controller.tag;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.sowell.copframe.dto.ajax.JsonRequest;
import cn.sowell.copframe.dto.ajax.JsonResponse;
import cn.sowell.copframe.utils.excel.ExcelReader;
import cn.sowell.copframe.utils.excel.SheetHeader;
import cn.sowell.copframe.utils.excel.SheetReader;
import cn.sowell.copframe.utils.excel.poi.PoiExcelReader;
import cn.sowell.copframe.utils.qrcode.QrCodeUtils;
import cn.sowell.cpftools.model.tag.service.TagPrintService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/tag")
public class TagPrintController {
	
	@Resource
	TagPrintService tPrintService;
	
	
	Logger logger = Logger.getLogger(TagPrintController.class);
	
	
	@RequestMapping("/tag_list")
	public String goTagList(){
		return "/tag/tag-list.jsp";
	}
	
	@RequestMapping("/ext")
	public String goExtTagPrint(){
		return "/tag/tag_ext_print.jsp";
	}
	
	/**
	 * 材料清单
	 * @return
	 */
	@RequestMapping("/referList")
	public String goReferTagPrint(){
		return "/tag/refer_tag_print.jsp";
	}
	
	/**
	 * 寄件人-收件人
	 * @return
	 */
	@RequestMapping("/event_tag")
	public String eventTag(){
		return "/tag/event_tag.jsp";
	}
	
	/**
	 * 所有条线
	 * @return
	 */
	@RequestMapping("/static_ext_tag")
	public String staticExtTag(){
		return "/tag/static_tag_ext_print.jsp";
	}
	
	@RequestMapping("/address")
	public String addressTag(){
		return "/tag/address_tag.jsp";
	}
	
	
	@RequestMapping("/dynamic_table")
	public String dynamicTable(){
		return "/tag/dynamic_table.jsp";
	}
	
	/**
	 * 办理事项to
	 * @return
	 */
	@RequestMapping("/cert")
	public String certTo(){
		return "/tag/cert.jsp";
	}
	
	
	@RequestMapping("/fromTo")
	public String fromTo(){
		return "/tag/fromTo.jsp";
	}
	
	@RequestMapping("/ext_contact")
	public String extContact(){
		return "/tag/ext_contact.jsp";
	}
	
	/**
	 * 条线分支
	 * @return
	 */
	@RequestMapping("/ext_devide")
	public String extDevice(){
		return "/tag/ext_devide.jsp";
	}
	
	
	/**
	 * 档案袋
	 * @return
	 */
	@RequestMapping("/portfolio")
	public String portfolio(){
		return "/tag/portfolio.jsp";
	}
	
	@RequestMapping("/comm_prop")
	public String commProp(){
		return "/tag/comm_prop.jsp";
	}
	
	@RequestMapping("/table_tag")
	public String tableTag(){
		return "/tag/table_tag.jsp";
	}
	
	
	
	@ResponseBody
	@RequestMapping("/generateTags")
	public JsonResponse generateTags(@RequestBody JsonRequest jReq, HttpServletRequest req){
		JsonResponse jRes = new JsonResponse();
		JSONArray data = jReq.getJsonObject().getJSONArray("data");
		
		String path = req.getSession().getServletContext().getRealPath("/");
		logger.info(path);
		String qrcodePath = path + "resources/qrcodes/";
		File folder = new File(qrcodePath);
		if(!folder.exists()){
			folder.mkdirs();
		}
		jRes.put("folder", "extQrcodes/");
		JSONArray items = new JSONArray();
		jRes.put("items", items);
		for (Object obj : data) {
			JSONObject item = (JSONObject) obj;
			String extTypeCode = item.getString("code");
			Integer count = item.getInteger("count");
			for (int i = 0; i < count; i++) {
				String code = tPrintService.generateExtTagCode(extTypeCode);
				QrCodeUtils.encodeQRCodeImage(code, "utf-8", qrcodePath + code + ".png", 400, 400);
				JSONObject codeItem = new JSONObject();
				codeItem.put("code", code);
				codeItem.put("fileName", code + ".png");
				codeItem.put("extTypeCode", extTypeCode);
				items.add(codeItem);
				logger.info(code);
			}
		}
		
		return jRes;
	}
	
	@RequestMapping("/device")
	public String goBatchDeviceTag(){
		return "/tag/tag_device_print.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/download_device_tmpl")
	public ResponseEntity<byte[]> downloadDeviceTmpl(HttpServletRequest req){
		ClassPathResource resource = new ClassPathResource("tmpl/杭州设维固定资产标签.xls");
		try {
			HttpHeaders headers = new HttpHeaders();    
			headers.setContentDispositionFormData("attachment", new String(
					"杭州设维固定资产标签.xls".getBytes("UTF-8"), "iso-8859-1"));// 为了解决中文名称乱码问题
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(resource.getFile()), headers, HttpStatus.CREATED);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@RequestMapping("/upload_device_file")
	public String upload(@RequestParam("file") CommonsMultipartFile file, RedirectAttributes rAttr){
		ExcelReader reader;
		try {
			reader = PoiExcelReader.createReader(file.getOriginalFilename(), file.getInputStream());
			SheetReader sheet = reader.getSheet(0);
			SheetHeader header = new SheetHeader(sheet.getRow(1));
			Map<Integer, Map<String, String>> map = new TreeMap<Integer, Map<String,String>>();
			sheet.iterateRow(()->{return row-> !row.getCell(0).isEmpty();}, (row, e) -> {
				LinkedHashMap<String, String> innerMap = new LinkedHashMap<String, String>();
				map.put(row.getRowNum(), innerMap);
				innerMap.put("序号", row.getString(header.getColumnIndexByCname("序号")));
				innerMap.put("资产名称", row.getStringWithBlank(header.getColumnIndexByCname("资产名称")));
				innerMap.put("型号规格", row.getStringWithBlank(header.getColumnIndexByCname("型号规格")));
				innerMap.put("资产编号", row.getStringWithBlank(header.getColumnIndexByCname("资产编号")));
				innerMap.put("启用日期", row.getString(header.getColumnIndexByCname("启用日期")));
				innerMap.put("使用人员", row.getStringWithBlank(header.getColumnIndexByCname("使用人员")));
			});
			rAttr.addFlashAttribute("deviceMap", map);
			rAttr.addFlashAttribute("fileName", file.getOriginalFilename());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return "redirect:device";
	}
	
	
	
}
