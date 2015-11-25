package com.qvc.cn.it.report.dao;

import com.qvc.cn.it.report.model.Role;
//@Repository
public class RoleDAOImpl extends GenericDAO<Role, Short> implements RoleDAO {

	@Override
	public Role findByEmpNo(String empno) {
		
		return (Role) sessionFactory.getCurrentSession()
				.createQuery("from Role role join Employee emp on emp.role = role and emp.number = ?")
				.setParameter(0, empno).uniqueResult();
		
	}
	

	

}
