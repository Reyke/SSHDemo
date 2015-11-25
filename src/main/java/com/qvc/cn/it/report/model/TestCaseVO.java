package com.qvc.cn.it.report.model;

public class TestCaseVO {
	private String case_name;
	private long case_id;
	private String status;
	public String getCase_name() {
		return case_name;
	}
	public void setCase_name(String case_name) {
		this.case_name = case_name;
	}
	public long getCase_id() {
		return case_id;
	}
	public void setCase_id(long case_id) {
		this.case_id = case_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "TestCaseVO [case_name=" + case_name + ", case_id=" + case_id
				+ ", status=" + status + "]";
	}
	
	
	

}
