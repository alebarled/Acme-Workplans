package acme.testing.administrator.spamPanel;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeWorkplansTest;

public class AdministratorThresholdUpdateTest extends AcmeWorkplansTest {

	// Test cases -----------------------------------------------------------------
	
	/*
	 * In this test we log in as administrator and update the threshold, verifying that it has indeed been updated.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/spamPanel/threshold-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void updatePositive(final int recordIndex, final String threshold) {
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "Spam settings");
		super.clickOnListingRecord(recordIndex);
				
		super.fillInputBoxIn("threshold", threshold);
		super.clickOnSubmitButton("Update");
		
		super.clickOnListingRecord(recordIndex);
		super.checkInputBoxHasValue("threshold", threshold);
	
		super.signOut();
		
	}
	
	/*
	 * In this test we log in as administrator and update the threshold, but we enter several 
	 * incorrect data to check that the page handles the errors in the cases that:
	 * - Let's leave the field blank
	 * - We induce a negative value
	 * - Enter a value above 100
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/spamPanel/threshold-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void updateNegative(final int recordIndex, final String threshold) {
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "Spam settings");
		super.clickOnListingRecord(0);
		
		super.fillInputBoxIn("threshold", threshold);
		super.clickOnSubmitButton("Update");
		super.checkErrorsExist("threshold");
		
		super.signOut();
		
	}
}
