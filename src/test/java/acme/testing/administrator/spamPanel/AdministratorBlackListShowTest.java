package acme.testing.administrator.spamPanel;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeWorkplansTest;

public class AdministratorBlackListShowTest extends AcmeWorkplansTest{
	
	// Test cases -------------------------------------------------------------
	
	/* 
	 * This test sign in as administrator and navigate to the blacklist, then it prove that the show is well formed.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/spamPanel/blacklist-show.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void showPositive(final int index, final int id, final String word) {		
		
		super.signIn("Administrator", "administrator");
		
		super.clickOnMenu("Administrator", "BlackList");
		
		super.clickOnListingRecord(index);
		super.checkInputBoxHasValue("word", word);
		super.checkButtonExists("Update");
		super.checkButtonExists("Delete");
		super.clickOnReturnButton("Return");
	}
	
	/*
	 * This test navigates into a spam word, with not authorized credentials (only administrator has authority).
	 * An error must rise, telling the user that has no permission.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/spamPanel/blacklist-show.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void showNegative(final int index, final int id, final String word) {		
		super.navigate("/administrator/spamconfig/show", String.format("id=%d",id));
		super.checkPanicExists();

		
	}

}
