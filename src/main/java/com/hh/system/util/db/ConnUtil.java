package com.hh.system.util.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
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
import java.util.Set;

import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.LogUtil;

import sun.misc.BASE64Encoder;

public class ConnUtil {
	public static final String url = "jdbc:mysql://XXX.XXX.XXX.XXX:3306/dbadapter";
	public static final String user = "root";
	public static final String password = "XXXXXX";

	public static Connection getConn(String type, String url, String user, String password) {
		try {
			if ("mysql".equals(type))
				Class.forName("com.mysql.jdbc.Driver");
			else if ("oracle".equals(type))
				Class.forName("oracle.jdbc.driver.OracleDriver");
			else if ("sqlserver".equals(type)) {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} else {
				Class.forName(type);
			}
			return DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}

	public static void close(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			pstmt = null;
		}
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
	}

	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			statement = null;
		}
	}

	public static List<Map<String, Object>> queryTableNameList(Connection conn) {
		ResultSet rs = null;
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		try {

			DatabaseMetaData meta = conn.getMetaData();
			rs = meta.getTables(null, null, null, new String[] { "TABLE" });
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				ResultSetMetaData rsm = rs.getMetaData();
				int col = rsm.getColumnCount();
				for (int i = 0; i < col; i++) {
					String colName = rsm.getColumnName(i + 1).toLowerCase();
					Object valueObject = rs.getObject(i + 1);
					map.put(colName, valueObject);
				}
				map.put("text", rs.getString(3));
				if (Convert.toString(meta.getDriverName()).indexOf("oracle") > -1) {
					if (!Convert.toString(meta.getUserName()).toLowerCase()
							.equals(Convert.toString(map.get("table_schem")).toLowerCase())) {
						continue;
					}
				}
				mapList.add(0, map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnUtil.close(rs);
			ConnUtil.close(conn);
		}
		return mapList;
	}

	public static final String TY_INTEGER = "int";
	public static final String TY_CHAR = "string";
	public static final String TY_DATE = "date";
	public static final String TY_DATETIME = "date";
	public static final String TY_BOOLEAN = "boolean";
	public static final String TY_FLOAT = "int";
	public static final String TY_BIT = "int";

	public static List<Map<String, Object>> queryFieldMapList(String tableName, Connection conn) {
		return queryFieldMapList("select * from " + tableName + " where 1=2 ", tableName, conn);
	}

	public static List<Map<String, Object>> queryFieldMapListBySql(String sql, Connection conn) {
		return queryFieldMapList(sql, null, conn);
	}

	public static List<Map<String, Object>> queryFieldMapList(String sql, String tableName, Connection conn) {
		ResultSet rs = null;
		Statement statement = null;
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		try {
			statement = conn.createStatement();
			statement.executeQuery(sql);
			rs = statement.getResultSet();
			ResultSetMetaData rsm = rs.getMetaData();
			int col = rsm.getColumnCount();
			for (int i = 0; i < col; i++) {
				int index = i + 1;
				int type = rsm.getColumnType(index);
				String dbType = "string";
				if (type == java.sql.Types.BIGINT || type == java.sql.Types.BIT || type == java.sql.Types.TINYINT
						|| type == java.sql.Types.SMALLINT || type == java.sql.Types.INTEGER
						|| type == java.sql.Types.FLOAT || type == java.sql.Types.REAL || type == java.sql.Types.DOUBLE
						|| type == java.sql.Types.NUMERIC || type == java.sql.Types.DECIMAL
						|| type == java.sql.Types.ROWID || type == java.sql.Types.VARBINARY) {
					dbType = TY_FLOAT;
				} else if (type == java.sql.Types.DATE || type == java.sql.Types.TIMESTAMP
						|| type == java.sql.Types.TIME) {
					dbType = TY_DATE;
				} else if (type == java.sql.Types.BOOLEAN) {
					dbType = TY_BOOLEAN;
				} else if (type == java.sql.Types.BLOB || type == java.sql.Types.LONGVARBINARY) {
					dbType = "byte";
				} else if (type == java.sql.Types.CLOB || type == java.sql.Types.LONGVARCHAR) {
					dbType = "bigstring";
				} else if (type == -100) {
					dbType = "TIMESTAMP";
				} else {
					dbType = TY_CHAR;
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", rsm.getColumnName(index).toLowerCase());
				map.put("type", dbType);

				if (Check.isNoEmpty(tableName)) {
					Map pkMap = getPrimaryKeys(tableName, conn);
					if (pkMap.get(rsm.getColumnName(index)) == null) {
						map.put("primary", "0");
					} else {
						map.put("primary", "1");
					}
				}
				mapList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnUtil.close(statement);
			ConnUtil.close(rs);
			ConnUtil.close(conn);
		}
		return mapList;
	}

	public static Map getPrimaryKeys(String tableName, Connection con) throws Exception {
		try {
			DatabaseMetaData dbMeta = con.getMetaData();
			Map pkMap = new HashMap();
			ResultSet primaryKeys = dbMeta.getPrimaryKeys(con.getCatalog(), null, tableName);
			while (primaryKeys.next()) {
				String pk = primaryKeys.getString("COLUMN_NAME");
				pkMap.put(pk, pk);
			}

			return pkMap;

		} catch (Exception e) {
			return new HashMap();
		}
	}

	public static List<Map<String, Object>> queryMapListBySql(Connection conn, String sql, List<Object> paramList) {
		ResultSet rs = null;
		PreparedStatement statement = null;
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		try {
			statement = conn.prepareStatement(sql.toString());
			int j = 1;
			if (paramList != null) {
				for (Object value : paramList) {
					statement.setObject(j, value);
					j++;
				}
			}
			statement.executeQuery();
			rs = statement.getResultSet();
			while (rs.next()) {
				Map<String, Object> map = rsToMap(null, rs);
				mapList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error("数据查询出错！" + e.getClass() + ":" + e.getMessage());
			throw new RuntimeException("数据查询出错！" + e.getClass() + ":" + e.getMessage());
		} finally {
			ConnUtil.close(statement);
			ConnUtil.close(rs);
			ConnUtil.close(conn);
		}
		return mapList;
	}

	public static List<Map<String, Object>> queryMapListBySql(Connection conn, String sql, List<Object> paramList,
			Map<String, String> columnToNameMap) {
		ResultSet rs = null;
		PreparedStatement statement = null;
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		try {
			statement = conn.prepareStatement(sql.toString());
			int j = 1;
			if (paramList != null) {
				for (Object value : paramList) {
					statement.setObject(j, value);
					j++;
				}
			}
			statement.executeQuery();
			rs = statement.getResultSet();
			while (rs.next()) {
				Map<String, Object> map = rsToMap(columnToNameMap, rs);
				mapList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error("数据查询出错！" + e.getClass() + ":" + e.getMessage());
			throw new RuntimeException("数据查询出错！" + e.getClass() + ":" + e.getMessage());
		} finally {
			ConnUtil.close(statement);
			ConnUtil.close(rs);
			ConnUtil.close(conn);
		}
		return mapList;
	}

	public static Map<String, Object> queryMapBySql(Connection conn, String sql, Map<String, Object> valueMap) {

		List<Object> paramList = new ArrayList<Object>();
		sql = sqlToSql(sql, valueMap, paramList);

		List<Map<String, Object>> mapList = queryMapListBySql(conn, sql, paramList, null);
		if (mapList.size() > 0) {
			return mapList.get(0);
		} else {
			return new HashMap<String, Object>();
		}
	}

	private static String sqlToSql(String sql, Map<String, Object> valueMap, List<Object> paramList) {
		if (Check.isNoEmpty(sql)) {
			String cond = sql + " ";
			Map<Integer, Object> map = new HashMap<Integer, Object>();
			List<Integer> indexList = new ArrayList<Integer>();
			Set<String> keySet = valueMap.keySet();
			for (String key : keySet) {
				String pkey = ":" + key + " ";
				int index = cond.indexOf(pkey);
				if (index > -1) {
					indexList.add(index);
					map.put(index, valueMap.get(key));
				}
			}
			for (String key : keySet) {
				String pkey = ":" + key + " ";
				int index = cond.indexOf(pkey);
				if (index > -1) {
					cond = cond.replace(pkey, "?");
				}
			}

			int[] indexarr = new int[indexList.size()];
			for (int i = 0; i < indexList.size(); i++) {
				indexarr[i] = indexList.get(0);
			}
			indexarr = Convert.order(indexarr);
			for (int i : indexarr) {
				paramList.add(map.get(i));
			}
			return cond;
		}
		return "";
	}

	public static int execSql(Connection conn, String sql) throws Exception {
		PreparedStatement sta = null;
		try {
			sta = conn.prepareStatement(sql);
			int exec = sta.executeUpdate();
			return exec;
		} catch (Exception e) {
			throw e;
		} finally {
			ConnUtil.close(sta);
			ConnUtil.close(conn);
		}
	}

	@SuppressWarnings("restriction")
	public static Map<String, Object> rsToMap(Map<String, String> columnToNameMap, ResultSet rs) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		ResultSetMetaData rsm = rs.getMetaData();
		int col = rsm.getColumnCount();
		for (int i = 0; i < col; i++) {
			String colName = rsm.getColumnName(i + 1).toLowerCase();
			Object valueObject = rs.getObject(i + 1);
			if (valueObject instanceof byte[]) {
				byte[] test = (byte[]) valueObject;
				if (test.length == 1) {
					valueObject = test[0];
				} else {
					valueObject = new BASE64Encoder().encode(test);
				}
			}
			// else if (valueObject instanceof BLOB) {
			// BLOB blob = (BLOB) valueObject;
			// valueObject = FileUtil.stream2Byte(blob
			// .getBinaryStream());
			// valueObject = new BASE64Encoder()
			// .encode((byte[]) valueObject);
			// }
			// else if (valueObject instanceof TIMESTAMP) {
			// if (valueObject != null) {
			// TIMESTAMP timestamp = (TIMESTAMP) valueObject;
			// try {
			// valueObject = timestamp.timestampValue();
			// } catch (NullPointerException e) {
			// logger.info("oracle 日期类型值为空");
			// valueObject = "";
			// }
			// }
			// }
			if (columnToNameMap != null) {
				String proName = columnToNameMap.get(colName);
				if (Check.isEmpty(proName)) {
					proName = colName;
				}
				map.put(proName, valueObject);
			} else {
				map.put(colName, valueObject);
			}
		}
		return map;
	}
}
