package com.qvc.cn.it.report.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qvc.cn.it.report.dao.IGenericDAO;
import com.qvc.cn.it.report.model.Demo;

@Service
public class DemoManagerImpl extends BaseManagerImpl implements
		DemoManager {

	@Resource(name="demoDAO")
	private IGenericDAO<Demo, Long> demoDAO;

	@Transactional
	public Demo find(long id) {
		return demoDAO.getByKey(id);
	}
	
	@Transactional
	public List<Demo> list() {
		return demoDAO.listAll();
	}

}
