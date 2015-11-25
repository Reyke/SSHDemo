package com.qvc.cn.it.report.manager;

import java.util.List;
import java.util.Map;

import com.qvc.cn.it.report.model.Country;
import com.qvc.cn.it.report.model.Scope;

public interface ScopeManager extends BaseManager {
	
	public List<Scope> getAllScope();
	public void addScope(Scope scope);
	public void updateScope(Scope scope);
	public void deleteScope(Scope scope);
	public void deleteScopeByCode(String scope);
	
	public List<Country> getAllCountry();
	
	public List<Map<String,Object>> getProjectsByCountryCode(String code);

}
