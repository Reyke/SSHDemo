package com.qvc.cn.it.report.dao;

import java.util.List;

import com.qvc.cn.it.report.model.TestSet;

public interface TestSetDAO extends IGenericDAO<TestSet, Long> {
	public List<TestSet> getSetByTaskID(long taskID);
}
