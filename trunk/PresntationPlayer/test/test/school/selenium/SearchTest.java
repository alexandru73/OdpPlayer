package test.school.selenium;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.regex.Pattern;

public class SearchTest extends SeleneseTestCase {
	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://localhost:8080/PresentationPlayer/");
		selenium.setSpeed("600");
		selenium.start();
	}

	@Test
	public void testSearch() throws Exception {
		selenium.open("/PresentationPlayer/presentation?p=57b0ce1e-366d-43a5-87fb-57de49ebb8e1");
		selenium.click("link=Sign in");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=List presentations");
		selenium.waitForPageToLoad("30000");
		selenium.select("id=combobox", "label=second cathegory");
		selenium.select("id=combobox", "label=first cathegory");
		selenium.click("css=option[value=\"1\"]");
		selenium.select("id=combobox", "label=All");
		selenium.select("id=resultsPerPage", "label=20");
		selenium.select("id=resultsPerPage", "label=30");
		selenium.type("id=search-input-pp", "asda");
		selenium.click("id=search-in-pp");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=search-input-pp", "the tutle");
		selenium.click("id=search-in-pp");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=search-input-pp", "the%20title");
		selenium.click("id=search-in-pp");
		selenium.waitForPageToLoad("30000");
		selenium.click("//div[@id='result-detail']/p[4]");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=List presentations");
		selenium.waitForPageToLoad("30000");
		selenium.select("id=combobox", "label=second cathegory");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
