package com.qvc.cn.it.report.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PasswordUtilDAO {
	@Resource
	private SessionFactory sessionFactory;

	public String encodePassword(String password) {

		return (String) sessionFactory.getCurrentSession()
				.createSQLQuery("select distinct PASSWORD(?) from role")
				.setParameter(0, password)
				.uniqueResult();

	}

}
