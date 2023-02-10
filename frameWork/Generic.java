package frameWork;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.common.io.Files;

public class Generic {
	private ExtentTest test;
	private ExtentReports reports;
	private WebDriver driver;

	public WebDriver getDriver() {
		if (driver == null) {
			test.log(Status.FAIL, "driver is null");
		}
		return driver;
	}

	public WebDriver setDriver(WebDriver driver) {
		this.driver = driver;
		return driver;
	}

	Generic(String fileName, String testCaseName) {
		extentReport(fileName, testCaseName);
	}

	/*
	 * Description : this is used to find out time details with the help of
	 * SimpleDateFormat class it is parameterized method in this parameter we have
	 * pass date and time format
	 */
	public String timeDetails() {
		DateFormat df = new SimpleDateFormat("MM_dd_yyyy__hh_mm_ss");
		return df.format(new Date());
	}

	/**
	 * Description :With the help of this method we can generate report
	 * 
	 * @param fileName      :It is used to give file name as a String
	 * @param testCaseName: it is used to pass TC Id or Name as a String
	 */
	public void extentReport(String fileName, String testCaseName) {

		try {
			File file = new File(fileName + timeDetails() + ".html");
			ExtentSparkReporter reportObj = new ExtentSparkReporter(file);
			reports = new ExtentReports();
			reports.attachReporter(reportObj);
			test = reports.createTest(testCaseName);
			test.log(Status.INFO, testCaseName + " is passed succesfully");
		} catch (Exception e) {
			e.printStackTrace();
			test.log(Status.INFO, testCaseName + " is Failed");
		}
	}

	/**
	 * Description : With the help of this method we can flush or generate our
	 * report.
	 */
	public void flushNew() {
		// new ExtentReports();
		reports.flush();
	}

	/**
	 * Description :with the help of this method we can invoke our browser
	 * 
	 * @param browserName: it is used to give browser name
	 * @param url:         it is used to give to open application
	 * @return : WebDriver reference variable
	 */
	public WebDriver launchBrowserAndOpenUrl(String browserName, String url) {
		try {
			if (browserName.equalsIgnoreCase("chromeDriver")) {
				driver = new ChromeDriver();
				test.log(Status.INFO, " Chrome Browser has been launched succesfully");
			} else if (browserName.equalsIgnoreCase("firfoxDriver")) {
				driver = new FirefoxDriver();
				test.log(Status.INFO, " Firefox browser has been launched succesfully");
			} else if (browserName.equalsIgnoreCase("edgeDriver")) {
				driver = new EdgeDriver();
				test.log(Status.INFO, " EdgeDriver browser has been launched succesfully");
			} else if (browserName.equalsIgnoreCase("InternetExplorerDriver")) {
				driver = new InternetExplorerDriver();
				test.log(Status.INFO, " InternetExplorerDriver browser has been launched succesfully");
			} else if (browserName.equalsIgnoreCase("SafariDriver")) {
				driver = new SafariDriver();
				test.log(Status.INFO, " SafariDriver browser has been launched succesfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			test.log(Status.FAIL, " browser is not launched,browser name is  not valid, please check.. ");
		}
		try {
			driver.get(url);
			test.log(Status.INFO, url + " is entered succesfully");
		} catch (Exception e) {
			driver.navigate().to(url);
			test.log(Status.INFO, url + " is entered succesfully");
		} catch (Throwable e) {
			e.getMessage();
			screenShot("url");
			test.log(Status.FAIL, url + " is not entered succesfully");
		}
		return driver;
	}

	/**
	 * @param locatorType   : it is used to for name of locator type
	 * @param locatorValue: it is used to for give locator path
	 * @return: it is returns WebElement class object.
	 */
	public WebElement getWebElement(String locatorType, String locatorValue) {
		WebElement userObj = null;
		if (locatorType.equalsIgnoreCase("xpath")) {
			return userObj = driver.findElement(By.xpath(locatorValue));
		} else if (locatorType.equalsIgnoreCase("linkText")) {
			return userObj = driver.findElement(By.linkText(locatorValue));
		} else if (locatorType.equalsIgnoreCase("name")) {
			return userObj = driver.findElement(By.name(locatorValue));
		} else if (locatorType.equalsIgnoreCase("className")) {
			return userObj = driver.findElement(By.className(locatorValue));
		} else if (locatorType.equalsIgnoreCase("id")) {
			return userObj = driver.findElement(By.id(locatorValue));
		} else if (locatorType.equalsIgnoreCase("cssSelector")) {
			return userObj = driver.findElement(By.cssSelector(locatorValue));
		} else if (locatorType.equalsIgnoreCase("tagName")) {
			return userObj = driver.findElement(By.tagName(locatorValue));
		} else if (locatorType.equalsIgnoreCase("partialLinkText")) {
			return userObj = driver.findElement(By.partialLinkText(locatorValue));
		} else {
			test.log(Status.FAIL, locatorType + "Locator Type is  wrong..please check");
		}
		return userObj;
	}

	/**
	 * @param locatorType   : it is used to for give name of locator type
	 * @param locatorValue: it is used to for give locator path
	 * @param elementName:  it is used for where we are working that element name
	 * @return : it returns the value in boolean
	 */
	public boolean checkElement(String locatorType, String locatorValue, String elementName) {
		boolean status = false;
		try {
			WebElement userObj = getWebElement(locatorType, locatorValue);

			if (userObj.isDisplayed() == true) {
				test.log(Status.PASS, elementName + " is visible");
				if (userObj.isEnabled() == true) {
					test.log(Status.PASS, elementName + " is enabled");
					status = true;
				} else {
					test.log(Status.FAIL, elementName + " is not enabled");
				}
			} else {
				test.log(Status.FAIL, elementName + " is not visible");

			}
		} catch (Exception e) {
			test.log(Status.FAIL, elementName + " is not Displayed and Enabled");
		}
		return status;

	}

	/**
	 * @param locatorType:  it is used to give for name of locator type
	 * @param locatorValue: it is used to give for locator path
	 * @param elementName:  it is used for where we are working that element name
	 * @param sendValue     : it is used to give value as a String in a textBox
	 */
	public void sendTextboxValue(String locatorType, String locatorValue, String elementName, String sendValue) {
		WebElement userObj = null;
		try {
			userObj = getWebElement(locatorType, locatorValue);
			boolean st = checkElement(locatorType, locatorValue, elementName);
			if (st == true) {
				userObj.clear();
				userObj.sendKeys(sendValue);
				test.log(Status.INFO, sendValue + " is entered succesfully by WebElement");
			}
		} catch (ElementNotInteractableException e) {
			new Actions(driver).sendKeys(sendValue).build().perform();
			test.log(Status.INFO, sendValue + " is entered succesfully by Actions ");

		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].value=\"" + sendValue + "", userObj);
			test.log(Status.INFO, sendValue + " is entered succesfully by JavascriptExecutor ");

		} catch (Throwable e) {
			e.printStackTrace();
			test.log(Status.FAIL, sendValue + " is not entered succesfully ,please check ");
			screenShot(elementName);
		}
	}

