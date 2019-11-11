package com.tutorialsninja.TutorialsninjaAutomation.base;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.tutorialsninja.TutorialsninjaAutomation.constant.FileConstant;
import com.tutorialsninja.TutorialsninjaAutomation.constant.GridConnection;
import com.tutorialsninja.TutorialsninjaAutomation.utils.ReadPropertiesFile;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * This class operate to choose the driver and choose that it run with grid
 * connection or without grid connection
 * 
 * @author arjun.santra
 *
 */

public class TestBase {
	public WebDriver driver;
	public Properties baseClass;
	public String url;
	public String browser;
	int downloadFile;
	String con;

	String huburl;

	/**
	 * This method choose which browser invoke and also choose normal execution
	 * happen or grid execution happen
	 * 
	 * @param browser
	 * @param url
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	//@Parameters({ "browser", "url" })
	@BeforeSuite
	public void intitailizeBrowser() throws IOException {
		//this.browser = browser;
		baseClass = new ReadPropertiesFile().loadProperty(FileConstant.CONFIG_FILE);
		//url = baseClass.getProperty("url");
		browser = baseClass.getProperty("browser");
		con = baseClass.getProperty("connection");
		huburl = baseClass.getProperty("remoteconnectionurl");
		//this.url = url;

		System.out.println("browser is " + browser);
		if (con.equals("normal")) {

			if (browser.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().arch64().setup();

				driver = new ChromeDriver();
				// driver.get(url);

			} else if (browser.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().arch64().setup();
				driver = new FirefoxDriver();
				// driver.get(url);
			} else if (browser.equalsIgnoreCase("internet explorer")) {
				WebDriverManager.iedriver().arch32().setup();
				DesiredCapabilities ieCaps = DesiredCapabilities.internetExplorer();
				ieCaps.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "");
				driver = new InternetExplorerDriver(ieCaps);
			}
		} else if (con.equalsIgnoreCase("grid")) {
			WebDriver drv = new GridConnection().gridCon(driver, browser, huburl);
			this.driver = drv;

		}
	}



	/**
	 * This method used to close the driver.
	 */
	@AfterSuite()
	public void endTest() {
		driver.quit();
	}
}
