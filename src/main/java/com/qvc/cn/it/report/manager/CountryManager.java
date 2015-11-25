package com.qvc.cn.it.report.manager;

import java.util.List;

import com.qvc.cn.it.report.model.Country;

public interface CountryManager extends BaseManager {
	
	public List<Country> getAllCountry();
	public void addCountry(Country country);
	public void updateCountry(Country country);
	public void deleteCountry(Country country);
	public void deleteCountryByCode(String code);

}
