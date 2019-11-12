package com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.testscripts;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.base.TestBase;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.constant.FileConstant;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.dataProvider.TestDataProvider;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.helper.Utility;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.pages.TutorialsNinjaValidation;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.reports.LogReport;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.utils.ExcelReader;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.utils.NullCellValueException;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.utils.ReadPropertiesFile;

public class TutorialsNinjaFunctionalityValidation extends TestBase {

	Properties loc;
	LogReport log;
	TutorialsNinjaValidation validationPage;
	Properties testDataFromProperty;
	Utility utility;

	@BeforeClass
	public void intailization() {
		String url = baseClass.getProperty("tutorialsninjaurl");
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

	@Test(priority = 2)
	public void tutorialsNinjaProductValidation() {

		try {
			validationPage.productSearchFunctionality(log);
		} catch (NullCellValueException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}

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
