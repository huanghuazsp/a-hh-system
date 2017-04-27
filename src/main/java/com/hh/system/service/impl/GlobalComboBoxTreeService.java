package com.hh.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;

@Service
public class GlobalComboBoxTreeService {

	@Autowired
	private IHibernateDAO hibernateDAO;

	public Object queryTree(String node, Map<String, String> paramsMap) {

		StringBuffer sql = new StringBuffer("SELECT * FROM ");
		if (!Check.isEmpty(paramsMap.get("table_name"))) {
			sql.append(paramsMap.get("table_name"));
			paramsMap.remove("table_name");
			sql.append(" WHERE node=:node ");
			if (!Check.isEmpty(paramsMap)) {
				Set<String> keysSet = paramsMap.keySet();
				for (String key : keysSet) {
					sql.append(" and " + key + " = :" + key);
				}
			}
			Map<String, Object> sqlParamMap = new HashMap<String, Object>();
			if (!"root".equals(node)) {
				paramsMap.put("node", node);
			} else {
				if (Check.isEmpty(paramsMap.get("node"))) {
					paramsMap.put("node", node);
				}
			}
			if (!Check.isEmpty(paramsMap)) {
				sqlParamMap.putAll(paramsMap);
			}
			List<Map<String, Object>> returnTreeList = hibernateDAO
					.queryListBySql(sql.toString(), sqlParamMap);

			return Convert.dTox(returnTreeList);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("text", "请指定好参数-table_name");
		map.put("id", "请指定好参数");
		map.put("leaf", true);
		List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
		treeList.add(map);
		return treeList;
	}

	public Object findTextById(String id, String table_name) {
		if (Check.isNoEmpty(id)) {
			Map<String, Object> map = new HashMap<String, Object>();
			if (id.indexOf(",") > -1) {
				if (Check.isEmpty(table_name)) {
					map.put("text", "表名无效-指定控件取值表");
					return map;
				}
				List<Map<String, Object>> mapList = hibernateDAO
						.queryListBySql("select * from " + table_name
								+ " where id in ('" + id.replaceAll(",", "','")
								+ "')");
				mapList = Convert.dTox(mapList);
				String ids = "";
				String texts = "";
				for (Map<String, Object> map2 : mapList) {
					ids += map2.get("id") + ",";
					texts += map2.get("text") + ",";
				}
				if (Check.isNoEmpty(ids)) {
					ids = ids.substring(0, ids.length() - 1);
				}
				if (Check.isNoEmpty(texts)) {
					texts = texts.substring(0, texts.length() - 1);
				}
				map.put("id", ids);
				map.put("text", texts);
				map.put("object", new Gson().toJson(mapList));
				return map;
			} else {
				if (Check.isEmpty(table_name)) {
					map.put("text", "表名无效-指定控件取值表");
					return map;
				}
				map = hibernateDAO.findEntity(table_name, id);
				if (map == null) {
					map = new HashMap<String, Object>();
					map.put("id", id);
					map.put("text", id);
				}
				map = Convert.dTox(map);
				map.put("object", new Gson().toJson(map));
				return map;
			}
		} else {
			return new Object();
		}
	}

}
