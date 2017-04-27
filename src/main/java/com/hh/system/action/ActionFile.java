package com.hh.system.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import com.hh.system.bean.SystemFile;
import com.hh.system.service.impl.SystemFileService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.FileUtil;
import com.hh.system.util.Json;
import com.hh.system.util.MessageException;
import com.hh.system.util.StaticVar;
import com.hh.system.util.base.BaseAction;
import com.hh.system.util.model.ResultFile;
import com.hh.system.util.request.Request;

@SuppressWarnings("serial")
public class ActionFile extends BaseAction {
	private File attachment;
	private String attachmentFileName;
	private String params;
	private String type;
	private String serviceId;
	private String parentServiceId;


	@Autowired
	private SystemFileService fileService;

	public Object save() {
		response.setContentType("text/html");
		String path = FileUtil.uploadFile(attachment, attachmentFileName, type);
		SystemFile systemFile = new SystemFile();
		try {
			systemFile.setPath(path);
			systemFile.setFileSize(attachment.length());
			systemFile.setType(type);
			systemFile.setServiceId(serviceId);
			systemFile.setParentServiceId(parentServiceId);
			int index_ = attachmentFileName.lastIndexOf(".");
			if (index_ > -1) {
				systemFile.setFileType(Convert.toString(attachmentFileName.substring(index_ + 1)).toLowerCase());
			}
			systemFile.setText(attachmentFileName);
			
			systemFile.setBaseHttpFilePath("/"+StaticVar.filebasefolder);
			fileService.save(systemFile);
			return systemFile;
		} catch (MessageException e) {
			return e;
		}
	}

	public Object download() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (Check.isNoEmpty(params)) {
			map = Json.toMap(params);
		} 
		SystemFile systemFile = fileService.findObjectById(Convert.toString(map.get("id")));
		File file = null;
		if (systemFile != null) {
			file = new File(StaticVar.filepath + systemFile.getPath());
		}
		try {
			
			FileInputStream fileInputStream = null;
			
			if (file!=null && file.exists()) {
				fileInputStream = new FileInputStream(file);
			}
			if (systemFile!=null) {
				ResultFile resultFile = new ResultFile(systemFile.getText(),fileInputStream,
						systemFile.getFileType());
				return resultFile;
			}else{
				ResultFile resultFile = new ResultFile("空文件.txt",fileInputStream,
						"txt");
				return resultFile;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Object downloadFile() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (Check.isNoEmpty(params)) {
			map = Json.toMap(params);
		} 
		String classPath = Convert.toString(map.get("path"));

		URL url = ResourceUtils.getURL("classpath:" + classPath);
		int index = classPath.lastIndexOf("/");
		String name = "";
		if (index > -1) {
			name = classPath.substring(index + 1);
		} else {
			name = classPath;
		}

		int typeindex = classPath.lastIndexOf("/");
		String type = "";
		if (typeindex > -1) {
			type = classPath.substring(typeindex + 1);
		} else {
			type = classPath;
		}

		InputStream is = url.openStream();
		try {
			ResultFile resultFile = new ResultFile(name, is, type);
			return resultFile;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public File getAttachment() {
		return attachment;
	}

	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}

	public String getAttachmentFileName() {
		return attachmentFileName;
	}

	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getParentServiceId() {
		return parentServiceId;
	}

	public void setParentServiceId(String parentServiceId) {
		this.parentServiceId = parentServiceId;
	}


}
