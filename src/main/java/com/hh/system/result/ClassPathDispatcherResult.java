/**
 * 
 */
package com.hh.system.result;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.ServletDispatcherResult;

import com.hh.system.util.StaticVar;
import com.opensymphony.xwork2.ActionInvocation;

/**
 * Struts2的ResultType，用于从ClassPath（磁盘路径或者.jar文件中）查找result的类。<br/>
 * 
 * @author Spectrum
 * 
 */
public class ClassPathDispatcherResult extends ServletDispatcherResult {
	private static final long serialVersionUID = -7555511936204007894L;
	private static final Logger logger = Logger
			.getLogger(ClassPathDispatcherResult.class);
	public static final String BASE_PATH = "/WEB-INF/CLASSPATH-PAGES/";

	private static Map<String, String> locations = new HashMap<String, String>();

	@Override
	public void doExecute(String finalLocation, ActionInvocation invocation)
			throws Exception {
		String location = locate(finalLocation);
		super.doExecute(location, invocation);
	}

	private String locate(String location) {
		String path = locations.get(location);
		if (path == null || "IDE".equalsIgnoreCase(StaticVar.startType)) {
			try {
				path = findAndExtract(location);
			} catch (IOException e) {
				logger.error("出错啦！！======"+"异常：" + e.getClass().getName() + "：" + e.getMessage());
				path = location;
				HttpServletResponse response = ServletActionContext.getResponse();
				HttpServletRequest request = ServletActionContext.getRequest();
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);//404
				try {
					request.getRequestDispatcher("base/404.jsp").forward(request,response);
				} catch (ServletException | IOException e1) {
					e1.printStackTrace();
				}
			}
			locations.put(location, path);
		}
		return path;
	}

	private String findAndExtract(String location) throws IOException {
		String path = location;
		// InputStream is = this.getClass().getClassLoader()
		// .getResourceAsStream(location);
		URL url = org.springframework.util.ResourceUtils.getURL("classpath:"
				+ location);
		InputStream is = url.openStream();
		try {
			String mp = BASE_PATH + location;
			String p = ServletActionContext.getServletContext().getRealPath(mp);
			File f = new File(p);
			if (f.exists()) {
				f.delete();
			}
			File fp = f.getParentFile();
			if (!fp.exists()) {
				fp.mkdirs();
			}
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f);
			try {
				new StreamConnector(is, fos).flush();
			} finally {
				try {
					fos.close();
				} catch (IOException ioex) {
				}
			}
			path = mp;
		} finally {
			try {
				is.close();
			} catch (IOException ioex) {
			}
		}
		return path;
	}
}
