package com.tutorialsninja.TutorialsninjaAutomation.pages;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.tutorialsninja.TutorialsninjaAutomation.constant.FileConstant;
import com.tutorialsninja.TutorialsninjaAutomation.helper.Utility;
import com.tutorialsninja.TutorialsninjaAutomation.helper.Waits;
import com.tutorialsninja.TutorialsninjaAutomation.reports.LogReport;
import com.tutorialsninja.TutorialsninjaAutomation.utils.ReadPropertiesFile;

public class TutorialsNinjaValidation {
	WebDriver driver;
	Utility utility;
	Properties loc;
	Properties testDataFromProperty;
	Waits wait;

	public TutorialsNinjaValidation(WebDriver driver) {
		this.driver = driver;
		utility = new Utility(driver);
		loc = new ReadPropertiesFile().loadProperty(FileConstant.LOCATOR_FILE);
		testDataFromProperty = new ReadPropertiesFile().loadProperty(FileConstant.VALIDATION_PROPERTY_FILE);
		wait = new Waits();

	}

	/**
	 * This method validate the redirection of page
	 * 
	 * @param expectedpageTile
	 * @return
	 */
	public String pageRedirection(String expectedPageTile) {
		String actualTitle = driver.getTitle();
		String message = assertion(actualTitle, expectedPageTile);
		return "Page Redirection " + message;
	}

	/**
	 * This method did assertion of given input
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
	 * This method search the product and validating its all specification
	 * 
	 * @param testData
	 * @param log
	 */
	public void productSearchFunctionality(String[] testData, LogReport log) {
		log.logger.info("Searching the " + testData[0]);
		utility.clearField(loc.getProperty("loc.searchbox.input"));
		utility.clickAndSendText(loc.getProperty("loc.searchbox.input"), testData[0]);
		utility.clickElement(loc.getProperty("loc.searchbox.search.btn"));
		utility.scrollIntoview(loc.getProperty("loc.searchproduct.caption.btn"));

		utility.clickElement(loc.getProperty("loc.searchproduct.caption.btn"));

		log.logger.info(testData[0] + " searching successfully");
		log.logger.info("product name validation");
		wait.hardWait(Long.parseLong(testDataFromProperty.getProperty("waitingtime")));
		String productName = utility.getElement(loc.getProperty("loc.tutorialsninja.productname.txt")).getText();
		log.logger.info(assertion(productName, testData[0]));
		log.logger.info("product price validation");
		String productPrice = utility.getElement(loc.getProperty("loc.productprice.txt")).getText();
		log.logger.info(assertion(productPrice, testData[1]));

		log.logger.info("product ex-tax validation");
		String producttax = utility.getElement(loc.getProperty("loc.productextax.txt")).getText();
		log.logger.info(assertion(producttax, testData[3]));

		if (productName.equalsIgnoreCase(testDataFromProperty.getProperty("product.iphone"))) {
			log.logger.info("product availabilty validation");
			String productAvailabilty = utility.getElement(loc.getProperty("loc.availabilityofiphone.txt")).getText();
			log.logger.info(assertion(productAvailabilty, testData[2]));
			log.logger.info("product Description validation");
			String productdescription = utility.getElement(loc.getProperty("loc.productdescriptionofiphone.txt"))
					.getText();
			log.logger.info(assertion(productdescription, testData[4]));
		} else if (productName.equalsIgnoreCase(testDataFromProperty.getProperty("product.macbook"))) {
			log.logger.info("product availabilty validation");
			String productAvailabilty = utility.getElement(loc.getProperty("loc.availabilityofmacbookpro.txt"))
					.getText();

			log.logger.info(assertion(productAvailabilty, testData[2]));
			log.logger.info("product Description validation");
			String productDescription = utility.getElement(loc.getProperty("loc.productdescriptionofmacbook.txt"))
					.getText();

			log.logger.info(assertion(productDescription, testData[4]));
		}

		log.logger.info("select product quantity");
		wait.waitPresenceOfElementLocated(driver, loc.getProperty("loc.productinputquantity.input"));
		utility.clearField(loc.getProperty("loc.productinputquantity.input"));
		utility.clickAndSendText(loc.getProperty("loc.productinputquantity.input"), testData[5]);
		log.logger.info("Product add to the cart");
		utility.clickElement(loc.getProperty("loc.addtocart.btn"));
		utility.clickElement(loc.getProperty("loc.homepage.btn"));

	}
}
