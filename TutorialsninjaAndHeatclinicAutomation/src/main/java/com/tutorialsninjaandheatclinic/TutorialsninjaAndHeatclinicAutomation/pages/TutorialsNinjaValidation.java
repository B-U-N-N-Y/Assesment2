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

public class TutorialsNinjaValidation {
	WebDriver driver;
	Utility utility;
	Properties loc;
	Properties testDataFromProperty;
	Waits wait;
	ExcelReader excelReader;

	public TutorialsNinjaValidation(WebDriver driver) {
		this.driver = driver;
		utility = new Utility(driver);
		excelReader = new ExcelReader();
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
	 * @throws NullCellValueException 
	 */
	public void productSearchFunctionality(LogReport log) throws NullCellValueException {

		int rowNumber = excelReader.getRowCount(FileConstant.TESTDATA_FILE_TUTORIALSNINJA,
				"tutorialsNinjaProductDetails");

		for (int index = 1; index < rowNumber; index++) {
			String expectedProductName=excelReader.getCellData(FileConstant.TESTDATA_FILE_TUTORIALSNINJA, "tutorialsNinjaProductDetails", "Product_Name", "TS-"+index);
			String expectedProductPrice=excelReader.getCellData(FileConstant.TESTDATA_FILE_TUTORIALSNINJA, "tutorialsNinjaProductDetails", "Product_Price", "TS-"+index);
			String expectedProductAvailability=excelReader.getCellData(FileConstant.TESTDATA_FILE_TUTORIALSNINJA, "tutorialsNinjaProductDetails", "Product_avialability", "TS-"+index);
			String expectedProductExTax=excelReader.getCellData(FileConstant.TESTDATA_FILE_TUTORIALSNINJA, "tutorialsNinjaProductDetails", "Product_Ex_Tax", "TS-"+index);
			String expectedProductDescription=excelReader.getCellData(FileConstant.TESTDATA_FILE_TUTORIALSNINJA, "tutorialsNinjaProductDetails", "Product_Description", "TS-"+index);
			String expectedProductQuantity=excelReader.getCellData(FileConstant.TESTDATA_FILE_TUTORIALSNINJA, "tutorialsNinjaProductDetails", "Quantity", "TS-"+index);
			log.logger.info("Searching for " + expectedProductName + " and validating the product details");
			log.logger.info("Searching the " + expectedProductName);
			utility.clearField(loc.getProperty("loc.searchbox.input"));
			utility.clickAndSendText(loc.getProperty("loc.searchbox.input"), expectedProductName);
			utility.clickElement(loc.getProperty("loc.searchbox.search.btn"));
			utility.scrollIntoview(loc.getProperty("loc.searchproduct.caption.btn"));

			utility.clickElement(loc.getProperty("loc.searchproduct.caption.btn"));

			log.logger.info(expectedProductName + " searching successfully");
			log.logger.info("product name validation");
			wait.hardWait(Long.parseLong(testDataFromProperty.getProperty("waitingtime")));
			String productName = utility.getElement(loc.getProperty("loc.tutorialsninja.productname.txt")).getText();
			log.logger.info(assertion(productName, expectedProductName));
			log.logger.info("product price validation");
			String productPrice = utility.getElement(loc.getProperty("loc.productprice.txt")).getText();
			log.logger.info(assertion(productPrice, expectedProductPrice));

			log.logger.info("product ex-tax validation");
			String producttax = utility.getElement(loc.getProperty("loc.productextax.txt")).getText();
			log.logger.info(assertion(producttax, expectedProductExTax));

			if (productName.equalsIgnoreCase(testDataFromProperty.getProperty("product.iphone"))) {
				log.logger.info("product availabilty validation");
				String productAvailabilty = utility.getElement(loc.getProperty("loc.availabilityofiphone.txt"))
						.getText();
				log.logger.info(assertion(productAvailabilty, expectedProductAvailability));
				log.logger.info("product Description validation");
				String productdescription = utility.getElement(loc.getProperty("loc.productdescriptionofiphone.txt"))
						.getText();
				log.logger.info(assertion(productdescription, expectedProductDescription));
			} else if (productName.equalsIgnoreCase(testDataFromProperty.getProperty("product.macbook"))) {
				log.logger.info("product availabilty validation");
				String productAvailabilty = utility.getElement(loc.getProperty("loc.availabilityofmacbookpro.txt"))
						.getText();

				log.logger.info(assertion(productAvailabilty, expectedProductAvailability));
				log.logger.info("product Description validation");
				String productDescription = utility.getElement(loc.getProperty("loc.productdescriptionofmacbook.txt"))
						.getText();

				log.logger.info(assertion(productDescription, expectedProductDescription));
			}

			log.logger.info("select product quantity");
			wait.waitPresenceOfElementLocated(driver, loc.getProperty("loc.productinputquantity.input"));
			utility.clearField(loc.getProperty("loc.productinputquantity.input"));
			utility.clickAndSendText(loc.getProperty("loc.productinputquantity.input"), expectedProductQuantity);
			log.logger.info("Product add to the cart");
			utility.clickElement(loc.getProperty("loc.addtocart.btn"));
			utility.clickElement(loc.getProperty("loc.homepage.btn"));

		}
	}
}
