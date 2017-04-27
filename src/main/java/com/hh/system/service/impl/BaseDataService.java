package com.hh.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hh.hibernate.util.base.BaseBean;
import com.hh.mongo.dao.inf.IDataBaseDAOInf;
import com.hh.system.service.inf.BaseServiceInf;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.StaticVar;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.system.util.dto.ParamList;
import com.hh.system.util.pk.PrimaryKey;

public class BaseDataService<T extends BaseBean> extends Base<T> implements
		BaseServiceInf<T> {
//	@Autowired
//	protected IDataBaseDAOInf<T> dao;
	
	protected String getEntityId() {
		return StaticVar.entityId;
	}

	protected IDataBaseDAOInf<T> getDao() {
		return null;
	}
	
	protected ParamInf getParam() {
		return ParamFactory.getParam();
	}
	
	protected boolean getCache() {
		return false;
	}

	public List<String> deleteTreeByIds(String ids) {
		List<String> idList = Convert.strToList(ids);
		List<String> deleteIdList = new ArrayList<String>();
		deleteYzNode(idList, deleteIdList);
		if (!Check.isEmpty(deleteIdList)) {
			deleteByIds(deleteIdList);
		}
		return deleteIdList;
	}

	protected void deleteYzNode(List<String> idList, List<String> deleteIdList) {
		List<String> yzIdList = new ArrayList<String>();
		if (!Check.isEmpty(idList)) {
			deleteIdList.addAll(idList);
//			deleteByIds(idList);
			List<T> list = getDao().queryList(this.getGenericType(0), getParam().in(StaticVar.node, idList));
			for (T hhXtCd : list) {
				yzIdList.add(hhXtCd.getId());
			}
			deleteYzNode(yzIdList, deleteIdList);
		}
	}

	public void deleteByIds(List<String> idList) {
		if (getCache()) {
			getDao().removeCache(this.getGenericType(0), idList);
		} else {
			getDao().remove(this.getGenericType(0), idList);
		}
	}

	public void deleteByIds(String ids) {
		deleteByIds(Convert.strToList(ids));
	}

	public T findObjectById(String id) {
		if (getCache()) {
			return getDao().findByIdCache(this.getGenericType(0), id);
		} else {
			return getDao().findById(this.getGenericType(0), id);
		}
	}

	public PagingData<T> queryPagingData(T entity, PageRange pageRange) {
		return getDao()
				.queryPage(this.getGenericType(0), pageRange, new ParamList());
	}

	public T save(T entity) throws MessageException {
		if (Check.isEmpty(entity.getId())) {
			getDao().createEntity(entity);
		} else {
			if (getCache()) {
				getDao().updateEntityCache(entity);
			} else {
				getDao().updateEntity(entity);
			}
		}
		return entity;
	}

	protected boolean checkTextOnly(T entity) {
		return getDao().count(this.getGenericType(0),
				getParam().is(StaticVar.text, entity.getText())
						.nis(getEntityId(), entity.getId())) > 0 ? true
				: false;
	}

	protected boolean checkTreeTextOnly(T entity) {
		return getDao().count(
				this.getGenericType(0),
				getParam()
						.nis(getEntityId(), entity.getId())
						.is(StaticVar.text, entity.getText())
						.is(StaticVar.node, entity.getNode())) > 0 ? true
				: false;
	}

	protected boolean checkParentNotLeaf(T hhXtData) {
		List<String> childrenIdList = new ArrayList<String>();
		queryChildrenIds(hhXtData.getId(), childrenIdList);
		return childrenIdList.contains(hhXtData.getNode());
	}

	private void queryChildrenIds(String id, List<String> childrenIdList) {

		List<T> tList = getDao().queryList(this.getGenericType(0), 
				getParam().is(StaticVar.node, id));
		for (T t : tList) {
			childrenIdList.add(t.getId());
			queryChildrenIds(t.getId(), childrenIdList);
		}
	}

	public T saveTree(T entity) throws MessageException {
		if (Check.isEmpty(entity.getNode())) {
			entity.setNode(StaticVar.root);
		}
		if (checkTreeTextOnly(entity)) {
			throw new MessageException("同级下名称不能一样，请更换！");
		}
		if (Check.isNoEmpty(entity.getId())) {
			if (checkParentNotLeaf(entity)) {
				throw new MessageException("选择的上级节点不能是自己的子节点，请更换！");
			}
		}
		if (Check.isEmpty(entity.getId())) {
			getDao().createEntity(entity);
		} else {
			if (entity.getId().equals(entity.getNode())) {
				throw new MessageException("上级节点不能选择自己，请更换！");
			}
			/*if (entity.getLeaf() == 1) {
				boolean as = getDao().count(this.getGenericType(0), 
						getParam().is(StaticVar.node, entity.getId())) > 0 ? true
						: false;
				if (as) {
					throw new MessageException(
							"此数据已经有子数据，不能修改其为叶子节点，如果要修改请删除子数据！");
				}
			}*/
			if (getCache()) {
				getDao().updateEntityCache(entity);
			} else {
				getDao().updateEntity(entity);
			}
		}
		return entity;
	}

	public List<T> queryTreeList(ParamInf paramInf) {
		paramInf.order(StaticVar.order);
		return getDao().queryList(this.getGenericType(0), paramInf);
	}

	public List<T> queryTreeList(String node,
			ParamInf paramInf) {
		paramInf.is("node", Check.isEmpty(node) ? "root" : node);
		return queryTreeList(paramInf);
	}
	
	public List<T> queryTreeList(String node) {
		return queryTreeList(node, ParamFactory.getParamHb());
	}

	public void updateOrderAll(String ids) {
		List<String> idList = Convert.strToList(ids);
		for (String id : idList) {
			T entity = findObjectById(id);
			entity.setOrder(PrimaryKey.getTime() );
			if (getCache()) {
				getDao().updateEntityCache(entity);
			} else {
				getDao().updateEntity(entity);
			}
		}
	}

	public T max(ParamInf paramInf) {
		T max = getDao().max(this.getGenericType(0), paramInf);
		return max;
	}

	public List<T> queryList(ParamInf paramInf) {
		return getDao().queryList(this.getGenericType(0), paramInf);
	}

	@Override
	public void updateOrder(String id1, Long order1, String id2, Long order2) {
		
	}

	@Override
	public void dragInner(String ids, String node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drag(String ids, String node, String targetId) {
		// TODO Auto-generated method stub
		
	}

}
