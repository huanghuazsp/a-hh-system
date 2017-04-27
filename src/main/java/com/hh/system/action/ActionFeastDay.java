 package com.hh.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.bean.SystemFeastDay;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.impl.SystemFeastDayService;

@SuppressWarnings("serial")
public class ActionFeastDay extends BaseServiceAction< SystemFeastDay > {
	@Autowired
	private SystemFeastDayService systemfeastdayService;
	public BaseService<SystemFeastDay> getService() {
		return systemfeastdayService;
	}
	
	public void updateFeastDay(){
		systemfeastdayService.updateFeastDay(object);
	}
	
	public Object queryFeastDay(){
		return systemfeastdayService.queryList(ParamFactory.getParamHb().like("feastDay",object.getFeastDay()));
	}
	
}
 