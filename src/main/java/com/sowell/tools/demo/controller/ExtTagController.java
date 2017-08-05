package com.sowell.tools.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sowell.tools.util.FormatUtils;
import com.sowell.tools.util.QrCodeUtils;
import com.sowell.tools.util.StringUtils;

@Controller
@RequestMapping("/extTag")
public class ExtTagController {
	
	@RequestMapping("/main")
	public String main(){
		return "view/extTag/ext_tag_main.jsp";
	}
	
	@ResponseBody
	@RequestMapping(value="/generateTags", produces=MediaType.APPLICATION_JSON_VALUE)
	public String generateTags(HttpServletRequest request){
		JSONObject jo = new JSONObject();
		try {
			BufferedReader reader = request.getReader();
			StringBuffer buffer = new StringBuffer();
			String line;
			while((line = reader.readLine()) != null){
				buffer.append(line);
			}
			JSONObject json = JSON.parseObject(buffer.toString());
			
			JSONArray data = json.getJSONArray("data");
			long result = ChronoUnit.DAYS.between((new Date(0l)).toInstant(), Instant.now());
	    	String dateCode = StringUtils.convert(FormatUtils.toInteger(result));
	    	
	    	List<String> codes = new ArrayList<String>(); 
	    	
			data.forEach(ele ->{
				JSONObject type = (JSONObject)ele;
				int count = type.getIntValue("count");
				String typecode = type.getString("code");
				while(count>0){
					codes.add("HS" + typecode + "00" + dateCode + StringUtils.uuid(4, 62));
					count--;
				}
			});
			
			//保存二维码的路径
			String folderPath = null;
			String path = request.getSession().getServletContext().getRealPath("./");
			
			System.out.println(path);
			
			
			for (String code : codes) {
				QrCodeUtils.encodeQRCodeImage(code.toString(), "utf-8", folderPath + code + ".png", 400, 400);
			}
			
		} catch (IOException e) {
		}
		
		return jo.toJSONString();
	}
	
}
