package com.hh.system.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import com.hh.hibernate.util.base.BaseBean;

public class BeanUtils {
	static {
		ConvertUtils.register(new DateConverter(null), java.util.Date.class);  
	}
	public static void copyProperties(Object dest, Object source) {
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(dest, source);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static void copyMap(Object dest, Map source) {
		try {
			org.apache.commons.beanutils.BeanUtils.populate(dest, source);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void defautlPropertiesSetNull(BaseBean bean) {
		bean.setId(null);
		bean.setCreateTime(null);
		bean.setUpdateTime(null);
		bean.setLeaf(0);
		bean.setNode(null);
		bean.setOrder(null);
		bean.setCreateUser(null);
		bean.setOrgid(null);
		bean.setUpdateUser(null);
	}
}
