package com.sowell.tools.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 
 * <p>Title:DateUtils</p>
 * <p>Description:
 * 	整合部分常用日期处理函数的工具类
 * </p>
 * @author 张荣波
 * @date 2014年11月10日 下午4:43:49
 */
public class DateUtils {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	private static final long timeZoneOffset = TimeZone.getDefault().getOffset(System.currentTimeMillis());
	
	/**
	 * 将Long型时间转化成格式化的时间字符串
	 * @param date
	 * @return
	 */
	public static String getFormatDateString(Long date){
		return date == null ? "" : dateFormat.format(new Date(date)); 
	}
	/**
	 * 将格式化的时间转化成Long型的时间格式
	 *  "yyyy-MM-dd HH:mm:ss";
	 * @param date
	 * @return
	 */
	public static Long getFormatDateLong(String date){
		Date d = null;
		Long ret = null;
		if(date != null){
			try {
				d = datetimeFormat.parse(date);
				ret = d.getTime();
			} catch (Exception e) {
				try {
					d = dateFormat.parse(date);
					ret = d.getTime() + timeZoneOffset;
				} catch (Exception e2) {
					
				}
			}
		}
		return ret;
	}
	/**
	 * 用格式字符串格式化对应时间字符串
	 * @param date
	 * @param formatStr
	 * @return
	 * @throws ParseException
	 */
	public static Long getFormatDateLong(String date, String formatStr) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date d =  format.parse(date);
		return d == null ? null : d.getTime();
	}
	
	public static Long getFormatDateLong(String date, Long standBy){
		Long ret = getFormatDateLong(date);
		return ret == null ? standBy : ret;
	}
	
	/**
	 * 根据一个Long类型的时间获得一个时间字符串
	 * @param time
	 * @return
	 */
	public static String getFormatTimeString(Long time){
		return time == null ? "" : datetimeFormat.format(new Date(time)); 
	}
	
	
	
	public static Date getCurrentTime(){
		return new Date(System.currentTimeMillis());
	}
	
	
	public static String getCurrentTime(SimpleDateFormat sdf){
		return sdf.format(getCurrentTime());
	}
	/**
	 * 根据yyyy-MM-dd HH:mm:ss的格式格式化当前时间
	 * @return
	 */
	public static String getCurrentTimeStr(){
		return getCurrentTime(datetimeFormat);
	}
	/**
	 * 根据yyyy-MM-dd的格式格式化当前时间
	 * @return
	 */
	public static String getCurrentDateStr(){
		return getCurrentTime(dateFormat);
	}
	/**
	 * 获得一个字符串表示当前的完整时间（包括年月日时分秒毫秒yyyyMMddHHmmssSSS）
	 * @return
	 */
	public static String getFullTimeStr(){
		return DateUtils.getCurrentTime(timeFormat);
	}
	
	static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy"),
			monthDayFormat = new SimpleDateFormat("MM-dd")
	;
	public static Integer getAge(Long birthday) {
		if(birthday != null){
			Date date = new Date(),
					birthdayDate = new Date(birthday)
			;
			
			String birthdayYear = yearFormat.format(birthdayDate),
					currentYear = yearFormat.format(date);
			Integer age = Integer.valueOf(currentYear) - Integer.valueOf(birthdayYear);
			
			
			String sameYearThisDate = birthdayYear + "-" + monthDayFormat.format(date);
			Long sameYearThisDateLong = getFormatDateLong(sameYearThisDate);
			if(sameYearThisDateLong >= birthday){
				return age;
			}else{
				return age - 1;
			}
		}
		return null;
	}
	
}
