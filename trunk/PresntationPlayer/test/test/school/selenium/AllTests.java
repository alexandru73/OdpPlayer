package test.school.selenium;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PlayerTest.class, RegisterTest.class, SearchTest.class, UploadPresentationTest.class,
		UserManagementTest.class })
public class AllTests {

}
