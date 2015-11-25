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
@DiscriminatorValue(Constants.ISSUE_TYPE_DEVICEISSUE)
public class DeviceIssue extends CommonIssue {
	public DeviceIssue() {
	}

	public DeviceIssue(Issues issues) {
		super(issues);

	}

	private String deviceName;

	@Column(name = "device_name", length = 1000)
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	@Override
	@Transient
	public boolean isValidate() {
		if (isEmpty(deviceName) || isEmpty(errorDescription)) {
			return false;
		} else {
			return true;
		}

	}

}
