package com.school.security;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.school.dao.BaseDao;
import com.school.model.User;

public class SecureLoginService implements UserDetailsService {
	private BaseDao baseDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Object conditions[][] = { { "username", username } };
		String fetch[] = { "authorities" };
		List<User> userList = baseDao.getEntitiesWithConditions(User.class, conditions, fetch);
		if (CollectionUtils.isNotEmpty(userList)) {
			User user = userList.get(0);
			return user;
		} else {
			throw new UsernameNotFoundException("Username is not valid");
		}
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

}
