package com.sowell.tools.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.CharUtils;
import org.apache.poi.ss.usermodel.Cell;

import com.sowell.tools.exception.ArgumentException;

/**
 * 
 * <p>Title:StringUtils</p>
 * <p>Description:
 * 	整合部分常用字符串处理函数的工具类
 * </p>
 * @author 张荣波
 * @date 2014年11月10日 下午4:42:00
 */
public class StringUtils extends org.springframework.util.StringUtils {
	
	/**
	 * 将字符串首字符转换成大写，当首字母不是字母的时候返回原字符串
	 * 这个函数可以用于组合类的get,set函数名
	 * @param str 要转换的字符串
	 * @return 转换后的字符串，其首字母大写
	 */
	public static String upCaseFirstChar(String str){
		synchronized (str) {
			char[] chs = str.toCharArray();
			if (CharUtils.isAsciiAlpha(chs[0])) {
				chs[0] = String.valueOf(chs[0]).toUpperCase().toCharArray()[0];
			}
			return String.valueOf(chs);
		}
	}
	/**
	 * 将字符串首字符转换成小写，当首字母不是字母的时候返回原字符串
	 * 这个函数可以用于还原类的get,set函数名
	 * @param str 要转换的字符串
	 * @return 转换后的字符串，其首字母大写
	 */
	public static String lowCaseFirstChar(String str){
		char[] chs = str.toCharArray();
		if (CharUtils.isAsciiAlpha(chs[0])) {
			chs[0] = String.valueOf(chs[0]).toLowerCase().toCharArray()[0];
		}
		return String.valueOf(chs);
	}
	/**
	 * 将str的所有字母转为大写
	 * @param str
	 * @return
	 */
	public static String toUpperCase(String str){
		if(str != null){
			return str.toUpperCase();
		}
		return null;
	}
	/**
	 * 将str的字母转为小写
	 * @param str
	 * @return
	 */
	public static String toLowCase(String str){
		if(str != null){
			return str.toLowerCase();
		}
		return null;
	}
	
	 /**
	  * 去除字符串首尾的特定字符序列
	  * @param str 要修整的字符串
	  * @param regex 检查的字符序列
	  * @return 新字符串
	  */
	 public static String trim(String str, String...regex){
		if(str == null){
			return null;
		}
		String ret = new String(str);
		if(regex.length == 0){
			return ret.trim();
		}
		Boolean hasChanged = true;
		while(hasChanged){
			hasChanged = false;
			for (String r : regex) {
				if(ret.startsWith(r)){
					ret = ret.substring(r.length());
					hasChanged = true;
				}
				if(ret.endsWith(r)){
					ret = ret.substring(0, ret.lastIndexOf(r));
					hasChanged = true;
				}
			}
		}
		return ret;
	 }
	 /**
	  * 将字符串根据分隔符分割为数组
	  * @param toSplit
	  * @param regex
	  * @param limit 可选参数，限制元素个数用
	  * @return 传入的字符串为空时返回null
	  */
	 public static String[] splitToArray(String toSplit, String regex, Integer...limit){
		 if(toSplit != null){
			 if(limit.length == 0){
				 return toSplit.split(regex);
			 }else{
				 return toSplit.split(regex, limit[0]);
			 }
		 }
		 return null;
	 }
	 /**
	  * 判断字符串能否转成integer
	  * @param string
	  * @return
	  */
	 public static Boolean isInteger(String string){
		 try {
			Integer.valueOf(string);
			return true;
		} catch (Exception e) {
			return false;
		}
	 }
	 private static String alphabetRegex = "^[A-Za-z]+$";
	 /**
	  * 判断一个字符串是不是全是字母
	  * @param string
	  * @return
	  */
	 public static Boolean isAlphabet(String string){
		 if(string != null){
			 return string.matches(alphabetRegex);
		 }
		 return false;
	 }
	 
	 /**
	  * 二次解码，编码为UTF-8
	  * @param string
	  * @return
	  */
	 public static String dblDecode(String string){
		 return dblDecode(string, "UTF-8");
	 }
	 /**
	  * 二次解码，编码为code
	  * @param string
	  * @param code
	  * @return
	  */
	 public static String dblDecode(String string, String code){
		 try {
			return URLDecoder.decode(URLDecoder.decode(string, code), code);
		} catch (Exception e) {
			return string;
		}
	 }
	 /**
	  * 转码函数
	  * @param string
	  * @param oriCode
	  * @param targetCode
	  * @return
	  */
	 public static String transcoding(String string, String oriCode, String targetCode){
		 try {
			String oriStr = new String(string.getBytes(), oriCode);
			String targetStr = new String(oriStr.getBytes(), targetCode);
			return targetStr;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return string;
		}
		 
	 }
	 /**
	  * 判断某个字符串是否是数字，如果是整数或者小数，那么就返回true
	  * @param str
	  * @return
	  */
	 public static Boolean isNumeric(String str){
		 String regexInteger = "^-?\\d+$";
		 String regexDouble = "^(-?\\d+)(\\.\\d+)?$";
		 if(!str.matches(regexInteger)){
			 return str.matches(regexDouble);
		 }
		 return true;
	 }
	 
