package com.hh.hibernate.util.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

import com.hh.hibernate.dao.inf.Comment;

@SuppressWarnings("serial")
@MappedSuperclass
public class BaseEntity<T> extends BaseEntitySimple<T>{
	protected Date createTime;
	protected String createUser;
	protected String createUserName;
	protected String updateUser;
	protected String orgid;
	protected String deptid;
	protected String jobid;
	protected Long order;
	
	@Comment("创建人")
	@Column(name = "CREATE_USER", length = 36, updatable = false)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Comment("修改人")
	@Column(name = "UPDATE_USER", length = 36)
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Comment("创建机构")
	@Column(name = "ORG_ID", length = 36)
	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	
	@Comment("创建部门")
	@Column(name = "DEPT_ID", length = 36)
	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	@Comment("创建岗位")
	@Column(name = "JOB_ID", length = 36)
	public String getJobid() {
		return jobid;
	}

	public void setJobid(String jobid) {
		this.jobid = jobid;
	}

	@Comment("排序字段")
	@Index(name="INDEX_ORDER_")
	@Column(name = "ORDER_", length = 15, updatable = false)
	public Long getOrder() {
		return order;
	}
	public void setOrder(Long order) {
		this.order = order;
	}
	
	@Comment("创建时间")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 7,updatable=false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Comment("创建人名称")
	@Column(name = "CREATE_USER_NAME", length = 64, updatable = false)
	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	
	
}
