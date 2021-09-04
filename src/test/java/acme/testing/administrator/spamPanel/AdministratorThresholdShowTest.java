package acme.testing.administrator.spamPanel;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeWorkplansTest;

public class AdministratorThresholdShowTest extends AcmeWorkplansTest{
	
	// Test cases -------------------------------------------------------------
	
	/* 
	 * This test sign in as administrator and navigate to the threshold, then it prove that the show is well formed.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/spamPanel/threshold-show.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void showPositive(final int index, final String threshold) {		
		
		super.signIn("Administrator", "administrator");
		
		super.clickOnMenu("Administrator", "Spam settings");
		
		super.clickOnListingRecord(index);
		
		super.checkInputBoxHasValue("threshold", threshold);
		
		super.checkButtonExists("Update");
		super.clickOnReturnButton("Return");
	}
	
	/*
	 * This test navigates into threshold show view, with not authorized credentials (only administrator has authority).
	 * An error must rise, telling the user that has no permission.
	 */
	@Test
	@Order(20)
	public void showNegative() {
		super.navigate("/administrator/threshold/show", "id=55");
		super.checkPanicExists();	
	}

}