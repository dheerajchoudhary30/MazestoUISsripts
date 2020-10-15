package com.mazesto.mz.automation.utilities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.mazesto.automation.commons.ThreadConfigurationJobMapper;
import com.mazesto.automation.listeners.Reporter;

public class ApplicationUtilities {
	
	private static Logger log  = LogManager.getLogger("ApplicationUtilities");
	Utilities utilities = new Utilities();



	/**
	 * It should wait Until the Pulse Balls are visible on the current screen. Max
	 * timeout time should be 60 sec. After 60 sec the class will print error pulse
	 * balls still visible and throw Exception.
	 */
	public void waitUntilPulseBallsAreVisible() {
		WebDriver driver = ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver();

		try {
			WebElement pulseBalls = driver.findElement(By.cssSelector(".loader-inner.ball-pulse-sync"));
			utilities.getAutomationUtilities().waitUntilElementIsInvisible(pulseBalls);
		} catch (Exception e) {
			Reporter.addStepLog("Pulse balls are not getting visible");
		}
	}
	
	/**
	 * It applies Date on Dual Calendar
	 * 
	 * @param elementActionPart - The element
	 * @param list              - the values to apply
	 */
	public void applyDualCalendarDate(WebElement elementActionPart, List<String> list) {
		Iterator<String> it = list.iterator();
		
		EventFiringWebDriver objEventFiringWebDriver = new EventFiringWebDriver(
				ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver());
		int pixelCount = 1300;
		utilities.getAutomationUtilities()
				.clickOnElementGeneric(elementActionPart.findElement(By.className("option-box-trigger-content")));
		if (list.size() == 1) {
			log.info("Working as preset Selections!!");
			utilities.getAutomationUtilities()
					.clickToElementUsingJS(utilities.getAutomationUtilities().getElementWithTextAmongElements(utilities
							.getAutomationUtilities().getReportElements(By.className("rdr-PredefinedRangesItem")),
							it.next()));
			objEventFiringWebDriver.executeScript(
					"document.querySelector('#option-box-wrapper > div > div.option-box-content').scrollTop = "
							+ pixelCount + "");
			//click on calendar apply button
			utilities.getAutomationUtilities().clickOnElement(
					utilities.getAutomationUtilities().getReportElement(By.id("_columnManager_btn_apply")));
			utilities.getAutomationUtilities().scrollUp();
		} else if (list.size() == 2) {
			log.info("Working as custom Filter!!");
			utilities.getAutomationUtilities()
					.clickToElementUsingJS(utilities.getAutomationUtilities().getElementWithTextAmongElements(utilities
							.getAutomationUtilities().getReportElements(By.className("rdr-PredefinedRangesItem")),
							"Custom"));

			elementActionPart.findElement(By.id("start")).clear();
			//elementActionPart.findElement(By.id("start")).click();
			elementActionPart.findElement(By.id("start")).sendKeys(list.get(0));
			elementActionPart.findElement(By.id("start")).click();
			elementActionPart.findElement(By.id("end")).clear();
			elementActionPart.findElement(By.id("end")).sendKeys(list.get(1));
			elementActionPart.findElement(By.id("start")).click();
			//utilities.getAutomationUtilities().customCalendar(list);
			objEventFiringWebDriver.executeScript(
					"document.querySelector('#option-box-wrapper > div > div.option-box-content').scrollTop = "
							+ pixelCount + "");
			
			// clickOnDualCalendarApplyBtn();
			utilities.getAutomationUtilities().clickOnElement(
					utilities.getAutomationUtilities().getReportElement(By.id("_columnManager_btn_apply")));
		} else {
			System.err.println("Found wrong input");
		}
	}

	/**
	 * It should enter Data into a field
	 * 
	 * @param elementActionPart - The element onto which to enter data
	 * @param list              - The list of values
	 */
	public void enterFieldData(WebElement elementActionPart, List<String> list) {
		Iterator<String> it = list.iterator();
		List<WebElement> inputElementCardNumber = elementActionPart.findElements(By.className("field-input"));
		for (WebElement inputFieldElement : inputElementCardNumber) {
			if (it.hasNext())
			utilities.getAutomationUtilities().enterData(it.next(), inputFieldElement);
			utilities.getAutomationUtilities().scrollUp();
		}
	}

