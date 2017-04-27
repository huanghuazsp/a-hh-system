package com.hh.system.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hh.system.inf.IFileAction;
import com.hh.system.util.Convert;
import com.hh.system.util.Json;
import com.hh.system.util.base.BaseServletAction;
import com.hh.system.util.document.ExcelUtil;
import com.hh.system.util.document.ExportSetInfo;

@SuppressWarnings("serial")
public class ExcelAction extends BaseServletAction implements IFileAction {
	private byte[] bytes = null;

	private String params;
	private String title;

	public String exportExcel() {

		Map<String, Object> paramsmap = Json.toMap(params);

		String dataListString = Convert.toString(paramsmap
				.get("dataListString"));
		title = Convert.toString(paramsmap.get("title"));
		String headNames = Convert.toString(paramsmap.get("headNames"));
		String fieldNames = Convert.toString(paramsmap.get("fieldNames"));

		List<Map<String, Object>> dataList = Json.toMapList(dataListString);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		List<String[]> headNameList = new ArrayList<String[]>();
		headNameList.add(headNames.split(","));
		List<String[]> fieldNameList = new ArrayList<String[]>();
		fieldNameList.add(fieldNames.split(","));
		ExportSetInfo setInfo = new ExportSetInfo();
		Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();
		map.put(title, dataList);
		setInfo.setObjsMap(map);
		setInfo.setFieldNames(fieldNameList);
		setInfo.setTitles(new String[] { title });
		setInfo.setHeadNames(headNameList);
		setInfo.setOut(baos);
		try {
			ExcelUtil.export2Excel(setInfo);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		this.setBytes(baos.toByteArray());
		return "excel";
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

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getTitle() {
		return title;
	}

}
