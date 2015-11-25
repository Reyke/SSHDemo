package com.qvc.cn.it.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@Entity
@Table(name="scope")
public class Scope {

	private int scope_id;
	private Project project_name;
	private Country country_code;
	private Date created_at;
	private String created_by;
	private Date updated_at;
	private String updated_by;
	
	
	@Id
	@Column(name = "scope_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getScope_id() {
		return scope_id;
	}
	
	public void setScope_id(int scope_id) {
		this.scope_id = scope_id;
	}
	
	@ManyToOne
	@JoinColumn(name = "project_name")
	public Project getProject_name() {
		return project_name;
	}

	public void setProject_name(Project project_name) {
		this.project_name = project_name;
	}

	@ManyToOne
	@JoinColumn(name = "country_code")
	public Country getCountry_code() {
		return country_code;
	}

	public void setCountry_code(Country country_code) {
		this.country_code = country_code;
	}


	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}


	@Column(name = "created_by", columnDefinition = "char(8)")
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


	@Column(name = "updated_by", columnDefinition = "char(8)")
	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public Scope() {
	}

}
