package test.school.selenium;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.regex.Pattern;

public class PlayerTest extends SeleneseTestCase {
	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://localhost:8080/PresentationPlayer/");
		selenium.setSpeed("600");
		selenium.start();
	}

	@Test
	public void testPlayerTest1() throws Exception {
		selenium.open("/PresentationPlayer");
		selenium.click("link=List presentations");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=search-input-pp", "the title");
		selenium.click("id=search-in-pp");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=imgS");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=outline_7");
		selenium.click("//div[@id='outline_6']/table/tbody/tr/td[2]");
		selenium.click("//div[@id='outline_5']/table/tbody/tr/td[2]");
		selenium.click("//div[@id='outline_4']/table/tbody/tr/td[2]");
		selenium.click("id=outline_3");
		selenium.click("//div[@id='outline_2']/table/tbody/tr/td[2]");
		selenium.click("css=#outline_12 > table");
		selenium.click("//div[@id='outline_12']/table/tbody/tr/td[2]");
		selenium.click("id=outline_9");
		selenium.click("id=outline_8");
		selenium.click("id=outline_7");
		selenium.type("id=ctrl-slide-input", "33");
		selenium.type("id=ctrl-slide-input", "5");
		selenium.click("id=next");
		selenium.click("id=last");
		selenium.click("id=stop");
		selenium.click("id=previous");
		selenium.click("id=stop");
		selenium.click("id=next");
		selenium.click("id=last");
		selenium.click("id=first");
		selenium.click("id=stop");
		selenium.click("id=next");
		selenium.click("id=previous");
		selenium.click("id=fullscreen");
		selenium.click("id=last");
		selenium.click("id=next");
		selenium.click("id=stop");
		selenium.click("id=play");
		selenium.click("id=previous");
		selenium.click("id=first");
		selenium.click("id=first");
		selenium.click("id=first");
		selenium.click("id=last");
		selenium.type("id=ctrl-slide-input", "5");
		selenium.click("id=fullscreen");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
