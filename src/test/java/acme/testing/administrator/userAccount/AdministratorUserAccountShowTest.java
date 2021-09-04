package acme.testing.administrator.userAccount;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeWorkplansTest;

public class AdministratorUserAccountShowTest extends AcmeWorkplansTest {

	// Test cases -----------------------------------------------------------------
	
	/*
	 * In this test we log in as administrator and navigate into user accounts list,
	 * then navigate into every single user account details, verifying its fields.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/userAccount/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void updatePositive(final int recordIndex, final String username,final String name,final String surname,
		final String email,final String roles,final String status) {
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "User accounts");
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("username", username);
		super.checkInputBoxHasValue("identity.name", name);
		super.checkInputBoxHasValue("identity.surname", surname);
		super.checkInputBoxHasValue("identity.email", email);
		super.checkInputBoxHasValue("roleList", roles);
		super.checkInputBoxHasValue("status", status);
	
		super.signOut();
		
	}
	
	/*
	 * In this test we do not log in as administrator and try to navigate into an administrator user account details. 
	 * This way we check that a non administrator account can not navigate into other user account details. 
	 */
	@Test
	@Order(20)
	public void updateNegative() {
		super.navigate("/administrator/user-account/show", "id=3");
		super.checkPanicExists();
		
	}
}
