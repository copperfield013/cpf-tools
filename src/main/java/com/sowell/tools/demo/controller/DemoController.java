package com.sowell.tools.demo.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.sowell.copframe.dto.ajax.JsonResponse;

import com.google.gson.JsonObject;
import com.sowell.tools.imp.utils.Table;
import com.sowell.tools.model.demo.service.Excel;
import com.sowell.tools.model.demo.service.PDFImageConvertor;
import com.sowell.tools.model.demo.service.PeopleId;
import com.sowell.tools.model.demo.service.ZipService;
import com.sowell.tools.model.demo.service.impl.ZipServiceImpl;
import com.sowell.tools.util.FileUtils;
import com.sowell.tools.util.ProgressRecorder;

@Controller
@RequestMapping("/demo")
public class DemoController {
	ZipService zipService = new ZipServiceImpl();
	
	
	@RequestMapping("index")
	public String index(){
		return "redirect:/main/index";
	}
	
	
	@RequestMapping("excel_rename")
	public String excelRename(){
		return "/demo/excel_rename.jsp";
	}
	
	@RequestMapping("goCheckHasHouseholder")
	public String gocheckHasHouseholder() {
		return "/demo/goCheckHasHouseholder.jsp";
	}
	
	@RequestMapping("export_building")
	public String goExportBuilding(){
		return "/demo/export_building.jsp";
	}
	
	@RequestMapping("export_repeat")
	public String goExoprtPeopleIdRepeat(){
		return "/demo/export_repeat.jsp";
	}
	
	@RequestMapping("blank")
	public String goBlank(){
		return "/demo/blank.jsp";
	}
	
	@RequestMapping("pdf_to_img")
	public String goPdfToImg(){
		return "/demo/pdf_to_img.jsp";
	};
	
	@RequestMapping("downloadBuildingModel")
	public ResponseEntity<byte[]> downloadBuildingModel(){
		return null;
	}
	
	@RequestMapping("print_tag")
	public String goPrintTag(){
		return "/demo/print_tag.jsp";
	};
	
	@RequestMapping("print_ext_tag")
	public String goPrintExtTag(){
		return "/demo/print_ext_tag.jsp";
	};
	
	
	@ResponseBody
	@RequestMapping("generatorBat")
	public String generatorBat(@RequestParam("source") String source){
		String[] pathes = source.split("\r\n");
		String bat = "";
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		long time = System.currentTimeMillis();
		Pattern pattern = Pattern.compile("^\"?(.*[\\\\/])?([^\\.\\\\/]+)\\.([^\\.\\\\/\"]*)?\"?$");
		for (String path : pathes) {
			Matcher matcher = pattern.matcher(path);
			String name = timeFormat.format(new Date(time ++));
			if(matcher.matches()) {
				String folder = matcher.group(1),
						fileName = matcher.group(2),
						extralName = matcher.group(3);
				folder = folder == null ? "" : folder;
				extralName = extralName == null ? "" : ("." +extralName);
				path = "\"" + folder + fileName + extralName + "\"";
				path += " \"" + folder + name + extralName + "\"";
			}else{
				path += " " + name;
			}
			
			bat += "copy " + path + "\n";
		}
		return bat + "pause\n";
	}
	
	@ResponseBody
	@RequestMapping("checkHasHouseholder")
	public String checkHasHouseholder(@RequestParam("source") String source){
		String[] infos = source.split("\r\n");
		String result = "";
		Set<String> flag = new HashSet<String>();
		String[] addresses = new String[infos.length];
		for (int i = 0; i < infos.length; i++) {
			String info = infos[i];
			String[] split = info.split("\t");
			String relation = split[0],
					address = split[1]
							;
			addresses[i] = address;
			if("户主".equals(relation)){
				flag.add(address);
			}
		}
		for (int i = 0; i < addresses.length; i++) {
			String f = flag.contains(addresses[i]) ? "TRUE" : "FALSE"; 
			result += addresses[i] + "\t" + f + "\n";
		}
		return result;
	}
	
