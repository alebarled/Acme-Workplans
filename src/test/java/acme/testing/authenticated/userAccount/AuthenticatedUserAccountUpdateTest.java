package acme.testing.authenticated.userAccount;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeWorkplansTest;

public class AuthenticatedUserAccountUpdateTest extends AcmeWorkplansTest{
	
	// Test cases -------------------------------------------------------------
	
	/* 
	 * This test signs in as a manager, navigates into its general data and updates its attributes.
	 * There shouldn't be any error, all data matches with restrictions.
	 */
	
	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/userAccount/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void updatePositive(final int recordIndex, final String password,
		final String confirmation, final String name,
		final String surname, final String email) {		
		
		super.signIn("manager1", "manag3r");
		
		super.clickOnMenu("Account", "General data");

		super.fillInputBoxIn("password", password);
		super.fillInputBoxIn("confirmation", confirmation);
		super.fillInputBoxIn("identity.name", name);
		super.fillInputBoxIn("identity.surname", surname);
		super.fillInputBoxIn("identity.email", email);		
		
		super.clickOnSubmitButton("Update");
		
		super.clickOnMenu("Account", "General data");
		
		super.checkInputBoxHasValue("identity.name", name);
		super.checkInputBoxHasValue("identity.surname", surname);
		super.checkInputBoxHasValue("identity.email", email);	
		
		super.signOut();
		
		
	}
	
	/* 
	 * This test signs in as a manager, navigates into a personal task and updates its attributes.
	 * Every row in csv file is a different case with different errors:
	 * 
	 * 	- case 0: All fields blank, errors would rise in every inputbox except confirmation.
	 * 
	 *  - case 1: Confirmation is not equal to password and email has invalid values.
	 */
	
	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/userAccount/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void updateNegative(final int recordIndex, final String password,
		final String confirmation, final String name,
		final String surname, final String email) {		
		
		super.signIn("manager1", "manag3r");
		
		super.clickOnMenu("Account", "General data");
		
		super.fillInputBoxIn("password", password);
		super.fillInputBoxIn("confirmation", confirmation);
		super.fillInputBoxIn("identity.name", name);
		super.fillInputBoxIn("identity.surname", surname);
		super.fillInputBoxIn("identity.email", email);
		
		super.clickOnSubmitButton("Update");
		
		switch(recordIndex) {
		
			case 0:
				super.checkErrorsExist("password");
				super.checkErrorsExist("identity.name");
				super.checkErrorsExist("identity.surname");
				super.checkErrorsExist("identity.email");
				break;
			
			case 1:
				super.checkErrorsExist("confirmation");
				super.checkErrorsExist("identity.email");
				break;
		}
		
		
	}

}
