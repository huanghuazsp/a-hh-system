package com.hh.system.util;

import java.util.HashMap;
import java.util.Map;

import com.hh.system.service.inf.IFileOper;
import com.hh.system.service.inf.LoadDataTime;
import com.hh.system.util.model.ClassJsModel;

public class StaticVar {
	//轮循 执行接口
	public static Map<String,LoadDataTime> loadDataTimeMap = new HashMap<String,LoadDataTime>();
	
	//附件处理接口
	public static Map<String,IFileOper> fileOperMap = new HashMap<String,IFileOper>();
	
	public static String webappPath = "";
	public static String contextPath = "";
	//附件目录
	public static String filebasefolder = "uploadfile";
	//附件目录
	public static String filebasepath = "/"+filebasefolder+"/";
	//附件绝对路径
	public static String filepath = "";
	//删除附件目录
	public static String deletefilebasepath = "/deleteuploadfile/";
	//删除附件绝对路径
	public static String deletefilepath = "";
	
	public static String JSP = "jsp";
	public static Map<String, ClassJsModel> resource_js_map = new HashMap<String, ClassJsModel>();
	public static Map<String, ClassJsModel> resource_map = new HashMap<String, ClassJsModel>();
	public static Map<String, String> systemProperties = new HashMap<String, String>();
	// 启动类型
	public static String startType = "server";

	// 排序字段
	public static String order = "order";
	//关系型主键字段
//	public static String id_r = "id";

	public static String entityId = "id";
	// mongo主键字段
	public static String mongoEntityId = "_id";
	// 树的上级节点
	public static String node = "node";
	// 树根节点父ID
	public static String root = "root";
	// 文本字段
	public static String text = "text";
	public static String asc = "asc";
	public static String desc = "desc";

	// 数据库类型
	public static String DATABASE = "oracle";
	public static Object DATABASE_SCHEMA;
	
	
	
	//角色id
	
	public static String role_admin_id= "admin";
	public static String role_default_id= "default";
	
	public static String role_admin_text= "超级管理员";
	public static String role_default_text= "默认";
	
	
	public static String getMessage(String message){
		return "<table cellpadding='0' cellspacing='0' border='0' width='100%'	height='100%'>"
				+"<tr>"
				+"<td align='center' valign='middle'>"
					+"<table>"
						+"<tr>"
							+"<td><div class='hh_blue'"
									+"style='border-radius: 8px; -moz-border-radius: 8px; margin-top: 2px; padding: 10px 15px;'>"
									+"<table>"
										+"<tr>"
											+"<td width='50px'><div class='hh_img_blue'></div></td>"
											+"<td style='font-weight: bold; font-size: 25px;'>"+message
											+"</td>"
										+"</tr>"
									+"</table>"
								+"</div></td>"
						+"</tr>"
					+"</table>"
				+"</td>"
			+"</tr>"
		+"</table>";
	}
}