	/**
	 * 
	 * Implementation of sendKeys
	 * 
	 * @param inputWebElement: Form WebElements in which data need to fill
	 * @param listValuesToEnter: List of value to fill data in form / text box
	 */

	public void enterFieldData(List<WebElement> inputWebElement, List<String> listValuesToEnter) {
		Iterator<String> it = listValuesToEnter.iterator();
		for (WebElement inputFieldElement : inputWebElement) {
			if (it.hasNext())
				utilities.getAutomationUtilities().scrollUp();
			utilities.getAutomationUtilities().enterData(it.next(), inputFieldElement);
			utilities.getAutomationUtilities().waitFor(1);
		}

	}
	/**
	 * It should multi-select options from multi-select dropdown.
	 * 
	 * @param elementActionPart - The element onto which to enter data
	 * @param list              - The list of values
	 * 
	 */
	public void multiSelectFromDropdown(WebElement elementActionPart, List<String> list) {
		utilities.getAutomationUtilities().waitFor(5);
		utilities.getAutomationUtilities().scrollUp();
		openMultiSelectFromDropdown(elementActionPart, list);
		clickOnActionPart(elementActionPart);
	}

	public void openMultiSelectFromDropdown(WebElement elementActionPart, List<String> list) {
		log.info("Element action part : " + elementActionPart);
		utilities.getAutomationUtilities().clickOnElement(elementActionPart.findElement(By.tagName("span")));
		WebElement grandParent = utilities.getAutomationUtilities().getParentElementOfElement(
				utilities.getAutomationUtilities().getParentElementOfElement(elementActionPart));
		List<WebElement> listItems = grandParent.findElements(By.tagName("li"));
		for (String item : list) {
			WebElement listElement = utilities.getAutomationUtilities().getElementWithSubElementTextAmongElements(
					listItems, By.className("select-menu-option"), item.trim());
			utilities.getAutomationUtilities().clickOnElement(listElement);
		}
	}

	public void clickOnActionPart(WebElement elementActionPart)

	{
		utilities.getAutomationUtilities().clickOnElementGeneric(elementActionPart);
	}

	/**
	 * It should Single-select options from multi-select dropdown.
	 * 
	 * @param elementActionPart - The element onto which to enter data
	 * @param list              - The list of values
	 * 
	 */
	public void singleSelectFromDropdown(WebElement elementActionPart, List<String> list, String action) {
		utilities.getAutomationUtilities().scrollUp();
		utilities.getAutomationUtilities().clickOnElementGeneric(elementActionPart);
		WebElement grandParent = utilities.getAutomationUtilities().getParentElementOfElement(
				utilities.getAutomationUtilities().getParentElementOfElement(elementActionPart));

		String value = list.stream().map(Object::toString).collect(Collectors.joining(","));

		List<WebElement> listItems = grandParent.findElements(By.tagName("li"));
		for (WebElement item : listItems) {

			if (item.findElement(By.className("select-menu-option")).getText().trim().equals(value)) {

				utilities.getAutomationUtilities()
						.clickOnElement(item.findElement(By.className("select-menu-item-inner")));
				break;
			}
		}

	}

