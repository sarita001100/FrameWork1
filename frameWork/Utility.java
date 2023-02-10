package frameWork;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Utility {
	static WebDriver driver;

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\COMTECH\\eclipse-Sarita\\Selenium_Project\\Exe.File\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://localhost:8888/");
		sendkeys("//input[@name=\"user_name\"]", "admin");
		sendkeys("//input[@name='user_password']","sarita");
		webElementClick("//input[@name='Login']");
	}

	public static void sendkeys(String xpathOrLocator, String sendkeysValue) {
		WebElement webObj = driver.findElement(By.xpath(xpathOrLocator));
		webObj.clear();
		webObj.sendKeys(sendkeysValue);
		System.out.println(sendkeysValue + " value is send succesfully in inputbox");
	}

	public static void webElementClick(String xpathOrLocator) {
		WebElement webObj = driver.findElement(By.xpath(xpathOrLocator));
		webObj.click();
	}

	public static void actionClick(String xpathOrLocator) {
		Actions actObj = new Actions(driver);
		driver.findElement(By.xpath(xpathOrLocator));
		actObj.click().build().perform();
	}

	public static void javascriptExecutor(String xpathOrLocator) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement webObj = driver.findElement(By.xpath(xpathOrLocator));
		js.executeScript("argument[0].click()", webObj);
	}

	public static void allClick() {
		try {
			webElementClick(null);
		} catch (ElementNotInteractableException e) {
			actionClick(null);
		} catch (Exception e) {
			javascriptExecutor(null);
		}
	}
}
