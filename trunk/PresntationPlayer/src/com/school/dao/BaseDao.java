package com.school.dao;

import java.io.Serializable;
import java.util.List;

import com.school.model.BaseEntity;
import com.school.model.DetailedPresentation;

public interface BaseDao {
	public Long save(BaseEntity entity);

	public void update(BaseEntity entity);

	public void delete(BaseEntity entity);

	public void deleteAll(List<? extends BaseEntity> entities);

	public void saveOrUpdateAll(List<? extends BaseEntity> entities);

	public <T extends BaseEntity> T getEntity(final Serializable id, final Class<T> klass);

	public <T extends BaseEntity> List<T> getEntitiesWithConditions(final Class<T> klass,
			final Object[][] params, final String[] fetch);

	public <T extends BaseEntity> List<T> getAllEntities(final Class<T> klass);

	public List<DetailedPresentation> getPaginatedElements(Integer page, Long currentUserId, String searchq,
			Long cathegory, int perPage);

	public Long countDetailedPresentations(Long currentUserId, String searchq, Long cathegory);
	
	public <T> Long count(Class<T> klass);

	public void deleteUser(Long userID);
}
