package com.qvc.cn.it.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "role")
public class Role {

	private short id;
	private String name;
	private String desc;
	private Date createAt;
	private Date updateAt;
	private String createBy;
	private String updateBy;
	private String visibleInd;

	@Column(nullable=false,name = "CREATED_BY", columnDefinition = "char(8)")
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Column(nullable = false,name = "UPDATED_BY", columnDefinition = "char(8)")
	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Column(nullable = false, name = "VISIBLE_IND", length = 1, updatable = true, columnDefinition = "char(1)")
	public String getVisibleInd() {
		return visibleInd;
	}

	public void setVisibleInd(String visibleInd) {
		this.visibleInd = visibleInd;
	}

	@Id
	@GeneratedValue
	@Column(name = "ROLE_ID", nullable = false, unique = true)
	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	@Column(name = "ROLE_NAME", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ROLE_DESC", nullable = true, length = 50)
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Column(name = "CREATED_AT", nullable = false)
	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	@Column(name = "UPDATED_AT", nullable = false)
	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

}