	/**
	 * @param locatorType:  it is used to give for name of locator type
	 * @param locatorValue: it is used to give for locator path
	 * @param elementName:  it is used for where we are working that element name
	 */
	public void click(String locatorType, String locatorValue, String elementName) {
		WebElement userObj = null;
		try {
			userObj = getWebElement(locatorType, locatorValue);
			boolean st = checkElement(locatorType, locatorValue, elementName);
			if (st == true) {
				userObj.click();
				test.log(Status.INFO, elementName + " is clicked succesfully by WebElement");
			}
		} catch (ElementNotInteractableException e) {
			new Actions(driver).click().build().perform();
			test.log(Status.INFO, elementName + " is clicked succesfully by Actions");

		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click()", userObj);
			test.log(Status.INFO, elementName + " is clicked succesfully by JavascriptExecutor ");

		} catch (Throwable e) {
			e.printStackTrace();
			test.log(Status.FAIL, elementName + " is not clicked succesfully ,please check ");
			screenShot(elementName);
		}
	}

	/**
	 * @param screenshotImageName: it is used for give screenshotImageName as a
	 *                             String
	 */
	public void screenShot(String screenshotImageName) {
		try {
			TakesScreenshot scr = (TakesScreenshot) driver;
			File from = scr.getScreenshotAs(OutputType.FILE);
			String tm = timeDetails();
			File to = new File("snapshots\\ " + screenshotImageName + tm + ".png");

			Files.copy(from, to);
			test.log(Status.INFO, "ScreenShot copy on your Destination");

		} catch (Exception e) {
			e.printStackTrace();
			test.log(Status.FAIL, "ScreenShot not copy on your Destination");
		}
	}