	/**
	 * It should Single-select options from multi-select dropdown.
	 * 
	 * @param elementActionPart - The element onto which to enter data
	 * @param list              - The list of values
	 * 
	 */
	public void singleSelectFromDropdownVerticalMenu(WebElement elementActionPart, List<String> list, String action) {
		utilities.getAutomationUtilities().scrollUp();
		utilities.getAutomationUtilities().clickOnElementGeneric(elementActionPart);
		WebElement grandParent = utilities.getAutomationUtilities().getParentElementOfElement(
				utilities.getAutomationUtilities().getParentElementOfElement(elementActionPart));

		String value = list.stream().map(Object::toString).collect(Collectors.joining(","));

		List<WebElement> listItems = grandParent.findElements(By.tagName("li"));
		for (WebElement item : listItems) {

			if (item.findElement(By.className("vertical-nav-label")).getText().trim().equals(value)) {

				utilities.getAutomationUtilities()
						.clickOnElement(item.findElement(By.className("vertical-nav-label")));
				break;
			}
		}

	}
	/**
	 * It applies amount field on filter
	 * 
	 * @param elementActionPart - The element
	 * @param                   list- the valies to apply
	 */
	public void enterAmountData(WebElement elementActionPart, List<String> list) {
		if (list.size() > 1) {
			log.info("Attempting amount entering!!");
			Iterator<String> it = list.iterator();
			System.out.println("Working on Amount field!!");
			if (it.hasNext()) {
				//WebElement enteramountdata = elementActionPart
				//		.findElement(By.className("field-input range-field low "));
				
				WebElement enteramountdata = elementActionPart
						.findElement(By.xpath("//*[@class='field-input range-field low ']"));
				
				utilities.getAutomationUtilities().enterData(it.next(), enteramountdata);
				
			} else
				System.err.println("Invalid Amount");
			if (it.hasNext()) {
				WebElement enteramountdata = elementActionPart
						.findElement(By.xpath("//*[@class='field-input range-field high ']"));
				utilities.getAutomationUtilities().scrollUp();
				utilities.getAutomationUtilities().enterData(it.next(), enteramountdata);
			
			} else
				System.err.println("Invalid Amount");
		} else {
			WebElement enteramountdata = elementActionPart.findElement(By.tagName("div"))
					.findElement(By.tagName("input"));
			utilities.getAutomationUtilities().enterData(list.get(0), enteramountdata);
			
		}
	}


	public WebElement getFilterTextFieldElement(List<WebElement> filtersElements, By filterNameLabelLocator,
			String filterKey) {
		WebElement matchedFilterElement = utilities.getAutomationUtilities()
				.getElementWithSubElementTextAmongElements(filtersElements, filterNameLabelLocator, filterKey);
		WebElement elementActionPart = matchedFilterElement;
		log.info("Initially elementActionPart class 1 : " + elementActionPart.getAttribute("class"));
		try {
			elementActionPart = matchedFilterElement.findElement(By.tagName("div")).findElement(By.tagName("div"));
			log.info("After 2 div  elementActionPart : " + elementActionPart.getAttribute("class"));
		} catch (Exception e) {
			elementActionPart = matchedFilterElement;
			log.info("In catch block " + elementActionPart.getAttribute("class"));
		}
		if (elementActionPart != null) {
			if (elementActionPart.getAttribute("class").trim().equalsIgnoreCase("field-group-vertical")) {
				log.info("Handling Year month");
				elementActionPart = elementActionPart.findElement(By.tagName("div"));
			} else if (elementActionPart.getAttribute("class").trim()
					.startsWith("field range-container dual-date-input")) {
				log.info("Handling Dual Calendar");
				elementActionPart = elementActionPart.findElements(By.tagName("div")).get(1);
			}
		}
		return elementActionPart;
	}

	public WebElement getFilterElement(List<WebElement> filtersElements, By filterNameLabelLocator, String filterKey) {
		WebElement matchedFilterElement = utilities.getAutomationUtilities()
				.getElementWithSubElementTextAmongElements(filtersElements, filterNameLabelLocator, filterKey);
		WebElement elementActionPart = matchedFilterElement;
		log.info("Initially elementActionPart class 1 : " + elementActionPart.getAttribute("class"));
		try {
			elementActionPart = matchedFilterElement.findElement(By.tagName("div")).findElement(By.tagName("div"));
			log.info("After 2 div  elementActionPart : " + elementActionPart.getAttribute("class"));
		} catch (Exception e) {
			elementActionPart = matchedFilterElement;
			log.info("In catch block " + elementActionPart.getAttribute("class"));
		}
		if (elementActionPart != null) {
			if (elementActionPart.getAttribute("class").trim().equalsIgnoreCase("field-group-vertical")) {
				log.info("Handling Year month");
				elementActionPart = elementActionPart.findElement(By.tagName("div"));
			} else if (elementActionPart.getAttribute("class").trim()
					.startsWith("field range-container dual-date-input")) {;
				log.info("Handling Dual Calendar");
				elementActionPart = elementActionPart.findElements(By.tagName("div")).get(1);
			}
		}
		return elementActionPart;
	}

