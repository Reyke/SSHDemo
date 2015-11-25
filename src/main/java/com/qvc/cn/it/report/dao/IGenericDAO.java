package com.qvc.cn.it.report.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IGenericDAO<T, PK extends Serializable> {
	public PK save(T entity);

	public void saveAll(List<T> entities);
	
	public void delete(T entity);

	public void deleteAll(Set<T> list);
	
	public void deleteByKey(PK pk);
	
	public void update(T entity);

	public List<T> listAll();
	
	
	public List<T> getEntity(Map<String, Object> map);

	public List<T> getEntityListByKeyList(List<PK> keys);
	
	public T getByKey(final PK id);

	public T loadByKey(final PK id);
	
	public void saveOrUpdate(T entity);
	
	public void flush();
	

	public List<T> getEntityByOr(Map<String, Object> map);

	public List<T> getEntityByAnd(Map<String, Object> map);
	
	public List<T> getEntityByLikeAnd(Map<String,Object> map);
	

}
