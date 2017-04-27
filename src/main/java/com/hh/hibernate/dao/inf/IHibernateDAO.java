package com.hh.hibernate.dao.inf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;

import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamInf;

public interface IHibernateDAO<T> {
	public SessionFactory getSessionFactory();

	public Session getSession();

	/**
	 * 保存一个实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 主键
	 */
	public Serializable createEntity(T entity);

	/**
	 * 保存一个实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void saveOrUpdateEntity(T entity);

	public int updateEntity(String hql,
			Map<? extends String, ? extends Object> paramsMap);

	public int updateEntity(String hql, Object[] objects);

	public void mergeEntity(T entity);

	/**
	 * 修改一个实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void updateEntity(T entity);

	public int updateEntity(String hql);

	/**
	 * 修改一个实体对象
	 * 
	 * @param hql
	 * @param entity
	 */
	public int updateEntity(String hql, T entity);

	/**
	 * 删除一个实体
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void deleteEntity(T entity);

	/**
	 * 删除多个实体
	 * 
	 * @param list
	 *            实体ID集合
	 * 
	 * @return 操作了几条数据
	 */
	public int deleteEntity(String hql, List<String> list);

	public int deleteEntity(String hql, Map<? extends String, ? extends Object> paramsMap);

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
	public int deleteEntity(Class<T> class1, String property, Object param);

	/**
	 * 根据list删除数据
	 * 
	 * @param class1
	 * @param list
	 * @return
	 */
	public int deleteEntity(Class<T> class1, String property, List<String> list);

	public int updateProperty(Class<T> class1, String id, String key,
			Object value);

	/**
	 * 根据主键返回一个实体类
	 * 
	 * @param entityClass
	 *            类类型（User.Class）
	 * @param id
	 *            主键
	 * @return 实体类
	 */
	public T findEntityByPK(Class entityClass, Serializable id);

	public T findEntity(Class<T> class1, ParamInf condList);

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
	public T loadEntityByPK(Class entityClass, Serializable id);

	/**
	 * 清缓存
	 */
	public void clear();

	/**
	 * 强制让缓存的数据和数据库做同步
	 */
	public void flush();

	/**
	 * 查询结果集 例子：select o from HhXtYh o where o.vdlzh=:vdlzh
	 * 
	 * @param hql
	 *            hql语句
	 * @param entity
	 *            参数对象
	 * @return 结果集
	 */
	public List<T> queryList(String hql, T entity);
	
	public List<Map<String, Object>> queryList(String hql, Map<String, Object> map);

	public List<T> queryList(Class<T> class1, String propertiesName,
			List<String> param);

	public List<T> queryList(Class<T> class1, String propertiesName,
			String param);

	/**
	 * 查询结果集 例子：select o from HhXtYhJs o where o.yhId = ?
	 * 
	 * @param hql
	 *            hql语句
	 * @param Object
	 *            [] objects 参数对象
	 * @return 结果集
	 */
	public List<T> queryList(String hql, Object[] objects);

	public List<T> queryList(String hql, Object[] objects, boolean cache);

	/**
	 * 查询结果集
	 * 
	 * @param class1
	 *            entity 类型
	 * @param list
	 *            参数list
	 */
	public List<T> queryList(Class<T> class1, ParamInf condList );

	/**
	 * 查询所有数据
	 * 
	 * @param class1
	 * @return
	 */
	public List<T> queryList(Class<T> class1);

	public List<T> queryList(String hql);

	/**
	 * @param class1
	 * @param object
	 * @return
	 */
	public List<T> queryList(Class<T> class1, Criterion criterion);

	/**
	 * 
	 * @param hql
	 * @param entity
	 *            entity 实现PagingInf接口 的 getPagingDataHQL 返回一个 字符串 例子：select o
	 *            from HhXtYhJs o where o.yhId = ：yhId
	 * @param pageRange
	 *            分页范围
	 * @return map的键 是 items（结果集） 和 total（总条数）
	 */
	public PagingData<T> queryPagingData(String whereHQL, T entity,
			PageRange pageRange);
	
	/**
	 * 
	 * @param hql
	 * @param entity
	 *            entity 实现PagingInf接口 的 getPagingDataHQL 返回一个 字符串 例子：select o
	 *            from HhXtYhJs o where o.yhId = ：yhId
	 * @param pageRange
	 *            分页范围
	 * @return map的键 是 items（结果集） 和 total（总条数）
	 */
	public PagingData<Map<String, Object>> queryPagingDataByHql(String hql,String hqlCount,  Map<String, Object> paramMap,
			PageRange pageRange);

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
	public PagingData<T> queryPagingData(Class<T> class1, ParamInf condList,
			PageRange pageRange);

	public PagingData<T> queryPagingData(Class<T> class1,
			ParamInf condList, PageRange pageRange,
			List<String> selectProperList);

	public PagingData<T> queryPagingData(Class<T> class1,
			ParamInf condList, PageRange pageRange,
			String[] selectPropers);

	/**
	 * 
	 * @param class1
	 *            类型
	 * @param pageRange
	 *            分页参数
	 * @return map的键 是 items（结果集） 和 total（总条数）
	 */
	public PagingData<T> queryPagingData(Class<T> class1, PageRange pageRange);

	public Map<String, Object> queryPagingData(String sql, String countSql,
			PageRange pageRange);

	public Map<String, Object> queryPagingData(String sql, String countSql,
			Map<String, Object> paramMap, PageRange pageRange);

	/**
	 * 查询count
	 * 
	 * @param hql
	 * @param entity
	 * @return
	 */
	public int findCount(String hql, T entity);
	public int findCount(String hql);

	/**
	 * 查询count is =0
	 * 
	 * @param hql
	 * @param entity
	 * @return
	 */
	public boolean findWhetherData(String hql, T entity);

	public Object findObjectBySql(String sql, Object[] objects);

	public Object findObjectBySql(String sql);
	
	public void executeSql(String sql, Map<String, Object> parameterMap);

	public boolean isExist(String hql, Object[] objects);

	public int findCountBySql(String sql, Object[] objects);

	public int executeSql(String sql, Object[] objects);

	public void executeSql(String sql);

	public Map<String, Object> findEntity(String tableName, String id);


	public List<Map<String, Object>> queryListBySql(String sql,
			Map<String, String> paramsMap);

	public List<Map<String, Object>> queryListBySql(String sql);

	public boolean isExist(T entity, String field);

	public T findEntity(Class<T> class1, Criterion criterion);

	public Object findPropertys(T entity, String[] returnPropertys,
			String[] wherePropertys);


	public void updateEntity(Class<T> class1, String whereproperty,
			Object whereValue, String key, Object value);

	public int findCount(Class<T> class1,ParamInf condList);
	
	public int findCount(Class<T> class1, Criterion criterion);
	
	
	public List<T> queryTreeList(Class<T> class1, ParamInf condList);
	public List<T> queryAllTreeList(Class<T> class1,ParamInf condList);

	public T max(Class<T> class1,ParamInf orderDesc);

	public List<T> queryList(Class<T> genericType, ParamInf paramInf, PageRange pageRange);

	public int findCount(String hql, Map<String, Object> paramsMap);

}
