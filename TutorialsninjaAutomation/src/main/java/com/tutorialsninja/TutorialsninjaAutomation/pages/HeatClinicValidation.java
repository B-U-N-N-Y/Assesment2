package com.tutorialsninja.TutorialsninjaAutomation.pages;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.tutorialsninja.TutorialsninjaAutomation.constant.FileConstant;
import com.tutorialsninja.TutorialsninjaAutomation.helper.Utility;
import com.tutorialsninja.TutorialsninjaAutomation.helper.Waits;
import com.tutorialsninja.TutorialsninjaAutomation.reports.LogReport;
import com.tutorialsninja.TutorialsninjaAutomation.utils.ReadPropertiesFile;

public class HeatClinicValidation {
	WebDriver driver;
	Utility utility;
	Properties loc;
	Properties testDatafromProperty;
	Waits wait;

	public HeatClinicValidation(WebDriver driver) {
		this.driver = driver;
		utility = new Utility(driver);
		loc = new ReadPropertiesFile().loadProperty(FileConstant.LOCATOR_FILE);
		testDatafromProperty = new ReadPropertiesFile().loadProperty(FileConstant.VALIDATION_PROPERTY_FILE);
		wait = new Waits();

	}

	/**
	 * This method validate all details of selected producr in cart
	 * 
	 * @param expecteddata
	 * @param log
	 */
	public void productvalidation(String[] expecteddata, LogReport log) {
		String productname = utility.getElement(loc.getProperty("loc.productname.txt")).getText();
		log.logger.info(assertion(productname, expecteddata[0]));
		String productsize = utility.getElement(loc.getProperty("loc.productsize.txt")).getText();
		log.logger.info(assertion(productsize, expecteddata[1]));
		String productcolour = utility.getElement(loc.getProperty("loc.productcolour.txt")).getText();
		log.logger.info(assertion(productcolour, expecteddata[2]));
		String productmessage = utility.getElement(loc.getProperty("loc.productmessage.txt")).getText();
		log.logger.info(assertion(productmessage, expecteddata[3]));
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
	 */
	public void totalPriceValidation(String[] expected, LogReport log) {
		log.logger.info("total price validation");
		String price = utility.getElement(loc.getProperty("loc.totalamount.txt")).getText();
		log.logger.info(assertion(price, expected[5]));
		log.logger.info("Update the quatity");
		utility.clearField(loc.getProperty("loc.productquantityupdate.input"));
		utility.clickAndSendText(loc.getProperty("loc.productquantityupdate.input"), expected[4]);
		utility.clickElement(loc.getProperty("loc.productquantityupdate.btn"));
		wait.hardWait(Long.parseLong(testDatafromProperty.getProperty("waitingtime")));
		String priceafterupdate = utility.getElement(loc.getProperty("loc.subtotal.txt")).getText();
		log.logger.info(assertion(priceafterupdate, expected[6]));

	}
}
