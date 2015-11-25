package com.qvc.cn.it.report.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CountryList {
	private List<Country> countries;

	public CountryList(List<Country> countries) {
		super();
		this.countries = countries;
	}

	public CountryList() {
		super();
		this.countries = new ArrayList<Country>();
	}
	
	
	public void add(Country report) {
		countries.add(report);
	}

	public List<Country> getcountries() {
		return countries;
	}

	public void setcountries(List<Country> countries) {
		this.countries = countries;
	}
	
	
}
