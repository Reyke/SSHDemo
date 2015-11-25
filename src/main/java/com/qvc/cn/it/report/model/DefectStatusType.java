package com.qvc.cn.it.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "defect_status_type")
public class DefectStatusType {
	
	private String defectStatus;
	private String createBy;
	private Date createAt;
	private Date updateAt;
	private String updateBy;
	
	@Id
	@Column(name = "defect_status")
	public String getDefectStatus() {
		return defectStatus;
	}

	public void setDefectStatus(String defectStatus) {
		this.defectStatus = defectStatus;
	}


	@Column(name = "CREATED_BY", columnDefinition = "char(8)")
	public String getCreateBy() {
		return createBy;
	}

	
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Column(name = "CREATED_AT")
	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	@Column(name = "UPDATED_AT")
	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	@Column(name = "UPDATED_BY", columnDefinition = "char(8)")
	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}


}
