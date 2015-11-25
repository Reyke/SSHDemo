package com.qvc.cn.it.report.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.qvc.cn.it.report.model.Country;
import com.qvc.cn.it.report.model.Scope;

@Repository
@org.springframework.context.annotation.Scope("prototype")
public class ScopeDAOImpl extends GenericDAO<Scope, Integer> implements ScopeDAO {

	@Override
	public List<Country> getAllCountries() {
		String hql 				= "SELECT DISTINCT(country_code) FROM Scope ";
		@SuppressWarnings("unchecked")
		List<Country>  list 	= this.getSessionFactory().getCurrentSession().createQuery(hql).list();
		list 					= (list != null && list.size() > 0) ? list : new ArrayList<Country>();
		
		return list;
	}
	
	@Override
	public List<Object[]> getAllProjects(String counytryCode) {
		String hql 				= "SELECT scope_id,project_name FROM Scope WHERE country_code ='"
									+ counytryCode 
									+ "'";
		@SuppressWarnings("unchecked")
		List<Object[]>  list 	= this.getSessionFactory().getCurrentSession().createQuery(hql).list();
		list 					= (list != null && list.size() > 0) ? list : new ArrayList<Object[]>();
		
		return list;
	}

}
