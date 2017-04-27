package com.hh.system.util;

import java.lang.reflect.Method;

import com.hh.system.service.impl.BeanFactoryHelper;

public class ClassReflex {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object execute(Object o, String methodname, Object... params) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try {
			Class clazz = loader.loadClass(o.getClass().getName());
			Class[] classes = new Class[params.length];
			for (int i = 0; i < params.length; i++) {
				classes[i] = params[i].getClass();
			}
			Method method = clazz.getMethod(methodname, classes);
			Object _ro = method.invoke(o, params);
			return _ro;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object executeClass(Class classname, String methodname,
			Object... params) throws Throwable {
		Object o = Class.forName(classname.getName()).newInstance();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class clazz = loader.loadClass(classname.getName());
		Class[] classes = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			classes[i] = params[i].getClass();
		}
		Method method = clazz.getMethod(methodname, classes);
		Object _ro = method.invoke(o, params);
		return _ro;
	}

	@SuppressWarnings("rawtypes")
	public static Object executeClassByPropertiesSet(Class classname,
			String propertiesName, Object... params) throws Throwable {
		return executeClassByPropertiesSet(classname,
				"set" + propertiesName.substring(0, 1).toUpperCase()
						+ propertiesName.substring(1), params);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object executeClassReturnObject(Object object, String methodname,
			Object... params) throws Throwable {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class clazz = loader.loadClass(object.getClass().getName());
		Class[] classes = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			classes[i] = params[i].getClass();
		}
		Method method = clazz.getMethod(methodname, classes);
		method.invoke(object, params);
		return object;
	}
	
	public static Object executeClassByPropertiesSet(Object object,
			String propertiesName, Object... params) throws Throwable {
		return executeClassReturnObject(object,
				"set" + propertiesName.substring(0, 1).toUpperCase()
						+ propertiesName.substring(1), params);
	}
	
	public static Object executeClassByPropertiesGet(Object object,
			String propertiesName, Object... params) throws Throwable {
		return executeClassReturnProperties(object,
				"get" + propertiesName.substring(0, 1).toUpperCase()
						+ propertiesName.substring(1), params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object executeClassReturnProperties(Object object, String methodname,
			Object... params) throws Throwable {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class clazz = loader.loadClass(object.getClass().getName());
		Class[] classes = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			classes[i] = params[i].getClass();
		}
		Method method = clazz.getMethod(methodname, classes);
		return method.invoke(object, params);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object executeSpringClass(String classname,
			String methodname, Object... params) throws Throwable {
		Object o = BeanFactoryHelper.getBeanFactory().getBean(
				Class.forName(classname));
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class clazz = loader.loadClass(classname);
		Class[] classes = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			classes[i] = params[i].getClass();
		}
		Method method = clazz.getMethod(methodname, classes);
		Object _ro = method.invoke(o, params);
		return _ro;
	}


}
