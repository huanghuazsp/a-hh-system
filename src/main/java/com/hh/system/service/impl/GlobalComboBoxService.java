package com.hh.system.service.impl;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;

@Service
public class GlobalComboBoxService {

	@Autowired
	private IHibernateDAO hibernateDAO;

	public Object queryItem(Map<String, String> paramsMap) {
		StringBuffer sql = new StringBuffer("SELECT * FROM ");
		if (paramsMap == null) {
			return null;
		}
		if (!Check.isEmpty(paramsMap.get("table_name"))) {
			sql.append(paramsMap.get("table_name"));
			paramsMap.remove("table_name");
			if (!Check.isEmpty(paramsMap)) {
				Set<String> keysSet = paramsMap.keySet();
				int i = 0;
				for (String key : keysSet) {
					sql.append((i == 0 ? " where " : " and ") + key + " = :"
							+ key);
					i++;
				}
			}

			return Convert.dTox(hibernateDAO.queryListBySql(sql.toString(),
					paramsMap));
		}

		return null;
	}

	public Object queryItemsByIds(String table_name, String ids) {
		ids=ids.replaceAll(",", "','");
		ids="'"+ids+"'";
		String sql ="SELECT * FROM "+table_name+" where id in ("+ids+")";
		return Convert.dTox(hibernateDAO.queryListBySql(sql));
	}

}
