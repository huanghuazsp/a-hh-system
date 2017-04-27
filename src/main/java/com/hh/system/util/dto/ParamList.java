package com.hh.system.util.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;

@SuppressWarnings("serial")
public class ParamList extends ArrayList<Object> implements ParamInf {

	@Override
	public ParamInf orderDesc(String field) {
			this.add(new Sort.Order(Sort.Direction.DESC, field));
		return this;
	}

	@Override
	public ParamInf order(String field) {
		this.add(new Sort.Order(Sort.Direction.ASC, field));
		return this;
	}

	@Override
	public ParamInf in(String field, Collection valueList) {
		this.add(new Criteria(field).in(valueList));
		return this;
	}

	@Override
	public ParamInf is(String field, Object value) {
		this.add(new Criteria(field).is(value));
		return this;
	}

	@Override
	public ParamInf nis(String field, Object value) {
		this.add(new Criteria(field).ne(value));
		return this;
	}

	@Override
	public ParamInf like(String field, String value) {
		this.add(new Criteria(field).regex(value));
		return this;
	}
	
	@Override
	public ParamInf likenoreg(String field, String value) {
		this.add(new Criteria(field).regex(value));
		return this;
	}


	@Override
	public ParamInf le(String field, Object value) {
		this.add(new Criteria(field).ne(value));
		return this;
	}

	@Override
	public ParamInf isNull(String field) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ParamInf isNotNull(String field) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ParamInf or(ParamInf paramInf) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ParamInf in(String field, Object[] users) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ParamInf ge(String string, Object value) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ParamInf nolike(String field, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParamInf nolikenoreg(String field, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParamInf and(ParamInf paramInf) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParamInf or(ParamInf... paramInf) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParamInf lt(String string, Object endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParamInf gt(String string, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

}
