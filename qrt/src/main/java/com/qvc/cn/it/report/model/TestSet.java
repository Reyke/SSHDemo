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
@Table(name = "test_set")
public class TestSet {

	
	private Long set_id;
	private String set_name;
	private Task task;
	private Long cycle_id;
	private Date created_at;
	private String created_by;
	private Date updated_at;
	private String updated_by;
	
	
	@Id
	@Column(name = "set_id", nullable = false, columnDefinition = "bigint")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getSet_id() {
		return set_id;
	}
	public void setSet_id(Long set_id) {
		this.set_id = set_id;
	}
	
	@Column(name = "set_name", length = 100)
	public String getSet_name() {
		return set_name;
	}
	public void setSet_name(String set_name) {
		this.set_name = set_name;
	}
	
	@ManyToOne
	@JoinColumn(name = "task_id")
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	
	@Column(name = "cycle_id", columnDefinition = "bigint")
	public Long getCycle_id() {
		return cycle_id;
	}
	public void setCycle_id(Long cycle_id) {
		this.cycle_id = cycle_id;
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

	
}
