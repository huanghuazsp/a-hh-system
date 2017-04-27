 package com.hh.system.bean;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.util.base.BaseEntity;
@Order
@SuppressWarnings("serial")
@Entity
@Table(name="SYS_FEAST_DAY")
public class SystemFeastDay  extends BaseEntity{
	
	private int type;
	
	
	@Column(name="TYPE_")
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	//feastDay
	private String feastDay;
	
	@Column(name="FEAST_DAY",length=16)
	public String getFeastDay() {
		return feastDay;
	}
	public void setFeastDay(String feastDay) {
		this.feastDay = feastDay;
	}
	
}