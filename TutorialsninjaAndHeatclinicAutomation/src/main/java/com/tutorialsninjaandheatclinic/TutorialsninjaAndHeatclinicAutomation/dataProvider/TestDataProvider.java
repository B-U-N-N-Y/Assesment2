package com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.dataProvider;

import org.testng.annotations.DataProvider;

import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.constant.FileConstant;
import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.utils.ProvideData;

/**
 * In this class, data is given by the dataprovider
 * 
 * @author arjun.santra
 */
public class TestDataProvider {
	/**
	 * In this method, getting data object array from excel sheet and returning to
	 * the calling method
	 */

	@DataProvider(name = "productData")
	public Object[][] getProductData() {
		ProvideData provideData = new ProvideData(FileConstant.TESTDATA_FILE_TUTORIALSNINJA, 0);
		Object[][] getSheetData = provideData.provideData();
		return getSheetData;
	}

	/**
	 * This method provide page title of all header
	 * 
	 * @return
	 */
	@DataProvider(name = "Headertitle")
	public Object[][] getheadertitleData() {
		ProvideData provideData = new ProvideData(FileConstant.TESTDATA_FILE_HEATCLINIC, 0);
		Object[][] getSheetData = provideData.provideData();
		return getSheetData;
	}

	/**
	 * This method provide shirt data
	 * 
	 * @return
	 */
	@DataProvider(name = "shirtdata")
	public Object[][] getshirtData() {
		ProvideData provideData = new ProvideData(FileConstant.TESTDATA_FILE_HEATCLINIC, 1);
		Object[][] getSheetData = provideData.provideData();
		return getSheetData;
	}

//	public static void main(String[] args) {
//
//		Object[][] data = new TestDataProvider().getCityData();
//		for (Object[] objects : data) {
//			String userName = (String) objects[0];
//			String password = (String) objects[1];
//			System.out.println(" " + userName + "   " + password);
//		}
//	}
}