package acme.testing.administrator.spamPanel;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeWorkplansTest;

public class AdministratorBlackListUpdateTest extends AcmeWorkplansTest{
	
	
	/*
	 * This test logs in as an administrator, navigates into blacklist and updates the
	 * first word that appears in the list with no errors.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/spamPanel/blacklist-positive-update.csv", encoding = "utf-8", numLinesToSkip=1)
	@Order(10)
	public void updatePositive(final int recordIndex, final String word) {
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "BlackList");
		
		super.clickOnListingRecord(0);
		super.fillInputBoxIn("word", word);
		super.clickOnSubmitButton("Update");

		super.clickOnListingRecord(0);
		super.checkInputBoxHasValue("word", word);
		
	}
	
	/*
	 * This test logs in as an administrator, navigates into blacklist and tries to update the
	 * first word that appears in the list.
	 * An error rise in word in 2 test cases in csv:
	 * case 0: empty word
	 * case 1: the word already exists
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/spamPanel/blacklist-negative-update.csv", encoding = "utf-8", numLinesToSkip=1)
	@Order(20)
	public void updateNegative(final int recordIndex, final String word) {
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "BlackList");
		
		super.clickOnListingRecord(0);
		super.fillInputBoxIn("word", word);
		super.clickOnSubmitButton("Update");

		super.checkErrorsExist("word");
		
	}
	
	

}
