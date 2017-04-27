package com.hh.database.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jndi.JndiObjectFactoryBean;

import com.hh.database.bean.DatabaseConnect;
import com.hh.database.service.impl.DatabaseConnectService;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.impl.BeanFactoryHelper;
import com.hh.system.util.Convert;
import com.hh.system.util.ExceptionUtil;
import com.hh.system.util.MessageException;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.db.ConnUtil;
import com.hh.system.util.model.ReturnModel;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.p6spy.engine.spy.P6DataSource;

@SuppressWarnings("serial")
public class ActionConnect extends BaseServiceAction<DatabaseConnect> {
	@Autowired
	private DatabaseConnectService databaseconnectService;
	
	private String sql;

	public BaseService<DatabaseConnect> getService() {
		return databaseconnectService;
	}

	JndiObjectFactoryBean JndiObjectFactoryBean = BeanFactoryHelper.getBean(JndiObjectFactoryBean.class);

	@Override
	public Object queryTreeList() {
		List<DatabaseConnect> databaseConnectList = new ArrayList<DatabaseConnect>();
		if ("root".equals(object.getNode())) {
			databaseConnectList = databaseconnectService.queryAllList();
			if (JndiObjectFactoryBean.getObject() instanceof ComboPooledDataSource) {
				ComboPooledDataSource comboPooledDataSource = (ComboPooledDataSource) JndiObjectFactoryBean.getObject();
				DatabaseConnect databaseConnect = new DatabaseConnect();
				databaseConnect.setText("本系统数据源");
				databaseConnect.setId("system");
				databaseConnect.setType(comboPooledDataSource.getDriverClass());
				databaseConnect.setUser(comboPooledDataSource.getUser());
				databaseConnect.setPassword(comboPooledDataSource.getPassword());
				databaseConnect.setConnect(comboPooledDataSource.getJdbcUrl());
				databaseConnectList.add(0, databaseConnect);
			}
			for (DatabaseConnect databaseConnect : databaseConnectList) {
				databaseConnect.setIconSkin("conn");
				if (!"system".equals(databaseConnect.getId())) {
					databaseConnect.setParams(databaseConnect.getType() + "@@@" + databaseConnect.getConnect() + "@@@"
							+ databaseConnect.getUser() + "@@@" + databaseConnect.getPassword() + "@@@"
							+ databaseConnect.getId());
				}
			}
		} else if ("system".equals(object.getNode())) {
			ComboPooledDataSource comboPooledDataSource = (ComboPooledDataSource) JndiObjectFactoryBean.getObject();
			Connection connection = ConnUtil.getConn(comboPooledDataSource.getDriverClass(),
					comboPooledDataSource.getJdbcUrl(), comboPooledDataSource.getUser(),
					comboPooledDataSource.getPassword());
			List<Map<String, Object>> tableMapList = ConnUtil.queryTableNameList(connection);
			for (Map<String, Object> map : tableMapList) {
				DatabaseConnect databaseConnect = new DatabaseConnect();
				databaseConnect.setText(Convert.toString(map.get("text")));
				databaseConnect.setParams(comboPooledDataSource.getDriverClass() + "@@@"
						+ comboPooledDataSource.getJdbcUrl() + "@@@" + comboPooledDataSource.getUser() + "@@@"
						+ comboPooledDataSource.getPassword() + "@@@" + Convert.toString(map.get("text")));
				databaseConnect.setNode("system");
				databaseConnect.setIconSkin("table");
				databaseConnectList.add(0, databaseConnect);
			}
		} else if ("conn".equals(object.getIconSkin())) {
			String idArr[] = object.getParams().split("@@@");
			Connection connection = ConnUtil.getConn(idArr[0], idArr[1], idArr[2], idArr[3]);
			List<Map<String, Object>> tableMapList = ConnUtil.queryTableNameList(connection);
			for (Map<String, Object> map : tableMapList) {
				DatabaseConnect databaseConnect = new DatabaseConnect();
				databaseConnect.setText(Convert.toString(map.get("text")));
				databaseConnect.setParams(idArr[0] + "@@@" + idArr[1] + "@@@" + idArr[2] + "@@@" + idArr[3] + "@@@"
						+ Convert.toString(map.get("text")));
				databaseConnect.setNode("system");
				databaseConnect.setIconSkin("table");
				databaseConnectList.add(0, databaseConnect);
			}
		} else if ("table".equals(object.getIconSkin())) {
			String idArr[] = object.getParams().split("@@@");
			Connection connection = ConnUtil.getConn(idArr[0], idArr[1], idArr[2], idArr[3]);
			List<Map<String, Object>> fieldMapList = ConnUtil.queryFieldMapList(idArr[4], connection);
			for (Map<String, Object> map2 : fieldMapList) {
				DatabaseConnect databaseConnect = new DatabaseConnect();
				databaseConnect
						.setText(Convert.toString(map2.get("name")) + "[" + Convert.toString(map2.get("type")) + "]");
				databaseConnect.setParams(object.getNode() + "@@@" + Convert.toString(map2.get("name")));
				databaseConnect.setNode(object.getNode());
				databaseConnect.setIconSkin("field");
				if ("1".equals(map2.get("primary"))) {
					databaseConnect.setIconSkin("primary");
				}
				databaseConnect.setLeaf(1);
				databaseConnectList.add(0, databaseConnect);
			}
		}
		return databaseConnectList;
	}

	public Object testConn() {
		try {
			Connection conn = ConnUtil.getConn(object.getType(), object.getConnect(), object.getUser(),
					object.getPassword());

			if (conn != null) {
				ConnUtil.close(conn);
//				DatabaseConnect object = getService().saveTree(this.object);
				return new ReturnModel("连接成功");
			} else {
				return new ReturnModel(ReturnModel.errorType, "连接失败");
			}
		} catch (MessageException e) {
			return e;
		}

	}
	
	
	public Object execConn() {
		Connection conn = ConnUtil.getConn(object.getType(), object.getConnect(), object.getUser(),
				object.getPassword());
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			int i= ConnUtil.execSql(conn,sql);
			returnMap.put("count", i);
		} catch (Exception e) {
			returnMap.put("msg", "异常：" + e.getClass().getName() + "：" + e.getMessage() + "<br/>" + ExceptionUtil.getMessage(e));
			returnMap.put("returnType", "error");
			e.printStackTrace();
		}
		return returnMap;
	}
	
	public Object queryConn() {
		Connection conn = ConnUtil.getConn(object.getType(), object.getConnect(), object.getUser(),
				object.getPassword());
		return ConnUtil.queryMapListBySql(conn, sql, new ArrayList<Object>());
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
