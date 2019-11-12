package com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.pages;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.constant.FileConstant;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.helper.Utility;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.helper.Waits;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.reports.LogReport;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.utils.ExcelReader;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.utils.NullCellValueException;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.utils.ReadPropertiesFile;

public class HeatClinicValidation {
	WebDriver driver;
	Utility utility;
	Properties loc;
	Properties testDatafromProperty;
	Waits wait;
	ExcelReader excelReader;

	public HeatClinicValidation(WebDriver driver) {
		this.driver = driver;
		utility = new Utility(driver);
		excelReader=new ExcelReader();
		loc = new ReadPropertiesFile().loadProperty(FileConstant.LOCATOR_FILE);
		testDatafromProperty = new ReadPropertiesFile().loadProperty(FileConstant.VALIDATION_PROPERTY_FILE);
		wait = new Waits();

	}

	/**
	 * This method validate all details of selected producr in cart
	 * 
	 * @param expecteddata
	 * @param log
	 * @throws NullCellValueException 
	 */
	public void productvalidation( LogReport log) throws NullCellValueException {
		String productName = utility.getElement(loc.getProperty("loc.productname.txt")).getText();
		String expectedProductName=excelReader.getCellData(FileConstant.TESTDATA_FILE_HEATCLINIC, "heatClinicProductDetails",
				"productname", "TS-01");
		log.logger.info(assertion(productName, expectedProductName));
		String productSize = utility.getElement(loc.getProperty("loc.productsize.txt")).getText();
		String expectedProductSize=excelReader.getCellData(FileConstant.TESTDATA_FILE_HEATCLINIC, "heatClinicProductDetails",
				"productsize", "TS-01");
		log.logger.info(assertion(productSize, expectedProductSize));
		String productColour = utility.getElement(loc.getProperty("loc.productcolour.txt")).getText();
		String expectedProductColour=excelReader.getCellData(FileConstant.TESTDATA_FILE_HEATCLINIC, "heatClinicProductDetails",
				"productcolour", "TS-01");
		log.logger.info(assertion(productColour, expectedProductColour));
		String productMessage = utility.getElement(loc.getProperty("loc.productmessage.txt")).getText();
		String expectedProductMessage=excelReader.getCellData(FileConstant.TESTDATA_FILE_HEATCLINIC, "heatClinicProductDetails",
				"message", "TS-01");
		log.logger.info(assertion(productMessage, expectedProductMessage));
	}

	/**
	 * This method did assertion and return the message
	 * 
	 * @param actual
	 * @param expected
	 * @return
	 */
	public String assertion(String actual, String expected) {
		try {
			Assert.assertEquals(actual, expected);
			return "Validation pass:" + actual + " and " + expected + " match";
		} catch (Exception e) {
			e.printStackTrace();
			return "Validation fail:" + actual + " and " + expected + " not match";

		}
	}

	/**
	 * This method add the product in the cart with all given specification
	 * 
	 * @param log
	 */
	public void addTheShirtToCart(LogReport log) {
		log.logger.info("Shirt is selected");
		utility.clickElement(loc.getProperty("loc.habaneroshirtbuynow.btn"));
		log.logger.info("Shirt colour is selected");
		utility.clickElement(loc.getProperty("loc.shirtcolour.btn"));
		log.logger.info("Shirt size is selected");
		utility.clickElement(loc.getProperty("loc.shirtsize.btn"));
		log.logger.info("Shirt personalized message is given");
		utility.clickAndSendText(loc.getProperty("loc.shirtpersonalizedmsg.input"),
				testDatafromProperty.getProperty("personalizedname"));
		log.logger.info("buy now is clicked");
		utility.clickElement(loc.getProperty("loc.buynow.btn"));
		wait.waitElementToBeClickable(driver, loc.getProperty("loc.viewcart.btn"));
		utility.clickElement(loc.getProperty("loc.viewcart.btn"));
		log.logger.info("view cart is clicked");
		wait.waitPresenceOfElementLocated(driver, loc.getProperty("loc.productname.txt"));

	}

	public void viewingMenValidation(LogReport log) {
		String viewingmentext = utility.getElement(loc.getProperty("loc.viewingmens.txt")).getText();
		String[] actualviewingmentext = viewingmentext.split(" ");
		log.logger.info(assertion(actualviewingmentext[0] + actualviewingmentext[1],
				testDatafromProperty.getProperty("viewingmen")));
		log.logger.info("Viewing Mens is succefully validate");
	}

	/**
	 * This method verify the total amount and update the product quantity and again
	 * validte the total amount
	 * 
	 * @param expected
	 * @param log
	 * @throws NullCellValueException 
	 */
	public void totalPriceValidation( LogReport log) throws NullCellValueException {
		log.logger.info("total price validation");
		String price = utility.getElement(loc.getProperty("loc.totalamount.txt")).getText();
		String expectedTotalPrice=excelReader.getCellData(FileConstant.TESTDATA_FILE_HEATCLINIC, "heatClinicProductDetails",
				"Totalprice", "TS-01");
		log.logger.info(assertion(price, expectedTotalPrice));
		log.logger.info("Update the quatity");
		utility.clearField(loc.getProperty("loc.productquantityupdate.input"));
		String quantity=excelReader.getCellData(FileConstant.TESTDATA_FILE_HEATCLINIC, "heatClinicProductDetails",
				"quantity", "TS-01");
		String expectedTotlPriceAfterUpdation=excelReader.getCellData(FileConstant.TESTDATA_FILE_HEATCLINIC, "heatClinicProductDetails",
				"TotalPriceAfterUpdate", "TS-01");
		utility.clickAndSendText(loc.getProperty("loc.productquantityupdate.input"), quantity);
		utility.clickElement(loc.getProperty("loc.productquantityupdate.btn"));
		wait.hardWait(Long.parseLong(testDatafromProperty.getProperty("waitingtime")));
		String priceafterupdate = utility.getElement(loc.getProperty("loc.subtotal.txt")).getText();
		log.logger.info(assertion(priceafterupdate, expectedTotlPriceAfterUpdation));

	}
}
