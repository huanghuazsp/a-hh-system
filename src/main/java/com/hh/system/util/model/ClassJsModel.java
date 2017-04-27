package com.hh.system.util.model;

public class ClassJsModel {
	private Long lastModified;
	private String code;
	public ClassJsModel(String code,Long lastModified){
		this.lastModified=lastModified;
		this.code=code;
	}
	public Long getLastModified() {
		return lastModified;
	}
	public void setLastModified(Long lastModified) {
		this.lastModified = lastModified;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
