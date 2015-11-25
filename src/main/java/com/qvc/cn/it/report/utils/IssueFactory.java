package com.qvc.cn.it.report.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.qvc.cn.it.report.model.CommonIssue;
import com.qvc.cn.it.report.model.DataIssue;
import com.qvc.cn.it.report.model.DeviceIssue;
import com.qvc.cn.it.report.model.Issues;
import com.qvc.cn.it.report.model.IssuesPOJO;
import com.qvc.cn.it.report.model.KnownIssue;
import com.qvc.cn.it.report.model.ObjectChange;
import com.qvc.cn.it.report.model.OtherIssue;
import com.qvc.cn.it.report.model.PerformanceIssue;
import com.qvc.cn.it.report.model.PossibleDefect;
import com.qvc.cn.it.report.model.RerunPassed;
import com.qvc.cn.it.report.model.ScreenShot;
import com.qvc.cn.it.report.model.ScriptIssue;
import com.qvc.cn.it.report.model.TestCase;

public class IssueFactory {
	public static Issues generateIssuesFromTestCase(TestCase testCase) {
		
		Issues issues = new Issues();
		issues.setTestCase(testCase);
		issues.setTaskId(testCase.getTask().getTask_id());
		issues.setInstanceId(testCase.getTest_instance_id());
		issues.setTestSetId(testCase.getTestSet().getSet_id());
		issues.setVersionDateNumber(testCase.getVersionDateNumber());
		issues.setRefFolderId(testCase.getRefFolderId());
		issues.setTestId(testCase.getTest_id());
		
		return issues;
	}
	
