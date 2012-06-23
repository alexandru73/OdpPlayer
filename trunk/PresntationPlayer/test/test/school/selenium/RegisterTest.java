package test.school.selenium;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.regex.Pattern;

public class RegisterTest extends SeleneseTestCase {
	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://localhost:8080/PresentationPlayer/");
		selenium.setSpeed("600");
		selenium.start();
	}

	@Test
	public void testRegister() throws Exception {
		selenium.open("/PresentationPlayer");
		selenium.click("link=Sign in");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Register");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=username", "mugur_cc");
		selenium.type("id=password", "123123");
		selenium.type("id=retype-password", "123123");
		selenium.type("id=name", "alexvb");
		selenium.type("id=email", "anyyta18@yahoo.com");
		selenium.click("id=registerSubmit");
		selenium.click("link=Sign in");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=j_username", "mugur_is");
		selenium.type("id=j_password", "123123");
		selenium.click("//input[@value='Sign in']");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
