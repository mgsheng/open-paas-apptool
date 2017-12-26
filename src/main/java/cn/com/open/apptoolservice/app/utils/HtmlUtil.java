package cn.com.open.apptoolservice.app.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

public class HtmlUtil {

	private static final Logger log = LoggerFactory.getLogger(HtmlUtil.class);

	private HtmlUtil(){}

	public static void writeJson(HttpServletResponse response, String jsonString) {
		try {
			response.setHeader("Content-type", "application/json;charset=UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonString);
		} catch (Exception e){
			log.error(e.getMessage(), e);
		}

	}

}
