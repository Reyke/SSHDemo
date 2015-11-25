package com.qvc.cn.it.report.control;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qvc.cn.it.report.manager.FileUploadManager;
import com.qvc.cn.it.report.manager.IssuesManager;
import com.qvc.cn.it.report.model.DataIssueType;
import com.qvc.cn.it.report.model.DefectStatusType;
import com.qvc.cn.it.report.model.IssueType;
import com.qvc.cn.it.report.model.Issues;
import com.qvc.cn.it.report.model.IssuesPOJO;

@Controller
@RequestMapping("/issues")
public class IssuesController {
	@Resource
	private IssuesManager issuesManager;
	
	@Resource
	private FileUploadManager fileUploadManager;

	@ResponseBody
	@RequestMapping(value = "/{caseId}", method = RequestMethod.GET)
	public Issues getIssue(@PathVariable(value = "caseId") Long caseId) {

		return issuesManager.getIssueByCaseId(caseId);
	}

	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateIssue(@ModelAttribute("issuePOJO") IssuesPOJO issuePOJO) {

		System.err.println(issuePOJO);
		return issuesManager.updateIssue(issuePOJO);
	}

	@ResponseBody
	@RequestMapping(value = "/getAllIssueType", method = RequestMethod.GET)
	public List<IssueType> getAllIssueType() {

		return issuesManager.getAllIssueType();
	}
	
	@ResponseBody
	@RequestMapping(value = "/getAllDataIssueType", method = RequestMethod.GET)
	public List<DataIssueType> getAllDataIssueType() {

		return issuesManager.getAllDataIssueType();
	}

	@ResponseBody
	@RequestMapping(value = "/getAllDefectStatusType", method = RequestMethod.GET)
	public List<DefectStatusType> getAllDefectStatusType() {
		return issuesManager.getAllDefectStatusType();
				
	}
	
	@ResponseBody
	@RequestMapping(value = "/getImgServiceUrl", method = RequestMethod.GET)
	public String getImgServiceUrl(){
		
		return fileUploadManager.getFileURL();
	}

}
