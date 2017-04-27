package com.hh.system.service.inf;

import java.util.List;

import com.hh.system.util.MessageException;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamInf;

public interface BaseServiceInf<T> {

	void updateOrderAll(String ids);

	void updateOrder(String id1, Long order1, String id2, Long order2);

	List<String> deleteTreeByIds(String ids);

	void deleteByIds(String ids);

	T findObjectById(String id);
	
	List<T> queryTreeList(String node) ;
	
	List<T> queryTreeList(ParamInf params) ;
	
	List<T> queryList(ParamInf paramInf) ;

	T saveTree(T entity) throws MessageException;

	T save(T entity) throws MessageException;

	public PagingData<T> queryPagingData(T entity, PageRange pageRange);
	
	void dragInner(String ids,String node);

	void drag(String ids, String node, String allIds);
	

}