	 /**
	  * 将某个数组转换成字符串，以特定分隔符分割
	  * @param array
	  * @param spliter
	  * @return
	  */
	 public static String toString(Object[] array, String spliter){
		 String ret = "";
		 if(array != null && StringUtils.hasText(spliter)){
			 for (Object object : array) {
				ret += FormatUtils.toString(object) + spliter;
			}
			 ret = StringUtils.trim(ret, spliter);
		 }
		 return ret;
	 }

	 /**
	  * 将poi的Excel单元格的字符串值返回
	  * @param cell
	  * @return
	  */
	 public static String toString(Cell cell){
		 int type = cell.getCellType();
		 switch (type) {
		case Cell.CELL_TYPE_BLANK:
			return null;
		case Cell.CELL_TYPE_BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case Cell.CELL_TYPE_ERROR:
			return String.valueOf(cell.getErrorCellValue());
		case Cell.CELL_TYPE_FORMULA:
			try {
			 	return String.valueOf(cell.getNumericCellValue());
			} catch (IllegalStateException e) {
				 return String.valueOf(cell.getRichStringCellValue());
			}
		case Cell.CELL_TYPE_NUMERIC:
			return String.valueOf(cell.getNumericCellValue());
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		default:
			return String.valueOf(cell.getRichStringCellValue());
		}
	 }
	 
	 private static Pattern lastNumberPattern = Pattern.compile("^(.*\\D)?(\\d+)(.*)$", Pattern.DOTALL);
	 private static Pattern endNumberPattern= Pattern.compile("^(.*\\D)?(\\d+)$", Pattern.DOTALL);
	 
	 /**
	  * 如果字符串是以数字结尾的，那么就取出这几个数字
	  * 否则就返回null
	  * @param string
	  * @return
	  */
	 public static String getEndNumber(String string){
		 if(string != null){
			 Matcher matcher = endNumberPattern.matcher(string);
			 if(matcher.matches()){
				 return matcher.group(2);
			 }
		 }
		 return null;
	 }
	 
	 /*
	  * 获取字符串最后的几位数字
	  */
	 public static String getLastNumber(String string){
		 String ret = "";
		 if(string != null){
			 Matcher matcher = lastNumberPattern.matcher(string);
			 if(matcher.matches()){
				 return matcher.group(2) ;
			 }
		 }
		 return ret;
	 }
	 
	 /**
	  * 判断如果是以其中任何一个字符串为后缀，那么返回正确
	  * 不传入后缀则为false
	  * @param source
	  * @param suffix
	  * @return
	  */
	 public static Boolean endWith(String source, String...suffix){
		 for (String string : suffix) {
			if(source.endsWith(string)){
				return true;
			}
		}
		 return false;
	 }
	 
	 private static char[] CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
	 /**
	  * 获取随机的uuid，长度为length，位数为radix
	  * @param length
	  * @param radix
	  * @return
	  */
	 public static synchronized String uuid(Integer length, Integer radix){
		 String ret = "";
		 if(length != null){
			 char[] uuid = new char[length];
			 for(int i = 0 ; i < length; i++){
				 uuid[i] = CHARS[(int)(Math.random() * radix)];
			 }
			 ret = String.valueOf(uuid);
		 }
		 return ret;
	 }
	 /**
	  * 获取长度为32，位数为16的随机uuid
	  * @return
	  */
	 public static String uuid(){
		 return uuid(32, 16);
	 }

	 public static String convert(int digit){
		int radix = CHARS.length;
		int d = digit / radix;
		StringBuffer buffer = new StringBuffer();
		do {
			int t = digit % radix;
			d = digit / radix;
			char c = CHARS[t];
			buffer.insert(0, c);
			digit = d;
		} while (d > 0);
		return buffer.toString();
	 }
	 
	 /**
	  * 将一个字符串集合通过逗号分隔，放到一个字符串内
	  * @param collection
	  * @return
	  */
	 public static String join(String[] list){
		 String ret = "";
		 for (String ele : list) {
			ret += ele + ",";
		 }
		 return StringUtils.trim(ret, ",");
	 }
	 
