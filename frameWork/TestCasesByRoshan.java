
package frameWork;

public class TestCasesByRoshan {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestCasesByRoshan tcByr = new TestCasesByRoshan();
		tcByr.testCase001();
		tcByr.testCase002();
		tcByr.testCase003();
	}

	public void testCase001() {
		Generic util = new Generic("validateSize", "TC001");
		util.launchBrowserAndOpenUrl("ChromeDriver", "http://localhost:8888/");
		util.validateSize("name", "user_name", "usernameBox", 149, 22);
		util.validateSize("name", "user_password", "userPassword", 149, 22);
		util.validateSize("name", "Login", "loginButton", 138, 40);
		util.flushNew();
	}

	public void testCase002() {
		Generic util = new Generic("validateLocation", "TC002");
		util.launchBrowserAndOpenUrl("ChromeDriver", "http://localhost:8888/");
		util.validateLocation("name", "user_name", "usernameBox", 640, 230);
		util.validateLocation("name", "user_password", "userPassword", 640, 262);
		util.validateLocation("name", "Login", "loginButton", 640, 350);
		util.flushNew();
	}

	public void testCase003() {
		Generic util = new Generic("creatLead", "TC003");
		util.launchBrowserAndOpenUrl("ChromeDriver", "http://localhost:8888/");
		util.windowMaximize();
		util.getWebElement("name", "user_name");
		util.sendTextboxValue("name", "user_name", "usernameBox", "admin");
		util.sendTextboxValue("name", "user_password", "passwordBox", "sarita");
		util.click("name", "Login", "loginButton");
		util.click("xpath", "//td/a[text()='Marketing']", "marketing");
		util.click("xpath", "//td[contains(@class,'level2')]/a[text()='Leads']", "lead");
		util.click("xpath", "//img[@title=\"Create Lead...\"]", "creat lead");
		util.sendTextboxValue("name", "firstname", "firstnameBox", "sarita");
		util.sendTextboxValue("name", "lastname", "lastnameBox", "yadav");
		util.sendTextboxValue("name", "company", "companyBox", "<Expert View Automation>");
		util.sendTextboxValue("id", "designation", "TitleBox", "<Hi This Is Sarita Yadav Task To Create New Lead>");
		util.sendTextboxValue("id", "phone", "phoneBox", "<26844334>");
		util.sendTextboxValue("id", "email", "emailBox", "ysarita1110@gmail.com");
		util.sendTextboxValue("name", "lane", "streetBox", "<Bhadohi>");
		util.sendTextboxValue("name", "country", "countryBox", "<India>");
		util.attachScreenshot("details page of lead.png");
		util.click("xpath", "//input[@class='crmbutton small save']", "saveButton");
		util.flushNew();
	}
}
