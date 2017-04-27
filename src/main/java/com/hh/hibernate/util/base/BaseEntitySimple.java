package com.hh.hibernate.util.base;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.hh.hibernate.dao.inf.Comment;


@SuppressWarnings("serial")
@MappedSuperclass
public class BaseEntitySimple<T> implements BaseBean<T> {
	protected String id;
	protected String bid;

	protected Date updateTime;

	protected int status;

	@Id
	@Column(name = "ID", nullable = false, length = 32)
	@Comment("主键")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Transient
	public String getCreateUser() {
		return "null";
	}

	public void setCreateUser(String createUser) {
	}

	@Transient
	public String getUpdateUser() {
		return "null";
	}

	public void setUpdateUser(String updateUser) {
	}

	@Transient
	public String getOrgid() {
		return "null";
	}

	public void setOrgid(String orgid) {
	}

	@Transient
	public String getDeptid() {
		return "null";
	}

	public void setDeptid(String orgid) {
	}

	@Transient
	public String getJobid() {
		return "null";
	}

	public void setJobid(String orgid) {
	}

	@Column(name = "STATUS_")
	@Comment("是否销毁（0=正常）")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Transient
	public Date getCreateTime() {
		return null;
	}

	public void setCreateTime(Date createTime) {
	}

	@Transient
	public Long getOrder() {
		return null;
	}

	public void setOrder(Long order) {
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME", length = 7)
	@Comment("修改时间")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Transient
	public String getNode() {
		return null;
	}

	public void setNode(String nodeId) {
	}

	@Transient
	public String getText() {
		return null;
	}

	@Override
	public void setText(String text) {
	}

	@Transient
	public int getLeaf() {
		return 0;
	}

	@Override
	public void setLeaf(int leaf) {
	}

	@Transient
	public List<T> getChildren() {
		return null;
	}

	@Override
	public void setChildren(List<T> children) {

	}

	@Transient
	public int getExpanded() {
		return 0;
	}

	public void setExpanded(int expanded) {
	}

	@Transient
	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	@Transient
	public String getCreateUserName() {
		return null;
	}

	public void setCreateUserName(String createUserName) {
	}

}
