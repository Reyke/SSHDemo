package com.qvc.cn.it.report.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qvc.cn.it.report.dao.TestCaseDAO;
import com.qvc.cn.it.report.model.Issues;
import com.qvc.cn.it.report.model.TestCaseVO;

@Service
public class TreeManagerImpl extends BaseManagerImpl implements TreeManager{
	@Resource
	private TestCaseDAO testCaseDAO;
	
	@Override
	public Map<String, List<TestCaseVO>> generateTreeDate(Long taskId, String empno,boolean isAdmin,String userId,String issueType) {
		List<Issues> temps = testCaseDAO.getAssignedTestCaseByTaskIDAndEmployNo(taskId, empno , isAdmin,userId,issueType);
		Map<String,List<TestCaseVO>> tmp = new HashMap<String, List<TestCaseVO>>();
		for(Issues iss:temps){
			String setName = iss.getTestCase().getTestSet().getSet_name();
			List<TestCaseVO> tcvs = tmp.get(setName);
			
			if(tcvs == null){
				tcvs = new  ArrayList<TestCaseVO>();
				tmp.put(setName, tcvs);
			}
			TestCaseVO tcv = new TestCaseVO();
			tcv.setCase_id(iss.getCaseId());
			tcv.setCase_name(iss.getTestCase().getCase_name());
			tcv.setStatus(iss.getStatus());
			
			tcvs.add(tcv);
		}
		
		
		return tmp;
	}
	
	
	

}
