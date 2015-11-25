package com.qvc.cn.it.report.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.qvc.cn.it.report.model.TestCaseVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/com/qvc/cn/it/report/applicationContext-test.xml")

public class TreeGenerateTest {
	@Resource
	private TreeManager treeManager;
	@Test
	@Transactional
	public void testGenerateTreeDate(){
		
		//Map<String, List<TestCaseVO>> results = treeManager.generateTreeDate((long)1560, "00909932",false);
		//System.err.println(results);
		
	}
	
	
	

}
