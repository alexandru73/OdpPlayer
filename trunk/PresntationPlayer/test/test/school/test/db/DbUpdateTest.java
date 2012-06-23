package test.school.test.db;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.school.dao.BaseDao;
import com.school.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test/spring/servlet-context.xml" })
public class DbUpdateTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Resource(name = "baseDaoImpl")
	BaseDao baseDao;
	User user = null;

	@Before
	public void beforeDeleteUser() {
		user = new User("New USer", "1234", "USER TEST", "xx@yahoo.com");
		Long id = baseDao.save(user);
		user.setId(id);
	}

	@Test
	public void testGetDepartment() {
		User user2 = baseDao.getEntity(user.getId(), User.class);
		assertEquals(user, user2);
		user2.setEmail("aa@yahoo.com");
		baseDao.update(user2);
		user = baseDao.getEntity(user.getId(), User.class);
		assertEquals(user2, user);
	}

	@After
	public void beforeInsertUser() {
		baseDao.delete(user);
	}

	public void setDepartmentDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
}
