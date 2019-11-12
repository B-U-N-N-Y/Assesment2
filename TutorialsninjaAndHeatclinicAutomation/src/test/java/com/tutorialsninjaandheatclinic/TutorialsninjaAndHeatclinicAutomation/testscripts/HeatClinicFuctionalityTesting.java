package com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.testscripts;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.base.TestBase;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.constant.FileConstant;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.dataProvider.TestDataProvider;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.helper.Utility;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.helper.Waits;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.pages.HeatClinicValidation;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.pages.TutorialsNinjaValidation;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.reports.LogReport;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.utils.ExcelReader;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.utils.NullCellValueException;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.utils.ReadPropertiesFile;

public class HeatClinicFuctionalityTesting extends TestBase {

	Properties loc;
	LogReport log;
	TutorialsNinjaValidation validationPage;
	Properties testDataFromProperty;
	Utility utility;
	Waits wait;
	HeatClinicValidation heatClinicValidation;
	ExcelReader excelReader;

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
		excelReader = new ExcelReader();
		utility = new Utility(driver);
		validationPage = new TutorialsNinjaValidation(driver);
		wait = new Waits();
		heatClinicValidation = new HeatClinicValidation(driver);

	}

	@Test(priority = 1)
	public void headerRedirectionValidation() throws NullCellValueException {
		String headerIndex;
		String pageTitle;
		int rowNumber = excelReader.getRowCount(FileConstant.TESTDATA_FILE_HEATCLINIC, "heatClinicMenuTitleBar");
		
		log.logger.info("Hearder is traverse");

		for (int index = 1; index < rowNumber; index++) {
			headerIndex = excelReader.getCellData(FileConstant.TESTDATA_FILE_HEATCLINIC, "heatClinicMenuTitleBar",
					"HeaderIndex", "TS-" + index);
			pageTitle = excelReader.getCellData(FileConstant.TESTDATA_FILE_HEATCLINIC, "heatClinicMenuTitleBar",
					"PageTitle", "TS-" + index);
			utility.clickElement(loc.getProperty("loc.headermenu.btn").replace("index", headerIndex));
			log.logger.info(validationPage.pageRedirection(pageTitle));
		}

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

		try {
			heatClinicValidation.productvalidation(log);
		} catch (NullCellValueException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}

		try {
			heatClinicValidation.totalPriceValidation(log);
		} catch (NullCellValueException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}

}
