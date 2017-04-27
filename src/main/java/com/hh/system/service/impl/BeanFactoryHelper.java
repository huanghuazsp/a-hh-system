package com.hh.system.service.impl;

import org.springframework.beans.factory.BeanExpressionException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

@Service
public class BeanFactoryHelper implements
		org.springframework.beans.factory.BeanFactoryAware {

	// 定义BeanFactory为类静态变量
	private static BeanFactory factory;

	// 因为实现了BeanFactoryAware,所以只要类BeanFactoryHelper归spring容器管理,
	// 当创建spring容器时,就会自动调用setBeanFactory方法,注入beanFactory
	public void setBeanFactory(BeanFactory factory)
			throws BeanExpressionException {
		this.factory = factory;
	}

	// 定义为类静态方法
	public static BeanFactory getBeanFactory() {
		return factory;
	}
	
	public static<T> T getBean(Class<T> requiredType1) {
		return factory.getBean(requiredType1);
	}
	

}
