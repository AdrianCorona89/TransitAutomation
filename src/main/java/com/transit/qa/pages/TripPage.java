package com.transit.qa.pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// TripPage JAVA FILE:
// This file contains all locators and helper methods needed for my tests.
public class TripPage {
	
	public WebDriver driver;
	public WebDriverWait wait;
	
	// LOCATORS:
	// These can be found as soon as the page opens
	@FindBy(xpath="//input[@placeholder='Origin']")
	WebElement originSearch;
	
	@FindBy(xpath="//input[@placeholder='Destination']")
	WebElement destinationSearch;
	
	@FindBy(xpath="//button//span[contains(text(),'Options')]")
	WebElement options;
	
	// CONSTRUCTOR:
	// It initializes attributes and initial locators
	public TripPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		PageFactory.initElements(driver, this);	
	}
	
	// HELPER METHODS
	// Will log the final URL found at the end of each test case, before assertions are executed
	public void logFinalUrl() {
		System.out.println("Final URL: " + driver.getCurrentUrl());
	}
	
	// Enter the origin address
	// Must be used with selectSuggestion(String) to end up action
	public void enterOrigin(String s) {
		originSearch.sendKeys(s);
	}
	
	// Enter destination address
	// Must be used with selectSuggestion(String) to end up action
	public void enterDestination(String s) {
		destinationSearch.sendKeys(s);
	}
	
	// Select suggestion
	// Works with origin and destination search boxes
	public void selectSuggestion(String s) {
		WebElement suggestion = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//li//*[contains(text(), '" + s + "')]")));
		suggestion.click();
	}
	
	// Helper method to confirm an itinerary proposition is displayed 
	public boolean isItineraryDisplayed() {
	    try {
	        WebElement itinerary = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='option']")));
	        return itinerary.isDisplayed();
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	// Helper method to confirm a walking itinerary is displayed
	public boolean isWalkingDisplayed() {
		try {
			WebElement walking = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[contains(text(),'Walk')]")));
			return walking.isDisplayed();			
		} catch (Exception e) {
			return false;
		}		
	}
	
	// Helper method for Arrive By. It opens the options modal
	// This method could be used for future options testing
	public void clickOptions() {
		options.click();
	}
	
	// Helper method to be used in Arrive By test case
	public void selectArriveBy() {
		try {
			// Find the ArriveBy button and select it
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='select departure type']"))).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-key='arriveBy']"))).click();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// Helper method to be used in testing in Arrive By test case
	public void selectCalendar() {
		try {
			// Find calendar and select it
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='Calendar']"))).click();
			
			// Go to next month to avoid potential issues with time being disabled and choose first date
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='next month']"))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//table//*[contains(text(),'1')])[1]"))).click();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// Method to be used to select time in testing in Arrive By test case
	public void selectTime(String time) {
		
		try {
			// Find date button and select it
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='select departure/arrival time']"))).click();
			
			// Select the time of the test
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '" + time + "')]"))).click();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// Method to be used to select time in testing in Arrive By test case
    // This method can be used for future testing in Options 
	public void saveOptions() {
		try {
			// Find Save button and click it
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Save')]"))).click();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// Method to be used to validate number of transit options proposed
	public boolean areOptionsDisplayed(int num) {
		// Find the list of options proposed and return true if the size is equal or higher to what is expected
		List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("(//section[@data-sentry-component='TripResultSection'])[1]//*[@role='option']")));
		if (options.size()>= num) {
			return true;
		} else {
			return false;
		}
	}
	
	// Method to be used to validate out of range error message
	public boolean isErrorMessageDisplayed(String errorMessage) {
		// Search for specific expected message, if found returns true
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()=\"" + errorMessage + "\"]"))).isDisplayed();
			
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	
	
	
	
}
