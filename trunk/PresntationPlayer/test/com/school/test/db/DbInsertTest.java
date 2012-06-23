package com.school.test.db;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Timed;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.school.dao.BaseDao;
import com.school.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/servlet-context.xml" })
public class DbInsertTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Resource(name = "baseDaoImpl")
	BaseDao baseDao;
	User user=null;

	@Test
	public void testGetDepartment() {
		user=new User("New USer", "1234", "USER TEST", "xx@yahoo.com");
		long x1 =baseDao.count(User.class);
		baseDao.save(user);
		long x2 =baseDao.count(User.class);		
		assertEquals(x1+1, x2);
	}
	
	@After
	public void beforeInsertUser(){
		baseDao.delete(user);
	}
	

}

