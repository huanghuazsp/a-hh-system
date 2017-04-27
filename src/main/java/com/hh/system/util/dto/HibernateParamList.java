package com.hh.system.util.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.hh.system.util.Check;

@SuppressWarnings("serial")
public class HibernateParamList extends ArrayList<Object> implements ParamInf {

	@Override
	public ParamInf order(String field) {
		this.add(Order.asc(field));
		return this;
	}

	@Override
	public ParamInf orderDesc(String field) {
		this.add(Order.desc(field));
		return this;
	}

	@Override
	public ParamInf in(String field, Collection valueList) {
		this.add(Restrictions.in(field, valueList));
		return this;
	}

	@Override
	public ParamInf in(String field, Object[] valueList) {
		this.add(Restrictions.in(field, valueList));
		return this;
	}

	@Override
	public ParamInf is(String field, Object value) {
		this.add(Restrictions.eq(field, value));
		return this;
	}

	@Override
	public ParamInf nis(String field, Object value) {
		this.add(Restrictions.ne(field, value));
		return this;
	}

	@Override
	public ParamInf like(String field, String value) {
		if (Check.isNoEmpty(value)) {
			this.add(Restrictions.like(field, "%" + value + "%"));
		}
		return this;
	}

	@Override
	public ParamInf likenoreg(String field, String value) {
		this.add(Restrictions.like(field, value));
		return this;
	}

	@Override
	public ParamInf le(String field, Object value) {
		this.add(Restrictions.le(field, value));
		return this;
	}

	@Override
	public ParamInf ge(String field, Object value) {
		this.add(Restrictions.ge(field, value));
		return this;
	}
	
	@Override
	public ParamInf lt(String field, Object value) {
		this.add(Restrictions.lt(field, value));
		return this;
	}
	
	@Override
	public ParamInf gt(String field, Object value) {
		this.add(Restrictions.gt(field, value));
		return this;
	}

	@Override
	public ParamInf isNull(String field) {
		this.add(Restrictions.or(Restrictions.isNull(field),
				Restrictions.eq(field, "")));
		return this;
	}

	@Override
	public ParamInf isNotNull(String field) {
		this.add(Restrictions.and(Restrictions.isNotNull(field),
				Restrictions.ne(field, "")));
		return this;
	}

	@Override
	public ParamInf or(ParamInf paramInf) {
		Criterion baseCriterion = (Criterion) paramInf.get(0);
		for (int i = 1; i < paramInf.size(); i++) {
			baseCriterion = Restrictions.or(baseCriterion,(Criterion) paramInf.get(i));
		}
		this.add(baseCriterion);
		return this;
	}
	
	@Override
	public ParamInf or(ParamInf[] paramInf) {
		Criterion baseCriterion1 = null;
		for (ParamInf paramInf2 : paramInf) {
			Criterion baseCriterion = (Criterion) paramInf2.get(0);
			for (int i = 1; i < paramInf2.size(); i++) {
				baseCriterion = Restrictions.and(baseCriterion,(Criterion) paramInf2.get(i));
			}
			if (baseCriterion1==null) {
				baseCriterion1=baseCriterion;
			}else{
				baseCriterion1 = Restrictions.or(baseCriterion1,baseCriterion);
			}
		}
		this.add(baseCriterion1);
		return this;
	}
	
	
	@Override
	public ParamInf and(ParamInf paramInf) {
		Criterion baseCriterion = (Criterion) paramInf.get(0);
		for (int i = 1; i < paramInf.size(); i++) {
			baseCriterion = Restrictions.and(baseCriterion,(Criterion) paramInf.get(i));
		}
		this.add(baseCriterion);
		return this;
	}

	@Override
	public ParamInf nolike(String field, String value) {
		this.add(Restrictions.not(Restrictions.like(field, "%" + value + "%")));
		return this;
	}

	@Override
	public ParamInf nolikenoreg(String field, String value) {
		this.add(Restrictions.not(Restrictions.like(field, value)));
		return this;
	}

}