	public static Issues createIssueInstance(Issues issue, IssuesPOJO pojo) {
		
		Issues result = null;
		if(null==pojo.getIssueType()){
			
			return result;
		}
		
		String manualPassed = pojo.getManualPassedInd();
		manualPassed = manualPassed == null ? "N" : manualPassed;
		
		if (Constants.ISSUE_TYPE_DATAISSUE.equals(pojo.getIssueType())) {
			DataIssue dataIssue = fillCommonPropertiesForIssues(new DataIssue(issue),pojo,issue);
			dataIssue.setDataType(pojo.getDataType());
			dataIssue.setErrorData(pojo.getErrorData());
			result = dataIssue;
	
		}else if(Constants.ISSUE_TYPE_DEVICEISSUE.equals(pojo.getIssueType())){
			DeviceIssue deviceIssue = fillCommonPropertiesForIssues(new DeviceIssue(issue),pojo,issue);
			deviceIssue.setDeviceName(pojo.getDeviceName());
			result = deviceIssue;
			
		}else if(Constants.ISSUE_TYPE_KNOWISSUE.equals(pojo.getIssueType())){
			KnownIssue knownIssue = fillCommonPropertiesForIssues(new KnownIssue(issue),pojo,issue);
			knownIssue.setActualResult(pojo.getActualResult());
			knownIssue.setData(pojo.getData());
			knownIssue.setDefectNumber(pojo.getDefectNumber());
			knownIssue.setDefectStatus(pojo.getDefectStatus());
			knownIssue.setErrorPageName(pojo.getErrorPageName());
			knownIssue.setExpectedResult(pojo.getExpectedResult());
			knownIssue.setReproducedSteps(pojo.getReproducedSteps());
			knownIssue.setTitle(pojo.getTitle());
			
			result = knownIssue;
			
		}else if(Constants.ISSUE_TYPE_OBJECTCHANGE.equals(pojo.getIssueType())){
			ObjectChange objectChange = fillCommonPropertiesForIssues(new ObjectChange(issue),pojo,issue);
			objectChange.setAfterChange(pojo.getAfterChange());
			objectChange.setBeforeChange(pojo.getBeforeChange());
			objectChange.setErrorPageName(pojo.getErrorPageName());
			objectChange.setModuleActionName(pojo.getModuleActionName());
			
			result = objectChange;
		}else if(Constants.ISSUE_TYPE_OTHERISSUE.equals(pojo.getIssueType())){
			OtherIssue otherIssue = fillCommonPropertiesForIssues(new OtherIssue(issue),pojo,issue);
			otherIssue.setTitle(pojo.getTitle());
			otherIssue.setManualPassedInd(manualPassed);
			
			result = otherIssue;
		}else if(Constants.ISSUE_TYPE_PERFORMANCEISSUE.equals(pojo.getIssueType())){
			PerformanceIssue performanceIssue = fillCommonPropertiesForIssues(new PerformanceIssue(issue),pojo,issue);
			performanceIssue.setErrorPageName(pojo.getErrorPageName());
			performanceIssue.setModuleActionName(pojo.getModuleActionName());
			
			result = performanceIssue;
		}else if(Constants.ISSUE_TYPE_POSSIBLEDEFECT.equals(pojo.getIssueType())){
			PossibleDefect possibleDefect = fillCommonPropertiesForIssues(new PossibleDefect(issue),pojo,issue);
			possibleDefect.setActualResult(pojo.getActualResult());
			possibleDefect.setData(pojo.getData());
			possibleDefect.setDefectNumber(pojo.getDefectNumber());
			possibleDefect.setDefectStatus(pojo.getDefectStatus());
			possibleDefect.setErrorPageName(pojo.getErrorPageName());
			possibleDefect.setExpectedResult(pojo.getExpectedResult());
			possibleDefect.setReproducedSteps(pojo.getReproducedSteps());
			possibleDefect.setTitle(pojo.getTitle());
			
			result = possibleDefect;
		}else if(Constants.ISSUE_TYPE_RERUNPASSED.equals(pojo.getIssueType())){
			RerunPassed rerunPassed = fillCommonPropertiesForIssues(new RerunPassed(issue),pojo,issue);
			
			result = rerunPassed;
		}else if(Constants.ISSUE_TYPE_SCRIPTISSUE.equals(pojo.getIssueType())){
			ScriptIssue scriptIssue = fillCommonPropertiesForIssues(new ScriptIssue(issue),pojo,issue);
			scriptIssue.setErrorPageName(pojo.getErrorPageName());
			scriptIssue.setModuleActionName(pojo.getModuleActionName());
			scriptIssue.setManualPassedInd(manualPassed);
			
			result = scriptIssue;
		}
		
		result.setTestId(pojo.getTestId());
		result.setRefFolderId(pojo.getRefFolderId());
		result.setIssueType(pojo.getIssueType());
		
		return result;
		
	}
	
	public static <T extends CommonIssue> T fillCommonPropertiesForIssues (T issue, IssuesPOJO pojo,Issues origIssues) {
		issue.setErrorDescription(pojo.getErrorDescription());
		
		if(null == pojo.getSsId()){
			pojo.setSsId(new Long[0]);
		}

		CommonIssue commonIssue = null;
		Set<ScreenShot> screenShots = new HashSet<ScreenShot>();
		if(origIssues instanceof CommonIssue){
			commonIssue = (CommonIssue)origIssues;
			screenShots .addAll(commonIssue.getErrorScreenshotSet());
			if(null == pojo.getSsId()){
				pojo.setSsId(new Long[0]);
			}
			List<Long> ssList = Arrays.asList(pojo.getSsId());

			for (ScreenShot ss : commonIssue.getErrorScreenshotSet()) {
				if (!ssList.contains(ss.getSsId())) {
					ss.setDelInd(true);
				}
			}
		}else{
			
		}
		
		ScreenShot screenShot = null;
		for(Long ssId: pojo.getSsId()){
			screenShot = new ScreenShot();
			screenShot.setSsId(ssId);
			
			screenShots.add(screenShot);
		}
		issue.setErrorScreenshotSet(screenShots);
		
		
		return issue;
		
	}
}
