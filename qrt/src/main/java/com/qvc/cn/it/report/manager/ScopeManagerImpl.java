package com.qvc.cn.it.report.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qvc.cn.it.report.dao.ScopeDAO;
import com.qvc.cn.it.report.model.Country;
import com.qvc.cn.it.report.model.Project;
import com.qvc.cn.it.report.model.Scope;

@Service
@org.springframework.context.annotation.Scope("prototype")
public class ScopeManagerImpl extends BaseManagerImpl implements
		ScopeManager {
	
	@Resource
	private ScopeDAO scopeDAO;

	@Override
	public List<Scope> getAllScope() {
		return scopeDAO.listAll();
	}

	@Override
	public void addScope(Scope scope) {
		scopeDAO.save(scope);
	}

	@Override
	public void updateScope(Scope scope) {
		scopeDAO.update(scope);

	}

	@Override
	public void deleteScope(Scope scope) {
		scopeDAO.delete(scope);
	}

	@Override
	public void deleteScopeByCode(String code) {
	}

	@Override
	public List<Country> getAllCountry() {
		
		return scopeDAO.getAllCountries();
	}

	@Override
	public List<Map<String,Object>> getProjectsByCountryCode(String code) {
		List<Map<String,Object>> list 				= null;
		List<Object[]> scopeList 		= scopeDAO.getAllProjects(code);
		if(scopeList.size() > 0){
			list 						= new ArrayList<Map<String,Object>>();
			for(Object[] obj : scopeList){
				Map<String,Object> map 		= new HashMap<String,Object>();
				map.put("scope_id", obj[0]);
				map.put("project_name", ((Project)obj[1]).getProject_name());
				list.add(map);
			}
			
			return list;
		}
		
		return null;
	}

}
