package com.hh.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.service.impl.GlobalComboBoxTreeService;
import com.hh.system.util.base.BaseAction;

@SuppressWarnings("serial")
public class ActionGlobalComboBoxTree extends BaseAction{
	@Autowired
	private GlobalComboBoxTreeService globalComboBoxTreeService;
	private String table_name;
	private String node;
	private String id;
	
	
	public Object queryTree() {
		return globalComboBoxTreeService.queryTree(node,paramsMap);
	}
	
	public Object findTextById() {
		return globalComboBoxTreeService.findTextById(id,table_name);
	}
	
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}





	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	
}
