package com.hh.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.service.impl.GlobalComboBoxService;
import com.hh.system.util.base.BaseAction;

@SuppressWarnings("serial")
public class ActionGlobalComboBox extends BaseAction{
	@Autowired
	private GlobalComboBoxService globalComboBoxService;
	private String table_name;
	
	
	public Object queryItem() {
		return globalComboBoxService.queryItem(paramsMap);
	}
	
	public Object queryItemsByIds() {
		return globalComboBoxService.queryItemsByIds(table_name,this.getIds());
	}


	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	
}
