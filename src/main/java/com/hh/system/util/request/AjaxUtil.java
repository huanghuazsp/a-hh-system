package com.hh.system.util.request;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

import com.hh.system.util.base.BaseAction;

public class AjaxUtil {
	private static final Logger logger = Logger.getLogger(AjaxUtil.class);

	public static String request(String url, String param) {
		String result = "";
		String urlName = "";
		try {
			urlName = url + param;
			URL U = new URL(urlName);
			URLConnection connection = U.openConnection();
			connection.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			in.close();

		} catch (Exception e) {
			logger.info("连接地址是:" + urlName);
			logger.info("与服务器连接发生异常错误:" + e.toString());
		}
		return result;
	}

	public static String request(String url) {
		return request(url, "");
	}

	public static void main(String[] args) {
		System.out
				.println(request(
						"http://localhost:8080/HHSSHE/system-login-login?xtYh.vdlzh=admin&xtYh.vmm=123456",
						""));
		request("http://localhost:8080/HHSSHE/workflow-WorkFlowTree-queryTreeList",
				"");
	}

}
