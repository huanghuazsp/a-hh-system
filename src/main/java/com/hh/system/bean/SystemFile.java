 package com.hh.system.bean;
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
@Order(fields = "order", sorts = "asc")
@SuppressWarnings("serial")
@Entity
@Table(name="SYS_FILE")
public class SystemFile  extends BaseEntity{
	//fileSize
	private long fileSize;
	
	private String baseHttpFilePath;
	
	@Column(name="FILE_SIZE")
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	//path
	private String path;
	
	@Column(name="PATH",length=1000)
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	//type
	private String type;
	
	@Column(name="TYPE",length=32)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	//fileType
	private String fileType;
	
	@Column(name="FILE_TYPE",length=32)
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	//text
	private String text;
	
	@Column(name="TEXT",length=512)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	//serviceId
	private String serviceId;
	
	@Column(name="SERVICE_ID",length=36)
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	//parentServiceId
	private String parentServiceId;
	
	@Column(name="PARENT_SERVICE_ID",length=36)
	public String getParentServiceId() {
		return parentServiceId;
	}
	public void setParentServiceId(String parentServiceId) {
		this.parentServiceId = parentServiceId;
	}
	
	@Transient
	public String getBaseHttpFilePath() {
		return baseHttpFilePath;
	}
	public void setBaseHttpFilePath(String baseHttpFilePath) {
		this.baseHttpFilePath = baseHttpFilePath;
	}
}