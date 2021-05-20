package acme.testing.administrator.spamPanel;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AdministratorThresholdList extends AcmePlannerTest{
	
	// Test cases -------------------------------------------------------------
	/* 
	 * As an administrator, test that can see the spam panel configuration and the asociated threshold
	 * There shouldn't be any error.
	*/
	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/spamPanel/threshold.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void listPositive(final int index, final String threshold) {		
		
		super.signIn("administrator", "administrator");
		super.clickOnMenu("Administrator", "Spam settings");
		
		super.checkColumnHasValue(index, 0, threshold);
	}
	
	@Test
	@Order(20)
	public void listNegative() {
		super.signIn("manager1", "manag3r");
		
		super.navigate("/administrator/threshold/list", "");
		super.checkErrorsExist();
		
	}
	
}
