package test.school.test.db;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.school.dao.BaseDao;
import com.school.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test/spring/servlet-context.xml" })
public class DbQueryTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Resource(name = "baseDaoImpl")
	BaseDao baseDao;

	@Test
	public void testGetDepartment() {
		long x2 = baseDao.count(User.class);
		List<User> userlist = baseDao.getAllEntities(User.class);
		assertEquals(x2, userlist.size());
	}

}
