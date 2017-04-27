 package com.hh.database.bean;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Lob;
import com.hh.hibernate.util.base.*;
import com.hh.hibernate.dao.inf.Order;
@Order
@SuppressWarnings("serial")
@Entity
@Table(name="DATABASE_CONNECT")
public class DatabaseConnect  extends BaseEntityTree<DatabaseConnect>{
	
	//type
	private String type;
	
	@Column(name="TYPE",length=32)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	//connect
	private String connect;
	
	@Column(name="CONNECT",length=256)
	public String getConnect() {
		return connect;
	}
	public void setConnect(String connect) {
		this.connect = connect;
	}
	
	//user
	private String user;
	
	@Column(name="USER",length=64)
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	//password
	private String password;
	
	@Column(name="PASSWORD",length=64)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	private String params;

	@Transient
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	
	
	
}