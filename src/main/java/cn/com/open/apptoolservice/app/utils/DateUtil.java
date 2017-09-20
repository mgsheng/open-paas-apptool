package cn.com.open.apptoolservice.app.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间处理类
 */
public class DateUtil {

	private DateUtil() {}

	/**
	 * 将时间转换成数据
	 * 
	 * @param date  需要转换的时间
	 * @param format 转换得格式 例如"yyyy-MM-dd hh:mm:ss"
	 * @return String
	 */
	public static String dateToString(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		// 附加时间格式
		dateFormat.applyPattern(format);
		// 将时间转换成字符串
		return dateFormat.format(date);
	}

}
