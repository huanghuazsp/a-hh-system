package com.hh.system.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hh.system.util.date.DateFormat;
import com.hh.system.util.pk.PrimaryKey;
import com.hh.system.util.request.Request;
import com.hh.usersystem.IUser;

public class BaseSystemUtil {

	public static String img_help = "/hhcommon/opensource/jquery/image/12/help.gif";


	public static String getBaseDoctype() {
		return "<!DOCTYPE HTML>";
		// return
		// "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">";
	}

	public static String getJqueryCss(String theme) {
		String css = StaticVar.systemProperties.get("hh.jqueryuicss");
		if (Check.isNoEmpty(theme)) {
			css = css.replace("base", theme);
		}
		// else {
		// css=StaticVar.systemProperties.get("bootcss")+css;
		// }
		return css;
	}

	public static String getBaseJs(String... args) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		return StaticVar.systemProperties.get("hh.systemicon") + getBaseJs(paramsMap, args);
	}
	
	public static String getMobileBaseJs(String... args) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		return StaticVar.systemProperties.get("hh.systemicon") + getMobileBaseJs(paramsMap, args);
	}
	
	public static String getMobileBaseJs(Map<String, String> paramsMap,
			String... args) {
		return  StaticVar.systemProperties.get("hh.metas")
				+ "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
				+ "<script type=\"text/javascript\">var contextPath='"
				+ Request.getContextPathAll()
				+ "';var theme = '"+Convert.toString(paramsMap.get("theme"))
				+ "';var uuid ='"+PrimaryKey.getUUID()+"';var timelong = "+new Date().getTime()+";setInterval(function(){timelong+=1000*60;if($.hh.browser.isIE){CollectGarbage();} }, 1000*60);</script>"
				+ Convert.toString(paramsMap.get("operPower"))
				+ StaticVar.systemProperties.get("hh.jqueryjs")
				+ StaticVar.systemProperties.get("hh.hhjquerymobilejs")
				+ getKey(args)
				+ StaticVar.systemProperties.get("hh.main")
				+ Convert.toString(StaticVar.systemProperties.get("mobilebebase"))
				+ StaticVar.systemProperties.get("hh.hhjquerymobile")
				+ Convert.toString(StaticVar.systemProperties.get("mobileafbase"));
	}

	public static String getBaseJs(Map<String, String> paramsMap,
			String... args) {
		return  StaticVar.systemProperties.get("hh.metas")
				+ "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">"
				+ "<script type=\"text/javascript\">var contextPath='"
				+  Request.getContextPathAll()
				+ "';var theme = '"+Convert.toString(paramsMap.get("theme"))
				+ "';var uuid ='"+PrimaryKey.getUUID()+"';var timelong = "+new Date().getTime()+";setInterval(function(){timelong+=1000*60;if($.hh.browser.isIE){CollectGarbage();}}, 1000*60);</script>"
				+ Convert.toString(paramsMap.get("operPower"))
				+ StaticVar.systemProperties.get("hh.jqueryjs")
				+ getJqueryCss(Convert.toString(paramsMap.get("theme")))
				+ StaticVar.systemProperties.get("hh.jqueryuijs")
				+ getKey(args)
				+ StaticVar.systemProperties.get("hh.main")
				+ Convert.toString(StaticVar.systemProperties.get("bebase"))
				+ StaticVar.systemProperties.get("hh.hhjqueryui")
				+ Convert.toString(StaticVar.systemProperties.get("afbase"));
	}

	public static String getKey(String... args) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String string : args) {
			stringBuffer.append(StaticVar.systemProperties.get(string));
		}
		return stringBuffer.toString();
	}
	
	public static String generatedValue(String value){
		if (value.indexOf("${")>-1) {
			IUser user =	(IUser)Request.getSession().get("loginuser");
			if(user!=null){
				value=value.replaceAll("\\$\\{当前登录人}", user.getText())
				.replaceAll("\\$\\{当前登录人岗位}",  user.getJobText())
				.replaceAll("\\$\\{当前登录人所在部门}",  user.getDeptText())
				.replaceAll("\\$\\{当前登录人所在机构}",  user.getOrgText());
			}
			value=value
			.replaceAll("\\$\\{当前时间yyyy-MM-dd}",  DateFormat.getDate("yyyy-MM-dd"))
			.replaceAll("\\$\\{当前时间yyyy-MM-dd HH:mm:ss}",  DateFormat.getDate("yyyy-MM-dd HH:mm:ss"))
			.replaceAll("\\$\\{当前时间yyyy-MM}",  DateFormat.getDate("yyyy-MM"))
			.replaceAll("\\$\\{当前时间yyyy}",  DateFormat.getDate("yyyy"))
			.replaceAll("\\$\\{当前时间HH:mm:ss}",  DateFormat.getDate("HH:mm:ss"))
			.replaceAll("\\$\\{当前时间HH:mm}",  DateFormat.getDate("HH:mm"))
			.replaceAll("\\$\\{当前时间yyyy-MM-dd HH}",  DateFormat.getDate("yyyy-MM-dd HH"));
		}
		return value;
	}

	
}
