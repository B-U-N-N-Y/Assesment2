package com.tutorialsninja.TutorialsninjaAutomation.testscripts;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.tutorialsninja.TutorialsninjaAutomation.base.TestBase;
import com.tutorialsninja.TutorialsninjaAutomation.constant.FileConstant;
import com.tutorialsninja.TutorialsninjaAutomation.dataProvider.TestDataProvider;
import com.tutorialsninja.TutorialsninjaAutomation.helper.Utility;
import com.tutorialsninja.TutorialsninjaAutomation.helper.Waits;
import com.tutorialsninja.TutorialsninjaAutomation.pages.HeatClinicValidation;
import com.tutorialsninja.TutorialsninjaAutomation.pages.TutorialsNinjaValidation;
import com.tutorialsninja.TutorialsninjaAutomation.reports.LogReport;
import com.tutorialsninja.TutorialsninjaAutomation.utils.ReadPropertiesFile;

public class HeatClinicFuctionalityTesting extends TestBase {

	Properties loc;
	LogReport log;
	TutorialsNinjaValidation validationPage;
	Properties testDataFromProperty;
	Utility utility;
	Waits wait;
	HeatClinicValidation heatClinicValidation;

	@BeforeClass
	public void intailization() {
		String url = baseClass.getProperty("heatclinicurl");
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(FileConstant.TIMEOUT_INSECONDS, TimeUnit.SECONDS);
		loc = new ReadPropertiesFile().loadProperty(FileConstant.LOCATOR_FILE);
		testDataFromProperty = new ReadPropertiesFile().loadProperty(FileConstant.VALIDATION_PROPERTY_FILE);
		log = new LogReport();
		utility = new Utility(driver);
		validationPage = new TutorialsNinjaValidation(driver);
		wait=new Waits();
		heatClinicValidation=new HeatClinicValidation(driver);

	}

	@Test(priority = 1, dataProvider = "Headertitle", dataProviderClass = TestDataProvider.class)
	public void headerRedirectionValidation(String headerindex,String title) {
		log.logger.info("Hearder is traverse");
		utility.clickElement(loc.getProperty("loc.headermenu.btn").replace("index", headerindex));
		log.logger.info(validationPage.pageRedirection(title));
	}
	
	@Test(priority = 2, dataProvider = "shirtdata", dataProviderClass = TestDataProvider.class)
	public void merchandiseFunctionality(String[] expecteddata) {
		Actions action = new Actions(driver);
		WebElement wb = utility.getElement(loc.getProperty("loc.merchandise.btn"));
		action.moveToElement(wb).build().perform();
		wait.waitElementToBeClickable(driver, loc.getProperty("loc.merchandise.mens.btn"));
		utility.clickElement(loc.getProperty("loc.merchandise.mens.btn"));
		heatClinicValidation.viewingMenValidation(log);
		heatClinicValidation.addTheShirtToCart(log);
		heatClinicValidation.productvalidation(expecteddata,log);
		heatClinicValidation.totalPriceValidation(expecteddata, log);
		
		
	}
	
	

}
