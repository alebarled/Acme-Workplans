package acme.testing.manager.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmeWorkplansTest;

public class ManagerTaskUpdateTest extends AcmeWorkplansTest{
	
	// Test cases -------------------------------------------------------------
	
	/* 
	 * This test signs in as a manager, navigates into a personal task and updates its attributes.
	 * There shouldn't be any error, all data matches with restrictions.
	 */
	
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void updatePositive(final int recordIndex, final String title,
		final String executionStart, final String executionEnd,
		final String workload, final String description, final String link) {		
		
		super.signIn("manager1", "manag3r");
		
		super.clickOnMenu("Manager", "Personal Tasks");
		super.clickOnListingRecord(recordIndex);

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("executionStart", executionStart);
		super.fillInputBoxIn("executionEnd", executionEnd);
		super.fillInputBoxIn("workload", workload);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("link", link);		
		
		super.clickOnSubmitButton("Update");
		
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, executionStart);
		super.checkColumnHasValue(recordIndex, 2, executionEnd);
		super.checkColumnHasValue(recordIndex, 3, workload);
		super.checkColumnHasValue(recordIndex, 4, description);
		super.checkColumnHasValue(recordIndex, 5, link);
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("executionStart", executionStart);
		super.checkInputBoxHasValue("executionEnd", executionEnd);
		super.checkInputBoxHasValue("workload", workload);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("link", link);	
		
		super.signOut();
		
		
	}
	
	/* 
	 * This test signs in as a manager, navigates into a personal task and updates its attributes.
	 * Every row in csv file is a different case with different errors:
	 * 
	 * 	- case 0: All fields blank, errors would rise in every inputbox except link (optional).
	 * 
	 *  - case 1: Title, description and link considerated spam. Execution start, execution end
	 *  and workload are invalid values.
	 *  
	 *  - case 2: Error would rise in execution end because start date must be earlier than end date.
	 *  An error would rise in workload because its value is negative. An error would rise in link because
	 *  is not a well-formed URL.
	 *  
	 *  - case 3: Error would rise in workload because it must fix in dates period.
	 *  
	 *  - case 4: Error would rise in workload because value must contain 2 decimal figures or less.
	 *  
	 *  - case 5: Error would rise in workload because value must be between 0 and 59 (minutes).
	 *  
	 *  - case 6: Error would rise in title and description.
	 *   		  Error would rise in workload because value must be less than 99.59.
	 */
	
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void updateNegative(final int recordIndex, final String title,
		final String executionStart, final String executionEnd,
		final String workload, final String description, final String link, final String isPublic) {		
		
		super.signIn("manager1", "manag3r");
		
		super.clickOnMenu("Manager", "Personal Tasks");
		super.clickOnListingRecord(0);

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("executionStart", executionStart);
		super.fillInputBoxIn("executionEnd", executionEnd);
		super.fillInputBoxIn("workload", workload);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("link", link);		
		if(isPublic.equals("true")) {
			final By inputLocator = By.id("isPublic$proxy");
			super.locateOne(inputLocator).click();
		}
		
		super.clickOnSubmitButton("Update");
		
		String xpath;
		By locator;
		xpath = String.format("//div[@class='form-group'][textarea[@id='%s'] and div[@class='text-danger']]", "description");
		locator = By.xpath(xpath);
		
		//check different errors for each row in csv file
		
		switch(recordIndex) {
		
		case 0:
			super.checkErrorsExist("title");
			super.checkErrorsExist("executionStart");
			super.checkErrorsExist("executionEnd");
			super.checkErrorsExist("workload");
			assert super.exists(locator) : String.format("No errors found in input box '%s'", "description");
			break;
		
		case 1:
			super.checkErrorsExist("title");
			super.checkErrorsExist("executionStart");
			super.checkErrorsExist("executionEnd");
			super.checkErrorsExist("workload");
			super.checkErrorsExist("link");
			assert super.exists(locator) : String.format("No errors found in input box '%s'", "description");
			break;
		
		case 2:
			super.checkErrorsExist("executionEnd");
			super.checkErrorsExist("workload");
			super.checkErrorsExist("link");
			break;
		
		case 3:
			super.checkErrorsExist("workload");
			break;
		
		case 4:
			super.checkErrorsExist("workload");
			break;
			
		case 5:
			super.checkErrorsExist("workload");
			break;
		
		case 6:
			super.checkErrorsExist("title");
			super.checkErrorsExist("workload");
			assert super.exists(locator) : String.format("No errors found in input box '%s'", "description");
			break;
		}
		
	}

}
