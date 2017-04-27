package com.hh.system.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.LogUtil;
import com.hh.system.util.StaticVar;

@Service
public class SetupInitServiceImpl {

	private static final Logger logger = Logger.getLogger(SetupInitServiceImpl.class);

	@PostConstruct
	private void init() {
		contentInit();
		paramsInit();
		systemProInit();
	}

	private void systemProInit() {
		loadProperties("basesystem.properties");
		loadProperties("system.properties");
		String include = StaticVar.systemProperties.get("include");
		if (Check.isNoEmpty(include)) {
			String [] includeList = include.split(",");
			for (String string : includeList) {
				loadProperties(string);
			}
		}
	}

	private void loadProperties(String name) {
		LogUtil.info("加载属性文件："+name);
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		Properties springProperties = new Properties();
		InputStream springin = classloader.getResourceAsStream(name);
		
		InputStreamReader inputStreamReader = null;
		
		if (springin!=null) {
			try {
				inputStreamReader = new InputStreamReader(springin, "UTF-8");
				springProperties.load(inputStreamReader);
				Enumeration enu2 = springProperties.propertyNames();
				while (enu2.hasMoreElements()) {
					String key = (String) enu2.nextElement();
					StaticVar.systemProperties.put(key, springProperties.getProperty(key));
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (inputStreamReader!=null) {
					try {
						inputStreamReader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					springin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else{
			LogUtil.error("找不到文件："+name);
		}
	}

	private void paramsInit() {
		Properties springProperties = getProperties();

		Object dialect = springProperties.get("hibernate.dialect");
		String dialectString = Convert.toString(dialect);
		if (dialectString.toLowerCase().indexOf("mysql") > -1) {
			StaticVar.DATABASE = "mysql";
		} else if (dialectString.toLowerCase().indexOf("oracle") > -1) {
			StaticVar.DATABASE = "oracle";
		}
		StaticVar.DATABASE_SCHEMA = springProperties.get("hibernate.default_schema");
	}

	public static Properties getProperties() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();

		Properties springProperties = new Properties();
		InputStream springin = classloader.getResourceAsStream("spring.properties");
		try {
			springProperties.load(springin);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				springin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return springProperties;
	}

	private void contentInit() {
		try {
			Context context = new InitialContext();
			StaticVar.startType = (String) context.lookup("java:comp/env/hh/startType");
		} catch (NamingException e) {
			logger.error("系统没有定义参数startType！");
		}
	}
}
