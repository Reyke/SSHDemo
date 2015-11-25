package com.qvc.cn.it.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "employee")
public class Employee {
	private String number;
	private String email;
	private Date createdAt;
	private Date updateAt;
	private String activeInd;
	private String password;
	private Date deleteAt;
	private String deleteBy;
	private String updateBy;
	private String createBy;
	private String deleteReason;
	private Role role;
	private String name;
	
	
	public Employee(String number) {
		this.number = number;
	}
	
	
	public Employee() {
		super();
	}

	public Employee(String number,String name, String email, Date createdAt, Date updateAt,
			String activeInd, String password, Date deleteAt, String deleteBy,
			String updateBy, String createBy, String deleteReason, Role role) {
		super();
		this.number = number;
		this.name = name;
		this.email = email;
		this.createdAt = createdAt;
		this.updateAt = updateAt;
		this.activeInd = activeInd;
		this.password = password;
		this.deleteAt = deleteAt;
		this.deleteBy = deleteBy;
		this.updateBy = updateBy;
		this.createBy = createBy;
		this.deleteReason = deleteReason;
		this.role = role;
	}
	
	
	@Column(name = "EMP_NAME", length=50,nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = true, name = "DELETED_AT")
	public Date getDeleteAt() {
		return deleteAt;
	}

	public void setDeleteAt(Date deleteAt) {
		this.deleteAt = deleteAt;
	}

	@Column(name = "DELETED_BY", columnDefinition = "char(8)")
	public String getDeleteBy() {
		return deleteBy;
	}

	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	@Column(name = "UPDATED_BY", columnDefinition = "char(8)")
	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Column(name = "CREATED_BY", columnDefinition = "char(8)")
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Column(name = "DELETED_REASON", length = 45)
	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "ROLE_ID")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Id
	@Column(nullable = false, name = "EMP_NBR", length = 8, unique = true, updatable = false, columnDefinition = "char(8)")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(nullable = true, name = "EMP_EMAIL", length = 50, updatable = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "CREATED_AT", updatable = false)
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Column(name = "UPDATED_AT", updatable = true)
	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	@Column(nullable = false, name = "ACTIVE_IND", length = 1, updatable = true, columnDefinition = "char(1)")
	public String getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}

	@Column(nullable = false, name = "EMP_PSWD", length = 41, updatable = true, columnDefinition = "char(41)")
	public String getPassword() {
		return password;       
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
