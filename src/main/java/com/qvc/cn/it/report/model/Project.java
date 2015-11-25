package com.qvc.cn.it.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name="project")
public class Project {

	private String project_name;
	private String project_desc;
	private Date created_at;
	private String created_by;
	private Date updated_at;
	private String updated_by;
	
	@Id
	@Column(name="project_name", length = 20, nullable = false)
	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	
	@Column(name="project_desc" , length = 50)
	public String getProject_desc() {
		return project_desc;
	}

	public void setProject_desc(String project_desc) {
		this.project_desc = project_desc;
	}
	
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP) 
	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	
	@Column(name="created_by", columnDefinition = "char(8)")
	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	
	@Column(name="updated_by", columnDefinition = "char(8)")
	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	
	public Project() {
	}

}
