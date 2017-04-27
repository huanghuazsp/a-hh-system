package com.hh.system.util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hh.system.util.Convert;
import com.hh.system.util.Json;
import com.hh.system.util.date.DateFormat;

public class DBUtil {
	public static void main(String[] args) {
		List<Map<String, Object>> maps = DBUtil.queryMapList(
				ConnUtil.getConn(
						"mysql",
						"jdbc:mysql://localhost:3306/Monitoring_System?useUnicode=true&amp;characterEncoding=utf-8",
						"root", "root"), "select * from digangle_data_p1_s1 order by time limit 1,100",
				new ArrayList<Object>());
		List<Object> mapList = new ArrayList< Object>();
		for (int i=0;i<maps.size()-1;i++) {
			Map<String, Object> map = maps.get(0);
			Map<String, Object> map2 = maps.get(i+1);
			String dataString = Convert.toString(map.get("data")).replace("Z ", "").replace("X ", "").replace("Y ", "").replace("Deg", "");
			Double x1 = Double.valueOf(dataString.split(" ")[0]);
			Double y1 = Double.valueOf(dataString.split(" ")[1]);
			Double z1 = Double.valueOf(dataString.split(" ")[2]);
			
			String dataString2 = Convert.toString(map2.get("data")).replace("Z ", "").replace("X ", "").replace("Y ", "").replace("Deg", "");
			Double x2 = Double.valueOf(dataString2.split(" ")[0]);
			Double y2 = Double.valueOf(dataString2.split(" ")[1]);
			Double z2 = Double.valueOf(dataString2.split(" ")[2]);
			
			mapList.add(Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) + (z1 - z2)*(z1-z2)));
		}
		String jsonString = Json.toStr(mapList);
	}

	public static List<Map<String, Object>> queryMapList(Connection conn,
			String sql, List<Object> paramList) {
		ResultSet rs = null;
		PreparedStatement statement = null;
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		try {
			statement = conn.prepareStatement(sql);
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
				Map<String, Object> map = new HashMap<String, Object>();
				ResultSetMetaData rsm = rs.getMetaData();
				int col = rsm.getColumnCount();
				for (int i = 0; i < col; i++) {
					String colName = rsm.getColumnName(i + 1).toLowerCase();
					Object valueObject = rs.getObject(i + 1);
					map.put(colName, valueObject);
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
}
