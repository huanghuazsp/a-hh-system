package com.hh.mongo.dao.inf;

import java.util.List;
import java.util.Map;

import com.hh.hibernate.util.base.BaseBean;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamInf;

public interface IDataBaseDAOInf<T extends BaseBean>{
	
	
	PagingData<T> queryPage(String tableName, PageRange pageRange,
			ParamInf condList);

	int count(Class<T> class1, ParamInf condList);

	T createEntity(T entity);

	List<T> queryList(Class<T> class1, ParamInf condList);

	PagingData<T> queryPage(Class<T> class1, PageRange pageRange,
			ParamInf condList);

	T max(Class<T> class1, ParamInf condList);
	
//	T save(T entity);

	Map<String, Object> save(String tableName, Map<String, Object> objectMap);
	
	void update(String tableName, Map<String, Object> objectMap);
	

	T findById(Class<T> class1, String id);

	Map<String, Object> findById(String tableName, String id);

	void remove(Class<T> class1, List<String> idList);
	
	void remove(Class<T> class1,ParamInf condList);
	
	void remove(Class<T> class1, String id);

	void remove(String tableName, List<String> idList);

	void remove(String tableName, String id);

	T updateEntity(T entity);

	void updateEntity(Class<T> class1, ParamInf condList,
			Map<String, Object> updateMap);
	
	
	T findByIdCache(Class<T> class1, String id);

	Map<String, Object> findByIdCache(String tableName, String id);

	void removeCache(Class<T> class1, List<String> idList);
	void removeCache(Class<T> class1, String id);

	void removeCache(String tableName, List<String> idList);

	void removeCache(String tableName, String id);

	T updateEntityCache(T entity);

	void updateEntityCache(Class<T> class1, ParamInf condList,
			Map<String, Object> updateMap);
}