	/**
	 * @param filePath: it is used for give filePath where we want so save our
	 *                  screenshot
	 */
	public void attachScreenshot(String filePath) {
		File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File t = new File(filePath);
		try {
			Files.copy(f, t);
			test.log(Status.INFO, "ScreenShot copy on your Destination");
		} catch (IOException e) {
			e.printStackTrace();
			test.log(Status.FAIL, "ScreenShot not copy on your Destination");
		}
		test.addScreenCaptureFromPath(t.getAbsolutePath());
	}

	/**
	 * @param locatorType:  it is used to give for name of locator type
	 * @param locatorValue: it is used to give for locator path or value
	 * @param elementName:  it is used for where we are working that element name
	 * @param textValue:    it is used to dropDown for VisibleText which we want to
	 *                      select by select class
	 */
	public void selectByText(String locatorType, String locatorValue, String elementName, String textValue) {
		WebElement userObj = null;
		try {
			userObj = getWebElement(locatorType, locatorValue);

			boolean st = checkElement(locatorType, locatorValue, elementName);
			if (st == true) {
				Select selectObj = new Select(userObj);
				selectObj.selectByVisibleText(textValue);
				test.log(Status.INFO, "DropDown is handled with the " + textValue);
			}

		} catch (Exception e) {
			screenShot(elementName);
			test.log(Status.FAIL, "DropDown is not handled with the " + textValue);
		}
	}

	/**
	 * @param locatorType:    it is used to give for name of locator type
	 * @param locatorValue:   it is used to give for locator path or value
	 * @param elementName:    it is used for where we are working that element name
	 * @param valueAttribute: it is used for give value attribute value to handle
	 *                        dropDown
	 */
	public void selectByValueAttribute(String locatorType, String lovatorValue, String elementName,
			String valueAttribute) {
		try {
			WebElement userObj = getWebElement(locatorType, lovatorValue);
			boolean st = checkElement(locatorType, lovatorValue, elementName);
			if (st == true) {
				Select selectObj = new Select(userObj);
				selectObj.selectByValue(valueAttribute);
				test.log(Status.INFO, "DropDown is handled with the " + valueAttribute);

			}
		} catch (Exception e) {
			screenShot("loginButton");
			test.log(Status.FAIL, "DropDown is not handled with the " + valueAttribute);

		}
	}

	/**
	 * @param locatorType:  it is used to give for name of locator type
	 * @param locatorValue: it is used to give for locator path or value
	 * @param elementName:  it is used for where we are working that element name
	 * @param indexValue    : it is used for give index number to handle dropDown
	 */
	public void selectByIndexed(String locatorType, String lovatorValue, String elementName, int indexValue) {
		try {
			WebElement userObj = getWebElement(locatorType, lovatorValue);
			boolean st = checkElement(locatorType, lovatorValue, elementName);
			if (st == true) {
				Select selectObj = new Select(userObj);
				selectObj.selectByIndex(indexValue);
				test.log(Status.INFO, "DropDown is handled with the" + indexValue);

			}
		} catch (Exception e) {
			screenShot("loginButton");
			test.log(Status.FAIL, "DropDown is not handled with the" + indexValue);

		}
	}

	/**
	 * @param locatorType:  it is used to give for name of locator type
	 * @param locatorValue: it is used to give for locator path or value
	 * @param elementName:  it is used for where we are working that element name
	 * @param exceptedText: it is used to give exceptedText for matching actual and
	 *                      expected text
	 */
	public void validateInnerText(String locatorType, String locatorValue, String elementName, String exceptedText) {
		String actualtext = null;
		try {
			actualtext = getInnerText(locatorType, locatorValue, elementName);
			if (actualtext.equalsIgnoreCase(exceptedText)) {
				test.log(Status.INFO,
						actualtext + " test validation passed. actualtext is matched with expectedtext" + exceptedText);
			}
		} catch (Exception e) {
			test.log(Status.FAIL, actualtext + " test validation failed.  actualtext is not matched with expectedtext"
					+ exceptedText);
			screenShot(elementName);
		}
	}

	public String getInnerText(String locatorType, String locatorValue, String elementName) {
		String getinn = null;
		try {
			WebElement userObj = getWebElement(locatorType, locatorValue);
			boolean ce = checkElement(locatorType, locatorValue, elementName);
			if (ce == true) {
				getinn = userObj.getText();
				test.log(Status.INFO, "getInnerText method is worked succesfully");
			}
		} catch (Exception e) {
			screenShot(elementName);
			test.log(Status.FAIL, "getInnerText method is not worked succesfully");
		}
		return getinn;
	}

