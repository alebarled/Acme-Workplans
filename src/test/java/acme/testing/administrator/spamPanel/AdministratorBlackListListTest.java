package acme.testing.administrator.spamPanel;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeWorkplansTest;

public class AdministratorBlackListListTest extends AcmeWorkplansTest{
	
	// Test cases -------------------------------------------------------------
	
	/* 
	 * This test sign in as administrator and navigate to the blacklist and tries to see that has access to the list.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/spamPanel/blacklist-list.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void listPositive(final int index, final String word) {		
		
		super.signIn("Administrator", "administrator");
		
		super.clickOnMenu("Administrator", "BlackList");

		super.checkColumnHasValue(index, 0, word);
	}
	
	/*
	 * As an anonymous user, tries to access the blacklist page and returns an error.
	 */
	@Test
	@Order(20)
	public void listNegative() {
		super.navigate("/administrator/spamconfig/list", "");
		super.checkPanicExists();
	}
}
