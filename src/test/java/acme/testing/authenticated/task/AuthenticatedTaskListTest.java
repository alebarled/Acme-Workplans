package acme.testing.authenticated.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeWorkplansTest;

public class AuthenticatedTaskListTest extends AcmeWorkplansTest{
	
	// Test cases -------------------------------------------------------------
	/* 
	 * This test signs in as an authenticated, navigates into the tasks list and  tries reading some of them.
	 * There shouldn't be any error.
	*/
	
	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/task/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void listPositive(final int index, final String title,
		final String executionStart, final String executionEnd,
		final String workload, final String description, final String link) {		
		
		super.signIn("manager1", "manag3r");
		
		super.clickOnMenu("Authenticated", "Tasks");
		
		super.checkColumnHasValue(index, 0, title);
		super.checkColumnHasValue(index, 1, executionStart);
		super.checkColumnHasValue(index, 2, executionEnd);
		super.checkColumnHasValue(index, 3, workload);
		super.checkColumnHasValue(index, 4, description);
		super.checkColumnHasValue(index, 5, link);
	}
	
	/* 
	 * This test as an anonymous, tries to navigate into the tasks list.
	 * There should be an error, anonymous are not authorised to go there.
	*/
	@Test
	@Order(20)
	public void listNegative() {
		
		super.navigate("/authenticated/task/list", "" );
		
		super.checkPanicExists();
	
		
	}
}
