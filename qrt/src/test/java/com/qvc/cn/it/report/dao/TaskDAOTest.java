package com.qvc.cn.it.report.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.qvc.cn.it.report.model.Task;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/com/qvc/cn/it/report/applicationContext-test.xml")
public class TaskDAOTest {
	@Resource
	private TaskDAO taskDAO;

	@Transactional
	@Test
	public void testGetAllLastestWeekTaskByEmpNo() {
		List<Object[]> results = taskDAO.getAllLastestWeekTask("00920178",false);
		System.err.println(results);

	}


	@Transactional
	@Test	
	public void testGetAllTaskByScopeId () {
		List<Task> tasks = taskDAO.getAllTaskByScopeId(1);
		for (Task t : tasks) {
			System.out.println(t.getTask_id());
		}
	}
}
