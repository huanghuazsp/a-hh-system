 package com.hh.system.service.impl;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.MessageException;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;

import org.springframework.stereotype.Service;

import com.hh.system.bean.SystemFeastDay;

@Service
public class SystemFeastDayService extends BaseService<SystemFeastDay> {

	public void updateFeastDay(SystemFeastDay object) {
		deleteByProperty("feastDay", object.getFeastDay());
		if(object.getType()==1){
			createEntity(object);
		}else if(object.getType()==2){
			createEntity(object);
		}
		
	}
	
	

	@Override
	public PagingData<SystemFeastDay> queryPagingData(SystemFeastDay entity, PageRange pageRange) {
		
		ParamInf hqlParamList = ParamFactory.getParamHb();
		hqlParamList.orderDesc("feastDay");
		if (Check.isNoEmpty(entity.getFeastDay())) {
			hqlParamList.like("feastDay", entity.getFeastDay());
		}
		return queryPagingData(pageRange, hqlParamList);
	}



	@Override
	public SystemFeastDay save(SystemFeastDay entity) throws MessageException {
		if(checkOnly("feastDay", entity)){
			throw new MessageException("日期已经存在！");
		}
		return super.save(entity);
	}
	
	
	
}
 