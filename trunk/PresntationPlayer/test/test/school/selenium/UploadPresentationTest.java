package test.school.selenium;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.regex.Pattern;

public class UploadPresentationTest extends SeleneseTestCase {
	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://localhost:8080/PresentationPlayer/");
		selenium.setSpeed("600");
		selenium.start();
	}

	@Test
	public void testUploadPresentation() throws Exception {
		selenium.open("/PresentationPlayer/search");
		selenium.click("link=Sign in");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=j_username", "alex");
		selenium.type("id=j_password", "1234");
		selenium.click("//input[@value='Sign in']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Upload");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=title", "The new presentation");
		selenium.type("id=description", "description of new presentation");
		selenium.type("id=slideDuration", "12");
		selenium.select("id=combobox", "label=second cathegory");
		selenium.click("id=metaSubmit");
		selenium.type("id=fileData", "/media/6E10E2C710E2957D/salvari/Pt scoala/An4.sem1/TM/Cursuri TM/4.video/video-equip.ppt");
		selenium.click("id=submitUpload");
		selenium.click("id=agreeToLicence");
		selenium.click("id=submitUpload");
		selenium.click("link=Latest presentations");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
