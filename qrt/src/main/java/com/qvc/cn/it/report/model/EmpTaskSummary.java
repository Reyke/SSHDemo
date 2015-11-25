package com.qvc.cn.it.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "emp_task_summary")
public class EmpTaskSummary {
	
	private int etId;
	private Employee emp;
	private Task task;
	private Date createdAT;
	private String createdBy;
	private Date updateAT;
	private String updateBy;
	private String finishedInt = "N";
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "EMP_NBR" , nullable=false,columnDefinition = "char(8)")
	public Employee getEmp() {
		return emp;
	}
	public void setEmp(Employee emp) {
		this.emp = emp;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "TASK_ID", nullable=false,columnDefinition = "int(20)")
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	
	@Id
	@Column(name = "ET_ID", columnDefinition = "int(11)", nullable=false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getEtId() {
		return etId;
	}
	public void setEtId(int etId) {
		this.etId = etId;
	}
	
	@Column(name = "CREATED_AT" ,columnDefinition = "datetime")
	public Date getCreatedAT() {
		return createdAT;
	}
	public void setCreatedAT(Date createdAT) {
		this.createdAT = createdAT;
	}
	
	@Column(name = "CREATED_BY" ,columnDefinition = "char(8)")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name = "UPDATED_AT" ,columnDefinition = "datetime")
	public Date getUpdateAT() {
		return updateAT;
	}
	public void setUpdateAT(Date updateAT) {
		this.updateAT = updateAT;
	}
	
	@Column(name = "UPDATED_BY" ,columnDefinition = "char(8)")
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	@Column(name = "FINISHED_IND" ,columnDefinition = "char(1)", nullable = false)
	public String getFinishedInt() {
		return finishedInt;
	}
	public void setFinishedInt(String finishedInt) {
		this.finishedInt = finishedInt;
	}
	
	
	
	@Override
	public String toString() {
		return "EmpTaskSummary [etId=" + etId + ", emp=" + emp + ", task="
				+ task + ", createdAT=" + createdAT + ", createdBy="
				+ createdBy + ", updateAT=" + updateAT + ", updateBy="
				+ updateBy + ", finishedInt=" + finishedInt + "]";
	}
	
	public EmpTaskSummary() {
		super();
	}
	
	public EmpTaskSummary(int etId, Employee emp, Task task, Date createdAT,
			String createdBy, Date updateAT, String updateBy, String finishedInt) {
		super();
		this.etId = etId;
		this.emp = emp;
		this.task = task;
		this.createdAT = createdAT;
		this.createdBy = createdBy;
		this.updateAT = updateAT;
		this.updateBy = updateBy;
		this.finishedInt = finishedInt;
	}
	
	
	
}
