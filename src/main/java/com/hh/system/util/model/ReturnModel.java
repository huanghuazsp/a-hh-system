package com.hh.system.util.model;

public class ReturnModel {
	private String href;
	private String msg = "无信息！！";
	private String titleMsg = "提示";
	private String type = "info";
	
	public static String  errorType = "error";
	

	public ReturnModel(String type, String msg) {
		this.type =type;
		this.msg = msg;
	}

	public ReturnModel(String msg) {
		this.msg = msg;
	}

	public ReturnModel() {
	}

	public String getMsg() {
		return msg;
	}

	public ReturnModel setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public String getTitleMsg() {
		return titleMsg;
	}

	public ReturnModel setTitleMsg(String titleMsg) {
		this.titleMsg = titleMsg;
		return this;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
