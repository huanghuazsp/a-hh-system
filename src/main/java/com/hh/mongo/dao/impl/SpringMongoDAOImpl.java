package com.hh.mongo.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.hh.cache.impl.CacheFactory;
import com.hh.hibernate.util.base.BaseBean;
import com.hh.mongo.dao.inf.IMongoDAOInf;
import com.hh.system.service.impl.BeanFactoryHelper;
import com.hh.system.util.CallBack;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.StaticVar;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamInf;
import com.hh.system.util.pk.PrimaryKey;
import com.hh.usersystem.LoginUserServiceInf;

//@Repository
public class SpringMongoDAOImpl<T extends BaseBean> implements IMongoDAOInf<T> {
	// @Autowired
	private MongoTemplate mongoTemplate;

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	private LoginUserServiceInf loginUserService;

	public LoginUserServiceInf getLoginUserService() {
		if (loginUserService == null) {
			loginUserService = BeanFactoryHelper.getBeanFactory().getBean(
					LoginUserServiceInf.class);
		}
		return loginUserService;
	}

	private void remove(String tableName, Query query) {
		mongoTemplate.remove(query, tableName);
	}

	private List<T> queryList(Class class1, String tableName, Query query) {
		List<T> list = mongoTemplate.find(query, class1, tableName);
		return list;
	}

	public void remove(String tableName, List<String> idList) {
		Query query = new Query(new Criteria(StaticVar.mongoEntityId).in(idList));
		remove(tableName, query);
	}
	

	@Override
	public void remove(Class<T> class1, ParamInf condList) {
		Query query = findQuery(condList);
		remove(class1.getName(), query);
	}

	public void remove(Class<T> class1, List<String> strToList) {
		remove(class1.getName(), strToList);
	}

	public void remove(String tableName, String id) {
		Query query = new Query(new Criteria(StaticVar.mongoEntityId).is(id));
		remove(tableName, query);
	}

	public Map<String, Object> findById(String tableName, String id) {
		return mongoTemplate.findById(id, Map.class, tableName);
	}

	public T findById(Class<T> class1, String id) {
		return (T) mongoTemplate.findById(id, class1, class1.getName());
	}

	public Map<String, Object> save(String tableName,
			Map<String, Object> objectMap) {
		if (Check.isEmpty(tableName)) {
			mongoTemplate.save(objectMap);
		} else {
			mongoTemplate.save(objectMap, tableName);
		}
		return objectMap;
	}
	
	
	
	public void update(String tableName,Map<String, Object> updateMap) {
		if (Check.isNoEmpty(updateMap.get("id"))) {
			Update update = new Update();
			Query query = new Query();
			query.addCriteria(new Criteria("id").is(updateMap.get("id")));
			Set<String> keySet = updateMap.keySet();
			for (String key : keySet) {
				update.set(key, updateMap.get(key));
			}
			mongoTemplate.updateFirst(query, update,tableName);
		}
	}

	public T save(T entity) {
		mongoTemplate.save(entity, entity.getClass().getName());
		return entity;
	}

	public T createEntity(T entity) {
		baseField(entity);
		save(entity);
		return entity;
	}

	private void baseField(T entity) {
		if (Check.isEmpty(entity.getId())) {
			if (Check.isNoEmpty(entity.getBid())) {
				entity.setId(entity.getBid());
			}else {
				entity.setId(PrimaryKey.getUUID());
			}
		}
		if (Check.isEmpty(entity.getCreateTime())) {
			entity.setCreateTime(new Date());
		}
		if (Check.isEmpty(entity.getUpdateTime())) {
			entity.setUpdateTime(new Date());
		}
		String userId = getLoginUserService().findUserId();
		if (Check.isEmpty(entity.getCreateUser())) {
			entity.setCreateUser(userId);
		}
		if (Check.isEmpty(entity.getUpdateUser())) {
			entity.setUpdateUser(userId);
		}
		if (Check.isEmpty(entity.getOrgid())) {
			entity.setOrgid(getLoginUserService().findOrgId());
		}
		if (Check.isEmpty(entity.getDeptid())) {
			entity.setDeptid(getLoginUserService().findDeptId());
		}
		if (Check.isEmpty(entity.getJobid())) {
			entity.setJobid(getLoginUserService().findJobId());
		}
		if (Check.isEmpty(entity.getOrder())) {
			entity.setOrder(PrimaryKey.getTime());
		}
	}

	public T updateEntity(T entity) {
		if (Check.isEmpty(entity.getUpdateTime())) {
			entity.setUpdateTime(new Date());
		}
		if (Check.isEmpty(entity.getUpdateUser())) {
			entity.setUpdateUser(getLoginUserService().findUserId());
		}
		save(entity);
		return entity;
	}

