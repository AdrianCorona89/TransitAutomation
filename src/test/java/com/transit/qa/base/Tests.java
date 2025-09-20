package com.transit.qa.base;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.transit.qa.pages.TripPage;
import io.github.bonigarcia.wdm.WebDriverManager;

// Tests JAVE FILE
// This file contains all tests and assertions for the tests
public class Tests {
	public WebDriver driver;
	
	// Tests shared constants
    private static final String ORIGIN = "5333 Casgrain Avenue, Montréal";
    private static final String ORIGIN_SUGG = "5333 Casgrain Avenue";
    private static final String DEST = "1321 Rue Ste-Catherine O, Montréal";
    private static final String DEST_SUGG = "1321 Saint-Catherine Street West";
    private static final String OUT_OF_RANGE_CITY = "Toronto";
	
    // Method to be used before each test case
    // By default, the test cases will run in headless mode
    // If you want to turn this off, you can set the boolean runHeadless false
	@BeforeMethod
	public void setUp() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        
        // boolean to run it headless
        boolean runHeadless = true; 
        if (runHeadless) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }
        
		driver = new ChromeDriver(options);
		driver.get("https://transitapp.com/en/trip");
		
	}
	
	// Method to be used after each test case
	// It will simply quit the browser to avoid any potential flakiness in the results 
	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	// Happy Path - Trip Search
	// It plans a trip and verifies that at least one itinerary 
	// is displayed and that one of the results is a walking-only trip
	@Test
	public void tripSearch() throws InterruptedException {
		System.out.println("This is a Trip Search test...");
		
		// Soft assert to run two verifications
		SoftAssert softAssert = new SoftAssert();
		
		// Using POM for better modularity
		TripPage tripPage = new TripPage(driver);
		tripPage.enterOrigin(ORIGIN);
		tripPage.selectSuggestion(ORIGIN_SUGG);
		tripPage.enterDestination(DEST);
		tripPage.selectSuggestion(DEST_SUGG);
		
		// Log current URL
		tripPage.logFinalUrl();
		
		// Soft asserting verifications to confirm itineraries are properly displayed
		softAssert.assertTrue(tripPage.isItineraryDisplayed(), "Itinerary failed to be displayed!");
		softAssert.assertTrue(tripPage.isWalkingDisplayed(),"Walking only trip failed to be displayed");
		
		// Assert all soft asserts
		softAssert.assertAll();
		
	}	
	
	// Arrive By - Specific Date/Time
	// It plans a trip with the planner set to Arrive by 12:00pm
	// In order to avoid any potential issues with the time web element not being enabled
	// it will, by default, set the planner for the first of the following month
	// This test verifies that the results include at least a certain number of transit plans
	// This number can be changed and is a local variable for this test
	@Test
	public void arriveBy () throws InterruptedException {
		System.out.println("This is an Arrive By test...");
		
		// Local variables used for this specific test
		String time = "12:00 PM";
		int numberOfOptions = 3;
		SoftAssert softAssert = new SoftAssert();
		
		// Using POM
		TripPage tripPage = new TripPage(driver);
		
		// Action: Plan a trip
		tripPage.enterOrigin(ORIGIN);
		tripPage.selectSuggestion(ORIGIN_SUGG);
		tripPage.enterDestination(DEST);
		tripPage.selectSuggestion(DEST_SUGG);
		
		// Action: Use Arrive by feature
		tripPage.clickOptions();
		tripPage.selectArriveBy();
		tripPage.selectCalendar();
		tripPage.selectTime(time);
		tripPage.saveOptions();
		
		// Log current URL
		tripPage.logFinalUrl();
		
		// Validation to confirm number of options displayed is correct
		softAssert.assertTrue(tripPage.areOptionsDisplayed(numberOfOptions), "Number of transit options displayed is not correct!");
		softAssert.assertAll();
	}
	
	// Out-of-Range - Error Message
	// Plan an out of range trip to Toronto
	// It verifies that the exact error message is displayer to the user
	// This error message is a local variable for the test
	@Test
	public void outOfRangeTrip() {
		System.out.println("This is an Out Of Range Error Message test...");
		
		// Local variable for what is expected on the error message
		String errorMessage = "You're going too far!";
		
		SoftAssert softAssert = new SoftAssert();
		
		// Using POM
		TripPage tripPage = new TripPage(driver);
		
		// Action: Plan trip
		tripPage.enterOrigin(ORIGIN);
		tripPage.selectSuggestion(ORIGIN_SUGG);
		tripPage.enterDestination(OUT_OF_RANGE_CITY);
		tripPage.selectSuggestion(OUT_OF_RANGE_CITY);
		
		// Log current URL
		tripPage.logFinalUrl();
		
		// Assertion to confirm error message is properly displayed
		softAssert.assertTrue(tripPage.isErrorMessageDisplayed(errorMessage), "Out of range error message is incorrect!");
		softAssert.assertAll();	
	}
}
