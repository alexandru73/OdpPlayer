package test.school.test.db;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DbDeleteTest.class, DbInsertTest.class, DbQueryTest.class, DbUpdateTest.class })
public class AllTests {

}