	public void validateAttributeValue(String locatorType, String locatorValue, String elementName,
			String attributeValue, String expectedAttributeValue) {
		try {
			String actualAttributeValue = getWebElement(locatorType, locatorValue).getAttribute(attributeValue);
			System.out.println(attributeValue + "valid");
			if (actualAttributeValue.equalsIgnoreCase(expectedAttributeValue)) {
				test.log(Status.PASS, elementName + "  test validation passed. actualText- " + actualAttributeValue
						+ " expectedText-" + expectedAttributeValue);
			}
		} catch (Exception e) {
			test.log(Status.FAIL, elementName + "  test validation failed. actualText- " + attributeValue
					+ " expectedText-" + expectedAttributeValue);
		}
	}

	public String getAttributeValue(String locatorType, String locatorValue, String elementName,
			String attributeValue) {
		String getinn = null;
		try {
			WebElement userObj = getWebElement(locatorType, locatorValue);
			boolean ce = checkElement(locatorType, locatorValue, elementName);
			if (ce == true) {
				getinn = userObj.getAttribute(attributeValue);
				test.log(Status.INFO, "this method is worked succesfully");

			}
		} catch (Exception e) {
			screenShot("find button");
			test.log(Status.FAIL, "this method is not worked succesfully");

		}
		System.out.println(getinn);

		return getinn;
	}

	public void validateSize(String locatorType, String locatorValue, String elementName, int wh, int ht) {
		Dimension expectedSize = new Dimension(wh, ht);
		Dimension actualSize = null;
		try {
			actualSize = giveSize(locatorType, locatorValue, elementName);
			System.out.println(actualSize + "==" + expectedSize);
			if (actualSize.equals(expectedSize)) {
				System.out.println("matched");
				test.log(Status.INFO, actualSize + " actualSize is matched with expectedSize " + expectedSize);
			} else {
				System.out.println("miss-matched");

			}
		} catch (Exception e) {
			test.log(Status.FAIL, actualSize + " is not matched with " + expectedSize);
		}
	}

	public Dimension giveSize(String locatorType, String locatorValue, String elementName) {
		Dimension getSize = null;
		try {
			WebElement userObj = getWebElement(locatorType, locatorValue);
			boolean ce = checkElement(locatorType, locatorValue, elementName);
			if (ce == true) {
				getSize = userObj.getSize();
				int ht = getSize.getHeight();
				int wh = getSize.getWidth();

				// System.out.println("height is "+ht +"width is "+wh);
				System.out.println(getSize);
				test.log(Status.INFO, "Size of " + elementName + " Height is " + ht + " Widht is " + wh);
			}
		} catch (Exception e) {
			screenShot(elementName);
			test.log(Status.FAIL, "The Size is not matched of " + elementName);
		}
		return getSize;
	}

	public Point giveLocation(String locatorType, String locatorValue, String elementName) {
		Point getLocationObj = null;
		try {
			WebElement userObj = getWebElement(locatorType, locatorValue);
			boolean ce = checkElement(locatorType, locatorValue, elementName);
			if (ce == true) {
				getLocationObj = userObj.getLocation();
				int xValue = getLocationObj.getX();
				int yValue = getLocationObj.getY();
				test.log(Status.INFO, "Location of " + elementName + " X- " + xValue + " Y- " + yValue);
				System.out.println(getLocationObj);
			}
		} catch (Exception e) {
			screenShot(elementName);
			test.log(Status.FAIL, "The Location is not matched of " + elementName);
		}
		return getLocationObj;
	}

	public void validateLocation(String locatorType, String locatorValue, String elementName, int xValue, int yValue) {
		Point actualLocation = null;
		Point expectedLocation = new Point(xValue, yValue);
		System.out.println(expectedLocation);
		try {
			actualLocation = giveLocation(locatorType, locatorValue, elementName);
			if (actualLocation.equals(expectedLocation)) {
				test.log(Status.INFO,
						actualLocation + " actualLocation is matched with expectedLocation " + expectedLocation);
			}
		} catch (Exception e) {
			test.log(Status.FAIL,
					actualLocation + " actualLocation is not matched with expectedLocation " + expectedLocation);
		}
	}

	public void validateTitle(String locatorType, String locatorValue, String elementName, String expectedTitle) {
		String actualTitleName = null;
		try {
			actualTitleName = giveTitleName(locatorType, locatorValue, elementName);

			if (actualTitleName.equalsIgnoreCase(expectedTitle))
				;
			test.log(Status.INFO, actualTitleName + " is matched with " + expectedTitle);
		} catch (Exception e) {
			test.log(Status.FAIL, actualTitleName + " is not matched with " + expectedTitle);

		}
	}

