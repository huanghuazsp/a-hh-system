package com.hh.hibernate.dao.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.transform.Transformers;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.hibernate.util.base.BaseBean;
import com.hh.system.service.impl.BeanFactoryHelper;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.StaticVar;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.system.util.pk.PrimaryKey;
import com.hh.usersystem.LoginUserServiceInf;

//@Repository
public class HibernateDAOImpl<T extends BaseBean> implements IHibernateDAO<T> {
	// @Autowired
	private SessionFactory sessionFactory;
	// @Autowired
	private LoginUserServiceInf loginUserService;

	public LoginUserServiceInf getLoginUserService() {
		if (loginUserService == null) {
			loginUserService = BeanFactoryHelper.getBeanFactory().getBean(
					LoginUserServiceInf.class);
		}
		return loginUserService;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void clear() {
		this.sessionFactory.getCurrentSession().clear();
	}

	public void flush() {
		this.sessionFactory.getCurrentSession().flush();
	}

	/**
	 * 保存一个实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 主键
	 */
	public Serializable createEntity(T entity) {
		baseField(entity);
		return this.sessionFactory.getCurrentSession().save(entity);
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
		if (Check.isEmpty(entity.getCreateUserName())) {
			entity.setCreateUserName( getLoginUserService().findUserName());
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
		if (Check.isEmpty(entity.getOrder()) || entity.getOrder()==0) {
			entity.setOrder(PrimaryKey.getTime());
		}
	}
	/**
	 * 保存一个实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void saveOrUpdateEntity(T entity) {
		T obj = findEntityByPK(entity.getClass(), entity.getId());
		if (obj==null) {
			createEntity(entity);
		}else{
			mergeEntity(entity);
		}
	}

	/**
	 * 修改一个实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void updateEntity(T entity) {

		if (Check.isEmpty(entity.getUpdateTime())) {
			entity.setUpdateTime(new Date());
		}

		if (Check.isEmpty(entity.getUpdateUser())) {
			entity.setUpdateUser(getLoginUserService().findUserId());
		}
		this.sessionFactory.getCurrentSession().update(entity);
	}

	public void mergeEntity(T entity) {
		if (Check.isEmpty(entity.getUpdateTime())) {
			entity.setUpdateTime(new Date());
		}

		if (Check.isEmpty(entity.getUpdateUser())) {
			entity.setUpdateUser(getLoginUserService().findUserId());
		}
		this.sessionFactory.getCurrentSession().merge(entity);
	}

	public int updateEntity(String hql) {
		return sessionFactory.getCurrentSession().createQuery(hql)
				.executeUpdate();
	}

	/**
	 * 修改一个实体对象
	 * 
	 * @param hql
	 * @param entity
	 */
	public int updateEntity(String hql, T entity) {
		return sessionFactory.getCurrentSession().createQuery(hql)
				.setProperties(entity).executeUpdate();
	}

	public int updateEntity(String hql,
			Map<? extends String, ? extends Object> paramsMap) {
		return sessionFactory.getCurrentSession().createQuery(hql)
				.setProperties(paramsMap).executeUpdate();
	}

	public int updateEntity(String hql, Object[] objects) {

		Query query = this.getSession().createQuery(hql);
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				query.setParameter(i, objects[i]);
			}
		}
		return query.executeUpdate();
	}

	public int updateProperty(Class<T> class1, String id, String key,
			Object value) {
		Query query = this.getSession().createQuery(
				"update " + class1.getName() + " set " + key
						+ "=? where id  = ?");
		query.setParameter(0, value);
		query.setParameter(1, id);
		return query.executeUpdate();
	}

	/**
	 * 删除一个实体
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void deleteEntity(T entity) {
		sessionFactory.getCurrentSession().delete(entity);
	}

	public int deleteEntity(String hql, List<String> list) {
		return sessionFactory.getCurrentSession().createQuery(hql)
				.setParameterList("list", list).executeUpdate();
	}

	public int deleteEntity(String hql, Map<? extends String, ? extends Object> paramsMap) {
		return sessionFactory.getCurrentSession().createQuery(hql)
				.setProperties(paramsMap).executeUpdate();
	}

	/**
	 * 删除多个实体
	 * 
	 * @param class1
	 *            类型
	 * @param property
	 *            属性名
	 * @param param
	 *            参数
	 * @return
	 */

