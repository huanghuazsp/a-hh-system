package com.hh.system.util.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

public class ExtCheckTree {
	private String id;
	private String text;
	private String icon;
	private boolean checked = false;
	private int leaf = 0;
	private int expanded = 0;
	private  Map<String, Object> propertys = new HashMap<String, Object>();
	private List<ExtCheckTree> children;
	
	private String name;
	private boolean isParent=true;
	
	private boolean nocheck =false;
	
	public int isExpanded() {
		return expanded;
	}

	public void setExpanded(int expanded) {
		this.expanded = expanded;
	}

	public int isLeaf() {
		return leaf;
	}

	public void setLeaf(int leaf) {
		this.isParent=leaf==0;
		this.leaf = leaf;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.name=text;
		this.text = text;
	}

	public Map<String, Object> getPropertys() {
		return propertys;
	}

	public void setPropertys(Map<String, Object> propertys) {
		this.propertys = propertys;
	}

	public List<ExtCheckTree> getChildren() {
		return children;
	}

	public void setChildren(List<ExtCheckTree> children) {
		this.children = children;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public int getLeaf() {
		return leaf;
	}

	public int getExpanded() {
		return expanded;
	}
	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}
}
