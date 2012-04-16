package com.school.dao;

import java.io.Serializable;
import java.util.List;

import com.school.model.BaseEntity;

public interface BaseDao {
	public Long save(BaseEntity entity);

	public void update(BaseEntity entity);

	public void delete(BaseEntity entity);

	public void deleteAll(List<? extends BaseDao> entities);

	public void saveOrUpdateAll(List<? extends BaseEntity> entities);

	public <T extends BaseEntity> T getEntity(final Serializable id, final Class<T> klass);
}
