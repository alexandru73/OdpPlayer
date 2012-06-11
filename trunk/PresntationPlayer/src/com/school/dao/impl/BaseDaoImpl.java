package com.school.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.school.dao.BaseDao;
import com.school.model.BaseEntity;
import com.school.model.DetailedPresentation;
import com.school.model.User;

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

	public <T extends BaseEntity> List<T> getAllEntities(final Class<T> klass) {
		return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(klass);
				return criteria.list();
			}
		});
	}

	public <T extends BaseEntity> List<T> getEntitiesWithConditions(final Class<T> klass, final Object[][] params,
			final String[] fetch) {
		return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(klass);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						criteria.add(Restrictions.eq((String) params[i][0], params[i][1]));
					}
				}
				if (fetch != null) {
					for (int j = 0; j < fetch.length; j++) {
						criteria.setFetchMode(fetch[j], FetchMode.JOIN);
					}
				}
				return (List<T>) criteria.list();
			}
		});
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

	@SuppressWarnings("unchecked")
	public List<DetailedPresentation> getPaginatedElements(Integer page, User currentUser, String searchq,
			Long cathegory, int perPage) {

		List<DetailedPresentation> presentationList = null;
		if (page != null && page != 0) {
			Criteria crit = createCriteriaForSearch(currentUser, searchq, cathegory);
			int start = (page - 1) * perPage;
			crit.setFirstResult(start);
			crit.setMaxResults(perPage);
			presentationList = crit.list();
		}
		return presentationList;
	}

	@SuppressWarnings("unchecked")
	public void deleteUser(Long userID) {
		if (userID != null) {
			Session session = getSession();
			Transaction tr = session.beginTransaction();
			tr.begin();
			Query q=session.createQuery("delete from UserAuthority as a where a.user.id=:userId").setParameter("userId", userID);
			q.executeUpdate();
			q=session.createQuery("delete from User as u where u.id=:userId").setParameter("userId", userID);
			q.executeUpdate();
			tr.commit();
		}
	}

	private Criteria createCriteriaForSearch(User currentUser, String searchq, Long cathegory) {
		Session session = getSession();
		Criteria crit = session.createCriteria(DetailedPresentation.class);
		crit.add(Restrictions.eq("isToBeDeleted", false));
		if (currentUser != null) {
			crit.add(Restrictions.eq("user", currentUser));
			crit.setFetchMode("user", FetchMode.JOIN);
		}
		if (StringUtils.isNotBlank(searchq)) {
			Disjunction disjunction = Restrictions.disjunction();
			String[] first = searchq.split("\\s+");
			for (int i = 0; i < first.length; i++) {
				disjunction.add(Restrictions.like("title", "%" + first[i] + "%"));
				disjunction.add(Restrictions.like("description", "%" + first[i] + "%"));
			}
			disjunction.add(Restrictions.like("title", "%" + searchq + "%"));
			disjunction.add(Restrictions.like("description", "%" + searchq + "%"));
			crit.add(disjunction);
		}
		if (cathegory != null) {
			Criteria cath = crit.createCriteria("cathegory");
			cath.add(Restrictions.idEq(cathegory));
		}
		return crit;
	}

	public Long countDetailedPresentations(User currentUser, String searchq, Long cathegory) {
		Criteria crit = createCriteriaForSearch(currentUser, searchq, cathegory);
		crit.setProjection(Projections.rowCount());
		Long noOfElements = (Long) crit.uniqueResult();
		return noOfElements;
	}
}
