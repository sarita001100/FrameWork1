package frameWork;

import java.io.IOException;
import java.time.Duration;

public class Calender {

	public static void main(String[] args) {
		Generic util = new Generic("size ", "TC001");
		util.launchBrowserAndOpenUrl("chromeDriver", "http://localhost:8888/");// launchBrowser method call
		// util. waitSetImplicitWait(5);
		util.sendTextboxValue("name", "user_name", "userName", "admin");
		util.sendTextboxValue("cssSelector", "input[name='user_password']", "userPassword", "sarita");
		util.click("name", "Login", " loginButtonImage");
		// util.click("linkText", "Marketing", "homePage");
		util.waitImplicit(10);
		util.click("xpath", "//td//img[@title='Open Calendar...']", "calender");
		util.flushNew();
	}

}
