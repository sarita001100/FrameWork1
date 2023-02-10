package frameWork;

public class TestCase  {

	public static void main(String[] args) {
		Generic util=new Generic ("vtiger ", "TC001");
		//util.extentReport("size ", "TC001");
		util.launchBrowserAndOpenUrl("ChromeDriver", "http://localhost:8888/");// launchBrowser method call
		// util.waitSetImplicitWait(5);
		//util. attachScreenshot("sky.png");
		util.sendTextboxValue("name", "user_name", "userName", "admin");
		util.sendTextboxValue("cssSelector", "input[name='user_password']", "userPassword", "sarita");
		// util.giveFirstSelected("xpath", "//select[@name='login_theme']");
//		util.giveAllOption("xpath", "//select[@name='login_theme']","colorTheme");
//		util.giveAllOption("name", "login_language","Language");
		// util.giveSelectedOption("xpath", "//select[@name='login_theme']");
		// util.giveSize("name", "Login", " loginButtonImage");
		util.validateSize("name", "Login", " loginButtonImage", 138, 40);
		util.validateLocation("name", "Login", " loginButtonImage", 640, 350);
		util.click("name", "Login", " loginButtonImage");
		util.click("linkText", "Marketing", "homePage");
		util.validateInnerText("xpath", "//td[@class='moduleName']", "my home page header", "My Home Page > Home");
		util.validateAttributeValue("className", "searchBtn","find button","value", "Find");
		// util.validateInnerText("xpath", "//td[@class=\"moduleName\"]", "home Page header",
		// "My Home Page > Home");
		// util.validateSize("name", "Login", " loginButtonImage", null);
		util.flushNew();

	}

}
