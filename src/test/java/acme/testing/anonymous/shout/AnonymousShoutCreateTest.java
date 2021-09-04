/*
 * EmployerApplicationLIstTest.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.anonymous.shout;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmeWorkplansTest;

public class AnonymousShoutCreateTest extends AcmeWorkplansTest {

	
	/* 
	 * This test navigates into a shout list to count the number of shouts and then go to the create form, as an anonymous  and create a new shout.
	 * There shouldn't be any error, all data matches with restrictions.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/shout/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)	
	public void createPositive(final int recordIndex, final String author, final String text, final String info) {		
		super.clickOnMenu("Anonymous", "Shouts List");
		
		super.clickOnMenu("Anonymous", "Create a shout");		
		
		super.fillInputBoxIn("author", author);
		super.fillInputBoxIn("text", text);
		super.fillInputBoxIn("info", info);
		
		super.clickOnSubmitButton("Shout!");
		
		super.clickOnMenu("Anonymous", "Shouts List");
	
		super.checkColumnHasValue(recordIndex,1, author);
		super.checkColumnHasValue(recordIndex,2, text);
		super.checkColumnHasValue(recordIndex, 3, info);
		
	}
	
	/* 
	 * This test navigates into a shout create form, as an anonymous and try to create a new shout.
	 * There should be errors:
	 * 	-Case 0: The author and the text must not be blank.
	 * 	-Case 1: The author length must be more than 5.
	 * 	-Case 2: The author length must be less than 25.
	 * 	-Case 3: The text length must be less than 100.
	 * 	-Case 4: The url must be well formed.
	 * 	-Case 5: The shouts field must not contain spam words.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/shout/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)	
	public void createNegativeCases(final int recordIndex, final String author, final String text, final String info) {		
		super.clickOnMenu("Anonymous", "Create a shout");		
		
		super.fillInputBoxIn("author", author);
		super.fillInputBoxIn("text", text);
		super.fillInputBoxIn("info", info);
		
		super.clickOnSubmitButton("Shout!");	
		
		String xpath;
		By locator;
		xpath = String.format("//div[@class='form-group'][textarea[@id='%s'] and div[@class='text-danger']]", "text");
		locator = By.xpath(xpath);
		
		switch (recordIndex) {
		case 0:
			super.checkErrorsExist("author");
			assert super.exists(locator);
			break;

		case 1:
			super.checkErrorsExist("author");			
			break;
			
		case 2:
			super.checkErrorsExist("author");			
			break;
			
		case 3:
			assert super.exists(locator);
			break;
			
		case 4:
			super.checkErrorsExist("info");
			break;
			
		case 5:
			super.checkErrorsExist("author");
			assert super.exists(locator);
			super.checkErrorsExist("info");
			break;
			
		}				
	}

}
