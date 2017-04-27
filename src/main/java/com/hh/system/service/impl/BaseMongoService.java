package com.hh.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.hibernate.util.base.BaseBean;
import com.hh.mongo.dao.inf.IDataBaseDAOInf;
import com.hh.mongo.dao.inf.IMongoDAOInf;
import com.hh.system.service.inf.BaseServiceInf;
import com.hh.system.util.StaticVar;

public class BaseMongoService<T extends BaseBean> extends BaseDataService<T>
		implements BaseServiceInf<T> {
	@Autowired
	protected IMongoDAOInf<T> dao;

	@Override
	protected IDataBaseDAOInf<T> getDao() {
		return dao;
	}

	@Override
	protected String getEntityId() {
		return StaticVar.mongoEntityId;
	}
	

}
