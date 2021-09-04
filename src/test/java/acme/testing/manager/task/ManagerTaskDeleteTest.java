package acme.testing.manager.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeWorkplansTest;

public class ManagerTaskDeleteTest extends AcmeWorkplansTest{

	//Test cases ------------------------------------------------------------
	
	/*
	 * In this test, the deletion of a task is performed (action that a manager can perform on its own tasks). 
	 * To do so, we register as a manager and enter a task of the manager and delete it, 
	 * and confirm that the task no longer exists in the list.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/list-mine.csv", encoding = "utf-8", numLinesToSkip=1)
	@Order(10)
	public void deletePositive(final String title, final String executionStart, final String executionEnd, final String workload, final String description, final String link, final String publica) {
		
		super.signIn("manager1", "manag3r");
		
		super.navigate("/manager/task/show", "id=16");
		
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("executionStart", executionStart);
		super.checkInputBoxHasValue("executionEnd", executionEnd);
		super.checkInputBoxHasValue("workload", workload);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("isPublic", publica);
		
		super.clickOnSubmitButton("Delete");
		
		super.navigate("/manager/task/show", "id=16");
		
		super.checkErrorsExist();
		super.navigateHome();
		super.signOut();
		
		//For security reasons we restore the database again.
		this.signIn("administrator", "administrator");
		super.clickOnMenu("Administrator", "Populate DB (initial)");
		super.clickOnMenu("Administrator", "Populate DB (samples)");
		super.checkAlertExists(true);		
		this.signOut();

	}
	/*
	 * This test checks that a manager cannot delete the tasks of other managers.
	 */
	
	@Test
	@Order(20)
	public void deleteNegative() {
		super.signIn("manager1", "manag3r");
		super.navigate("/manager/task/delete", "id=31");
		super.checkPanicExists();
	}
	
}
