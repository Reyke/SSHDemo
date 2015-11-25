package com.qvc.cn.it.report.manager;

import java.util.List;
import java.util.Map;

import com.qvc.cn.it.report.model.TestCaseVO;


public interface TreeManager extends BaseManager {

	public Map<String,List<TestCaseVO>> generateTreeDate(Long taskId, String empno,boolean isAdmin,String userId,String issueType);
	
	
}
