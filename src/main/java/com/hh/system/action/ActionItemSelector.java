package com.hh.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.service.impl.GlobalItemSelectorService;
import com.hh.system.util.base.BaseAction;

@SuppressWarnings("serial")
public class ActionItemSelector extends BaseAction {
	@Autowired
	private GlobalItemSelectorService globalItemSelectorService;
	private String table_name;

	public Object queryItem() {
		return globalItemSelectorService.queryItem(paramsMap);
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

}
