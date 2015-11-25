package com.qvc.cn.it.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="issue_component")
public class IssueComponent {
	
//	private int id;
//	private String tableName;
	private String fieldName;
	private String title;
	private String enableInd;
	private String createdBy;
	private Date createdAt;
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
	
//	@Id
//	@Column(name="table_name", length=100)
//	public String getTableName() {
//		return tableName;
//	}
//	public void setTableName(String tableName) {
//		this.tableName = tableName;
//	}
	
	@Id
	@Column(name="field_name")
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name="enable_ind", columnDefinition="char(1)")
	public String getEnableInd() {
		return enableInd;
	}
	public void setEnableInd(String enableInd) {
		this.enableInd = enableInd;
	}
	@Column(name="created_by", columnDefinition="char(8)")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name="created_at")
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	

}
