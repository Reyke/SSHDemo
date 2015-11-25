package com.qvc.cn.it.report.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qvc.cn.it.report.dao.IGenericDAO;
import com.qvc.cn.it.report.dao.PasswordUtilDAO;
import com.qvc.cn.it.report.model.Employee;

@Service
public class LoginManagerImpl extends BaseManagerImpl implements
LoginManager{

	@Resource(name="employeeDAO")
	private IGenericDAO<Employee, String> employeeDAO;
	
	@Resource(name="passwordUtilDAO")
	private PasswordUtilDAO passwordUtilDAO;
	
	@Transactional
	public List<Employee> isPasswordVaild(Employee emp) {
		
		String password = passwordUtilDAO.encodePassword(emp.getPassword());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("number", emp.getNumber());
		map.put("password", password);
		map.put("activeInd", "Y");
		
		List<Employee> list = employeeDAO.getEntity(map);
		
		return list ;
	}

}