	 /**
	  * 去掉字符串的所有空格，包括全角和半角的空格
	  * @param str
	  * @return
	  */
	 public static String removeBlank(String str){
		 if(str != null){
			 return str.replace(" ", "").replace("　", "");
		 }
		 return str;
	 }
	 
	 /**
	  * 替换str字符串的第position个target字符串为replacement
	  * @param str
	  * @param target
	  * @param replacement
	  * @param position
	  * @return
	 * @throws ArgumentException 
	  */
	 public static String replace( String str, String target, String replacement, int position ) throws ArgumentException{
		 if(str != null && target != null && replacement != null && position > 0){
			 int i = -1;
			 do {
				 i = str.indexOf( target, i + 1 );
				 position --;
			 } while ( position > 0 );
			 if( i >= 0 ){
				 return str.substring( 0, i ) + replacement + str.substring( i + target.length() );
			 }else{
				 return str;
			 }
		 }else{
			throw new ArgumentException("参数错误", new String[] { "str", "target",
					"replacement", "position" }, new Object[] { str, target,
					replacement, position });
		 }
	 }
	 
	public static String replace(String str, String replacement, int position) throws ArgumentException {
		return replace(str, "?", replacement, position);
	}
	
	/**
/**
	 * 根据正则表达式截取源字符串中对应的片段，如果不存在这个片段，那么就返回null
	 * @param source 要截取的源字符串
	 * @param pattern 用于截取的正则表达式
	 * @param index 匹配的子串索引
	 * @param group 返回匹配的组号
	 * @return 如果匹配成功，并且组号对应的片段存在，那么就返回这个片段，如果不匹配，或者组号对应的片段不存在，那么就返回null
	 */
	public static String getString(String source, Pattern pattern, int index,  int group){
		Matcher matcher = pattern.matcher(source);
		String ret = null;
		while(matcher.find() && index -- >= 0 ){
			try {
				ret =  matcher.group(group);
			} catch (IndexOutOfBoundsException  e) {
			}
		}
		return ret;
	}
	/**
	 * 根据正则表达式截取源字符串中对应的片段，如果不存在这个片段，那么就返回null
	 * @param source 要截取的源字符串
	 * @param pattern 用于截取的正则表达式
	 * @return 如果全匹配成功，那么就返回这个片段，如果不匹配，那么就返回null
	 */
	public static String getString(String source, Pattern pattern){
		return getString(source, pattern, 0, 0);
	}
	/**
	 * 根据正则表达式截取源字符串中对应的片段，如果不存在这个片段，那么就返回null
	 * @param source 要截取的源字符串
	 * @param pattern 用于截取的正则表达式
	 * @param group 返回匹配的组号
	 * @return 如果全匹配成功，那么就返回这个片段，如果不匹配，那么就返回null
	 */
	public static String getString(String source, Pattern pattern, int group){
		return getString(source, pattern, 0, group);
	}
	
	static Pattern SCIENTIFIC = Pattern.compile("^((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))$");
	public static boolean isScientific(String str){
		Matcher matcher = SCIENTIFIC.matcher(str);
		if(matcher.matches()){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 将Java字符串转换成JSON格式的字符串
	 * @param str
	 * @return
	 */
	public static String escapeToStringJSONElement(String str){
		String ret = "";
		if(str != null){
			ret = str;
			ret = ret.replaceAll("\\\\", "\\\\\\\\");
			ret = ret.replaceAll("\"", "\\\\\"");
			ret = ret.replaceAll("\'", "\\\\\'");
		}
		return ret;
	}
	
	/**
	 * 比较两个String
	 * @param a
	 * @param b
	 * @return
	 */
	public static int compare(Comparable a, Comparable b){
		return (a == null && b == null ? 0 : a == null && b != null ? -1
				: a != null && b == null ? 1 : a.compareTo(b));
	}
	
	static Pattern mobilePattern = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");
	static Pattern phonePattern1 = Pattern.compile("^[0][1-9][2,3]-[0-9]{5,10}");
	static Pattern phonePattern2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");
	public static boolean isContactNumber(String contactNumber){
		if(contactNumber != null){
			if(mobilePattern.matcher(contactNumber).matches()
					|| phonePattern1.matcher(contactNumber).matches()
					|| phonePattern2.matcher(contactNumber).matches()){
				return true;
			}
		}
		return false;
	}
	public static String toString(String[] ranges) {
		return null;
	}
}
