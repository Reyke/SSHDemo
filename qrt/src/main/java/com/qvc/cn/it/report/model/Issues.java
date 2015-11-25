package com.qvc.cn.it.report.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.qvc.cn.it.report.utils.Constants;

@XmlRootElement
@Table(name = "issues")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "issue_type")
@DiscriminatorValue(value = Constants.ISSUE_TYPE_UNDETERMINED)
public class Issues {

	private Employee responser;
	private Long taskId;
	private Long testSetId;
	private int versionDateNumber;
	private Long instanceId;

	private TestCase testCase;
	private String issueType;
	private Long refFolderId;
	private Long testId;
	
	@Column(name = "test_id", columnDefinition = "bigint")
	public Long getTestId() {
		return testId;
	}
	public void setTestId(Long testId) {
		this.testId = testId;
	}
	
	@Column(name = "ref_folder_id", columnDefinition = "bigint")
	public Long getRefFolderId() {
		return refFolderId;
	}
	public void setRefFolderId(Long refFolderId) {
		this.refFolderId = refFolderId;
	}
	
	@Column(name = "issue_type",insertable=false,updatable=false)
	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	private Long caseId;

	@Id
	@GeneratedValue(generator = "SharedPrimaryKeyGenerator")
	@GenericGenerator(name = "SharedPrimaryKeyGenerator", strategy = "foreign", parameters = @Parameter(name = "property", value = "testCase"))
	@Column(name = "case_id")
	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	@OneToOne
	@PrimaryKeyJoinColumn
	public TestCase getTestCase() {
		return testCase;
	}

	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}

	@Column(name = "TEST_INSTANCE_ID", columnDefinition = "bigint")
	public Long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	@ManyToOne
	@JoinColumn(name = "emp_nbr")
	public Employee getResponser() {
		return responser;
	}

	public void setResponser(Employee responser) {
		this.responser = responser;
	}

	@Column(name = "task_id", columnDefinition = "bigint")
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	@Column(name = "set_id", columnDefinition = "bigint")
	public Long getTestSetId() {
		return testSetId;
	}

	public void setTestSetId(Long testSetId) {
		this.testSetId = testSetId;
	}

	@Column(name = "version_date_nbr", columnDefinition = "int")
	public int getVersionDateNumber() {
		return versionDateNumber;
	}

	public void setVersionDateNumber(int versionDateNumber) {
		this.versionDateNumber = versionDateNumber;
	}

	@Override
	public String toString() {
		return  "Issues [responser=" + responser + ", taskId=" + taskId
				+ ", testSetId=" + testSetId + ", versionDateNumber="
				+ versionDateNumber + ", instanceId=" + instanceId
				+ ", testCase=" + testCase + ", caseId=" + caseId + "]";
	}

	@Transient
	public boolean isValidate() {
		return false;
	}

	protected boolean isEmpty(String target) {
		if (target == null || target.equals(""))
			return true;
		return false;
	}
	
	@Transient
	public String getType(){
		return this.getClass().getSimpleName();
		
	}

	@Transient
	public String getStatus(){
		String result = null;
		if(isValidate()){
			result = "green";
		}else if (!Constants.ISSUE_TYPE_UNDETERMINED.equals(getIssueType())) {
			result = "orange";
//		} else if("Failed".equalsIgnoreCase(getTestCase().getOriginal_status())){
//			result = "red";
		}else{
			result = "yellow";
		}
		return result;
	}
	

}
