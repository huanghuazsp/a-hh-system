 package com.hh.system.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.StaticVar;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.request.Request;
import com.hh.system.bean.SystemFile;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.impl.SystemFileService;

@SuppressWarnings("serial")
public class ActionSystemFile extends BaseServiceAction< SystemFile > {
	@Autowired
	private SystemFileService systemfileService;
	
	public BaseService<SystemFile> getService() {
		return systemfileService;
	}
	
	public Object queryList() {
		List<SystemFile> systemFiles = systemfileService.queryList(ParamFactory.getParamHb().is("serviceId", object.getServiceId()).likenoreg("type", object.getType()+"%"));
		for (SystemFile systemFile : systemFiles) {
			systemFile.setBaseHttpFilePath("/"+StaticVar.filebasefolder);
		}
		return systemFiles;
	}

}
 