	@RequestMapping("generateBuilding")
	public String generateBuilding(Model model, @RequestParam("file") CommonsMultipartFile file, @RequestParam("sheetName") String sheetName){
		String fileName = file.getOriginalFilename();
		Class<? extends Workbook> wbClass = fileName.endsWith(".xls") ? HSSFWorkbook.class : XSSFWorkbook.class;
		try {
			Workbook wb = FileUtils.importFile(file.getInputStream(), wbClass);
			if(wb != null){
				Excel excel = new Excel();
				Table table = excel.deal(wb,sheetName);
				model.addAttribute("table", table);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*String downloadFileName = "楼栋数据模板",
				fileLink = "demo/downloadBuildingModel.html";
		model.addAttribute("fileName", downloadFileName);
		model.addAttribute("fileLink", fileLink);*/
		return "/demo/view_table.jsp";
	}
	
	@RequestMapping("generateRepeat")
	public String generatePeopleRepeat(Model model, @RequestParam("file") CommonsMultipartFile file, @RequestParam("sheetName") String sheetName){
		String fileName = file.getOriginalFilename();
		Class<? extends Workbook> wbClass = fileName.endsWith(".xls") ? HSSFWorkbook.class : XSSFWorkbook.class;
		try {
			Workbook wb = FileUtils.importFile(file.getInputStream(), wbClass);
			if(wb != null){
				PeopleId excel = new PeopleId();
				Table table = excel.deal(wb,sheetName);
				model.addAttribute("table", table);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/demo/view_table.jsp";
	}
	
	@ResponseBody
	@RequestMapping("converter_pdf_to_img")
	public ResponseEntity<byte[]> converterPdfToImage(ModelAndView mv,
			@RequestParam("file") CommonsMultipartFile file,
			@RequestParam("dpi") int dpi, HttpServletRequest request) throws InterruptedException {
		try {
			ProgressRecorder progressRecorder = new ProgressRecorder();
			progressRecorder.setProgressMsg("开始转换");
			request.getSession().setAttribute(PDF_CONVERTE_PROGRESS, progressRecorder);
			PDFImageConvertor convertor = new PDFImageConvertor(file.getInputStream(), dpi, null, progressRecorder);
			int[] convertIndex = convertor.getConverteIndex();
			FileOutputStream[] fos = new FileOutputStream[convertIndex.length];
			File[] fs = new File[convertIndex.length];
			for (int i = 0; i < fos.length; i++) {
				File f = File.createTempFile("pdf_to_img", "");
				fs[i] = f;
				fos[i] = new FileOutputStream(f);
			}
			progressRecorder.setStepLength(80/convertIndex.length);
			convertor.converte(fos);
			convertor.getDocument().close();
			for (FileOutputStream item : fos) {
				item.close();
			}
			progressRecorder.setProgressMsg("转换完成，正在准备下载文件");
			
			String fileName = getFileNameNoEx(StringUtils.getFilename(file.getOriginalFilename()));
			String downloadFileName = fileName + ".zip";
			
			File downloadFile = null;
			if(fs.length == 1){
				downloadFile = fs[0];
				downloadFileName = fileName + ".png";
			}else{
				progressRecorder.setProgressMsg("正在压缩图片为zip文件");
				//压缩图片文件为zip文件
				File zip = File.createTempFile(downloadFileName, "zip");
				OutputStream outputStream = new FileOutputStream(zip);
				zipService.zip(fs, outputStream, fileName, "png");
				outputStream.close();
				downloadFile  = zip;
				progressRecorder.setProgressMsg("压缩完成，准备下载");
			}
			progressRecorder.fullProgress();
			//输出下载文件
			HttpHeaders headers = new HttpHeaders();    
			headers.setContentDispositionFormData("attachment", new String(
					downloadFileName.getBytes("UTF-8"), "iso-8859-1"));// 为了解决中文名称乱码问题
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
	        byte[] bytes = org.apache.commons.io.FileUtils.readFileToByteArray(downloadFile);
			return new ResponseEntity<byte[]>(bytes ,    
	                                          headers, HttpStatus.CREATED);    
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("download")
	public ResponseEntity<byte[]> donwload(){
		try {
			File file = new File("d:/text.txt");
			byte[] bytes = org.apache.commons.io.FileUtils.readFileToByteArray(file);
			
			//输出下载文件
			HttpHeaders headers = new HttpHeaders();    
			headers.setContentDispositionFormData("attachment", new String(
					file.getName().getBytes("UTF-8"), "iso-8859-1"));// 为了解决中文名称乱码问题
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
	        return new ResponseEntity<byte[]>(bytes ,    
                    headers, HttpStatus.CREATED);    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private static String PDF_CONVERTE_PROGRESS = "pdf_converte_progress";
	
	
	@ResponseBody
	@RequestMapping("ajax_pdf_converte_progress")
	public String ajaxPdfConverteProgress(HttpServletRequest request){
		HttpSession session = request.getSession();
		ProgressRecorder progressRecorder = (ProgressRecorder) session.getAttribute(PDF_CONVERTE_PROGRESS);
		JsonObject jo = new JsonObject();
		if(progressRecorder == null){
			jo.addProperty("status", "unknow");
			return jo.toString();
		}else{
			return progressRecorder.toJSON();
		}
	}
	
	
	
	@RequestMapping("upload")
	public void upload(@RequestParam("file") CommonsMultipartFile file){
		System.out.println(file.getOriginalFilename());
	}
	
	
	public static String getFileNameNoEx(String filename) {   
        if ((filename != null) && (filename.length() > 0)) {   
            int dot = filename.lastIndexOf('.');   
            if ((dot >-1) && (dot < (filename.length()))) {   
                return filename.substring(0, dot);   
            }   
        }   
        return filename;   
    }
	
	
	@ResponseBody
	@RequestMapping("jRes")
	public JsonResponse jRes(){
		JsonResponse jRes = new JsonResponse();
		jRes.setStatus("suc");
		jRes.put("aaa", "asdsadsa");
		return jRes;
	}
	
	
	
	public static void main(String[] args) {
		/*Pattern pattern = Pattern.compile("^\"?(.*[\\\\/])?([^\\.\\\\/]+)\\.([^\\.\\\\/]*)?\"?$");
		String path = "C:\\Users\\Copperfield/Desktop\\openFrame-0.2/pom.xml";
		Matcher matcher = pattern.matcher(path);
		System.out.println(matcher.matches());
		System.out.println(matcher.group(1));
		System.out.println(matcher.group(2));
		System.out.println(matcher.group(3));*/
		
		
		long result = ChronoUnit.DAYS.between((new Date()).toInstant(), (new Date(0l)).toInstant());
		System.out.println(result);
	}
	
}
