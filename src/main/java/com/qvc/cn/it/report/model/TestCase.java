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
@Table(name = "test_case")
public class TestCase {
	private Long case_id;
	private String case_name;
	private TestSet testSet;
	private Long test_id;
	private String original_status;
	private Long test_instance_id;
	private String tester;
	private Date exe_date;
	private Date exe_time;
	private Date created_at;
	private String created_by;
	private Date updated_at;
	private String updated_by;
	private Task task;
	private int versionDateNumber;
	private Long refFolderId;
	
	@Column(name = "ref_folder_id")
	public Long getRefFolderId() {
		return refFolderId;
	}
	public void setRefFolderId(Long refFolderId) {
		this.refFolderId = refFolderId;
	}
	
	@Column(name = "version_date_nbr")
	public int getVersionDateNumber() {
		return versionDateNumber;
	}
	public void setVersionDateNumber(int versionDateNumber) {
		this.versionDateNumber = versionDateNumber;
	}
	
	@ManyToOne
	@JoinColumn(name = "task_id")
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	
	@Id
	@Column(name = "case_id", nullable = false, columnDefinition = "bigint")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getCase_id() {
		return case_id;
	}
	
	public void setCase_id(Long case_id) {
		this.case_id = case_id;
	}
	
	@Column(name = "case_name", length = 300)
	public String getCase_name() {
		return case_name;
	}
	
	public void setCase_name(String case_name) {
		this.case_name = case_name;
	}
	
	@ManyToOne
	@JoinColumn(name = "set_id")
	public TestSet getTestSet() {
		return testSet;
	}
	
	public void setTestSet(TestSet testSet) {
		this.testSet = testSet;
	}
	
	@Column(name = "test_id", columnDefinition = "bigint")
	public Long getTest_id() {
		return test_id;
	}
	
	@Column(name = "original_status", length = 45)
	public String getOriginal_status() {
		return original_status;
	}
	
	public void setOriginal_status(String original_status) {
		this.original_status = original_status;
	}
	
	@Column(name = "test_instance_id", columnDefinition = "bigint")
	public Long getTest_instance_id() {
		return test_instance_id;
	}
	
	public void setTest_instance_id(Long test_instance_id) {
		this.test_instance_id = test_instance_id;
	}
	
	@Column(name = "tester", columnDefinition = "char(8)")
	public String getTester() {
		return tester;
	}
	
	public void setTester(String tester) {
		this.tester = tester;
	}
	
	@Column(name = "exe_date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getExe_date() {
		return exe_date;
	}
	public void setExe_date(Date exe_date) {
		this.exe_date = exe_date;
	}
	
	@Column(name = "exe_time")
	@Temporal(TemporalType.TIME)
	public Date getExe_time() {
		return exe_time;
	}
	public void setExe_time(Date exe_time) {
		this.exe_time = exe_time;
	}
	public void setTest_id(Long test_id) {
		this.test_id = test_id;
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
