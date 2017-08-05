package com.sowell.tools.util.common.code;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * 
 * <p>Title: Encoder</p>
 * <p>Description: </p><p>
 * 
 * </p>
 * @author 张荣波
 * @date 2015年11月16日 下午6:10:41
 */
public class Encoder {
	private HashMap<String, String> map ;
	private boolean containsUnicode;
	public Encoder() {
		super();
		this.containsUnicode = Charset.defaultCharset().contains(Charset.forName("unicode"));
		map = new HashMap<String, String>();
		map.put("\\u25AA", "\\ub7");
	}
	
	public Encoder(String[] keyValues){
		this();
		for(int i = 0; i < keyValues.length; i += 2){
			map.put(keyValues[i], keyValues[i + 1]);
		}
	}
	
	
	
	public String toUnicodeString(String src){
		String target = "";
		for(int i = 0 ; i < src.length(); i++){
			char c = src.charAt(i);
			target += "\\u" + Integer.toHexString(c).toUpperCase();
		}
		return target;
	}
	
	public String caseUnicode2String(String unicodeString){
	    StringBuffer string = new StringBuffer();
	    String[] hex = unicodeString.split("\\\\u");
	    for (int i = 1; i < hex.length; i++) {
	        // 转换出每一个代码点
	        int data = Integer.parseInt(hex[i], 16);
	        // 追加成string
	        string.append((char) data);
	    }
	    return string.toString();
	}
	
	/**
	 * 将字符串根据map映射GBK中不存在的字符,转换成GBK可以支持的字符串
	 * @param src
	 * @return
	 */
	public String map2GBK(String src){
		if(src != null){
			String unicode =  toUnicodeString(src);
			String replacedUnicode = mapUnicode(unicode);
			return caseUnicode2String(replacedUnicode);
		}
		return src;
		
	}

	
	private String mapUnicode(String unicode) {
		if(unicode != null){
			for (Entry<String, String> entry : this.map.entrySet()) {
				unicode = unicode.replace(entry.getKey(), entry.getValue());
			}
		}
		return unicode;
	}

	public boolean containsUnicode() {
		return containsUnicode;
	}
	
	
	
	
}
