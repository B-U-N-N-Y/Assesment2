package com.tutorialsninja.TutorialsninjaAutomation.testscripts;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.tutorialsninja.TutorialsninjaAutomation.base.TestBase;
import com.tutorialsninja.TutorialsninjaAutomation.constant.FileConstant;
import com.tutorialsninja.TutorialsninjaAutomation.dataProvider.TestDataProvider;
import com.tutorialsninja.TutorialsninjaAutomation.helper.Utility;
import com.tutorialsninja.TutorialsninjaAutomation.pages.TutorialsNinjaValidation;
import com.tutorialsninja.TutorialsninjaAutomation.reports.LogReport;
import com.tutorialsninja.TutorialsninjaAutomation.utils.ReadPropertiesFile;

public class TutorialsNinjaFunctionalityValidation extends TestBase {

	Properties loc;
	LogReport log;
	TutorialsNinjaValidation validationPage;
	Properties testDataFromProperty;
	Utility utility;

	@BeforeClass
	public void intailization() {
		String url=baseClass.getProperty("tutorialsninjaurl");
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(FileConstant.TIMEOUT_INSECONDS, TimeUnit.SECONDS);
		loc = new ReadPropertiesFile().loadProperty(FileConstant.LOCATOR_FILE);
		testDataFromProperty = new ReadPropertiesFile().loadProperty(FileConstant.VALIDATION_PROPERTY_FILE);
		log = new LogReport();
		utility = new Utility(driver);
		validationPage = new TutorialsNinjaValidation(driver);

	}

	@Test(priority = 1)
	public void tutorialsNinjaHomePageValidation() {

		log.logger.info("Homepage is invoking");
		log.logger.info(validationPage.pageRedirection(testDataFromProperty.getProperty("homepagetitle")));
		log.logger.info("HomePage validate successfull");

	}

	@Test(priority = 2, dataProvider = "productData", dataProviderClass = TestDataProvider.class)
	public void tutorialsNinjaProductValidation(String[] testdata) {
		log.logger.info("Searching for " + testdata[0] + " and validating the product details");
		validationPage.productSearchFunctionality(testdata, log);

	}

	@Test(priority = 3)
	public void cartValidation() {
		utility.clickElement(loc.getProperty("loc.shoppingcart.btn"));
		log.logger.info("Grand total before updating the quantity");
		utility.scrollDownPage(testDataFromProperty.getProperty("scrolldown"));
		String grandTotal = utility.getElement(loc.getProperty("loc.grandtotla.txt")).getText();
		log.logger.info(validationPage.assertion(grandTotal, testDataFromProperty.getProperty("grandtotal")));
		utility.scrollIntoview(loc.getProperty("loc.cartpage.quantity.input"));
		utility.clearField(loc.getProperty("loc.cartpage.quantity.input"));
		utility.clickAndSendText(loc.getProperty("loc.cartpage.quantity.input"),
				testDataFromProperty.getProperty("updatequatity"));
		utility.clickElement(loc.getProperty("loc.updatequantity.btn"));
		String afterUpdationGrandTotal = utility.getElement(loc.getProperty("loc.grandtotla.txt")).getText();
		log.logger.info("Grand total after quantity updation");
		log.logger.info(validationPage.assertion(afterUpdationGrandTotal,
				testDataFromProperty.getProperty("grandtotalafterupdate")));

	}

}
