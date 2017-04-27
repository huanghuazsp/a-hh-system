package com.hh.system.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.hibernate.util.base.BaseEntitySimple;
import com.hh.system.service.inf.BaseServiceInf;
import com.hh.system.util.Check;
import com.hh.system.util.ClassReflex;
import com.hh.system.util.Convert;
import com.hh.system.util.Json;
import com.hh.system.util.MessageException;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.system.util.pk.PrimaryKey;
import com.hh.usersystem.LoginUserServiceInf;

public class BaseService<T extends BaseEntitySimple> extends Base<T> implements BaseServiceInf<T> {
	@Autowired
	protected IHibernateDAO<T> dao;
	
	@Autowired
	protected LoginUserServiceInf loginUser;

	public PagingData<T> queryPagingData(T entity, PageRange pageRange) {
		return dao.queryPagingData(this.getGenericType(0), ParamFactory.getParamHb(), pageRange);
	}

	public PagingData<T> queryPagingData( PageRange pageRange, ParamInf hqlParamList) {
		return dao.queryPagingData(this.getGenericType(0), hqlParamList, pageRange);
	}

	public List<T> queryList(ParamInf paramInf, PageRange pageRange) {
		return dao.queryList(this.getGenericType(0), paramInf, pageRange);
	}

	public T save(T entity) throws MessageException {
		if (Check.isEmpty(entity.getId())) {
			dao.createEntity(entity);
		} else {
			dao.updateEntity(entity);
		}
		return entity;
	}
	public T saveOrUpdate(T entity) throws MessageException {
		dao.saveOrUpdateEntity(entity);
		return entity;
	}

	public void update(String id, String key, Object value) {
		dao.updateProperty(this.getGenericType(0), id, key, value);
	}

	public void update(String property, Object propertyValue, String key, Object value) {
		dao.updateEntity(this.getGenericType(0), property, propertyValue, key, value);
	}

	public T findObjectById(String id) {
		T entity = dao.findEntityByPK(this.getGenericType(0), id);
		return entity;
	}

	public T findObject(ParamInf list) {
		T entity = dao.findEntity(this.getGenericType(0), list);
		return entity;
	}

	public T findObjectByProperty(String property, Object value) {
		T entity = dao.findEntity(this.getGenericType(0), Restrictions.eq(property, value));
		return entity;
	}

	public List<T> queryListByProperty(String property, Object value) {
		if (value instanceof Collection) {
			List<T> entityList = dao.queryList(this.getGenericType(0),
					ParamFactory.getParamHb().in(property, (Collection) value));
			return entityList;
		} else {
			List<T> entityList = dao.queryList(this.getGenericType(0), Restrictions.eq(property, value));
			return entityList;
		}
	}

	@Transactional
	public List<T> queryAllList() {
		List<T> entityList = dao.queryList(this.getGenericType(0));
		return entityList;
	}

	public List<T> queryListByIds(List<String> ids) {
		if (ids.size() == 0) {
			return new ArrayList<T>();
		}
		return dao.queryList(this.getGenericType(0), "id", ids);
	}

	public List<T> queryList(ParamInf paramList) {
		return dao.queryList(this.getGenericType(0), paramList);
	}

	public void deleteByIds(String ids) {
		List<String> idList = Convert.strToList(ids);
		deleteByIds(idList);
	}

	public void deleteByProperty(String property, Object value) {
		if (value instanceof List) {
			dao.deleteEntity(this.getGenericType(0), property, (List) value);
		} else {
			dao.deleteEntity(this.getGenericType(0), property, value);
		}

	}

	public void updateOrder(String id1, Long order1, String id2, Long order2) {
		if (Check.isNoEmpty(order2) && Check.isNoEmpty(order1)) {
			dao.updateEntity("update " + this.getGenericType(0).getName() + " o set o.order=? where o.id=?",
					new Object[] { order1, id2 });
			dao.updateEntity("update " + this.getGenericType(0).getName() + " o set o.order=? where o.id=?",
					new Object[] { order2, id1 });
		} else {
			T entity1 = dao.findEntity(this.getGenericType(0), Restrictions.eq("id", id1));
			long order1_ = entity1.getOrder();
			T entity2 = dao.findEntity(this.getGenericType(0), Restrictions.eq("id", id2));
			long order2_ = entity2.getOrder();
			dao.updateEntity("update " + this.getGenericType(0).getName() + " o set o.order=? where o.id=?",
					new Object[] { order1_, id2 });
			dao.updateEntity("update " + this.getGenericType(0).getName() + " o set o.order=? where o.id=?",
					new Object[] { order2_, id1 });
		}
	}

	public int findCount(ParamInf paramList) {
		return dao.findCount(this.getGenericType(0), paramList);
	}

	public void createEntity(T entity) {
		dao.createEntity(entity);
	}
	
	public void updateEntity(T entity) {
		dao.updateEntity(entity);
	}

	public IHibernateDAO<T> getDao() {
		return dao;
	}

	public void setDao(IHibernateDAO<T> dao) {
		this.dao = dao;
	}

	public List<T> queryAllTreeList() {
		return dao.queryAllTreeList(this.getGenericType(0), ParamFactory.getParamHb().is("node", "root"));
	}

