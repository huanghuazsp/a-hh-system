package com.hh.system.util.model;

import java.io.File;
import java.io.InputStream;

public class ResultFile {
	private String type;
	private String name;
	private InputStream inputStream;
	
	private boolean cache;
	private File file;
	
	public ResultFile(String text, File file, String fileType) {
		this.type= fileType;
		this.file= file;
		this.name= text;
	}
	
	public ResultFile(String text, InputStream fileInputStream, String fileType) {
		this.type= fileType;
		this.inputStream= fileInputStream;
		this.name= text;
	}

	public ResultFile(String text, InputStream fileInputStream, String fileType, boolean cache) {
		this.type= fileType;
		this.inputStream= fileInputStream;
		this.name= text;
		this.cache =cache;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public boolean isCache() {
		return cache;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	

}
