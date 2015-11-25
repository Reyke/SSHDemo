package com.qvc.cn.it.report.manager;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.qvc.cn.it.report.dao.GenericDAO;
import com.qvc.cn.it.report.model.DataIssue;
import com.qvc.cn.it.report.model.Issues;
import com.qvc.cn.it.report.model.CommonIssue;
import com.qvc.cn.it.report.model.IssuesPOJO;
import com.qvc.cn.it.report.model.KnownIssue;
import com.qvc.cn.it.report.model.ScreenShot;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/com/qvc/cn/it/report/applicationContext-test.xml")

public class IssuesManagerTest {
	@Resource
	private IssuesManager issuesManager;
	
	@Resource(name="screenShotDAO")
	private GenericDAO<ScreenShot, Long> screenShotDAO;
	
	
	@Transactional
	@Test
	public void testGetIssueByCaseId(){
		Issues issue = issuesManager.getIssueByCaseId(3111L);
		System.out.println(issue);
		assertTrue(issue instanceof KnownIssue);
		
		
	}
	@Transactional
	@Test
	public void testUpdateIssue(){
		Issues issue = issuesManager.getIssueByCaseId(3223L);
//		
		IssuesPOJO pojo = new IssuesPOJO();
		pojo.setIssueType(issue.getIssueType());
		pojo.setCaseId(issue.getCaseId());
		pojo.setErrorDescription("test other issue err desc");
		
//		Set<ScreenShot> sss = new HashSet<ScreenShot>();
//		Set<ScreenShot> sss = ((CommonIssue)issue).getErrorScreenshotSet();
		
//		sss.add(screenShotDAO.getByKey(92L));
		
		Long[] ssIds = new Long[]{102L};
		
		
		pojo.setSsId(ssIds);
		
//		pojo.setErrorScreenshotSet(sss);
		issuesManager.updateIssue(pojo);
	}

}
