package com.school.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.school.dao.BaseDao;
import com.school.model.BaseEntity;

public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {

	public Long save(BaseEntity entity) {
		return (Long) getHibernateTemplate().save(entity);
	}

	public void saveOrUpdateAll(List<? extends BaseEntity> entities) {
		getHibernateTemplate().saveOrUpdateAll(entities);
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

	public <T extends BaseEntity> T getEntity(final Serializable id, final Class<T> klass) {
		return getHibernateTemplate().execute(new HibernateCallback<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(klass);
				criteria.add(Restrictions.idEq(id));
				return (T) criteria.uniqueResult();
			}
		});
	}
}
