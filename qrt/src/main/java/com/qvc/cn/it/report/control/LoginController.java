package com.qvc.cn.it.report.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qvc.cn.it.report.manager.LoginManager;
import com.qvc.cn.it.report.model.Employee;

@Controller
public class LoginController extends BaseController {
	
	@Autowired
	private LoginManager loginManager;

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public Employee isPasswordVaild(HttpServletRequest request,HttpServletResponse response, String name,String password,ModelMap modelMap) {
		Employee employee = new Employee();
		employee.setNumber(name);
		employee.setPassword(password);
		modelMap.put("Employee", employee);
		
		List<Employee> list = loginManager.isPasswordVaild(employee);
		if(list.size() >0){
			return list.get(0);
		}else{
			return null ;
		}
		
	}
	
}
