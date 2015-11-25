package com.qvc.cn.it.report.dao;

import com.qvc.cn.it.report.model.Role;

public interface RoleDAO extends IGenericDAO<Role, Short> {
	
	public Role findByEmpNo(String empno);

}
