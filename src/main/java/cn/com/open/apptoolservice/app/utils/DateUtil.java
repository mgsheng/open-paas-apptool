package cn.com.open.apptoolservice.app.utils;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理类
 */
public class DateUtil {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat();;

	/**
	 * 将时间转换成数据
	 * 
	 * @param date  需要转换的时间
	 * @param format 转换得格式 例如"yyyy-MM-dd hh:mm:ss"
	 * @return String
	 */
	public static String dateToString(Date date, String format) {
		// 附加时间格式
		dateFormat.applyPattern(format);
		// 将时间转换成字符串
		return dateFormat.format(date);
	}

	/**
	 * 将时间转换成时间
	 * 
	 * @param date  需要转换的时间
	 * @param format 转换得格式 例如"yyyy-MM-dd hh:mm:ss"
	 * @return Date
	 */
	public static Date dateToDate(Date date, String format) {
		// 附加时间格式
		dateFormat.applyPattern(format);
		// 将时间转换成字符串
		try {
			return dateFormat.parse(dateToString(date, format));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将字符串转换成时间
	 * 
	 * @param dateString 需要转换的时间字符串
	 * @param format 转换得格式 例如"yyyy-MM-dd hh:mm:ss"
	 * @return Date
	 */
	public static Date stringToDate(String dateString, String format) {
		// 附加时间格式
		dateFormat.applyPattern(format);
		// 将时间转换成字符串
		try {
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 比较两个时间的差值(以秒为单位)
	 * 
	 * @param date1
	 *            时间1
	 * @param date2
	 *            时间2
	 * @return long
	 */
	public static long dateDiff(Date date1, Date date2) {
		// return date1.getTime() / (24*60*60*1000) - date2.getTime() /
		// (24*60*60*1000);
		return date2.getTime() / 1000 - date1.getTime() / 1000; // 用立即数，减少乘法计算的开销
	}
	
	/**
	 * 作用：获取当前日期 格式 2007－03－04 返回类型：Date 参数：null
	 */
	public static String getCurrentDate(){
		// 获取当前日期
		return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
	}


	/**
	 * 作用：获取当前日期 格式 2007－03－04 12：10：20 返回类型：Date 参数：null
	 */
	public static String datetimeByString() {
		// 获取当前日期
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
	}

	/**
	 * 获取当前日期
	 * @return yyyyMMdd
	 */
	public static String datetimetoday(){
		return new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
	}

	/** 
	 * 取得两个时间段的时间间隔 
	 * return t2 与t1的间隔天数 
	 * throws ParseException 如果输入的日期格式不是0000-00-00 格式抛出异常 
	 */ 
	 public static int getBetweenDays(String t1,String t2) throws ParseException{ 
		 DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		 int betweenDays = 0; 
		 Date d1 = format.parse(t1);
		 Date d2 = format.parse(t2);
		 Calendar c1 = Calendar.getInstance(); 
		 Calendar c2 = Calendar.getInstance(); 
		 c1.setTime(d1); 
		 c2.setTime(d2); 
		 // 保证第二个时间一定大于第一个时间 
		 if(c1.after(c2)){ 
			 c1 = c2; 
			 c2.setTime(d1); 
		 } 
		 int betweenYears = c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR); 
		 betweenDays = c2.get(Calendar.DAY_OF_YEAR)-c1.get(Calendar.DAY_OF_YEAR); 
		 for(int i=0;i<betweenYears;i++){
			 c1.set(Calendar.YEAR,(c1.get(Calendar.YEAR)+1)); 
			 betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR); 
		 } 
		 return betweenDays; 
	 }
	 
	 /**  2.* 获取两个日历的月份之差  3.*   4.* @param calendarBirth  5.* @param calendarNow  6.* @return 
	  * 
	  * @param calendarBirth
	  * @return
	  */ 
	 public static int getMonthsOfAge(Calendar calendarBirth, Calendar calendarNow){   
		 return (calendarNow.get(Calendar.YEAR) - calendarBirth.get(Calendar.YEAR))* 12+ 
		 calendarNow.get(Calendar.MONTH)- calendarBirth.get(Calendar.MONTH);  
	 }
	 
	 /**
	  * 判断两个时间的大小
	  * @param DATE1
	  * @param DATE2
	  * @return
	  */
	 public static int compare_date(String DATE1, String DATE2) {
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 try {
			 Date dt1 = (Date)df.parse(DATE1);
			 Date dt2 = (Date)df.parse(DATE2);
			 if (dt1.getTime() > dt2.getTime()) {
				 return 1;
			 } else if (dt1.getTime() < dt2.getTime()) {
				 return -1;
			 } else {
				 return 0;
			 }
		 } catch (Exception exception) {
			 exception.printStackTrace();
		 }
		 return 0;
    }
	 
	public static String changeCharset(String str, String newCharset) throws UnsupportedEncodingException {
		if (str != null) {
			byte[] bs = str.getBytes();
			return new String(bs, newCharset);
		}
		return null;
	}

}
