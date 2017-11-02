package com.sowell.tools.model.demo.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.util.StringUtils;

import com.sowell.tools.model.demo.service.ZipService;
import com.sowell.tools.util.ProgressRecorder;

public class ZipServiceImpl implements ZipService{

	@Override
	public void zip(File[] files, OutputStream outputStream) {
		this.zip(files, outputStream, null, null, null);
	}
	
	@Override
	public void zip(File[] files, OutputStream outputStream, String prefix, String extName, ProgressRecorder record) {
		ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
		zipOutputStream.setEncoding("gbk");
		record = record == null? new ProgressRecorder(): record;
		byte[] buf=new byte[10240];  
		try {
			int inc = 1;
			Map<String, Integer> nameMap = new HashMap<String, Integer>();
			for (File file : files) {
				record.setProgressMsg("正在压缩第" + inc + "/" + files.length + "个文件");
				System.out.println("正在压缩第" + inc + "/" + files.length + "个文件");
				if(file.isFile()){
					String name = null;
					if(prefix != null){
						String _extName = extName == null ? StringUtils.getFilenameExtension(file.getName()) :extName;
						name = prefix + "_" + inc++ + "." + _extName;
					}else{
						name = StringUtils.getFilename(file.getName());
						Integer existMax = nameMap.get(name);
						if(existMax != null){
							nameMap.put(name, ++existMax);
							StringBuffer stringBuffer = new StringBuffer(name);
							int dot = stringBuffer.lastIndexOf(".");
							if(dot > 0){
								stringBuffer.insert(dot, "(" + existMax + ")");
							}else{
								stringBuffer.insert(0, "(" + existMax + ")");
							}
						}
					}
		            FileInputStream input = new FileInputStream(file);
		            zipOutputStream.putNextEntry(new ZipEntry(name));
		            int len;  
	                while((len=input.read(buf))>0){  
	                	zipOutputStream.write(buf,0,len);  
	                }  
		            zipOutputStream.closeEntry();
		            input.close();
				}
				record.incStep();
			}
			zipOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) throws IOException {
		File f = new File("f://temp.zip");
		if(!f.exists()){
			f.createNewFile();
		}
		File f1 = new File("F://sss-0.png"),
			f2 = new File("F://sss-1.png")
		;
		FileOutputStream fo = new FileOutputStream(f);
		ZipServiceImpl service = new ZipServiceImpl();
		service.zip(new File[]{f1, f2}, fo);
		fo.close();
	}
	
	
	

}
