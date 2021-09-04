package acme.testing.anonymous.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeWorkplansTest;

public class AnonymousTaskShowTest extends AcmeWorkplansTest{
	
	// Test cases -------------------------------------------------------------
	
	/* 
	 * This test navigates into a public task, with not authorized credentials (public).
	 * It also verificates that all the parameters of the task are correct.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/task/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void showPositive(final int index, final String title,
		final String executionStart, final String executionEnd,
		final String workload, final String description, final String link) {		
		
		super.navigate("/anonymous/task/show", "id=31");
		
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("executionStart", executionStart);
		super.checkInputBoxHasValue("executionEnd", executionEnd);
		super.checkInputBoxHasValue("workload", workload);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("link", link);

		
	}
	
	/*
	 * This test navigates into a public task already finished.
	 * An error must rise, telling the user that has no permission to show the task 23 info.
	 */
	@Test
	@Order(20)
	public void showNegative() {		
		super.navigate("/anonymous/task/show", "id=23");
		super.checkPanicExists();

		
	}

}
