package com.qvc.cn.it.report.manager;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.qvc.cn.it.report.dao.EmployeeDAO;
import com.qvc.cn.it.report.dao.IGenericDAO;
import com.qvc.cn.it.report.dao.PasswordUtilDAO;
import com.qvc.cn.it.report.model.Employee;
import com.qvc.cn.it.report.model.Role;

@Service
public class EmployeeManagerImpl extends BaseManagerImpl implements
		EmployeeManager {
	@Resource
	private EmployeeDAO employeeDAO;
	
	@Resource
	private IGenericDAO<Role, Short> roleDAO;
	
	@Resource
	private PasswordUtilDAO passwordUtilDAO;

	@Override
	@Cacheable(value="employees")
	public List<Employee> getAllEmployee() {
		return employeeDAO.listAllValidEmployees();
	}

	@Override
	@CacheEvict(value={"qaAdmin" ,"qaTester", "employees"}, allEntries = true)
	public Employee addEmployee(Employee emp, String operator) {
		if ("".equals(emp.getPassword())) {
			return null;
		}
		emp.setCreateBy(operator);
		emp.setUpdateAt(new Date());
		emp.setUpdateBy(operator);
		emp.setCreatedAt(new Date());
		emp.setActiveInd("Y");
		emp.setPassword(passwordUtilDAO.encodePassword(emp.getPassword()));

		employeeDAO.saveOrUpdate(emp);
		Role role = roleDAO.getByKey(emp.getRole().getId());
		emp.setRole(role);
		return emp;
	}

	@Override
	@CacheEvict(value={"qaAdmin" ,"qaTester", "employees"}, allEntries = true)
	public Employee updateEmployee(Employee emp, String operator) {
		Employee empDb = employeeDAO.getByKey(emp.getNumber());

		empDb.setUpdateAt(new Date());
		empDb.setUpdateBy(operator);
		if (null != emp.getPassword()&&!"".equals(emp.getPassword())) {
			empDb.setPassword(passwordUtilDAO.encodePassword(emp.getPassword()));
		}
		empDb.setName(emp.getName());
		empDb.setEmail(emp.getEmail());
		
		Role role = roleDAO.getByKey(emp.getRole().getId());

		empDb.setRole(role);

		return empDb;

	}

	@Override
	@CacheEvict(value={"qaAdmin" ,"qaTester", "employees"}, allEntries = true)
	public boolean deleteEmployee(Employee emp, String operator) {
		emp.setActiveInd("N");
		emp.setDeleteAt(new Date());
		emp.setDeleteBy(operator);
		employeeDAO.update(emp);
		return true;
	}

	@Override
	@CacheEvict(value={"qaAdmin" ,"qaTester", "employees"}, allEntries = true)
	public boolean deleteEmployeeByNumber(String number, String operator) {
		Employee emp = employeeDAO.loadByKey(number);
		emp.setActiveInd("N");
		emp.setDeleteAt(new Date());
		emp.setDeleteBy(operator);
		employeeDAO.update(emp);
		
		return true;
	}

	@Override
	public Employee getEmployeeByNumber(String number) {

		return employeeDAO.getByKey(number);
	}
	
	@Cacheable(value="qaTester")
	public List<Employee> getAllQATester(){
		Map<String,Object> map 		= new HashMap<String, Object>();
		map.put("activeInd", "Y");
		map.put("role.name", "TESTER");
		
		return employeeDAO.getEntityByAnd(map);
	}
	
	/**
	 * 
	 *  
	 * @description query all qa_admin employee
	 * 
	 * @title getAllQaAdmin
	 * @author changwei Lei <changwei lei@qvc.com>
	 * @return
	 */
	@Cacheable(value="qaAdmin")
	public List<Employee> getAllQaAdmin(){
		Map<String,Object> map 		= new HashMap<String, Object>();
		map.put("activeInd", "Y");
		map.put("role.name", "QA_ADMIN");
		
		return employeeDAO.getEntityByAnd(map);
	}

	@Override
	@Cacheable(value="roles")
	public List<Role> getAllValidRoles() {
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("visibleInd", "Y");
		
		return roleDAO.getEntity(key);
	}

}
