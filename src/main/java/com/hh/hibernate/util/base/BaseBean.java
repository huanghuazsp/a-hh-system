package com.hh.hibernate.util.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public abstract interface BaseBean<T> extends Serializable, Cloneable {

	public int getStatus();

	public void setStatus(int id);

	public String getId();

	public void setId(String id);

	public String getCreateUser();

	public void setCreateUser(String createUser);

	public String getUpdateUser();

	public void setUpdateUser(String updateUser);

	public String getOrgid();

	public void setOrgid(String orgid);

	public String getDeptid();

	public void setDeptid(String orgid);

	public String getJobid();

	public void setJobid(String orgid);

	public Date getCreateTime();

	public void setCreateTime(Date createTime);

	public Long getOrder();

	public void setOrder(Long order);

	public Date getUpdateTime();

	public void setUpdateTime(Date updateTime);

	public String getNode();

	public void setNode(String nodeId);

	public String getText();

	public void setText(String text);

	public int getLeaf();

	public void setLeaf(int leaf);

	public List<T> getChildren();

	public void setChildren(List<T> children);

	public int getExpanded();

	public void setExpanded(int expanded);

	public String getBid();

	public void setBid(String bid);

	public String getCreateUserName();

	public void setCreateUserName(String createUserName);

}
