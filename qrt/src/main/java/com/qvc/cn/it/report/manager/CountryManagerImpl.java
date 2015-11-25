package com.qvc.cn.it.report.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.qvc.cn.it.report.dao.IGenericDAO;
import com.qvc.cn.it.report.model.Country;

@Service
@Scope("prototype")
public class CountryManagerImpl extends BaseManagerImpl implements
		CountryManager {
	@Resource
	private IGenericDAO<Country, String> countryDAO;

	@Override
	public List<Country> getAllCountry() {
		return countryDAO.listAll();
	}

	@Override
	public void addCountry(Country country) {
		countryDAO.save(country);
	}

	@Override
	public void updateCountry(Country country) {
		countryDAO.update(country);

	}

	@Override
	public void deleteCountry(Country country) {
		countryDAO.delete(country);
	}

	@Override
	public void deleteCountryByCode(String code) {
		countryDAO.getByKey(code);
	}

}