	public String giveTitleName(String locatorType, String locatorValue, String elementName) {
		String getTitle = null;
		try {
			WebElement userObj = getWebElement(locatorType, locatorValue);
			boolean ce = checkElement(locatorType, locatorValue, elementName);
			if (ce == true) {
				getTitle = driver.getTitle();
			}
		} catch (Exception e) {
			screenShot(elementName);
		}
		return getTitle;
	}

	public void windowHandle(String locatorType, String locatorValue, String elementName) {
		String winHandleObj = null;
		try {
			WebElement userObj = getWebElement(locatorType, locatorValue);
			boolean ce = checkElement(locatorType, locatorValue, elementName);
			if (ce == true) {
				winHandleObj = driver.getWindowHandle();
			}
		} catch (Exception e) {
			screenShot(elementName);
		}
	}

	/**
	 * @param locatorType  :it is used to give name of locator type
	 * @param locatorValue : it is used to give locator path or value
	 * @param elementName  :
	 * @param title
	 */
	public void windowHandles(String locatorType, String locatorValue, String elementName, String title) {
		Set<String> winHandlesObj1 = null;
		try {
			WebElement userObj = getWebElement(locatorType, locatorValue);
			boolean ce = checkElement(locatorType, locatorValue, elementName);
			if (ce == true) {
				winHandlesObj1 = driver.getWindowHandles();
				for (String str : winHandlesObj1) {
					String title1 = driver.switchTo().window(str).getTitle();
					if (title1.equalsIgnoreCase(title)) {
						break;
					} else {
						test.log(Status.FAIL, MarkupHelper.createLabel(
								title + " title is not matched with " + title1 + " title", ExtentColor.RED));
					}
				}
			}
		} catch (Exception e) {
			screenShot(elementName);
		}
	}

	/**
	 * @param frameNumber : it is used to give frame number as aInt to handle frame
	 */
	public void handleFrame(int frameNumber) {
		try {
			driver.switchTo().frame(frameNumber);
			test.log(Status.INFO, frameNumber + " is entered succesfully ");
		} catch (Exception e) {
			test.log(Status.FAIL, frameNumber + " is not entered succesfully ");
		}
	}

	/**
	 * @param IDOrName : it is used to give id or name as a String to handle frame
	 */
	public void handleFrame(String IDOrName) {
		try {
			driver.switchTo().frame(IDOrName);
			test.log(Status.INFO, IDOrName + " is correct");
		} catch (Exception e) {
			test.log(Status.FAIL, IDOrName + " is not correct..please check");
		}
	}

	/**
	 * @param frameElement: it is used to give WebElement class reference object
	 */
	public void handleFrame(WebElement frameElement) {
		try {
			driver.switchTo().frame(frameElement);
			test.log(Status.INFO, frameElement + " is correct");
		} catch (Exception e) {
			test.log(Status.FAIL, frameElement + " is not correct..please check");

		}
	}

	/**
	 * Description: this method is used to go parent frame from current frame
	 */
	public void goParentFrame() {
		try {
			driver.switchTo().parentFrame();
			test.log(Status.INFO, "it is entered in parent frame succesfully");
		} catch (Exception e) {
			test.log(Status.FAIL, "it is not entered in parent frame succesfully");
		}
	}

	/**
	 * Description: this method is used to direct out from any frame
	 */
	public void outFromFrame() {
		try {
			driver.switchTo().defaultContent();
			test.log(Status.INFO, "");
		} catch (Exception e) {

		}
	}

