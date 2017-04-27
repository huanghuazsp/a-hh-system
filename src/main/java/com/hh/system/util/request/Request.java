package com.hh.system.util.request;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.hh.system.util.Check;
import com.hh.system.util.MessageException;
import com.hh.system.util.model.ResultFile;
import com.hh.system.util.model.ReturnModel;
import com.opensymphony.xwork2.ActionContext;

public class Request {

	public static Map<String, Object> getParamMapByRequest(HttpServletRequest request) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			Object value = request.getParameter(name);
			paraMap.put(name, value);
		}
		return paraMap;
	}

	private static Gson gson = new Gson();

	public Gson getGson() {
		return gson;
	}

	public static void returnResult(HttpServletRequest request, HttpServletResponse response, Object objects)
			throws Exception {
		PrintWriter out = null;
		try {
			if (objects instanceof ReturnModel) {
				out = response.getWriter();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("returnModel", objects);
				map.put("success", true);
				out.print(gson.toJson(map));
			} else if (objects != null && objects instanceof MessageException) {
				out = response.getWriter();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("returnModel", new ReturnModel(((MessageException) objects).getMessage()));
				map.put("success", true);
				out.print(gson.toJson(map));
			} else if (objects != null && objects instanceof ResultFile) {
				ResultFile resultFile = (ResultFile) objects;
				// response.setHeader("Cash", "no cash");
				response.setContentType(resultFile.getType());
				OutputStream output = response.getOutputStream();
				if (Check.isNoEmpty(resultFile.getName())) {
					String fileName = new String(resultFile.getName().replaceAll(" ", "").getBytes("gbk"), "ISO8859-1");
					response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
				}
				if (resultFile.isCache()) {
					long clientLastModified = request.getDateHeader("If-Modified-Since");
					if (clientLastModified > 100000) {
						response.setStatus(304);
					} else {
						response.addDateHeader("Last-Modified", System.currentTimeMillis());
						response.setHeader("Cache-Control", "public");
					}
				}
				if (resultFile.getFile() != null) {
					fileResult(request, response, resultFile);
				} else {
					fileResult(output, resultFile.getInputStream());
				}
			} else if (objects instanceof StringBuffer) {
				out = response.getWriter();
				out.print(objects);
			} else {
				out = response.getWriter();
				out.print(gson.toJson(objects));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	public static void fileResult(OutputStream outputStream, InputStream inputStream) {
		if (inputStream == null) {
			return;
		}
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(inputStream);
			byte[] temp = new byte[1024];
			int size = 0;
			while ((size = in.read(temp)) != -1) {
				outputStream.write(temp, 0, size);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void fileResult(HttpServletRequest request, HttpServletResponse response, ResultFile resultFile)
			throws IOException, InterruptedException {
		File downloadFile = resultFile.getFile();
		// 要下载的文件大小
		long fileLength = downloadFile.length();
		// 已下载的文件大小
		long pastLength = 0;
		// 是否快车下载，否则为迅雷或其他
		boolean isFlashGet = true;
		// 用于记录需要下载的结束字节数（迅雷或其他下载）
		long lenEnd = 0;
		// 用于记录客户端要求下载的数据范围字串
		String rangeBytes = request.getHeader("Range");
		// 用于随机读取写入文件
		RandomAccessFile raf = null;
		OutputStream os = null;
		OutputStream outPut = null;
		byte b[] = new byte[1024];
		// 如果客户端下载请求中包含了范围
		if (null != rangeBytes) {
			// 返回码 206
			response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
			rangeBytes = request.getHeader("Range").replaceAll("bytes=", "");
			// 判断 Range 字串模式
			if (rangeBytes.indexOf('-') == rangeBytes.length() - 1) {
				// 无结束字节数，为快车
				isFlashGet = true;
				rangeBytes = rangeBytes.substring(0, rangeBytes.indexOf('-'));
				pastLength = Long.parseLong(rangeBytes.trim());
			} else {
				// 迅雷下载
				isFlashGet = false;
				String startBytes = rangeBytes.substring(0, rangeBytes.indexOf('-'));
				String endBytes = rangeBytes.substring(rangeBytes.indexOf('-') + 1, rangeBytes.length());
				// 已下载文件段
				pastLength = Long.parseLong(startBytes.trim());
				// 还需下载的文件字节数(从已下载文件段开始)
				lenEnd = Long.parseLong(endBytes);
			}
		}
		// 通知客户端允许断点续传，响应格式为：Accept-Ranges: bytes
		response.setHeader("Accept-Ranges", "bytes");
		// response.reset();
		// 如果为第一次下载，则状态默认为 200，响应格式为： HTTP/1.1 200 ok
		if (0 != pastLength) {
			// 内容范围字串
			String contentRange = "";
			// 响应格式
			// Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]||[文件的总大小]
			if (isFlashGet) {
				contentRange = new StringBuffer("bytes").append(new Long(pastLength).toString()).append("-")
						.append(new Long(fileLength - 1).toString()).append("/").append(new Long(fileLength).toString())
						.toString();
			} else {
				contentRange = new StringBuffer(rangeBytes).append("/").append(new Long(fileLength).toString())
						.toString();
			}
			response.setHeader("Content-Range", contentRange);
		}
		String fileName = new String(resultFile.getName().replaceAll(" ", "").getBytes("gbk"), "ISO8859-1");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + "");
		// 响应的格式是:
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Length", String.valueOf(fileLength));
		try {
			os = response.getOutputStream();
			outPut = new BufferedOutputStream(os);
			raf = new RandomAccessFile(downloadFile, "r");
			// 跳过已下载字节
			raf.seek(pastLength);
			if (isFlashGet) {
				// 快车等
				int n = 0;
				while ((n = raf.read(b, 0, 1024)) != -1) {
					outPut.write(b, 0, n);
				}
			} else {
				// 迅雷等
				while (raf.getFilePointer() < lenEnd) {
					outPut.write(raf.read());
				}
			}
			outPut.flush();
		} catch (IOException e) {
			/**
			 * 在写数据的时候 对于 ClientAbortException 之类的异常
			 * 是因为客户端取消了下载，而服务器端继续向浏览器写入数据时， 抛出这个异常，这个是正常的。 尤其是对于迅雷这种吸血的客户端软件。
			 * 明明已经有一个线程在读取 bytes=1275856879-1275877358，
			 * 如果短时间内没有读取完毕，迅雷会再启第二个、第三个。。。线程来读取相同的字节段， 直到有一个线程读取完毕，迅雷会 KILL
			 * 掉其他正在下载同一字节段的线程， 强行中止字节读出，造成服务器抛 ClientAbortException。
			 * 所以，我们忽略这种异常
			 */
		} finally {
			if (outPut != null) {
				outPut.close();
			}
			if (raf != null) {
				raf.close();
			}
		}
	}

	// \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
	// 字符串在编译时会被转码一次,所以是 "\\b"
	// \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
	static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i" + "|windows (phone|ce)|blackberry"
			+ "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp" + "|laystation portable)|nokia|fennec|htc[-_]"
			+ "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
	static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser" + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

	// 移动设备正则匹配：手机端、平板
	static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
	static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);

	/**
	 * 检测是否是移动设备访问
	 * 
	 * @Title: check
	 * @param userAgent
	 *            浏览器标识
	 * @return true:移动设备接入，false:pc端接入
	 */
	public static boolean checkClientType(String userAgent) {
		if (null == userAgent) {
			userAgent = "";
		}
		// 匹配
		Matcher matcherPhone = phonePat.matcher(userAgent);
		Matcher matcherTable = tablePat.matcher(userAgent);
		if (matcherPhone.find() || matcherTable.find()) {
			return true;
		} else {
			return false;
		}
	}

	public static String getContextPath() {
		return ServletActionContext.getRequest().getContextPath();
	}

	public static String getContextPathAll() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getContextPath();
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	}

	public static String getServerPathAll() {
		HttpServletRequest request = ServletActionContext.getRequest();
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
	}

	public static Map<String, Object> getSession() {
		if (ActionContext.getContext() == null || ActionContext.getContext().getSession() == null) {
			return null;
		}
		return ActionContext.getContext().getSession();
	}

	public static HttpServletRequest getRequest() {
		try {
			return ServletActionContext.getRequest();
		} catch (Exception e) {
			return null;
		}

	}
}
