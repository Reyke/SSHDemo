package com.qvc.cn.it.report.dao;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.qvc.cn.it.report.model.Demo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/com/qvc/cn/it/report/applicationContext-test.xml")
public class DemoDAOTest{

	private static final String TEST_DEMO_NAME = "Eric's Demo";
	private static final String TEST_DEMO_NAME_CHANGED = "Eric's Demo 2";
	
	@Resource(name="demoDAO")
	IGenericDAO<Demo, Long> demoDAO;
	
	@Transactional
	@Test
	public void testDemoDAO() {
		// we'll remove all exists records before our testing.
		List<Demo> demos = demoDAO.listAll();
		if (demos != null && demos.size() > 0) {
			for (Demo demo : demos) {
				demoDAO.delete(demo);
			}
		}
		
		assertEquals("delete method not work.", 0, demoDAO.listAll().size());
		
		Demo demo = new Demo();
		demo.setName(TEST_DEMO_NAME);
		demoDAO.save(demo);
		
		assertEquals("save method not work.", 1, demoDAO.listAll().size());
		
		demo.setName(TEST_DEMO_NAME_CHANGED);
		demoDAO.update(demo);
		
		demo = demoDAO.listAll().get(0);
		assertEquals("update method not work.", TEST_DEMO_NAME_CHANGED, demo.getName());
		
		demo.setName(TEST_DEMO_NAME);
		demoDAO.saveOrUpdate(demo);
		
		assertEquals("saveOrUpdate method not work.", TEST_DEMO_NAME, demo.getName());
		
		demoDAO.delete(demo);
		
		assertEquals("delete moethod not work.", 0, demoDAO.listAll().size());
		
		demo = new Demo();
		demo.setName(TEST_DEMO_NAME);
		demoDAO.save(demo);

		long id = demoDAO.listAll().get(0).getId();
		demoDAO.deleteByKey(id);
		assertEquals("deleteByKey moethod not work.", 0, demoDAO.listAll().size());
		
	}
	
	
}