	/**
	 * Description: this method is used to accept the popUp
	 */
	public void popupAccept() {
		try {
			driver.switchTo().alert().accept();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description: this method is used for dismiss the popUp
	 */
	public void popupdismiss() {
		try {
			driver.switchTo().alert().dismiss();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description: this method is used for get text from popUp
	 */
	public void getTextFromPopUp() {
		try {
			String text = driver.switchTo().alert().getText();
			test.log(Status.INFO, "the text of this popup is " + text);
		} catch (Exception e) {
			test.log(Status.FAIL, "the text of this popup is wrong");
			e.printStackTrace();
		}
	}

	/**
	 * @param sendValue : it is used to send value in pop to handle time
	 */
	public void sendValueInPopUp(String sendValue) {
		try {
			driver.switchTo().alert().sendKeys(sendValue);
			test.log(Status.INFO, sendValue + " is entered succesfully");
		} catch (Exception e) {
			test.log(Status.FAIL, sendValue + " is not entered succesfully");

		}
	}

	/**
	 * @param locatorType:  it is used to give name of locator type
	 * @param locatorValue  : it is used to give locator path or value
	 * @param fileValuePath : it is used to give fileXpath
	 */
	public void uploadfile(String locatorType, String locatorValue, String fileValuePath) {
		try {
			WebElement fileObj = getWebElement(locatorType, locatorValue);
			fileObj.sendKeys(fileValuePath);
			test.log(Status.INFO, fileValuePath + " is correct file is uploded");
		} catch (Exception e) {
			test.log(Status.FAIL, fileValuePath + " is not correct file is not uploded");

		}
	}

	/**
	 * @param scrollObj : it is used to give WebElement class reference object
	 */
	public void scrollwithJavaScriptUsingElement(WebElement scrollObj) {
		((JavascriptExecutor) driver).executeScript("arguments[0].ScrollIntoview()", scrollObj);
	}

	public void scrollwithJavaScriptusingCoordinates(int x, int y) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(" + x + "," + y + ")", " ");
	}

	/**
	 * @param locatorType         :it is used to give name of locator type
	 * @param locatorValue:       it is used to give locator path or value
	 * @param colorElementName    :it is used to give colorName of element
	 * @param codeOfColorElement: it is used to give code of color element where we
	 *                            are working
	 */
	public void colorOrBackGroundColorOfElement(String locatorType, String locatorValue, String colorElementName,
			String codeOfColorElement) {
		try {
			String colorObj = getWebElement(locatorType, locatorValue).getCssValue(colorElementName);
			String value = Color.fromString(colorObj).asHex();
			if (value.equals(codeOfColorElement)) {
				test.log(Status.INFO, colorElementName + " is right");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, colorElementName + " is wrong");
			e.printStackTrace();
		}
	}

	/**
	 * @param locatorType   :it is used to give name of locator type
	 * @param locatorValue: it is used to give locator path or value
	 */
	public void rightClick(String locatorType, String locatorValue) {
		try {
			WebElement click = getWebElement(locatorType, locatorValue);
			Actions action = new Actions(driver);
			action.contextClick(click).build().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param locatorType:it is used to give name of locator type where we want to
	 *                       hold element
	 * @param locatorValue   :it is used to give locator path or value where we want
	 *                       to hold element
	 * @param locatorType1:  it is used to give name of locator type where we want
	 *                       to release element
	 * @param locatorValue1  :it is used to give locator path or value where we want
	 *                       to release element
	 */
	public void doClickAndRelease(String locatorTypeHold, String locatorValueHold, String locatorTypeRelease,
			String locatorValueRelease) {
		try {
			WebElement hold = getWebElement(locatorTypeHold, locatorValueHold);
			WebElement real = getWebElement(locatorTypeRelease, locatorValueRelease);
			Actions action = new Actions(driver);
			action.clickAndHold(hold).release(real).build().perform();

			test.log(Status.INFO, " this element released by action class");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param locatorType:      it is used to give name of locator type
	 * @param locatorValue:     it is used to give locator path or value
	 * @param locatorTypeDrop:  it is used to give type of locator where we want to
	 *                          drop element
	 * @param locatorValueDrop: it is used to give type of locator value where we
	 *                          want to drop element
	 * @param elementName       : it is used to give element name where we are
	 *                          working
	 */
	public void doDragAndDrop(String locatorType, String locatorValue, String locatorTypeDrop, String locatorValueDrop,
			String elementName) {
		try {
			WebElement drag = getWebElement(locatorType, locatorValue);
			WebElement drop = getWebElement(locatorTypeDrop, locatorValueDrop);
			Actions action = new Actions(driver);
			test.log(Status.INFO, MarkupHelper
					.createLabel("Drag and drop is performed on" + elementName + " successfully", ExtentColor.BLACK));
			action.dragAndDrop(drag, drop);
		} catch (Exception e) {
			e.printStackTrace();
			test.log(Status.FAIL, MarkupHelper
					.createLabel("Drag and drop is not performed on" + elementName + " successfully", ExtentColor.RED));
		}
	}

	/**
	 * @param timeunitInSecond :it is used to give time format in second
	 */
	public void waitImplicit(int timeunitInSecond) {
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeunitInSecond));
			test.log(Status.INFO, timeunitInSecond + " seconds timeout is executed for implicit wait");
		} catch (Exception e) {
			test.log(Status.FAIL, "implicit wait is not executed for driver as expected ");
		}
	}

	/**
	 * @param locatorType:      it is used to give name of locator type
	 * @param locatorValue:     it is used to give locator path or value
	 * @param timeUnitInSecond: it is used to give time format in second
	 */
	public void waitUntilElementIsEnabled(String locatortype, String locatorvalue, int timeUnitInSecond) {
		WebDriverWait explicitWaitObj = new WebDriverWait(driver, Duration.ofSeconds(timeUnitInSecond));
		explicitWaitObj.until(ExpectedConditions.elementToBeClickable(getWebElement(locatortype, locatorvalue)));
	}

	/**
	 * @param locatorType:      it is used to give name of locator type
	 * @param locatorValue:     it is used to give locator path or value
	 * @param timeUnitInSecond: it is used to give time format in second
	 */
	public void waitUntilElementIsDisabled(String locatortype, String locatorvalue, int timeUnitInSecond) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeUnitInSecond));
		wait.until(ExpectedConditions
				.not(ExpectedConditions.elementToBeClickable(getWebElement(locatortype, locatorvalue))));
	}

	/**
	 * @param locatorType:      it is used to give name of locator type
	 * @param locatorValue:     it is used to give locator path or value
	 * @param timeUnitInSecond: it is used to give time format in second
	 */
	public void waitUntilElementIsinvisible(String locatortype, String locatorvalue, int timeUnitInSecond) {
		WebDriverWait explicitWaitObj = new WebDriverWait(driver, Duration.ofSeconds(timeUnitInSecond));
		explicitWaitObj.until(ExpectedConditions.invisibilityOf(getWebElement(locatortype, locatorvalue)));
	}

	/**
	 * @param locatorType:      it is used to give name of locator type
	 * @param locatorValue:     it is used to give locator path or value
	 * @param timeUnitInSecond: it is used to give time format in second
	 * @param expectedText:     it is used to give our expected text which we want
	 *                          to give
	 */
	public void waitUntilElementTextChanged(String locatortype, String locatorvalue, int timeUnitInSecond,
			String expectedText) {
		WebDriverWait explicitWaitObj = new WebDriverWait(driver, Duration.ofSeconds(timeUnitInSecond));
		explicitWaitObj.until(
				ExpectedConditions.textToBePresentInElement(getWebElement(locatortype, locatorvalue), expectedText));
	}

	/**
	 * @param locatorType:  it is used to give for name of locator type
	 * @param locatorValue: it is used to give for locator path or value
	 * @param elementName:  it is used for where we are working that element name
	 */
	public void giveFirstSelected(String locatorType, String locatorValue) {
		WebElement selElement = getWebElement(locatorType, locatorValue);
		Select first = new Select(selElement);
		String firstObj = first.getFirstSelectedOption().getText();
		test.log(Status.INFO,
				MarkupHelper.createLabel(firstObj + " is selected option in dropDown", ExtentColor.GREEN));
	}

	/**
	 * @param locatorType:  it is used to give for name of locator type
	 * @param locatorValue: it is used to give for locator path or value
	 * @param elementName:  it is used for where we are working that element name
	 */
	public void giveAllOption(String locatorType, String locatorValue, String elementName) {
		ArrayList<String> arr = new ArrayList<String>();
		Select sel = new Select(getWebElement(locatorType, locatorValue));
		List<WebElement> list = sel.getOptions();
		for (int i = 0; i <= list.size() - 1; i++) {
			WebElement webobj = list.get(i);
			String g = webobj.getText();
			arr.add(g);
		}
		test.log(Status.INFO, MarkupHelper.createLabel(arr + " all are options of " + elementName, ExtentColor.BLUE));
	}

	/**
	 * @param locatorType:  it is used to give for name of locator type
	 * @param locatorValue: it is used to give for locator path or value
	 * @param elementName:  it is used for where we are working that element name
	 */
	public void validateElementIsEnabled(String locatorType, String locatorValue, String elementName) {
		try {
			WebElement userObj = getWebElement(locatorType, locatorValue);
			boolean status = userObj.isEnabled();
			if (status == true) {
				test.log(Status.PASS, elementName + " is enabled");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, elementName + " is disabled");
		}
	}

	/**
	 * @param locatorType:  it is used to give for name of locator type
	 * @param locatorValue: it is used to give for locator path or value
	 * @param elementName:  it is used for where we are working that element name
	 */
	public void validateElementIsDisabled(String locatorType, String locatorValue, String elementName) {
		try {
			WebElement userObj = getWebElement(locatorType, locatorValue);
			boolean status = userObj.isEnabled();
			if (status == false) {
				test.log(Status.PASS, elementName + " is disabled");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, elementName + " is enabled");
		}
	}

	/**
	 * @param locatorType:  it is used to give for name of locator type
	 * @param locatorValue: it is used to give for locator path or value
	 * @param elementName:  it is used for where we are working that element name
	 */
	public void validateElementIsVisible(String locatorType, String locatorValue, String elementName) {
		try {
			WebElement userObj = getWebElement(locatorType, locatorValue);
			boolean status = userObj.isDisplayed();
			if (status == true) {
				test.log(Status.PASS, elementName + " is visible");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, elementName + " is  invisible");
		}
	}

	/**
	 * @param locatorType:  it is used to give for name of locator type
	 * @param locatorValue: it is used to give for locator path or value
	 * @param elementName:  it is used for where we are working that element name
	 */
	public void validateElementInVisible(String locatorType, String locatorValue, String elementName) {
		try {
			WebElement userObj = getWebElement(locatorType, locatorValue);
			boolean status = userObj.isDisplayed();
			if (status == false) {
				test.log(Status.PASS, elementName + " is invisible");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, elementName + " is  visible");
		}
	}

	/**
	 * @param locatorType:  it is used to give for name of locator type
	 * @param locatorValue: it is used to give for locator path or value
	 * @param elementName:  it is used for where we are working that element name
	 */
	public void validateElementIsSelected(String locatorType, String locatorValue, String elementName) {
		try {
			WebElement userObj = getWebElement(locatorType, locatorValue);
			boolean status = userObj.isSelected();
			if (status == true) {
				test.log(Status.PASS, elementName + " is selected");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, elementName + " is not selected");
		}
	}

	/**
	 * @param locatorType:  it is used to give for name of locator type
	 * @param locatorValue: it is used to give for locator path or value
	 * @param elementName:  it is used for where we are working that element name
	 */
	public void validateElementIsDeSelected(String locatorType, String locatorValue, String elementName) {
		try {
			WebElement userObj = getWebElement(locatorType, locatorValue);
			boolean status = userObj.isSelected();
			if (status == false) {
				test.log(Status.PASS, elementName + " is deselected");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, elementName + " is selected");
		}
	}

	/**
	 * this method is used for close the current window.
	 */
	public void closeWindow() {
		try {
			driver.close();
			test.log(Status.INFO, "Window is closed succesfully");
		} catch (Exception e) {
			test.log(Status.FAIL, "Window is not closed ");

		}
	}

	/**
	 * this method is used for quit all window.
	 */
	public void quitWindow() {
		try {
			driver.quit();
			test.log(Status.INFO, "all Window is closed succesfully");
		} catch (Exception e) {
			test.log(Status.FAIL, "All Window is not closed ");
		}
	}

	public void windowMaximize() {
		try {
			driver.manage().window().maximize();
          test.log(Status.INFO, "this window is maximized succesfully");
		} catch (Exception e) {
	          test.log(Status.FAIL, "this window is  not maximized succesfully");
e.printStackTrace();
		}
	}

	public void windowMinimize() {
		try {
			driver.manage().window().minimize();
	          test.log(Status.INFO, "this window is minimized succesfully");

		} catch (Exception e) {
			e.printStackTrace();
	          test.log(Status.FAIL, "this window is  not minimized succesfully");
}
	}

	/**
	 * Description :It is used to do back page
	 */
	public void backPage() {
		try {
			driver.navigate().back();
			test.log(Status.INFO, "the page has back");
		} catch (Exception e) {
			test.log(Status.FAIL, "the page has not back");
		}
	}
	/**
	 * Description :It is used to do forward page
	 */
	public void forwardPage() {
		try {
			driver.navigate().forward();
			test.log(Status.INFO, "the page has forward");
		} catch (Exception e) {
			test.log(Status.FAIL, "the page has not forward");
		}
	}
	/**
	 * Description :It is used to do refresh page
	 */
	public void refreshPage() {
		try {
			driver.navigate().refresh();
			test.log(Status.INFO, "the page has refresh");
		} catch (Exception e) {
			test.log(Status.FAIL, "the page has not refresh");
		}
	}
	
	
	
}
