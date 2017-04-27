package com.hh.system.util.db;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.hh.system.util.Convert;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnPoolUtil {
	private static final ConnPoolUtil instance = new ConnPoolUtil();
	private static ComboPooledDataSource cpds = new ComboPooledDataSource(true);
	private static Map<String, String> proMap = new HashMap<String, String>();
	public static void initJavaScript() {
		ClassLoader classloader = Thread.currentThread()
				.getContextClassLoader();
		Properties springProperties = new Properties();
		InputStream springin = classloader
				.getResourceAsStream("db.properties");
		try {
			springProperties.load(springin);
			springin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Set<Object> springPropertiessetSet = springProperties.keySet();
		for (Object object : springPropertiessetSet) {
			proMap.put(Convert.toString(object),
					Convert.toString(springProperties.get(object)));
		}
	}
	
	public static  String getProperties(String key) {
		return proMap.get(key);
	}
	public static  String getProperties(String key,String defaultValue) {
		if (proMap.get(key)==null) {
			return defaultValue;
		}else{
			return proMap.get(key);
		}
	}

	static {
		cpds.setDataSourceName(getProperties("db.dataSourceName"));
		cpds.setJdbcUrl(getProperties("db.jdbcUrl"));
		try {
			cpds.setDriverClass(getProperties("db.driverClass")
					.toString());
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		cpds.setUser(getProperties("db.user").toString());
		cpds.setPassword(getProperties("db.password").toString());
		cpds.setMaxPoolSize(Integer.valueOf(
				getProperties("db.maxPoolSize", "100").toString())
				.intValue());

		cpds.setMinPoolSize(Integer.valueOf(
				getProperties("db.minPoolSize", "10").toString())
				.intValue());

		cpds.setAcquireIncrement(Integer.valueOf(
				getProperties("db.acquireIncrement").toString())
				.intValue());

		cpds.setInitialPoolSize(Integer
				.valueOf(
						getProperties("db.initialPoolSize", "10")
								.toString()).intValue());

		cpds.setMaxIdleTime(Integer.valueOf(
				getProperties("db.maxIdleTime", "240").toString())
				.intValue());
	}

	public static ConnPoolUtil getInstance() {
		return instance;
	}

	public static Connection getConnection() {
		try {
			return cpds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}