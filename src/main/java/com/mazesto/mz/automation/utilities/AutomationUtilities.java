package com.mazesto.mz.automation.utilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mazesto.automation.commons.ThreadConfigurationJobMapper;

/**
 * @author Dheeraj Choudhary
 *
 */
public class AutomationUtilities {
	private Logger log = LogManager.getLogger(getClass().getName());
	
	
	public static boolean switchBetwenTabs() {

		WebDriver driver = ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver();
		String parent = driver.getWindowHandle();
		java.util.Set<String> s1 = driver.getWindowHandles();
		Iterator<String> I1 = s1.iterator();

		while (I1.hasNext()) {
			String child_window = I1.next();
			if (!parent.equals(child_window)) {
				driver.switchTo().window(child_window);
				return true;
			}
		}
		System.err.println("Tab is not swicthed");
		return false;

	}

	/**
	 * It should call wait on Thread for number of seconds mentioned
	 * 
	 * @param seconds - The number of seconds to wait before proceeding with
	 *                execution
	 */
	public void waitFor(Integer seconds) {
		try {
			seconds = seconds * 1000;
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			System.err.println("Interrupted wait in waitFor(seconds) method -> AutomationUtilities");
		}

	}

	/**
	 * It will poll every 200ms to find the element where max timeout is 40 seconds.
	 * By default exit after 40 seconds.
	 * 
	 * @param element - the element to look for
	 */
	public void waitUntilElementIsVisible(WebElement element) {
		try {
			FluentWait<WebDriver> fluentWait = new FluentWait<>(
					ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver())
							.withTimeout(Duration.ofSeconds(60)).pollingEvery(Duration.ofMillis(200))
							.ignoring(NoSuchElementException.class);
			fluentWait.until(ExpectedConditions.visibilityOf(element));

		} catch (Exception e) {
			System.err.println("element not found! " + e.getClass().getName());

		}
	}

	/**
	 * It will poll every 200ms to find the element where max timeout is 40 seconds.
	 * Max wait in seconds provided
	 * 
	 * @param element - the element to look for
	 * @param seconds - max seconds to wait for
	 */
	public void waitUntilElementIsVisible(WebElement element, Integer seconds) {
		try {
			FluentWait<WebDriver> fluentWait = new FluentWait<>(
					ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver())
							.withTimeout(Duration.ofSeconds(60)).pollingEvery(Duration.ofMillis(200))
							.ignoring(NoSuchElementException.class);
			fluentWait.until(ExpectedConditions.visibilityOf(element));

		} catch (Exception e) {
			System.err.println("element not found! " + e.getClass().getName());
		}
	}

	/**
	 * It will poll every 200ms to find the first element among the list where max
	 * timeout is 40 seconds. By default exit after 40 seconds.
	 * 
	 * @param elements - the list of elements among which the first one to look for
	 */
	public void waitUntilElementIsVisible(List<WebElement> elements) {
		try {
			FluentWait<WebDriver> fluentWait = new FluentWait<>(
					ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver())
					.withTimeout(Duration.ofSeconds(60)).pollingEvery(Duration.ofMillis(200))
							.ignoring(NoSuchElementException.class).ignoring(IndexOutOfBoundsException.class);

			fluentWait.until(ExpectedConditions.visibilityOf(elements.get(0)));

		} catch (Exception e) {
			System.err.println("element not found! " + e.getClass().getName());

		}
	}

	/**
	 * It will poll every 200ms to check the element is not visible where max
	 * timeout is 40 seconds. By default exit after 40 seconds.
	 * 
	 * @param element - the element to look for
	 */
	public void waitUntilElementIsInvisible(WebElement element) {
		try {
			FluentWait<WebDriver> fluentWait = new FluentWait<>(
					ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver())
					.withTimeout(Duration.ofSeconds(60)).pollingEvery(Duration.ofMillis(200))
							.ignoring(NoSuchElementException.class);
			fluentWait.until(ExpectedConditions.invisibilityOf(element));

		} catch (Exception e) {
			System.err.println("element not found! " + e.getClass().getName());

		}
	}

	/**
	 * Check the element is visible or not using explicit wait
	 */

	public void waitUntilElementisDisplayedOnScreen(WebElement element) {

		WebDriverWait wait = new WebDriverWait(
				ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver(), 40);

		wait.until(ExpectedConditions.visibilityOf(element));

	}

