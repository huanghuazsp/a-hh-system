package com.hh.system.util.base;

import com.hh.hibernate.util.base.BaseEntitySimple;
import com.hh.system.service.inf.BaseServiceInf;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.system.util.model.ReturnModel;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class BaseServiceAction<T extends BaseEntitySimple> extends BaseAction<T>
		implements ModelDriven<T> {
	protected T object = null;

	public BaseServiceAction() {
		try {
			object = (T) getGenericType(0).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public T getModel() {
		return object;
	}

	public Object queryPagingData() {
		return getService().queryPagingData(object,
				this.getPageRange());
	}

	public Object save() {
		try {
			T object = getService().save(this.object);
			return object;
		} catch (MessageException e) {
			return e;
		}
	}

	public Object saveTree() {
		try {
			T object = getService().saveTree(this.object);
			return object;
		} catch (MessageException e) {
			return e;
		}
		
	}

	public Object findObjectById() {
		T object = getService().findObjectById(this.object.getId());
		return object;
	}

	public void deleteByIds() {
		getService().deleteByIds(this.getIds());
	}
	
	public void dragInner() {
		getService().dragInner(this.getIds(),this.object.getNode());
	}
	
	public void drag() {
		getService().drag(this.getIds(),this.object.getNode(),this.object.getId());
	}

	public void deleteTreeByIds() {
		getService().deleteTreeByIds(this.getIds());
	}

	public Object queryTreeList() {
		ParamInf hqlParamList = convertTreeParams();
		return getService().queryTreeList(hqlParamList);
	}

	protected ParamInf convertTreeParams() {
		String node =Check.isEmpty(object.getNode()) ? "root" : object.getNode();
		ParamInf hqlParamList = ParamFactory.getParamHb();
		if (Check.isNoEmpty(object.getText()) && "root".equals(node)) {
			hqlParamList.like("text", object.getText());
		}else{
			hqlParamList.is("node", node);
		}
		return hqlParamList;
	}

	public void order() {
		String id1 = request.getParameter("id1");
		String order1 = request.getParameter("order1");
		String id2 = request.getParameter("id2");
		String order2 = request.getParameter("order2");
		getService().updateOrder(id1, Convert.toLong(order1), id2, Convert.toLong(order2) );
	}

	public void orderAll() {
		getService().updateOrderAll(getIds());
	}

	public BaseServiceInf<T> getService() {
		return null;
	}
	
	public Object queryList() {
		ParamInf hqlParamList = convertTreeParams();
		return getService().queryList(hqlParamList);
	}

}
