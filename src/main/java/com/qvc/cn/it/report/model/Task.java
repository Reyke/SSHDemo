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
@Table(name="task")
public class Task {
	
	private Long task_id;
	private String task_name;
	private Scope scope;
	private Long folder_id;
	//private String folder_name;
	private char active_ind;
	
	private Date created_at;
	private String created_by;
	private Date updated_at;
	private String updated_by;
	
	private Date deadline;
	private Integer versionDateNbr;
	private Date alm_start_exe_at;
	
	@Column(name = "alm_start_exe_at")
	public Date getAlm_start_exe_at() {
		return alm_start_exe_at;
	}

	public void setAlm_start_exe_at(Date alm_start_exe_at) {
		this.alm_start_exe_at = alm_start_exe_at;
	}

	@Column(name = "version_date_nbr")
	public Integer getVersionDateNbr() {
		return versionDateNbr;
	}

	public void setVersionDateNbr(Integer versionDateNbr) {
		this.versionDateNbr = versionDateNbr;
	}

	public Task(Long task_id){
		this.task_id = task_id ;
	}
	
	@Id
	@Column(name = "task_id", nullable = false, columnDefinition = "bigint")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getTask_id() {
		return task_id;
	}

	public void setTask_id(Long task_id) {
		this.task_id = task_id;
	}

	@Column(name = "task_name", length = 100)
	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}
	
	@ManyToOne
	@JoinColumn(name = "scope_id")
	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}


	@Column(name = "folder_id")
	public Long getFolder_id() {
		return folder_id;
	}

	public void setFolder_id(Long folder_id) {
		this.folder_id = folder_id;
	}

/*	@Column(name = "folder_name", length = 100)
	public String getFolder_name() {
		return folder_name;
	}

	public void setFolder_name(String folder_name) {
		this.folder_name = folder_name;
	}*/

	@Column(name = "active_ind", columnDefinition = "char(1)")
	public char getActive_ind() {
		return active_ind;
	}

	public void setActive_ind(char active_ind) {
		this.active_ind = active_ind;
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

	public Task() {
	}

	@Column(name = "deadline_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	
	
	
	

}
