package com.qvc.cn.it.report.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.qvc.cn.it.report.utils.Constants;

@XmlRootElement
@Entity
@Table(name = "issues")
@DiscriminatorValue(Constants.ISSUE_TYPE_DATAISSUE)
public class DataIssue extends CommonIssue {

	private String dataType;
	private String errorData;
	
	public DataIssue(){
		
	}
	public DataIssue(Issues issues){
		super(issues);
		
	}
	@Column(name = "data_type", length = 1000)
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Column(name = "error_data", length = 1000)
	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}
	
	@Override
	@Transient
	public boolean isValidate() {
		if (isEmpty(dataType)||isEmpty(dataType) || isEmpty(errorData)
				|| isEmpty(errorDescription)) {
			return false;
		} else {
			return true;
		}

	}


}
