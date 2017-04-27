package com.hh.system.util.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.hh.hibernate.util.base.BaseBean;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.date.DateFormat;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.model.ReturnModel;

@SuppressWarnings("serial")
public class BaseAction<T extends BaseBean> extends BaseServletAction {
	/**
	 * 分页模版
	 */
	public PageRange getPageRange() {
		PageRange pageRange = new PageRange( this.getStart(),
				this.getLimit());
		return pageRange;
	}

	// 第几页
	private int page;
	// 第几条数据开始
	private int start;
	// 每页显示多少条
	private int limit;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	private String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 参数Map
	 */
	protected Map<String, String> paramsMap = new HashMap<String, String>();

	public Map<String, String> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, String> paramsMap) {
		this.paramsMap = paramsMap;
	}

	protected Map<String, Object> convertMap(Map<String, Object> object) {
		if (object == null) {
			return new HashMap<String, Object>();
		}
		Set<String> keyset = object.keySet();
		for (String key : keyset) {
			Object[] objects = (Object[]) object.get(key);
			if (objects.length > 0) {
				Object param = objects[0];
				if ("true".equals(param) || "false".equals(param)) {
					object.put(key, Convert.toBoolean(param));
				} else {
					object.put(key, param);
				}
			}
		}
		return object;
	}

	public Class getGenericType(int index) {
		Type genType = getClass().getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			throw new RuntimeException("Index outof bounds");
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

	public <T> T findParam(String key, Class<T> requiredType) {
		String s = findParam(key);
		Object val = null;
		if (requiredType == String.class) {
			return (T) s;
		} else if (requiredType != String.class) {
			if (requiredType == Integer.class) {
				if (Check.isEmpty(val)) {
					val=0;
				}else {
					val = Integer.valueOf(Integer.parseInt(s));
				}
			} else if (requiredType == Long.class) {
				if (Check.isEmpty(val)) {
					val=0l;
				}else {
					val = Long.valueOf(Long.parseLong(s));
				}
			} else if (requiredType == Float.class) {
				if (Check.isEmpty(val)) {
					val=0;
				}else {
					val = Float.valueOf(Float.parseFloat(s));
				}
			} else if (requiredType == Double.class) {
				if (Check.isEmpty(val)) {
					val=0;
				}else {
					val = Double.valueOf(Double.parseDouble(s));
				}
			} else if (requiredType == Boolean.class) {
				val = Convert.toBoolean(s);
			} else if (requiredType == Date.class) {
				val = DateFormat.strToDate(s);
			}
		}
		return (T) val;
	}

	public String findParam(String key) {
		String value = Convert.toString(request.getParameter(key));
		return value;
	}

}
