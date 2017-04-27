package com.hh.system.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hh.system.util.Check;
import com.hh.system.util.StaticVar;
import com.hh.system.util.base.BaseAction;
import com.hh.system.util.model.ClassJsModel;
import com.hh.system.util.model.ExtTree;

@SuppressWarnings("serial")
public class ActionResourceFile extends BaseAction {
	private String path;

	public void js() throws IOException {
		response.setContentType("application/x-javascript");
		outResource("js");
	}

	public void javascript() throws IOException {
		response.setContentType("application/x-javascript");
		outResource("resource");
	}

	public void css() throws IOException {
		response.setContentType("text/css");
		outResource("resource");
	}

	public Map<String, Object> resource1(String path)
			throws FileNotFoundException {
		Map<String, Object> map = new HashMap<String, Object>();
		ClassJsModel classJsModel = StaticVar.resource_map.get(path);
		Long Last_Modified = 0l;
		InputStream inputStream = null;
		File jar = new File(StaticVar.webappPath + path);
		if (jar.exists()) {
			if (Last_Modified < jar.lastModified()) {
				Last_Modified = jar.lastModified();
			}
		}
		if (Check.isEmpty(classJsModel)
				|| classJsModel.getLastModified() < jar.lastModified()) {
			inputStream = new FileInputStream(jar);
		}
		map.put("Last_Modified", Last_Modified);
		map.put("inputStream", inputStream);
		map.put("classJsModel", classJsModel);
		map.put("jar", jar);
		return map;
	}

	public Map<String, Object> js1(String path) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		ClassJsModel classJsModel = StaticVar.resource_js_map.get(path);
		Long Last_Modified = 0l;
		InputStream inputStream = null;
		path = "page/" + path.replaceAll("\\.", "/") + ".class.js";
		URL url = org.springframework.util.ResourceUtils.getURL("classpath:"
				+ path);
		String jarpath = url.getFile().split("\\!")[0];
		File jar = new File(new URL(jarpath).getPath());
		if (jar.exists()) {
			if (Last_Modified < jar.lastModified()) {
				Last_Modified = jar.lastModified();
			}
		}
		if (Check.isEmpty(classJsModel)
				|| classJsModel.getLastModified() < jar.lastModified()) {
			inputStream = url.openStream();
		}
		map.put("Last_Modified", Last_Modified);
		map.put("inputStream", inputStream);
		map.put("classJsModel", classJsModel);
		map.put("jar", jar);
		return map;
	}

	private void outResource(String type) throws IOException,
			FileNotFoundException {
		String[] pathList = this.path.split(",");
		PrintWriter out = response.getWriter();
		Long Last_Modified = 0l;
		StringBuffer stringBuffer = new StringBuffer();
		long clientLastModified = request.getDateHeader("If-Modified-Since");
		for (String path : pathList) {
			path = path.replaceAll(" ", "");
			StringBuffer classCode = new StringBuffer();
			Map<String, Object> map = new HashMap<String, Object>();
			if ("resource".equals(type)) {
				map = resource1(path);
			} else if ("js".equals(type)) {
				map = js1(path);
			}
			Last_Modified = (Long) map.get("Last_Modified");
			InputStream inputStream = (InputStream) map.get("inputStream");
			File jar = (File) map.get("jar");
			ClassJsModel classJsModel = (ClassJsModel) map.get("classJsModel");

			if (Check.isEmpty(classJsModel)
					|| classJsModel.getLastModified() < jar.lastModified()) {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						inputStream));
				String line = null;
				while ((line = in.readLine()) != null) {
					classCode.append(line + "\n");
				}
				in.close();
				// String ysCode = "";
				if ("resource".equals(type)) {
					// if (path.endsWith(".css")) {
					// ysCode= classCode.toString();
					// }else {
					// ysCode= ResourceUtil.outJs(classCode.toString());
					// }
					StaticVar.resource_map.put(
							path,
							new ClassJsModel(classCode.toString(), jar
									.lastModified()));
				} else if ("js".equals(type)) {
					// ysCode= ResourceUtil.outJs(classCode.toString());
					StaticVar.resource_js_map.put(path, new ClassJsModel(
							classCode.toString(), jar.lastModified()));
				}
				stringBuffer.append(classCode);
			} else {
				stringBuffer.append(classJsModel.getCode());
			}
		}
		end(out, Last_Modified, stringBuffer, clientLastModified);
	}

	private void end(PrintWriter out, Long Last_Modified,
			StringBuffer stringBuffer, long clientLastModified) {
		if (Math.abs(clientLastModified - Last_Modified) < 1000) {
			response.setStatus(304);
		} else {
			response.addDateHeader("Last-Modified", Last_Modified);
			response.setHeader("Cache-Control", "public");
			out.println(stringBuffer);
		}
		out.close();
	}

	public Object queryIconFilePathList() {
		String filePath = "";
		if ("root".equals(request.getParameter("node"))) {
			filePath = path;
		} else {
			filePath = request.getParameter("node").toString();
		}
		String fileRealPath = StaticVar.webappPath + filePath;
		File file = new File(fileRealPath);
		File[] subFile = file.listFiles();
		List<ExtTree> extTrees = new ArrayList<ExtTree>();
		for (File file2 : subFile) {
			ExtTree extTree = new ExtTree();
			extTree.setId(filePath + "/" + file2.getName());
			extTree.setText(filePath + "/" + file2.getName());
			if (!file2.isDirectory()) {
				extTree.setIcon(filePath + "/" + file2.getName());
				extTree.setLeaf(1);
			}
			extTrees.add(extTree);
		}
		return extTrees;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	private void printIn(BufferedReader in) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("application/x-javascript");
		String line = null;
		while ((line = in.readLine()) != null) {
			out.println(line);
		}
		in.close();
		out.close();
	}

	public void json() throws IOException {
		InputStream in = getClass().getClassLoader().getResourceAsStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		printIn(br);
	}
	// public void img() throws IOException {
	// response.setContentType("image/png");
	// InputStream in = getClass().getClassLoader().getResourceAsStream(path);
	// byte[] b = new byte[1024];
	// int len = -1;
	// OutputStream output = response.getOutputStream();
	// try {
	// while ((len = in.read(b)) > 0) {
	// output.write(b, 0, len);
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// output.flush();
	// output.close();
	// in.close();
	// }
	// }

}
