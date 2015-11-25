package com.qvc.cn.it.report.control;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qvc.cn.it.report.manager.EmployeeManager;
import com.qvc.cn.it.report.model.Employee;
import com.qvc.cn.it.report.model.EmployeeList;
import com.qvc.cn.it.report.model.Role;

@Controller
public class EmployeeController extends BaseController {
	@Resource
	private EmployeeManager employeeManager;

	@RequestMapping(method = RequestMethod.GET, value = "/employee")
	public ModelAndView getAllEmployee() {
		return new ModelAndView(MARSHALLED_VIEW, "object", new EmployeeList(
				employeeManager.getAllEmployee()));

	}

	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Employee registerEmployee(HttpServletRequest request) {
		Employee emp = validateRegisterOrUpdate(request);
		if(emp == null||"".equals(emp.getPassword())){
		
			return null;
		}
		return employeeManager.addEmployee(emp,
				request.getParameter("operator_no"));

	}

	@ResponseBody
	@RequestMapping(value = "/updateEmp", method = RequestMethod.POST)
	public Employee updateEmployee(HttpServletRequest request) {
		Employee emp = validateRegisterOrUpdate(request);
		if (null != emp) {

			emp = employeeManager.updateEmployee(emp,
					request.getParameter("operator_no"));
		}
		return emp;

	}

	@ResponseBody
	@RequestMapping(value = "/deleteEmp", method = RequestMethod.POST)
	public boolean deleteEmployee(HttpServletRequest request) {
		String number = request.getParameter("empno");

		if (number == null || "".equals(number)) {
			return false;
		}

		return employeeManager.deleteEmployeeByNumber(number,
				request.getParameter("operator_no"));

	}

	private Employee validateRegisterOrUpdate(HttpServletRequest request) {

		String number = request.getParameter("empno");
		String email = request.getParameter("empemail");
		String name = request.getParameter("empname");
		String roleId = request.getParameter("emprole");
		String password = request.getParameter("password");
		String opt_number = request.getParameter("operator_no");
		if (null == number || "".equals(number) || email == null
				|| "".equals(email) || name == null || "".equals(name)
				|| roleId == null || "".equals(roleId) || opt_number == null
				|| "".equals(opt_number)) {
			return null;
		}
		Role role = new Role();
		if (null != roleId) {
			role.setId(Short.parseShort(roleId));
		}

		Employee emp = new Employee();
		emp.setNumber(number.trim());
		emp.setEmail(email.trim());
		emp.setName(name.trim());
		emp.setPassword(password);
		emp.setActiveInd("Y");

		// request.getSession().
		emp.setRole(role);
		return emp;
	}
	
	@ResponseBody
	@RequestMapping("/allValidRoles")
	public List<Role> getAllValidRoles(){
		return employeeManager.getAllValidRoles();
	}
	
	
	/**
	 * 
	 *  
	 * @description get All of Tester , not include admin
	 * 
	 * @title getAllQATester
	 * @author changwei Lei <changwei lei@qvc.com>
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getAllQATester")
	public ModelAndView getAllQATester() {
		
		return new ModelAndView(MARSHALLED_VIEW, "object", employeeManager.getAllQATester());
	}
	
	/**
	 * 
	 *  
	 * @description get all of qa admin employee
	 * 
	 * @title getAllQAAdmin
	 * @author changwei Lei <changwei lei@qvc.com>
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getAllQAAdmin")
	public ModelAndView getAllQAAdmin(){
		
		return new ModelAndView(MARSHALLED_VIEW, "object", employeeManager.getAllQaAdmin());
	}
	
}