	/*
	 * wait until expected Conditions meet
	 *
	 */
	public void waitUntilMyLocatorIsPresentOnScreen(By by) {
		WebDriverWait wait = new WebDriverWait(
				ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver(), 40);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));

	}

	/**
	 * It should click on any element irrespective of the browser/device.
	 * 
	 * @param element - The element to be clicked on.
	 */

	public void clickOnElementGeneric(WebElement element) {

		// Currently not implemented device specific clicks

		clickOnElement(element);
	}

	/**
	 * It should click on a Big Browser element
	 * 
	 * @param element - The element to be clicked on.
	 */
	public void clickOnElement(WebElement element) {

		waitUntilElementIsVisible(element);
		element.click();
	}

	/**
	 * It should scroll to element showing it up.
	 * 
	 * @param element - The element to scroll to.
	 */
	public void scrollToElementUsingJS(WebElement element) {
		if (element != null) {
			try {
				((JavascriptExecutor) ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData()
						.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);

			} catch (NoSuchElementException e) {
				System.err.println(e.getMessage());
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	/**
	 * It should scroll to element need to Click on.
	 * 
	 * @param element - The element to be clicked on.
	 */
	public void clickToElementUsingJS(WebElement element) {
		if (element != null) {
			try {
				((JavascriptExecutor) ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData()
						.getDriver()).executeScript("arguments[0].click();", element);

			} catch (NoSuchElementException e) {
				System.err.println(e.getMessage());
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	public void scrollUp() {

		((JavascriptExecutor) ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData()
				.getDriver()).executeScript("window.scrollTo(0, -document.body.scrollHeight);");
	}

	public WebElement getParentElementOfElement(WebElement childElement) {
		return childElement.findElement(By.xpath(".."));
	}

	/**
	 * It should enter data into the element provided after clearing it first.
	 * 
	 * @param data    - The data to be entered
	 * @param element - The element to which the data is to be entered.
	 */

	public void enterDataWithoutUsingJS(String data, WebElement element) {
		element.click();
		element.sendKeys(Keys.CONTROL + "a");
		element.sendKeys(Keys.DELETE);
		scrollToElementUsingJS(element);
		element.sendKeys(data);
	}

	/**
	 * It should enter data into the element provided after clearing it first.
	 * 
	 * @param data    - The data to be entered
	 * @param element - The element to which the data is to be entered.
	 * @throws InterruptedException 
	 */

	public void enterData(String data, WebElement element)  {
		element.click();
		element.sendKeys(Keys.CONTROL + "a");
		element.sendKeys(Keys.DELETE);
		element.sendKeys(data);
		 while(!data.equalsIgnoreCase(element.getText())) {
					clearTextUsingDeleteButton(element);
					element.sendKeys(data);
					break;
		 }
	}

	public boolean isDataEntered(String data, WebElement element) {
		String enteredData = null;
		if (null != element && element.getAttribute("value") != null)
			enteredData = element.getAttribute("value");
		else {
			enteredData = element.getText();
		}
		System.out.println("enteredData: " + enteredData);
		System.out.println("Data: " + data);
		return (null != enteredData && enteredData.trim().equals(data.trim()));
	}

	public boolean isDataEntered(Double data, WebElement element) {
		String enteredData = null;
		if (null != element)
			enteredData = element.getAttribute("value");
		System.out.println("enteredData: " + enteredData);
		System.out.println("Data: " + data);
		return (Double.parseDouble(enteredData) == (data));
	}

	/**
	 * It should check if element is found or not
	 * 
	 * @param by - The element to find
	 * @return status of element presence
	 */
	public boolean isElementVisible(By by) {
		WebDriver driver = ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver();
		return (!driver.findElements(by).isEmpty());
	}

	/**
	 * It will iterate over the elements to find inner element by the locator
	 * provide to add it into a list and return.
	 * 
	 * @param elements          - The elements to iterate over.
	 * @param subElementLocator - The locator of the sub element
	 * @return - the list of sub element texts.
	 */
	public List<String> getSubElementsTexts(List<WebElement> elements, By subElementLocator) {
		return elements.stream().map(e -> e.findElement(subElementLocator).getText()).collect(Collectors.toList());
	}
	
	/**
	 * It should check if element is found or not
	 * 
	 * @param element - The element to find
	 * @return status of element presence
	 */
	public boolean isElementVisible(WebElement element) {
		return (null != element && element.isDisplayed());
	}

	/**
	 * It should check if element is enabled or not
	 * 
	 * @param element - The element to find
	 * @return status of element enability
	 */
	public boolean isElementEnabled(WebElement element) {
		return (null != element && element.isEnabled());
	}

	/**
	 * It should check if element is present or not
	 * 
	 * @param element - The element to find
	 * @return status of element presence
	 */
	public boolean isElementPresnet(By by) {
		WebDriver driver = ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver();
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		List<WebElement> elements = driver.findElements(by);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return (!elements.isEmpty());
	}

	/**
	 * It should check if element is present or not
	 * 
	 * @param element - The element to find
	 * @return status of element presence
	 */
	public boolean isElementPresnetInsideElement(WebElement element, By by) {
		WebDriver driver = ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver();
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		List<WebElement> elements = element.findElements(by);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return (!elements.isEmpty());
	}

	/**
	 * It should iterate over the elements provided and return the first element for
	 * which the mentioned label is found. #Usage : Multiple classes
	 * 
	 * @param elements - The elements to iterate over
	 * @param label    - The label to be matched
	 * @return - The element matching
	 */
	public WebElement getElementWithTextAmongElements(List<WebElement> elements, String label) {
		// elements.stream().forEach(e -> System.out.print(e.getText() + ", "));

		List<WebElement> elementsWithLabelMatching = elements.stream()
				.filter(e -> label.trim().equalsIgnoreCase(e.getText().trim())).collect(Collectors.toList());
		System.out.println("Element of matching label" + elementsWithLabelMatching);

		if (elementsWithLabelMatching.isEmpty())
			return null;
		else
			return elementsWithLabelMatching.get(0);
	}
	
	
	

	/**
	 * It should iterate over "elements" and for each element if will find another
	 * element inside. The element will be found on basis of "by" Provided. Then it
	 * will iterate among them to find the element where the sub element text
	 * matched with the "label" provided.
	 * 
	 * @param elements - The elements to iterate over
	 * @param by       - The selector By to find the sub element
	 * @param label    - The label by which to get Text of Sub Element
	 * @return - The parent element for which there is a subelement with By provided
	 *         and its text is the expected label.
	 * @throws InterruptedException
	 */

	public WebElement getElementWithSubElementTextAmongElements(List<WebElement> elements, By by, String label) {
		log.info("Label : " + label);

		for (WebElement currentElement : elements) {

			try {
				if (label.trim().equalsIgnoreCase(currentElement.findElement(by).getText().replace("?", "").trim())) {
					return currentElement;
				}

			} catch (Exception e) {
				log.info("Exception caught while finding element! " + label);
			}
		}
		return null;
	}

	/**
	 * * It should iterate over "elements" and for each element if will find another
	 * element inside. The element will be found on basis of "by" Provided. Then it
	 * will iterate among them to find the elements where the sub element text
	 * matched with the "label" provided.
	 * 
	 * @param elements - The elements to iterate over
	 * @param by       - The selector By to find the sub element
	 * @param label    - The label by which to get Text of Sub Element
	 * @return - The parent element for which there is a sub element with By
	 *         provided and its text is the expected label.
	 */
	public List<WebElement> getElementsWithSubElementTextAmongElements(List<WebElement> elements, By by, String label) {
		List<WebElement> allElementsWithSubText = new ArrayList<>();
		for (WebElement currentElement : elements) {
			try {
				if (label.trim().equalsIgnoreCase((currentElement.findElement(by).getText().trim())))
					allElementsWithSubText.add(currentElement);
			} catch (NoSuchElementException e) {
			}
		}
		return allElementsWithSubText;
	}

	public String getTextOfElement(WebElement element) {
		WebDriver driver = ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver();
		String returnText = element.getText();
		System.out.println(element.getText());
		if (returnText == null || returnText.trim().equals(""))
			returnText = element.getAttribute("innerHTML");
		else
			return returnText;

		if (returnText == null || returnText.trim().equals(""))
			returnText = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText", element);
		else
			return returnText;

		if (returnText == null || returnText.trim().equals(""))
			returnText = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].textContent",
					element);
		else
			return returnText;

		return returnText;

	}

	/**
	 * It should clear the element textbox. Provided the element is textbox
	 * 
	 * @param element - the textbox element to be cleared
	 */
	public void clearData(WebElement element) {
		scrollToElementUsingJS(element);
		element.clear();

	}

	public WebElement filterFirstDisplayedElement(List<WebElement> elements) {
		int counter = 0;
		System.out.println("Number of elements from which to filter = " + elements.size());
		for (WebElement each : elements) {
			System.out.println("Counter " + (++counter));
			System.out.println("Class" + each.getAttribute("clientHeight").getClass());
			System.out.println("Height " + each.getAttribute("clientHeight"));
			System.out.println("Width " + each.getAttribute("clientWidth"));

			if (each.isDisplayed()) {
				return each;
			}
		}
		return null;
	}

	public WebElement filterDisplayedElementWithText(List<WebElement> elements, String elementText) {

		WebElement element = null;
		for (WebElement each : elements) {
			if (each.isDisplayed() && each.getText().trim().equalsIgnoreCase(elementText.trim())) {
				element = each;
				break;
			}
		}
		return element;
	}

	public static WebElement getWebElement(String identifierType, String identifierValue) {
		WebDriver driver = ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver();

		if (identifierType.equals("id")) {
			WebElement element = driver.findElement(By.id(identifierValue));
			return element;

		}

		else if (identifierType.equals("xpath")) {
			WebElement element = driver.findElement(By.xpath(identifierValue));

			return element;
		}

		else {

			WebElement element = driver.findElement(By.tagName(identifierValue));

			return element;
		}
	}

	public void searhData(String searchIdentifierType, String searchIdentifierValue, String searchValue) {

		// Assuming there will be different id's for different search button
		WebElement element = getWebElement(searchIdentifierType, searchIdentifierValue);
		scrollToElementUsingJS(element);
		// clickOnElement(elememt);
		clearData(element);

		enterData(searchValue, element);

	}

	public void searhData(WebElement element, String searchValue) {

		scrollToElementUsingJS(element);
		// clickOnElement(elememt);
		clearData(element);

		enterData(searchValue, element);

	}

	/**
	 * This method is used to scroll down to 400
	 */
	public void scrollDown() {

		((JavascriptExecutor) ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData()
				.getDriver()).executeScript("scroll(0, 400);");
	}

	/**
	 * This method is used to scroll to Bottom
	 */
	public void scrolltoBottom() {

		((JavascriptExecutor) ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData()
				.getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	/**
	 * This method is used to scroll to Right Horizontally.
	 * @throws InterruptedException 
	 */
	public void scrolltoRight()  {

		WebDriver driver = ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver();
		WebElement scrollArea = driver.findElement(By.className("ag-body-horizontal-scroll-viewport"));
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollLeft += 250",scrollArea);     
	}
	
	/**
	 * This method is used to scroll to Right Horizontally.
	 * @throws InterruptedException 
	 */
	public void scrolltoLeft()  {

		WebDriver driver = ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver();
		WebElement scrollArea = driver.findElement(By.className("ag-body-horizontal-scroll-viewport"));
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollLeft -= 3000",scrollArea);     
	}

	public List<List<String>> getTableData(String tableIdentifierType, String tableIdentifierValue) {
		List<List<String>> tabularData = new ArrayList<List<String>>();
		WebElement table = getWebElement(tableIdentifierType, tableIdentifierValue);
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		boolean isFirst = true;
		for (WebElement row : rows) {
			List<WebElement> columns;
			if (isFirst) {
				columns = row.findElements(By.tagName("th"));
				isFirst = false;
			} else {
				columns = row.findElements(By.tagName("td"));
			}
			List<String> columnsData = new ArrayList<String>();
			for (WebElement e : columns) {
				columnsData.add(e.getText());
			}
			tabularData.add(columnsData);
		}
		return tabularData;
	}

	/**
	 * 
	 * @param list1
	 */
	public void customCalendar(List<String> list1) {
		WebDriver driver = ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver();
		boolean flag = false;
		String calendar;
		waitFor(2);
		String customFromCalendar = driver
				.findElement(By.cssSelector("div.rdr-DateRange > div:nth-child(1) > div.rdr-MonthAndYear > div > span"))
				.getText().trim();

		String customToCalendar = driver
				.findElement(By.cssSelector("div.rdr-DateRange > div:nth-child(2) > div.rdr-MonthAndYear > div > span"))
				.getText().trim();
		for (int k = 0; k < list1.size(); k++) {
			if (k == 0) {
				calendar = customFromCalendar;
				String[] str = list1.get(k).split("/");
				Integer monthCount = Integer.parseInt(str[0]);
				String month = new DateFormatSymbols().getMonths()[monthCount - 1];
				String date = str[1];
				Integer year = Integer.parseInt(str[2]);
				String[] calenderData = calendar.split("-");
				String month1 = calenderData[0].trim();
				Integer year1 = Integer.parseInt(calenderData[1].trim());
				for (int i = 0; i < 50; i++) {
					if (year <= year1) {
						driver.findElement(
								By.cssSelector("div.rdr-MonthAndYear > div > button.rdr-MonthAndYear-button.prev"))
								.click();
						String calender1 = driver
								.findElement(By.cssSelector(
										"div.rdr-DateRange > div:nth-child(1) > div.rdr-MonthAndYear > div > span"))
								.getText().trim();
						String[] calenderData1 = calender1.split("-");
						year1 = Integer.parseInt(calenderData1[1].trim());
						month1 = calenderData1[0].trim();
						if (year.equals(year1)) {
							for (int j = 0; j < 50; j++) {
								if (month.equals(month1)) {
									List<WebElement> datelist = driver.findElements(
											By.xpath("//div[@class='dual-calendar__pairs']/div[2]/div[1]/div[3]/span"));
									for (int a = 1; a < datelist.size(); a++) {
										if (date.charAt(0) == '0') {
											date = date.replace('0', ' ');
										}
										if (datelist.get(a).getText().equals(date.trim())) {
											String dataxapth = "//div[@class='dual-calendar__pairs']/div[2]/div[1]/div[3]/span["
													+ (a + 1) + "]";
											driver.findElement(By.xpath(dataxapth)).click();
											flag = true;
											break;
										}
									}
								} else {
									driver.findElement(By.cssSelector(
											"div.rdr-MonthAndYear > div > button.rdr-MonthAndYear-button.prev"))
											.click();
									String monthnew = driver.findElement(By.cssSelector(
											"div.rdr-DateRange > div:nth-child(1) > div.rdr-MonthAndYear > div > span"))
											.getText().trim();
									String[] MonthData1 = monthnew.split("-");
									month1 = MonthData1[0].trim();
								}
								if (flag == true) {
									break;
								}
							}
						}
						if (flag == true) {
							break;
						}
					}
				}
			} else {
				flag = false;
				calendar = customToCalendar;
				String[] str = list1.get(k).split("/");
				Integer monthCount = Integer.parseInt(str[0]);
				String month = new DateFormatSymbols().getMonths()[monthCount - 1];
				String date = str[1];
				Integer year = Integer.parseInt(str[2]);
				String[] calenderData = calendar.split("-");
				String month1 = calenderData[0].trim();
				Integer year1 = Integer.parseInt(calenderData[1].trim());
				for (int i = 0; i < 50; i++) {
					if (year <= year1 && !(month.equals(month1))) {
						driver.findElement(By.cssSelector(
								"div:nth-child(2) > div.rdr-MonthAndYear > div > button.rdr-MonthAndYear-button.prev"))
								.click();
						String calender1 = driver
								.findElement(By.cssSelector(
										"div.rdr-DateRange > div:nth-child(2) > div.rdr-MonthAndYear > div > span"))
								.getText().trim();
						String[] calenderData1 = calender1.split("-");
						year1 = Integer.parseInt(calenderData1[1].trim());
						month1 = calenderData1[0].trim();
						if (year.equals(year1)) {
							for (int j = 0; j < 50; j++) {
								if (month.equals(month1)) {
									List<WebElement> datelist = driver.findElements(
											By.xpath("//div[@class='dual-calendar__pairs']/div[2]/div[2]/div[3]/span"));
									for (int a = 1; a < datelist.size(); a++) {
										if (date.charAt(0) == '0') {
											date = date.replace('0', ' ');
										}
										if (datelist.get(a).getText().equals(date.trim())) {
											String dataxapth = "//div[@class='dual-calendar__pairs']/div[2]/div[2]/div[3]/span["
													+ (a + 1) + "]";
											driver.findElement(By.xpath(dataxapth)).click();
											flag = true;
											break;
										}
									}
								} else {
									driver.findElement(By.cssSelector(
											"div:nth-child(2) > div.rdr-MonthAndYear > div > button.rdr-MonthAndYear-button.prev"))
											.click();
									String monthnew = driver.findElement(By.cssSelector(
											"div.rdr-DateRange > div:nth-child(2) > div.rdr-MonthAndYear > div > span"))
											.getText().trim();
									String[] MonthData1 = monthnew.split("-");
									month1 = MonthData1[0].trim();
								}
								if (flag == true) {
									break;
								}
							}
						}
						if (flag == true) {
							break;
						}
					} else {
						List<WebElement> datelist = driver
								.findElements(By.xpath("//div[@class='rdr-Calendar']/div[3]/span"));
						for (int a = 1; a < datelist.size(); a++) {
							if (date.charAt(0) == '0') {
								date = date.replace('0', ' ');
							}
							if (datelist.get(a).getText().equals(date.trim())) {
								String dataxapth = "//div[@class='rdr-Calendar']/div[3]/span[" + (a + 1) + "]";
								driver.findElement(By.xpath(dataxapth)).click();
								flag = true;
								break;
							}
						}
						if (flag == true) {
							break;
						}
					}
					if (flag == true) {
						break;
					}
				}
			}
		}
	}

	public List<List<String>> getTableData(WebElement table) {
		List<List<String>> tabularData = new ArrayList<List<String>>();

		List<WebElement> rows = table.findElements(By.tagName("tr"));
		boolean isFirst = true;

		System.out.println("Number of rows present " + rows.size());

		for (WebElement row : rows) {
			List<WebElement> columns;
			if (isFirst) {

				columns = row.findElements(By.tagName("th"));
				isFirst = false;
			} else {

				columns = row.findElements(By.tagName("td"));
			}
			List<String> columnsData = new ArrayList<String>();
			for (WebElement e : columns) {
				if (!e.getText().equals("Actions") || e.getText().equals(""))
					columnsData.add(e.getText().trim());
			}
			tabularData.add(columnsData);
		}

		return tabularData;
	}

	public List<String> trimStringList(List<String> list) {
		return list.stream().map(e -> e.trim()).collect(Collectors.toList());
	}

	// Validate if the column contains all the expected vales
	public boolean isTableColumnContainingOnlyExpectedValues(List<List<String>> table, Integer columnIndex,
			String... expectedValues) {
		List<String> expectedValuesList = Arrays.asList(expectedValues);
		if (expectedValuesList.toString().contains("Activated")
				|| expectedValuesList.toString().contains("Deactivated")) {
			expectedValuesList = expectedValuesList.stream().map(e -> e.toUpperCase()).collect(Collectors.toList());
		}
		System.out.println("Expected list" + Arrays.toString(expectedValues));

		for (List<String> row : table) {
			if (!table.get(0).equals(row)) {
				row = trimStringList(row);
				System.out.println("Index " + columnIndex);
				if (columnIndex < (row.size() - 1)) {
					if (row.get(columnIndex) != null && !expectedValuesList.contains(row.get(columnIndex).trim()))
						return false;
				}
			}
		}

		return true;
	}

	/**
	 * It refresh the page
	 */
	public void refreshPage() {
		WebDriver driver = ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver();
		driver.navigate().refresh();
	}

	/**
	 * TODO
	 * 
	 * @param element - Element to double click
	 */
	public void doubleClickOnElementGeneric(WebElement element) {
		WebDriver driver = ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver();
		Actions action = new Actions(driver);
		log.getName().getClass();
		waitUntilElementIsVisible(element);
		action.doubleClick(element).build().perform();
	}

	/**
	 * It should return the text of List Elements
	 * 
	 * @param element - List of Elements
	 */

	public List<String> getElementsTexts(List<WebElement> headerColumns) {

		List<String> allElementsWithText = new ArrayList<>();

		for (WebElement currentElement : headerColumns) {
			allElementsWithText.add(currentElement.getText());

		}

		return allElementsWithText;
	}

	

	/**
	 * @param elements - Web Elements
	 * @param attribute - Attribute text
	 * @return list of attribute values 
	 */
	public List<String> getAttributeValue(List<WebElement> elements,String attribute) {

		List<String> allElementsAttribute = new ArrayList<>();

		for (WebElement currentElement : elements) {
			allElementsAttribute.add(currentElement.getAttribute(attribute));

		}

		return allElementsAttribute;
	}
	
	public boolean strictStringMatch(String actual, String expected) {
		if (actual.equals(expected)) {
			System.out.println(actual + " matches with " + expected + " successfully");
			return true;
		}
		return false;
	}

	/**
	 * It fiend elements using By locator returns list of web elements
	 */
	public List<WebElement> getReportElements(By locator) {
		return ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver()
				.findElements(locator);
	}

	/**
	 * It fiend element using By locator
	 */
	public WebElement getReportElement(By locator) {
		return ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver()
				.findElement(locator);
	}

	/**
	 * This method will accept the string with Alphanumeric value and split Integer
	 * and return it
	 * 
	 * @param InputString it will accept the string for which we want to split the
	 *                    string and integer
	 * @return will return the integer
	 */
	public String FetchNumberFromString(String InputString) {
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(InputString);
		while (m.find()) {
			return m.group();
		}

		return m.group();
	}

	/**
	 * This method will accept the string with Alphanumeric value and split string
	 * and integer and return it
	 * 
	 * @param InputString it will accept the string for which we want to split the
	 *                    string and integer
	 * @return will return the integer
	 */
	public String[] RetrievestringFromAlphanumeric(String InputString) {
		String[] part = InputString.split("(?<=\\D)(?=\\d)");
		System.out.println("String" + part[0]);
		System.out.println("Integer" + part[1]);
		return part;

	}

	/**
	 * 
	 * This methods checks if any attribute is present in a webelement
	 * 
	 * @param element
	 * @param attribute
	 * @return
	 */

	public boolean isAttributePresent(WebElement element, String attribute) {
		Boolean result = false;
		try {
			String value = element.getAttribute(attribute);
			if (value != null) {
				result = true;
			}
		} catch (Exception e) {
		}

		return result;
	}

	/**
	 * It should iterate over the elements provided and return the first element for
	 * which the mentioned label is found. #Usage : Multiple classes
	 * 
	 * @param elements - The elements to iterate over
	 * @param label    - The label to be matched
	 * @return - The element matching
	 */
	public WebElement getElementWithSubTextAmongElements(List<WebElement> elements, String subtext) {
		// elements.stream().forEach(e -> System.out.print(e.getText() + ", "));

		log.info("WebElements are : " + elements + " Label" + subtext);

		List<WebElement> elementsWithLabelMatching = elements.stream().filter(e -> (e.getText().contains(subtext)))
				.collect(Collectors.toList());
		System.out.println("Element of matching label" + elementsWithLabelMatching);

		if (elementsWithLabelMatching.isEmpty())
			return null;
		else
			return elementsWithLabelMatching.get(0);
	}
	
	/**
	 * It should iterate over the elements provided and return the first element for
	 * which the mentioned label is found. #Usage : Multiple classes
	 * 
	 * @param elements - The elements to iterate over
	 * @param label    - The label to be matched
	 * @return - The element matching
	 */
	public List<WebElement> getElementsWithSubTextAmongElements(List<WebElement> elements, String subtext) {
		// elements.stream().forEach(e -> System.out.print(e.getText() + ", "));

		log.info("WebElements are : " + elements + " Label" + subtext);

		List<WebElement> elementsWithLabelMatching = elements.stream().filter(e -> (e.getText().contains(subtext)))
				.collect(Collectors.toList());
		System.out.println("Element of matching label" + elementsWithLabelMatching);

		if (elementsWithLabelMatching.isEmpty())
			return null;
		else
			return elementsWithLabelMatching;
	}

	public void scrollUpUsingActionClass() {

		WebDriver driver = ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver();
		Actions action = new Actions(driver);
		action.sendKeys(Keys.PAGE_UP).build().perform();

	}

	public void navigateBack() {
		WebDriver driver = ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver();
		driver.navigate().back();

	}

	public void MoveToElement(WebElement HoverElement) {
		WebDriver driver = ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver();
		Actions builder = new Actions(driver);
		builder.moveToElement(HoverElement).perform();
	}

	public Map<String, String> mapKeyLimiter(Map<String, String> map1, int length) {
		Map<String, String> map = new HashMap<String, String>();
		String newKey = "";
		for (Map.Entry<String, String> entry : map1.entrySet()) {

			System.out.println(entry.getKey());

			if (entry.getKey().length() > length) {
				newKey = entry.getKey().substring(0, length - 1);
			} else {
				newKey = entry.getKey();
			}

			map.put(newKey, entry.getValue());
		}
		return map;

	}

	/**
	 * method is used to check element visibility for second user
	 * 
	 * @param by      - element locator
	 * @param driver2 - second driver instance
	 */
	public void waitUntilElementIsPresent(By by, WebDriver driver2) {
		try {
			FluentWait<WebDriver> fluentWait = new FluentWait<>(driver2)
					.withTimeout(Duration.ofSeconds(60))
					.pollingEvery(Duration.ofMillis(200))
					.ignoring(NoSuchElementException.class);
			fluentWait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
			log.info("element not found! " + e.getClass().getName());
		}
	}
	
	/**
	 * method is used to check element visibility for second user
	 * 
	 * @param by      - element locator
	 */
	public void waitUntilElementIsPresent(By by) {
		WebDriver driver = ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver();
		try {
			FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
					.withTimeout(Duration.ofSeconds(300))
					.pollingEvery(Duration.ofMillis(200))
					.ignoring(NoSuchElementException.class);
			fluentWait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
			log.info("element not found! " + e.getClass().getName());
		}
	}
	
	/**
	 * method is used to check element is clickable
	 * 
	 * @param element    - web element
	 */
	public void waitUntilElementIsClickable(WebElement element ){
		WebDriver driver = ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver();
		try {
			FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
					.withTimeout(Duration.ofSeconds(300))
					.pollingEvery(Duration.ofMillis(200))
					.ignoring(NoSuchElementException.class);
			fluentWait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			log.info("element not found! " + e.getClass().getName());
		}
	}

	/**
	 * @param filePath     - path of the file which you want to drop
	 * @param fileDropArea - WebElement of file drop area
	 * @param offsetX      - offset in X direction in drop area
	 * @param offsetY      - offset in Y direction in drop area
	 */
	public void DropFile(File filePath, WebElement fileDropArea, int offsetX, int offsetY) {
		if (!filePath.exists())
			throw new WebDriverException("File not found: " + filePath.toString());
		WebDriver driver = ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver();

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		// WebDriverWait wait = new WebDriverWait(driver, 30);

		String JS_DROP_FILE = "var target = arguments[0]," + "    offsetX = arguments[1],"
				+ "    offsetY = arguments[2]," + "    document = target.ownerDocument || document,"
				+ "    window = document.defaultView || window;" + "" + "var input = document.createElement('INPUT');"
				+ "input.type = 'file';" + "input.style.display = 'none';" + "input.onchange = function () {"
				+ "  var rect = target.getBoundingClientRect(),"
				+ "      x = rect.left + (offsetX || (rect.width >> 1)),"
				+ "      y = rect.top + (offsetY || (rect.height >> 1)),"
				+ "      dataTransfer = { files: this.files };" + ""
				+ "  ['dragenter', 'dragover', 'drop'].forEach(function (name) {"
				+ "    var evt = document.createEvent('MouseEvent');"
				+ "    evt.initMouseEvent(name, !0, !0, window, 0, 0, 0, x, y, !1, !1, !1, !1, 0, null);"
				+ "    evt.dataTransfer = dataTransfer;" + "    target.dispatchEvent(evt);" + "  });" + ""
				+ "  setTimeout(function () { document.body.removeChild(input); }, 25);" + "};"
				+ "document.body.appendChild(input);" + "return input;";

		WebElement input = (WebElement) jse.executeScript(JS_DROP_FILE, fileDropArea, offsetX, offsetY);
		input.sendKeys(filePath.getAbsoluteFile().toString());
		// wait.until(ExpectedConditions.stalenessOf(input));
	}

	/**
	 * It will upload file from system to the application
	 * 
	 * @param filePath2 - path of file located in the system
	 * @return void
	 */
	public void uploadFileWithRobot(String filePath2) {

		StringSelection stringSelection = new StringSelection(filePath2);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);

		Robot robot = null;

		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		robot.delay(250);
		// Press Enter
		robot.keyPress(KeyEvent.VK_ENTER);

		// Release Enter
		robot.keyRelease(KeyEvent.VK_ENTER);

		// Press CTRL+V
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);

		// Release CTRL+V
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	/**
	 * method is used to check element visibility for second user
	 * 
	 * 
	 * @param element - Web element for which presence is tested
	 */
	public void waitUntilElementIsPresent(WebElement element) {

		System.out.println(" elements : " + element.toString().substring(42, element.toString().length() - 1)
				.replace(":", "(\"").replace(" ", "").concat("\")"));
	    By by = locatorParser( element.toString().substring(42, element.toString().length() - 1)
				.replace(":", "(\"").replace(" ", "").concat("\")"));
		try {
			FluentWait<WebDriver> fluentWait = new FluentWait<>(
					ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver())
					.withTimeout(Duration.ofSeconds(60)).pollingEvery(Duration.ofMillis(200))
							.ignoring(NoSuchElementException.class);
			 fluentWait.until(ExpectedConditions.presenceOfElementLocated(by));

		} catch (Exception e) {
			log.info("element not found! " + e.getClass().getName());
		}
	}

	/**
	 * It should iterate over "elements" and for each element if will find another
	 * element inside. The element will be found on basis of "by" Provided. Then it
	 * will iterate among them to find the element containing the sub element
	 * text(It will not be exact match) with the "label" provided.
	 * 
	 * @param elements - The elements to iterate over
	 * @param by       - The selector By to find the sub element
	 * @param label    - The label by which to get Text of Sub Element
	 * @return - The parent element for which there is a subelement with By provided
	 *         and its text will contain the label
	 * @throws InterruptedException
	 */

	public WebElement getElementContainingSubElementTextAmongElements(List<WebElement> elements, By by, String label) {
		log.info("Label : " + label);
		for (WebElement currentElement : elements) {

			try {
				if ((currentElement.findElement(by).getText().replace("?", "").trim()).contains(label.trim())) {
					log.info("Current element : " + currentElement.findElement(by).getText().replace("?", "").trim());
					return currentElement;
				}

			} catch (Exception e) {
				log.info("Exception caught while finding element! " + label);
			}
		}
		return null;
	}

	public static By locatorParser(String locator) {

		By loc = By.id(locator);

		if (locator.contains("id"))
			loc = By.id(locator.substring(locator.indexOf("\"") + 1, locator.length() - 2));

		else if (locator.contains("name"))
			loc = By.name(locator.substring(locator.indexOf("\"") + 1, locator.length() - 2));

		if (locator.contains("xpath"))
			loc = By.xpath(locator.substring(locator.indexOf("\"") + 1, locator.length() - 2));

		return loc;

	}

	/**
	 * @param s : String like 'This is project one'
	 * @return string in caeml case like 'ThisIsProjectOne'
	 */
	public String convertToCamelCase(String stringToConvert, String stringSplitter) {
		String[] parts = stringToConvert.split(stringSplitter);
		String camelCaseString = "";
		for (String part : parts) {
			camelCaseString = camelCaseString + toProperCase(part);
		}
		return camelCaseString;

	}

	private static String toProperCase(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}

	/**
	 * @param element - clear values in a text field
	 */
	public void clearTextUsingDeleteButton(WebElement element) {
		element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
	}
	
	public Map<String, String> trimAndRemoveAllSpacialCharKeyMap(Map<String, String> map) {

		for (Entry<String, String> entry : new HashSet<>(map.entrySet())) {
			String trimmed = entry.getKey().trim().replaceAll("[^A-Za-z0-9]", "");
			if (!trimmed.equals(entry.getKey())) {
				map.remove(entry.getKey());
				map.put(trimmed, entry.getValue());
			}
		}
		return map;
	}
	
    
	/**
	 * Author- Amit
	 * @param map
	 * @return string
	 * 
	 */
	public String convertWithIteration(Map<String, ?> map) {
	    //StringBuilder mapAsString = new StringBuilder("{");
		StringBuilder mapAsString = new StringBuilder();
	    for (String key : map.keySet()) {
	    	
	        mapAsString.append(key + ":" + map.get(key) + ", ");
	    }
	    mapAsString.delete(mapAsString.length()-2, mapAsString.length()).append("}");
	   return mapAsString.toString();
	}
	
	/**
	 * this method will read row data from excel file based on unique key provided in first column
	 * @param key - unique key defined in first column
	 * @param filePath - path of file
	 * @return list of row data in string format 
	 */
	public List<String> readRowDataFromExcelBasedOnUniqueKey(String key,String filePath)
	 {
		Workbook newWorkbook = null;
		 List<String> dataList = new ArrayList<String>();
		 try {
				newWorkbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
			} catch (IOException e) {
				System.err.println(e.getMessage());
				System.err.println(e.getStackTrace());
			}
			for (int i = 0; i < newWorkbook.getNumberOfSheets(); i++) {
				Sheet Sheet = newWorkbook.getSheetAt(i);
				for (Row row : Sheet)
				{if(row.getCell(0).toString().equals(key))
					{
					
					int rowSize = row.getLastCellNum();
					for(int j=0;j<rowSize;j++)
					{
						dataList.add(row.getCell(j).toString().trim())	;
					}
					break;
					}
				}
				
			}
			return dataList;
	 }
	
	
	public boolean compareTwoLists(List<String> list1, List<String> list2) {

		boolean isValuePresent = false;
		List<String> list1Missing = new ArrayList<>();
		List<String> list2Missing = new ArrayList<>();
		
		for(String str1 :list1) {
				for(String str2 :list2) {
					if(str1.trim().equalsIgnoreCase(str2.trim())) {
						isValuePresent = true;
					}
				}
				
				if(!isValuePresent) {
					list1Missing.add(str1.trim());
				} else {
					isValuePresent = false;
				}
				
			}
			
			for(String str1 :list2) {
				for(String str2 :list1) {
					if(str1.trim().equalsIgnoreCase(str2.trim())) {
						isValuePresent = true;
					}
				}
				
				if(!isValuePresent) {
					list2Missing.add(str1.trim());
				} else {
					isValuePresent = false;
				}
				
			}
			
	if(list1Missing.size()>0)
	log.info("List 1 Missing Element - ( Not present in list2 ) : " + list1Missing );
	if(list2Missing.size()>0)
	log.info("List 2 Missing Element - ( Not present in list1 ) : " + list2Missing );

	
	return list1Missing.size()>0?false:(list2Missing.size()>0?false:true);
		
	}
	
	/**
	 * Author- Dheeraj
	 * @param map
	 * @return string
	 * 
	 */
	public String convertMaptoString(Map<String, ?> map) {
	    //StringBuilder mapAsString = new StringBuilder("{");
		StringBuilder mapAsString = new StringBuilder();
	    for (String key : map.keySet()) {
	        mapAsString.append(key + ":" + map.get(key) + ", ");
	    }
	    //mapAsString.delete(mapAsString.length()-2, mapAsString.length()).append("}");
	    return mapAsString.toString();
	}
	
	
	
		
	}

