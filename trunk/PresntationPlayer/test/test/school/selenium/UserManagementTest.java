package test.school.selenium;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.regex.Pattern;

public class UserManagementTest extends SeleneseTestCase {
	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://localhost:8080/PresentationPlayer/");
		selenium.setSpeed("600");
		selenium.start();
	}

	@Test
	public void testUserManagement() throws Exception {
		selenium.open("/PresentationPlayer/login");
		selenium.type("id=j_username", "alex");
		selenium.type("id=j_password", "1234");
		selenium.click("//input[@value='Sign in']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Users details");
		selenium.waitForPageToLoad("30000");
		selenium.click("css=#1212416 > td..sorting_1");
		selenium.click("id=resetPassw");
		selenium.click("xpath=(//button[@type='button'])[4]");
		selenium.click("//tr[@id='2031618']/td[2]");
		selenium.click("id=resetPassw");
		selenium.click("xpath=(//button[@type='button'])[3]");
		selenium.click("css=#1114112 > td..sorting_1");
		selenium.click("css=#1212416 > td..sorting_1");
		selenium.click("id=deleteRow");
		selenium.click("xpath=(//button[@type='button'])[2]");
		selenium.click("id=userTable_last");
		selenium.click("id=userTable_first");
		selenium.click("link=2");
		selenium.click("link=1");
		selenium.select("name=userTable_length", "label=50");
		selenium.type("css=label > input[type=\"text\"]", "any");
		selenium.click("css=#1376260 > td..sorting_1");
		selenium.click("css=#1114112 > td..sorting_1");
		selenium.click("css=td..sorting_1");
		selenium.click("css=#1212424 > td..sorting_1");
		selenium.click("//tr[@id='1114112']/td[2]");
		selenium.click("link=Sign out");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
