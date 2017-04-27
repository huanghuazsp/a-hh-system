package com.hh.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.hibernate.util.base.BaseBean;

@Service
public class HibernateService {
	@Autowired
	private IHibernateDAO<BaseBean> dao;
	@Transactional
	public Object findObjectBySql(String sql) {
		return dao.findObjectBySql(sql);
	}
}