	public void updateEntity(Class<T> class1, ParamInf condList,
			Map<String, Object> updateMap) {
		Update update = new Update();
		Query query = findQuery(condList);
		Set<String> keySet = updateMap.keySet();
		for (String key : keySet) {
			update.set(key, updateMap.get(key));
		}
		mongoTemplate.updateFirst(query, update, class1.getName());
	}

	public int count(Class<T> class1, ParamInf condList) {
		Query query = findQuery(condList);
		return Convert.toInt(mongoTemplate.count(query, class1.getName()));
	}

	public int count(String tableName, ParamInf condList) {
		Query query = findQuery(condList);
		return Convert.toInt(mongoTemplate.count(query, tableName));
	}

	public List<T> queryList(String tableName, ParamInf condList) {
		Query query = findQuery(condList);
		return queryList(Map.class, tableName, query);
	}

	public PagingData<T> queryPage(Class<T> class1, PageRange pageRange,
			ParamInf condList) {
		PagingData<T> pagingData = new PagingData<T>();
		int count = count(class1, condList);
		Query query = findQuery(condList);
		query.limit(pageRange.getLimit());
		query.skip(pageRange.getStart());
		pagingData.setItems(queryList(class1, class1.getName(), query));
		pagingData.setTotal(count);
		return pagingData;
	}

	public PagingData<T> queryPage(String tableName, PageRange pageRange,
			ParamInf condList) {
		PagingData<T> pagingData = new PagingData<T>();
		int count = count(tableName, condList);
		Query query = findQuery(condList);
		query.limit(pageRange.getLimit());
		query.skip(pageRange.getStart());
		pagingData.setItems(queryList(Map.class, tableName, query));
		pagingData.setTotal(count);
		return pagingData;
	}

	public List<T> queryList(Class<T> class1, ParamInf condList) {
		Query query = findQuery(condList);
		List<T> list = queryList(class1, class1.getName(), query);
		return list;
	}

	private Query findQuery(ParamInf condList) {
		Query query = new Query();
		for (Object object : condList) {
			if (object instanceof Criteria) {
				query.addCriteria((Criteria) object);
			} else if (object instanceof Order) {
				query.with(new Sort((Order) object));
			}
		}
		return query;
	}

	@Override
	public T max(Class<T> class1, ParamInf condList) {
		PageRange pageRange = new PageRange(0, 1);
		PagingData<T> pagingData = queryPage(class1, pageRange, condList);
		if (pagingData.getItems().size() > 0) {
			return pagingData.getItems().get(0);
		} else {
			return null;
		}

	}

	@Override
	public T findByIdCache(Class<T> class1, String id) {
		return CacheFactory.getCache(class1, class1.getName()).get(id,
				new CallBack() {
					@Override
					public Object execute(Map<String, Object> params) {
						String tableName = Convert.toString(params
								.get("cacheName"));
						String id = Convert.toString(params.get("key"));
						Class<T> class2 = (Class<T>) params.get("class");
						return  findById(class2, id);
					}
				});
	}

	@Override
	public Map<String, Object> findByIdCache(String tableName, String id) {
		return CacheFactory.getCache(Map.class, tableName).get(id,
				new CallBack() {
					@Override
					public Object execute(Map<String, Object> params) {
						String tableName = Convert.toString(params
								.get("cacheName"));
						String id = Convert.toString(params.get("key"));
						return  findById(tableName, id);
					}
				});
	}

	@Override
	public void removeCache(Class<T> class1, List<String> strToList) {
		remove(class1, strToList);
		 CacheFactory.getCache(class1).remove(strToList);
	}

	@Override
	public void removeCache(String tableName, List<String> idList) {
		remove(tableName, idList);
		 CacheFactory.getCache(tableName).remove(idList);
	}

	@Override
	public void removeCache(String tableName, String id) {
		remove(tableName, id);
		CacheFactory.getCache(tableName).remove(id);
	}

	@Override
	public T updateEntityCache(T entity) {
		T t = updateEntity(entity);
		CacheFactory.getCache(entity.getClass()).remove(entity.getId());
		return t;
	}

	@Override
	public void updateEntityCache(Class<T> class1, ParamInf condList,
			Map<String, Object> updateMap) {
		updateEntity(class1, condList, updateMap);
		CacheFactory.getCache(class1).clear();
	}

	@Override
	public void remove(Class<T> class1, String id) {
		List<String> idStr = new ArrayList<String>();
		idStr.add(id);
		remove(class1, idStr);
	}

	@Override
	public void removeCache(Class<T> class1, String id) {
		List<String> idStr = new ArrayList<String>();
		idStr.add(id);
		removeCache(class1, idStr);
	}


}
