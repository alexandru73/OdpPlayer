package com.school.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.school.dao.BaseDao;
import com.school.model.BaseEntity;

public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {

	public Long save(BaseEntity entity) {
		return (Long)getHibernateTemplate().save(entity);
	}

	public void update(BaseEntity entity) {
		getHibernateTemplate().update(entity);
	}

	public void delete(BaseEntity entity) {
		getHibernateTemplate().delete(entity);
	}

	public void deleteAll(List<? extends BaseDao> entities) {
		getHibernateTemplate().deleteAll(entities);
	}

}