	public List<T> queryTreeList(ParamInf paramList) {
		return dao.queryTreeList(this.getGenericType(0), paramList);
	}

	public List<T> queryTreeList(String node, ParamInf hqlParamList) {
		hqlParamList.is("node", Check.isEmpty(node) ? "root" : node);
		return dao.queryTreeList(this.getGenericType(0), hqlParamList);
	}

	public List<T> queryTreeList(String node) {
		return queryTreeList(node, ParamFactory.getParamHb());
	}

	public List<String> deleteTreeByIds(String ids) {
		List<String> idList = Convert.strToList(ids);
		List<String> deleteIdList = new ArrayList<String>();
		deleteYzNode(idList, deleteIdList);
		if (!Check.isEmpty(deleteIdList)) {
			dao.deleteEntity(this.getGenericType(0), "id", deleteIdList);
		}
		return deleteIdList;
	}

	@Transactional
	public void dragInner(String ids, String parentId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("parentId", parentId);
		paramsMap.put("idList", Convert.strToList(ids));
		dao.updateEntity(
				"update " + this.getGenericType(0).getName() + " o set o.node=:parentId  where o.id in (:idList)",
				paramsMap);
		updateOrderAll(ids);
	}

	@Transactional
	public void drag(String ids, String parentId, String allIds) {
		dragInner(ids, parentId);

		updateOrderAll(allIds);
	}

	protected void deleteYzNode(List<String> idList, List<String> deleteIdList) {
		List<String> yzIdList = new ArrayList<String>();
		if (!Check.isEmpty(idList)) {
			deleteIdList.addAll(idList);
			// dao.deleteEntity(this.getGenericType(0), "id", idList);
			List<T> menuList = dao.queryList(this.getGenericType(0), Restrictions.in("node", idList));
			for (T hhXtCd : menuList) {
				yzIdList.add(hhXtCd.getId());
			}
			deleteYzNode(yzIdList, deleteIdList);
		}
	}

	public T saveTree(T hhXtData) throws MessageException {
		if (Check.isEmpty(hhXtData.getNode())) {
			hhXtData.setNode("root");
		}
		if (Check.isEmpty(hhXtData.getText())) {
			throw new MessageException("名称不能为空！");
		}
		if (checkTextOnly(hhXtData)) {
			throw new MessageException("同级下名称不能一样，请更换！");
		}
		if (Check.isNoEmpty(hhXtData.getId())) {
			if (checkParentNotLeaf(hhXtData)) {
				throw new MessageException("选择的上级节点不能是自己的子节点，请更换！");
			}
		}
		if (Check.isEmpty(hhXtData.getId())) {
			dao.createEntity(hhXtData);
		} else {
			if (hhXtData.getId().equals(hhXtData.getNode())) {
				throw new MessageException("上级节点不能选择自己，请更换！");
			}
//			if (hhXtData.getLeaf() == 1) {
//				boolean as = dao.findWhetherData(
//						"select count(o) from " + hhXtData.getClass().getName() + " o " + "where  node = :id ",
//						hhXtData);
//				if (as) {
//					throw new MessageException("此数据已经有子数据，不能修改其为叶子节点，如果要修改请删除子数据！");
//				}
//			}
			dao.updateEntity(hhXtData);
		}
		return hhXtData;
	}

	protected boolean checkParentNotLeaf(T hhXtData) {
		List<String> childrenIdList = new ArrayList<String>();
		queryChildrenIds(hhXtData.getId(), childrenIdList);
		return childrenIdList.contains(hhXtData.getNode());
	}

	private void queryChildrenIds(String id, List<String> childrenIdList) {
		List<T> tList = dao.queryList(this.getGenericType(0), Restrictions.eq("node", id));
		for (T t : tList) {
			childrenIdList.add(t.getId());
			queryChildrenIds(t.getId(), childrenIdList);
		}
	}

	protected boolean checkTextOnly(T entity) {
		return dao.findWhetherData("select count(o) from " + entity.getClass().getName() + " o "
				+ "where o.text=:text and (o.id!=:id or :id is null) and node = :node ", entity);
	}

	protected boolean checkOnly(String properties, T entity) {
		return dao.findWhetherData("select count(o) from " + entity.getClass().getName() + " o " + "where o."
				+ properties + "=:" + properties + " and (o.id!=:id or :id is null)  ", entity);
	}

	public void updateOrderAll(String ids) {
		List<String> idList = Convert.strToList(ids);
		for (String id : idList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order", PrimaryKey.getTime());
			map.put("id", id);
			dao.updateEntity("update " + this.getGenericType(0).getName() + " o set o.order=:order  where o.id =:id", map);
		}
	}

	public void deleteByIds(List<String> deleteIds) {
		if (!Check.isEmpty(deleteIds)) {
			dao.deleteEntity(this.getGenericType(0), "id", deleteIds);
		}
	}

	// public String findJsonStrById(String id,String key) {
	// T entity = dao.findEntityByPK(this.getGenericType(0), id);
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("id", entity.getId());
	// map.put(key, ClassReflex.execute(entity, "get" + key.substring(0,
	// 1).toUpperCase() + key.substring(1)));
	// return Json.toStr(map);
	// }
	//
	// public String findJsonStrById(String id) {
	// return findJsonStrById(id, "text");
	// }

}