	public int deleteEntity(Class<T> class1, String property, Object param) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"delete " + class1.getName() + " o where o." + property
								+ " = :" + property)
				.setParameter(property, param).executeUpdate();
	}

	public int deleteEntity(Class<T> class1, String property, List<String> list) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"delete " + class1.getName() + " o where o." + property
								+ " IN (:" + property + ")")
				.setParameterList(property, list).executeUpdate();
	}

	/**
	 * 根据主键返回一个实体类
	 * 
	 * @param entityClass
	 *            类类型（User.Class）
	 * @param id
	 *            主键
	 * @return 实体类
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public T findEntityByPK(Class entityClass, Serializable id) {
		return findEntity(entityClass, Restrictions.eq(StaticVar.entityId, id));
	}

	/**
	 * 根据主键返回一个实体类(注意：不会马上发出请求，
	 * 等真正使用到对象的时候才会发送sql语句，如果session关闭了，还没发送请求，而又去使用对象会有异常)
	 * 
	 * @param entityClass
	 *            类类型（User.Class）
	 * @param id
	 *            主键
	 * @return 实体类
	 */
	@SuppressWarnings("unchecked")
	public T loadEntityByPK(@SuppressWarnings("rawtypes") Class entityClass,
			Serializable id) {
		return (T) this.sessionFactory.getCurrentSession()
				.load(entityClass, id);
	}

	/**
	 * 查询结果集
	 * 
	 * @param hql
	 *            hql语句
	 * @param entity
	 *            参数对象
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryList(String hql, T entity) {
		Query query = this.getSession().createQuery(hql);
		if (entity.getClass().isAnnotationPresent(Cache.class)) {
			query.setCacheable(true);
		}
		return query.setProperties(entity).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryList(String hql, Map<String, Object> entity) {
		Query query = this.getSession().createQuery(hql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.setProperties(entity).list();
	}

	/**
	 * 查询结果集 例子：select o from HhXtYhJs o where o.yhId = ?
	 * 
	 * @param hql
	 *            hql语句
	 * @param Object
	 *            [] objects 参数对象
	 * @return 结果集
	 */

	@SuppressWarnings("unchecked")
	public List<T> queryList(String hql, Object[] objects) {
		return this.queryList(hql, objects, false);
	}

	@SuppressWarnings("unchecked")
	public List<T> queryList(String hql, Object[] objects, boolean cache) {
		Query query = this.getSession().createQuery(hql);
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				query.setParameter(i, objects[i]);
			}
		}
		if (cache) {
			query.setCacheable(true);
		}
		return query.list();
	}

	/**
	 * 查询结果集
	 * 
	 * @param class1
	 *            entity 类型
	 * @param list
	 *            参数list
	 */

	@SuppressWarnings("unchecked")
	public List<T> queryList(Class<T> class1, ParamInf list) {
		Criteria criteria = this.getSession().createCriteria(class1);
		for (Object object : list) {
			if (object instanceof Criterion) {
				criteria.add((Criterion) object);
			} else if (object instanceof Order) {
				criteria.addOrder((Order) object);
			}
		}
		order(class1, criteria);
		if (class1.isAnnotationPresent(Cache.class)) {
			criteria.setCacheable(true);
		}
		return criteria.list();
	}

	/**
	 * 查询所有数据
	 * 
	 * @param class1
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public List<T> queryList(Class<T> class1) {
		Criteria criteria = this.getSession().createCriteria(class1);
		order(class1, criteria);
		if (class1.isAnnotationPresent(Cache.class)) {
			criteria.setCacheable(true);
		}
		return criteria.list();
	}

	public List<T> queryList(String hql) {
		return this.getSession().createQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	public List<T> queryList(Class<T> class1, Criterion criterion) {
		Criteria criteria = this.getSession().createCriteria(class1)
				.add(criterion);
		order(class1, criteria);
		if (class1.isAnnotationPresent(Cache.class)) {
			criteria.setCacheable(true);
		}
		return criteria.list();
	}

	/**
	 * 
	 * @param entity
	 *            entity 实现PagingInf接口 的 getPagingDataHQL 返回一个 字符串 例子：select o
	 *            from HhXtYhJs o where o.yhId = ：yhId
	 * @param pageRange
	 *            分页范围
	 * @return map的键 是 items（结果集） 和 total（总条数）
	 */

	@SuppressWarnings("unchecked")
	public PagingData<T> queryPagingData(String whereHQL, T entity,
			PageRange pageRange) {
		PagingData<T> pageData = new PagingData<T>();
		Query query = this.getSession().createQuery(
				"select o from " + entity.getClass().getName() + " o ");
		query.setProperties(entity);
		query.setFirstResult(pageRange.getStart());
		query.setMaxResults(pageRange.getLimit());
		if (entity.getClass().isAnnotationPresent(Cache.class)) {
			query.setCacheable(true);
		}
		List<T> items = query.list();
		pageData.setItems(items);
		query = this.getSession().createQuery(
				"select count(o) from " + entity.getClass().getName() + " o ");
		// 设置HQL语句参数
		query.setProperties(entity);
		if (entity.getClass().isAnnotationPresent(Cache.class)) {
			query.setCacheable(true);
		}
		pageData.setTotal(Convert.toInt(query.uniqueResult()));
		return pageData;
	}

	@SuppressWarnings("unchecked")
	public PagingData<Map<String, Object>> queryPagingDataByHql(String hql,String hqlCount,  Map<String, Object> paramMap,
			PageRange pageRange) {
		PagingData<Map<String, Object>> pageData = new PagingData<Map<String, Object>>();
		Query query = this.getSession().createQuery(hql);
		query.setProperties(paramMap);
		query.setFirstResult(pageRange.getStart());
		query.setMaxResults(pageRange.getLimit());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> items = query.list();
		pageData.setItems(items);
		query = this.getSession().createQuery(hqlCount);
		// 设置HQL语句参数
		query.setProperties(paramMap);
		pageData.setTotal(Convert.toInt(query.uniqueResult()));
		return pageData;
	}
	
	/**
	 * 
	 * @param class1
	 *            类型
	 * @param paramList
	 *            条件
	 * @param pageRange
	 *            分页参数
	 * @return map的键 是 items（结果集） 和 total（总条数）
	 */

	public PagingData<T> queryPagingData(Class<T> class1, ParamInf list,
			PageRange pageRange) {
		return this.queryPagingData(class1, list, pageRange,
				new ArrayList<String>());
	}

	public PagingData<T> queryPagingData(Class<T> class1, PageRange pageRange) {
		return this.queryPagingData(class1, ParamFactory.getParamHb(),
				pageRange);
	}

	public Map<String, Object> queryPagingData(String sql, String countSql,
			PageRange pageRange) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		SQLQuery countSqlQuery = this.getSession().createSQLQuery(countSql);

		sqlQuery.setFirstResult(pageRange.getStart());
		sqlQuery.setMaxResults(pageRange.getLimit());
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		resultMap.put("items", sqlQuery.list());
		resultMap.put("total", countSqlQuery.uniqueResult());
		return resultMap;
	}

	public Map<String, Object> queryPagingData(String sql, String countSql,
			Map<String, Object> paramMap, PageRange pageRange) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		SQLQuery countSqlQuery = this.getSession().createSQLQuery(countSql);

		sqlQuery.setProperties(paramMap);
		countSqlQuery.setProperties(paramMap);
		sqlQuery.setFirstResult(pageRange.getStart());
		sqlQuery.setMaxResults(pageRange.getLimit());
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		resultMap.put("items", sqlQuery.list());
		resultMap.put("total", countSqlQuery.uniqueResult());
		return resultMap;
	}

	public int findCount(String hql) {
		Query query = this.getSession().createQuery(hql);
		return Integer.valueOf(query.uniqueResult().toString());
	}

	public int findCount(String hql, T entity) {
		Query query = this.getSession().createQuery(hql);
		if (entity.getClass().isAnnotationPresent(Cache.class)) {
			query.setCacheable(true);
		}
		return Integer.valueOf(query.setProperties(entity).uniqueResult()
				.toString());
	}
	
	public int findCount(String hql,Map<String, Object> paramsMap) {
		Query query = this.getSession().createQuery(hql);
		return Integer.valueOf(query.setProperties(paramsMap).uniqueResult()
				.toString());
	}

	public boolean findWhetherData(String hql, T entity) {
		Query query = this.getSession().createQuery(hql);
		if (entity.getClass().isAnnotationPresent(Cache.class)) {
			query.setCacheable(true);
		}
		return ((Long) query.setProperties(entity).uniqueResult()) == 0 ? false
				: true;
	}

	public boolean isExist(String hql, Object[] objects) {
		Query query = this.getSession().createQuery(hql);
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				query.setParameter(i, objects[i]);
			}
		}
		return !query.uniqueResult().equals(0l);
	}

	public T findEntity(Class<T> class1, ParamInf list) {
		Criteria criteria = this.getSession().createCriteria(class1);
		for (Object object : list) {
			if (object instanceof Criterion) {
				criteria.add((Criterion) object);
			} else if (object instanceof Order) {
				criteria.addOrder((Order) object);
			}
		}
		if (class1.isAnnotationPresent(Cache.class)) {
			criteria.setCacheable(true);
		}
		return (T) criteria.uniqueResult();
	}

	public T findEntity(Class<T> class1, Criterion criterion) {
		Criteria criteria = this.getSession().createCriteria(class1);
		criteria.add(criterion);
		if (class1.isAnnotationPresent(Cache.class)) {
			criteria.setCacheable(true);
		}
		return (T) criteria.uniqueResult();
	}

	public int findCountBySql(String sql, Object[] objects) {
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		for (int i = 0; i < objects.length; i++) {
			sqlQuery.setParameter(i, objects[i]);
		}
		return Integer.valueOf(sqlQuery.uniqueResult().toString());
	}

	public Object findObjectBySql(String sql, Object[] objects) {
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		for (int i = 0; i < objects.length; i++) {
			sqlQuery.setParameter(i, objects[i]);
		}
		return sqlQuery.uniqueResult();
	}

	public Object findObjectBySql(String sql) {
		return this.findObjectBySql(sql, new Object[] {});
	}

	public int executeSql(String sql, Object[] objects) {
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		for (int i = 0; i < objects.length; i++) {
			sqlQuery.setParameter(i, objects[i]);
		}
		return sqlQuery.executeUpdate();
	}

	public void executeSql(String sql) {
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void executeSql(String sql, Map<String, Object> parameterMap) {
		this.getSession().createSQLQuery(sql).setProperties(parameterMap)
				.executeUpdate();
	}

	public Map<String, Object> findEntity(String tableName, String id) {
		return (Map<String, Object>) this.getSession()
				.createSQLQuery("select * from " + tableName + " where id=?")
				.setParameter(0, id)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.uniqueResult();
	}


	public List<Map<String, Object>> queryListBySql(String sql,
			Map<String, String> paramsMap) {
		return (List<Map<String, Object>>) this.getSession()
				.createSQLQuery(sql).setProperties(paramsMap)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	public List<Map<String, Object>> queryListBySql(String sql) {
		return this.queryListBySql(sql, new HashMap<String, String>());
	}

	public boolean isExist(T entity, String field) {
		return this.findWhetherData("select count(o) from "
				+ entity.getClass().getName() + " o " + "where o." + field
				+ "=:" + field + " and (o.id!=:id or :id is null)", entity);
	}

	public Object findPropertys(T entity, String[] returnPropertys,
			String[] wherePropertys) {
		StringBuffer hql = new StringBuffer("select ");
		for (int i = 0; i < returnPropertys.length; i++) {
			String string = returnPropertys[i];
			hql.append((i == 0 ? "" : " , ") + "o." + string);
		}
		hql.append(" from " + entity.getClass().getName() + " o where ");
		for (int i = 0; i < wherePropertys.length; i++) {
			String string = wherePropertys[i];
			hql.append((i == 0 ? "" : " and ") + " o." + string + "=:" + string);
		}
		Query query = this.getSession().createQuery(hql.toString());
		query.setProperties(entity);
		return query.uniqueResult();
	}

	public PagingData<T> queryPagingData(Class<T> class1,
			ParamInf hqlParamList, PageRange pageRange,
			List<String> selectProperList) {

		PagingData<T> pageData = new PagingData<T>();
		Criteria criteria = this.getSession().createCriteria(class1);
		Criteria criteriaCount = this.getSession().createCriteria(class1);
		for (Object object : hqlParamList) {
			if (object instanceof Criterion) {
				criteria.add((Criterion) object);
				criteriaCount.add((Criterion) object);
			} else if (object instanceof Order) {
				criteria.addOrder((Order) object);
			}
		}
		criteria.setFirstResult(pageRange.getStart());
		criteria.setMaxResults(pageRange.getLimit());
		if (Check.isNoEmpty(selectProperList)) {
			ProjectionList projectionList = Projections.projectionList();
			for (String prperties : selectProperList) {
				projectionList.add(Projections.property(prperties)
						.as(prperties));
			}
			criteria.setProjection(projectionList);
			criteria.setResultTransformer(Transformers.aliasToBean(class1));
		}
		order(class1, criteria);
		if (class1.isAnnotationPresent(Cache.class)) {
			criteria.setCacheable(true);
			criteriaCount.setCacheable(true);
		}
		pageData.setItems(criteria.list());
		pageData.setTotal(Convert.toInt(criteriaCount.setProjection(
				Projections.rowCount()).uniqueResult()));
		return pageData;
	}

	public PagingData<T> queryPagingData(Class<T> class1,
			ParamInf hqlParamList, PageRange pageRange, String[] selectPropers) {

		List<String> selectProperList = new ArrayList<String>();
		for (String string : selectPropers) {
			selectProperList.add(string);
		}
		return this.queryPagingData(class1, hqlParamList, pageRange,
				selectProperList);
	}

	public List<T> queryList(Class<T> class1, String propertiesName,
			List<String> param) {
		return this.queryList(class1, Restrictions.in(propertiesName, param));
	}

	public List<T> queryList(Class<T> class1, String propertiesName,
			String param) {
		return this.queryList(class1, Restrictions.eq(propertiesName, param));
	}

	private static Map<Object, List<Order>> orderMap = new HashMap<Object, List<Order>>();

	private void order(Class<T> class1, Criteria criteria) {
		if (class1.isAnnotationPresent(com.hh.hibernate.dao.inf.Order.class)) {
			List<Order> orderList = orderMap.get(class1.getName());
			if (orderList == null) {
				orderList = new ArrayList<Order>();
				Annotation[] a = class1.getAnnotations();
				for (Annotation annotation : a) {
					if (annotation instanceof com.hh.hibernate.dao.inf.Order) {
						com.hh.hibernate.dao.inf.Order order = (com.hh.hibernate.dao.inf.Order) annotation;
						String orderstr = order.fields();
						String sortstr = order.sorts();
						String[] orders = orderstr.split(",");
						String[] sorts = sortstr.split(",");
						for (int i = 0; i < orders.length; i++) {
							if (sorts.length - 1 < i) {
								orderList.add(Order.desc(orders[i]));
							} else if ("desc".equals(sorts[i])) {
								orderList.add(Order.desc(orders[i]));
							} else if ("asc".equals(sorts[i])) {
								orderList.add(Order.asc(orders[i]));
							}
						}
						orderMap.put(class1.getName(), orderList);
						break;
					}
				}
			}
			for (Order order : orderList) {
				criteria.addOrder(order);
			}
		}
	}

	public void updateEntity(Class<T> class1, String whereproperty,
			Object whereValue, String key, Object value) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put(key, value);
		paramsMap.put(whereproperty, whereValue);
		if (whereValue instanceof List) {
			this.updateEntity("update " + class1.getName() + " set " + key + " = :"
					+ key + " where  " + whereproperty + " in :" + whereproperty, paramsMap);
		}else{
			this.updateEntity("update " + class1.getName() + " set " + key + " = :"
					+ key + " where  " + whereproperty + " = :" + whereproperty, paramsMap);
		}
		
	}

	public int findCount(Class<T> class1, ParamInf paramList) {
		Criteria criteria = this.getSession().createCriteria(class1);
		for (Object object : paramList) {
			if (object instanceof Criterion) {
				criteria.add((Criterion) object);
			}
		}
		if (class1.isAnnotationPresent(Cache.class)) {
			criteria.setCacheable(true);
		}
		criteria.setProjection(Projections.rowCount());
		return Integer.valueOf(criteria.uniqueResult().toString());
	}

	public int findCount(Class<T> class1, Criterion criterion) {
		Criteria criteria = this.getSession().createCriteria(class1);
		criteria.add(criterion);
		if (class1.isAnnotationPresent(Cache.class)) {
			criteria.setCacheable(true);
		}
		criteria.setProjection(Projections.rowCount());
		return Integer.valueOf(criteria.uniqueResult().toString());
	}

	public List<T> queryTreeList(Class<T> class1, ParamInf list) {
		List<T> objectList = this.queryList(class1, list);
		for (T object : objectList) {
			addChildren(class1, list, object);
		}
		return objectList;
	}

	private void addChildren(Class<T> class1, ParamInf list, T object) {
		ParamInf objeList = ParamFactory.getParamHb();
		for (Object object1 : list) {
			if (object1 instanceof SimpleExpression) {
				if ("node".equals(((SimpleExpression) object1)
						.getPropertyName()) || "id".equals(((SimpleExpression) object1)
								.getPropertyName())) {
					continue;
				}
			}
			objeList.add(object1);
		}
		if (object.getLeaf() == 0 && object.getExpanded() == 1) {
			objeList.add(Restrictions.eq("node", object.getId()));
			List<T> children = this.queryTreeList(class1, objeList);
			for (T t : children) {
				addChildren(class1, list, t);
			}
			object.setChildren(children);
			if (children.size()==0) {
				object.setLeaf(1);
			}
		}else {
			objeList.add(Restrictions.eq("node", object.getId()));
			int count = this.findCount(class1, objeList);
			if (count==0) {
				object.setLeaf(1);
			}
		}
	}

	public List<T> queryAllTreeList(Class<T> class1, ParamInf list) {
		List<T> objectList = this.queryList(class1, list);
		for (T object : objectList) {
			addAllChildren(class1, list, object);
		}
		return objectList;
	}

	private void addAllChildren(Class<T> class1, ParamInf list, T object) {
		ParamInf objeList = ParamFactory.getParamHb();
		for (Object object1 : list) {
			if (object1 instanceof SimpleExpression) {
				if ("node".equals(((SimpleExpression) object1)
						.getPropertyName())) {
					continue;
				}
			}
			objeList.add(object1);
		}

		// 已经是子节点了有必要再查询吗
		// if (object.getLeaf()!=1) {
		//
		// }
		// if (object.getLeaf() == 0 && object.getExpanded() == 1) {
		objeList.add(Restrictions.eq("node", object.getId()));
		List<T> children = this.queryTreeList(class1, objeList);
		for (T t : children) {
			addAllChildren(class1, list, t);
		}
		object.setChildren(children);
		if (children.size() == 0) {
			object.setLeaf(1);
		}
		// }
	}

	@Override
	public T max(Class<T> class1, ParamInf condList) {
		Criteria criteria = this.getSession().createCriteria(class1);
		for (Object object : condList) {
			if (object instanceof Criterion) {
				criteria.add((Criterion) object);
			} else if (object instanceof Order) {
				criteria.addOrder((Order) object);
			}
		}
		order(class1, criteria);
		if (class1.isAnnotationPresent(Cache.class)) {
			criteria.setCacheable(true);
		}
		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		List<T> list = criteria.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<T> queryList(Class<T> genericType, ParamInf paramInf, PageRange pageRange) {
		Criteria criteria = this.getSession().createCriteria(genericType);
		for (Object object : paramInf) {
			if (object instanceof Criterion) {
				criteria.add((Criterion) object);
			} else if (object instanceof Order) {
				criteria.addOrder((Order) object);
			}
		}
		criteria.setFirstResult(pageRange.getStart());
		criteria.setMaxResults(pageRange.getLimit());
		order(genericType, criteria);
		if (genericType.isAnnotationPresent(Cache.class)) {
			criteria.setCacheable(true);
		}
		return criteria.list();
	}

}
