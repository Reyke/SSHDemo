package com.qvc.cn.it.report.dao;

import java.util.List;

import com.qvc.cn.it.report.model.Country;
import com.qvc.cn.it.report.model.Scope;

public interface ScopeDAO extends IGenericDAO<Scope, Integer> {

	public List<Country> getAllCountries();

	public List<Object[]> getAllProjects(String countryCode);

}
