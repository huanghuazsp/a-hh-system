package com.hh.system.action.out;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.bean.SystemFile;
import com.hh.system.service.impl.SystemFileService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.Json;
import com.hh.system.util.StaticVar;
import com.hh.system.util.base.BaseAction;
import com.hh.system.util.model.ResultFile;

@SuppressWarnings("serial")
public class ActionFile extends BaseAction {

	@Autowired
	private SystemFileService fileService;

	private String params;
	private String path;

	public Object img() {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
		ResultFile resultFile = new ResultFile("", inputStream, "image/" + path.substring(path.lastIndexOf(".") + 1),
				true);
		return resultFile;
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
		
//		ResultFile resultFile = new ResultFile(systemFile.getText(), file, systemFile.getFileType());
//		return resultFile;
		try {
			FileInputStream fileInputStream = null;

			if (file.exists()) {
				fileInputStream = new FileInputStream(file);
			}

			ResultFile resultFile = new ResultFile(systemFile.getText(), fileInputStream, systemFile.getFileType());
			return resultFile;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

}
