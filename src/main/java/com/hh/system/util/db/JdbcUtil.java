package com.hh.system.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.hh.system.service.impl.SetupInitServiceImpl;

public class JdbcUtil {
	static String driverClass = null;
	static String url = null;
	static String userName = null;
	static String password = null;
	static {
		try {
			Properties springProperties = SetupInitServiceImpl.getProperties();
			driverClass = springProperties.getProperty("jdbc.driverClass");
			url = springProperties.getProperty("jdbc.jdbcUrl");
			userName = springProperties.getProperty("jdbc.username");
			password = springProperties.getProperty("jdbc.password");
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static String getSchema() {
		try {
			return  getConnection().getCatalog();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static int isTable(String tableName) {
		int count = JdbcUtil.count("select count(*) from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='"+getSchema()+"' and TABLE_NAME='"+tableName+"'  ");
		return count>0?1:0;
	}
	
	public static void close(ResultSet rs, Statement stmt, Connection con) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(Object o) {
		if (o instanceof ResultSet) {
			try {
				((ResultSet) o).close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (o instanceof Statement) {
			try {
				((Statement) o).close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (o instanceof Connection) {
			try {
				((Connection) o).close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static List<Map<String, Object>> query(String sql) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection connection = getConnection();
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			int colCount = md.getColumnCount();
			List<String> colNameList = new ArrayList<String>();
			for (int i = 0; i < colCount; i++) {
				colNameList.add(md.getColumnLabel(i + 1));
			}
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (String colName : colNameList) {
					Object object = rs.getObject(colName);
					map.put(colName.toLowerCase(), object);
				}
				mapList.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, connection);
		}
		return mapList;
	}

	public static int count(String sql) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection connection = getConnection();
		int count = 0;
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				count =  rs.getInt(1);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, connection);
		}
		return count;
	}

}
