package frameWork;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.common.io.Files;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Generic_practice {
	static WebDriver driver;
	static ExtentTest test;

	public static void main(String[] args) {

	}
public static String timeDetails() {
	DateFormat dfor = new SimpleDateFormat("dd_MM_yyyy__hh_mm_ss");
	return  dfor.format(new Date());
}
	public static void extentReport(String fileName, String testcaseName) {
		String tm=timeDetails();
		File fileObj = new File(fileName + tm + "//.html");
		ExtentSparkReporter repo = new ExtentSparkReporter(fileObj);
		ExtentReports reports = new ExtentReports();
		reports.attachReporter(repo);
		reports.setSystemInfo("UserName", System.getProperty("user.name"));
		reports.setSystemInfo("Tester Name", "Sarita Yadav");
		test = reports.createTest(testcaseName);

	}

	public static void Browser() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		test.log(Status.INFO, "Chrome Browser is launched succesfully");
	}

	public static void url(String urlName) {
		driver.get(urlName);
		test.log(Status.INFO, urlName + " url opened succesfully");
	}

	public static void userTextBox(String locatorName, String locatorValue, String xpathOrLocator, String keyName,
			String boxname, String elementName) throws IOException {
		try {
			WebElement element = getWebElement(locatorName, locatorValue);
			boolean ba = checkElement(element, boxname);
			if (ba == true) {
				element.sendKeys(keyName);
			}
		} catch (Exception e) {
			screenShot(elementName);
		}
	}

	public static WebElement getWebElement(String locatorName, String locatorValue) {
		WebElement element = null;
		if (locatorName.equalsIgnoreCase("xpath")) {
			element = driver.findElement(By.xpath(locatorValue));
		} else if (locatorName.equalsIgnoreCase("className")) {
			element = driver.findElement(By.className(locatorValue));
		} else if (locatorName.equalsIgnoreCase("name")) {
			element = driver.findElement(By.name(locatorValue));
		} else if (locatorName.equalsIgnoreCase("id")) {
			element = driver.findElement(By.id(locatorValue));
		} else if (locatorName.equalsIgnoreCase("cssSelector")) {
			element = driver.findElement(By.cssSelector(locatorValue));
		} else if (locatorName.equalsIgnoreCase("partialLinkText")) {
			element = driver.findElement(By.partialLinkText(locatorValue));
		} else if (locatorName.equalsIgnoreCase("tagName")) {
			element = driver.findElement(By.tagName(locatorValue));
		} else if (locatorName.equalsIgnoreCase("linkText")) {
			element = driver.findElement(By.linkText(locatorValue));
		}
		return element;

	}

	public static boolean checkElement(WebElement element, String boxName) {
		boolean status = false;
		if (element.isDisplayed() == true) {
			test.log(Status.PASS, boxName + " text box is displayed");
			if (element.isEnabled() == true) {
				test.log(Status.PASS, boxName + " text box is enabled");
				status = true;
			} else {
				test.log(Status.FAIL, boxName + " text box is not displayed");
			}
		} else {
			test.log(Status.FAIL, boxName + " text box is not enabled");
		}
		return status;
	}

	public static void screenShot(String fileName)  {
		TakesScreenshot screen = (TakesScreenshot) driver;
		File from = screen.getScreenshotAs(OutputType.FILE);
	String tm=	timeDetails();
		File to = new File(fileName +tm+ ".png");
		try {
			Files.copy(from, to);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void click(String locatorName, String boxName, String locatorValue, String fileName)
			throws IOException {
		try {
			WebElement element = getWebElement(locatorName, locatorValue);
			boolean ch = checkElement(element, boxName);
			if (ch == true) {
				element.click();
			}
		} catch (Exception e) {
			screenShot(fileName);
		}
	}


}