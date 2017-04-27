package com.hh.hibernate.util.base;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.hh.system.util.Check;

@MappedSuperclass
public class BaseEntityTree<T> extends BaseEntity<T> {
	private static final long serialVersionUID = 1L;
	private String text;
	private String node;
	protected int leaf;
	private int expanded;
	private String icon;
	private String iconSkin;
	private List<T> children;
	
	private String name;
	protected boolean isParent=true;
	
	private boolean nocheck =false;

	@Column(name = "TEXT", length = 64)
	public String getText() {
		return text;
	}
	
	@Transient
	public String getName() {
		return Check.isEmpty(name)?getText():name;
	}

	public void setText(String text) {
		this.name=text;
		this.text = text;
	}

	@Column(name = "NODE", length = 36)
	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}
	
	@Transient
	public int getLeaf() {
		return leaf;
	}

	public void setLeaf(int leaf) {
		this.isParent=leaf==0;
		this.leaf = leaf;
	}

	@Column(name = "EXPANDED", length = 1)
	public int getExpanded() {
		return expanded;
	}

	public void setExpanded(int expanded) {
		this.expanded = expanded;
	}

	@Column(name = "ICON", length = 256)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}


	@Transient
	public List<T> getChildren() {
		return children;
	}

	public void setChildren(List<T> children2) {
		this.children = children2;
	}
	@Transient
	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Transient
	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	@Transient
	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}
	
	

}
