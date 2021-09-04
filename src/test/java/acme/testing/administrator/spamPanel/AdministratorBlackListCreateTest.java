package acme.testing.administrator.spamPanel;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeWorkplansTest;

public class AdministratorBlackListCreateTest extends AcmeWorkplansTest{

	/*
	 * A test that checks the "Create a spam word" functionality, 
	 * for this We log in as an administrator and create a spam word.
	 * There shouldn't be any error, all data matches with restrictions.
	 * Also, we log in as a manager and try to update a task with the spam
	 * word added right before and it raise an 
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/spamPanel/blacklist-positive-create.csv", encoding = "utf-8", numLinesToSkip=1)
	@Order(10)
	public void createPositive(final int recordIndex, final String word) {
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "Add new prohibited word");
		
		super.fillInputBoxIn("word", word);
		
		super.clickOnSubmitButton("Add");
		
		super.clickOnMenu("Administrator", "BlackList");
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("word", word);

		super.signOut();
		
		super.signIn("manager1", "manag3r");
		
		super.clickOnMenu("Manager", "Personal Tasks");
		super.clickOnListingRecord(0);
	
		super.fillInputBoxIn("title", word);
		
		super.clickOnSubmitButton("Update");
		
		super.checkErrorsExist("title");
		
		super.signOut();
		
	}
	
	/*
	 * This test checks that an administrator can not create a spam word
	 * already created.
	 * - Word: This word already exists.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/spamPanel/blacklist-negative-create.csv", encoding = "utf-8", numLinesToSkip=1)
	@Order(20)
	public void createDuplicatedNegative(final int recordIndex, final String word) {
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "Add new prohibited word");
		
		super.fillInputBoxIn("word", word);
		
		super.clickOnSubmitButton("Add");

		super.checkErrorsExist("word");
		
		super.signOut();
	}
	
	/*
	 * This test checks that an administrator can not create an empty spam word
	 * - Word: Not be empty
	 */
	@Test
	@Order(30)
	public void createBlankNegative() {
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "Add new prohibited word");
		
		super.fillInputBoxIn("word", "");
		
		super.clickOnSubmitButton("Add");

		super.checkErrorsExist("word");
		
		super.signOut();
	}
	
}
