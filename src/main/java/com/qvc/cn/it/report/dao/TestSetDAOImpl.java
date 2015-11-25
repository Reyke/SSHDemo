package com.qvc.cn.it.report.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.qvc.cn.it.report.model.TestSet;

@Repository
@Scope("prototype")
public class TestSetDAOImpl extends GenericDAO<TestSet, Long> implements TestSetDAO{
	@SuppressWarnings("unchecked")
	@Override
	public List<TestSet> getSetByTaskID(long taskID) {
		String hql = "from TestSet as testset where testset.task.task_id =?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setLong(0, taskID);
		return query.list();
	}
	
}