	public void enterDataInFilterElements(WebElement elementActionPart, List<String> FilterdataKey) {
		if (elementActionPart != null) {
			log.info("element action part : " + elementActionPart);
			log.info("#####" + elementActionPart.getAttribute("class").trim());
			switch (elementActionPart.getAttribute("class").trim()) {
			case "field range-container": // Amount ranges from-to
				// utilities.getAutomationUtilities().scrollUp();
				utilities.getApplicationUtils().enterAmountData(elementActionPart, FilterdataKey);
				utilities.getAutomationUtilities().waitFor(5);
				break;

			case "outside-click-handler": // dual Calendar
				utilities.getApplicationUtils().applyDualCalendarDate(elementActionPart, FilterdataKey);
				break;

			case "field":
				utilities.getApplicationUtils().enterFieldData(elementActionPart, FilterdataKey);				
				break;

			case "field credit-card-input":
				utilities.getAutomationUtilities().scrollUp();
				utilities.getApplicationUtils().enterFieldData(elementActionPart, FilterdataKey);
				break;

			case "ellipsis":
				utilities.getAutomationUtilities().scrollUp();
				utilities.getApplicationUtils().multiSelectFromDropdown(elementActionPart, FilterdataKey);
				break;

			}
		} else
			System.err.println("error with elements!!");
	}

	

		public void verifyCharacterLimit(int charLimit)
		{

		    Random r = new Random();

		    String alphabet = "123xyz";
		    for (int i = 0; i < charLimit+5; i++) {
		        System.out.println(alphabet.charAt(r.nextInt(alphabet.length())));
		    }
		}
		
		public void clickOnApplyButtonFilter(WebElement applyButtonElement) {
				utilities.getFrameworkUtilities().addScreenCaptureToReport("clicking on apply button after entering data");
				utilities.getAutomationUtilities().waitUntilElementIsVisible(applyButtonElement);
				utilities.getAutomationUtilities().clickOnElementGeneric(applyButtonElement);
				utilities.getAutomationUtilities().waitFor(2);
	    }
		
		
	
		/**
		 * this method will convert String of given timestamp format to case timeline timestamp format
		 * @param timeStampToBeFormatted - String of time stamp to be formatted
		 * @return String formattedTimeStamp - timestamp in case timeline format
		 */
		public String getTimestampInCaseTimelineFormat(String timeStampToBeFormatted)
		{
			Timestamp timestamp = Timestamp.valueOf(timeStampToBeFormatted);		
			SimpleDateFormat newFormatter = new SimpleDateFormat("MMM. dd, yyyy HH:mm a");		
			String formattedTimeStamp = newFormatter.format(timestamp);
			return formattedTimeStamp;
		}

		/**
		 * this method will return String of current date and time
		 * @param dateAndTimeFormat - String of timestamp format that needs to be retrieved(Format example: MM/dd/YYYY HH:mm:ss or MM/dd/YYYY )
		 * @return String formattedTimeStamp - timestamp given format
		 */
		public String getCurrentDateAndTime(String dateAndTimeFormat ) {
			//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/YYYY HH:mm:ss");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateAndTimeFormat);
			   LocalDateTime now = LocalDateTime.now();
			   return dtf.format(now);
			   
		}
		/**
		 * this method will convert String of given timestamp format to a given format
		 * @param timeStampToBeFormatted - String of time stamp to be formatted
		 * @return String formattedTimeStamp - timestamp given format
		 */
		public String getTimestampInCaseTimelineFormat(String timeStampToBeFormatted,String format)
		{
			Timestamp timestamp = Timestamp.valueOf(timeStampToBeFormatted);		
			SimpleDateFormat newFormatter = new SimpleDateFormat(format);		
			String formattedTimeStamp = newFormatter.format(timestamp);
			return formattedTimeStamp;
		} 
		
	
		/**
		 * @param map-  key and value 
		 * @return - String in the form of 'key1:value1,key2:value2...'
		 */
		public String convertMapToFilterType(Map<String,String> map) {
			String convertedFilter = "";
			String tempconvertedFilter = "";
			for (Map.Entry<String,String> entry : map.entrySet()) { 
				tempconvertedFilter = (entry.getKey() + ":" + entry.getValue());
				convertedFilter =  tempconvertedFilter  + "," +convertedFilter;
			}
			return convertedFilter;
		}
		
		
		
		
		
		
		
}
	