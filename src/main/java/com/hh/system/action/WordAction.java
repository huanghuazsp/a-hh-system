package com.hh.system.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.hh.system.inf.IFileAction;
import com.hh.system.util.Convert;
import com.hh.system.util.FileUtil;
import com.hh.system.util.Json;
import com.hh.system.util.StaticVar;
import com.hh.system.util.base.BaseServletAction;
import com.hh.system.util.date.DateFormat;
import com.hh.system.util.document.WordUtil;
import com.hh.system.util.pk.PrimaryKey;

@SuppressWarnings("serial")
public class WordAction extends BaseServletAction implements IFileAction {
	private byte[] bytes = null;
	private String title;
	private String params;

	public String exportWord() {
		Map<String, Object> paramsmap = Json.toMap(params);

		String dataListString = Convert.toString(paramsmap
				.get("dataListString"));
		title = Convert.toString(paramsmap.get("title"));
		
		String pathString = StaticVar.webappPath + "/temp/";
		pathString += DateFormat.getDate();
		FileUtil.mkdirs(pathString);
		this.setBytes(WordUtil.exportWordByte(dataListString, pathString+"/"
				+ PrimaryKey.getUUID()+ ".doc"));
		return "word";
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public int getContentLength() {
		return this.bytes.length;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}



}
