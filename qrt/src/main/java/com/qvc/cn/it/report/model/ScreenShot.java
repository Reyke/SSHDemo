package com.qvc.cn.it.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "screenshot")
public class ScreenShot {
	
	private Long ssId;
	private String screenShotLink;
	private boolean delInd = false;
	
	
	@Transient
	public boolean isDelInd() {
		return delInd;
	}
	public void setDelInd(boolean delInd) {
		this.delInd = delInd;
	}
	@Id
	@Column(name = "ss_id", columnDefinition = "bigint")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getSsId() {
		return ssId;
	}
	public void setSsId(Long ssId) {
		this.ssId = ssId;
	}
	
	@Column(name = "screenshot_link")
	public String getScreenShotLink() {
		return screenShotLink;
	}
	public void setScreenShotLink(String screenShotLink) {
		this.screenShotLink = screenShotLink;
	}
	
}
