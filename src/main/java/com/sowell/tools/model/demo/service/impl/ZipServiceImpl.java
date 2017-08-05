package com.sowell.tools.model.demo.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.util.StringUtils;

import com.sowell.tools.model.demo.service.ZipService;

public class ZipServiceImpl implements ZipService{

	@Override
	public void zip(File[] files, OutputStream outputStream) {
		this.zip(files, outputStream, null, null);
	}
	
	@Override
	public void zip(File[] files, OutputStream outputStream, String prefix, String extName) {
		ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
		try {
			int inc = 1;
			Map<String, Integer> nameMap = new HashMap<String, Integer>();
			for (File file : files) {
				System.out.println("压缩第" + inc + "个文件");
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
							StringBuffer buf = new StringBuffer(name);
							int dot = buf.lastIndexOf(".");
							if(dot > 0){
								buf.insert(dot, "(" + existMax + ")");
							}else{
								buf.insert(0, "(" + existMax + ")");
							}
						}
					}
		            FileInputStream input = new FileInputStream(file);
		            zipOutputStream.putNextEntry(new ZipEntry(name));
		            int temp = 0;
		            while((temp = input.read()) != -1){
		            	zipOutputStream.write(temp);
		            }
		            input.close();
				}
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
