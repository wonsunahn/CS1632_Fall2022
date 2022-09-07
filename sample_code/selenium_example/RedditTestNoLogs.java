import static org.junit.Assert.*;

import java.util.logging.*;

import org.junit.*;
import org.openqa.selenium.*;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import org.openqa.selenium.logging.*;

import org.openqa.selenium.remote.*;

/**
 * As a user,
 * I would like to see reddit links in all sorts of ways,
 * So that I can know what is happening in the world
 * @author wlaboon
 *
 */

public class RedditTestNoLogs {

    static WebDriver driver;

    // Note that the @BeforeClass annotation runs ONCE
    // per class, before any of the test cases are run
    // Compare this to @Before, which runs before
    // EACH @Test-annotated method (test case)
    
    @BeforeClass
    public static void setUpDriver() {

	// Note that the logging level is a Java standard (thus the
	// use of a java.util class instead of something specific
	// to Selenium.  You can modify these levels yourself if,
	// for example, you would like to see only SEVERE errors.
	// They can be set to ALL (show all messages) or OFF (no messages)
	// or to any minimum level from FINEST (most verbose) to SEVERE
	// (only show the most egregious of errors).
	// A full lists of levels and descriptions are located at:
	// https://docs.oracle.com/javase/7/docs/api/java/util/logging/Level.html
	
	java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
	System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
	driver = new HtmlUnitDriver(); 
	
    }
    
    
    // Start at the home page for reddit for each test
    @Before
    public void setUp() throws Exception {
	driver.get("https://www.reddit.com");
    }

    // Given that I am on the main page
    // When I view the title
    // Then I see that it contains the word "reddit"
    @Test
    public void testShowsCorrectTitle() {
		
	// Simply check that the title contains the word "reddit"
		
	String title = driver.getTitle();
	assertTrue(title.contains("reddit"));
    }
	
    // Given that I am on the main page
    // When I view the header
    // Then I see that it contains "new", "rising", and "top" links
    @Test
    public void testHasCorrectHeaderLinks() {
		
	// Check for new, rising, and top links - if any of
	// these is not found, fail the test
		
	try {
	    driver.findElement(By.linkText("new"));
	    driver.findElement(By.linkText("rising"));
	    driver.findElement(By.linkText("top"));
	} catch (NoSuchElementException nseex) {
	    fail();
	}
    }
	
    // Given that I am on the main page
    // When I view the Remember Me section
    // Then I should see that it contains the phrase "remember me"
    @Test
    public void testHasRememberMe() {
		
	// Check that there is a remember-me element
	// that contains the text "remember me"
	// If it does not exist, or text is incorrect, fail test
		
	try {
	    WebElement e = driver.findElement(By.id("remember-me"));
	    String elementText = e.getText();
	    assertTrue(elementText.contains("remember me"));
	} catch (NoSuchElementException nseex) {
	    fail();
	}
    }
	
    // Given that I am on the main page
    // When I click on the "new" link
    // Then I should be redirected to the "new" page
    @Test
    public void testSeeNewLinks() {
		
	// find the "new" link and click on it
	// The page you go to should include "newest submissions"
	// in the title
		
	driver.findElement(By.linkText("new")).click();
	String newPageTitle = driver.getTitle();
	assertTrue(newPageTitle.contains("newest submissions"));
    }
	
    // Given that I am on the main page
    // And I am not logged in 
    // When I try to login with an valid username and invalid password
    // Then I am given the opportunity to reset the password
    @Test
    public void testBadPasswordResetLink() {
		
	// Enter username "meow", password "meow"
		
	driver.findElement(By.name("user")).sendKeys("dasbill");
	driver.findElement(By.name("passwd")).sendKeys("meow");
		
	// Look for the submit button (in the login div) and click
	// to attempt to login 
		
	WebElement loginDiv = driver.findElement(By.id("login_login-main"));
		
	WebElement submitButton = loginDiv.findElement(By.className("btn"));
	submitButton.click();
		
	// Check that there is a link to reset password and it is visible
		
	try {
	    WebElement resetPw = driver.findElement(By.linkText("reset password"));
	    assertTrue(resetPw.isDisplayed());
	} catch (NoSuchElementException nseex) {
	    fail();
	}
    }
	

}
