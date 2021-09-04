package acme.testing.anonymous.task;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import acme.testing.AcmeWorkplansTest;

public class AnonymousTaskListTest extends AcmeWorkplansTest{
	
	// Test cases -------------------------------------------------------------
	/* 
	 * This test signs in as an anonymous, navigates into the tasks list and tries reading some of them.
	 * There shouldn't be any error.
	*/
	
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/task/list-all.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void listPositive(final int index, final String title,
		final String executionStart, final String executionEnd,
		final String workload, final String description, final String link) {		
		
		super.clickOnMenu("Anonymous", "Tasks");
		
		super.checkColumnHasValue(index, 0, title);
		super.checkColumnHasValue(index, 1, executionStart);
		super.checkColumnHasValue(index, 2, executionEnd);
		super.checkColumnHasValue(index, 3, workload);
		super.checkColumnHasValue(index, 4, description);
		super.checkColumnHasValue(index, 5, link);
	}
	
	/* 
	 * This test signs in as an anonymous, navigates into the tasks list and tries reading all of them comparing them with a private task.
	 * There shouldn't be any error, the task must not be in the list due to its visibility.
	*/
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/task/list-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void listPositiveNoTaskPrivate(final int index, final String title,
		final String executionStart, final String executionEnd,
		final String workload, final String description, final String link) {
		
		super.clickOnMenu("Anonymous", "Tasks");
					
			final List<WebElement> allInputElements = this.driver.findElements(By.tagName("tr")); 
			for(final WebElement inputElement : allInputElements) 
	        	{
				assert !inputElement.getText().contains(title) &&
					!inputElement.getText().contains(executionStart) &&
					!inputElement.getText().contains(executionEnd) &&
					!inputElement.getText().contains(workload) &&
					!inputElement.getText().contains(description) &&
					!inputElement.getText().contains(link);
	   }
	}
	
	/*
	 * This test signs in as a manager and navigate into the tasks list.
	 * A panic error should rise and this test checks it.
	 */
	@Test
	@Order(30)
	public void listNegative() {	
		
		super.signIn("manager1", "manag3r");
		super.navigate("/anonymous/task/list", "");
		
		super.checkPanicExists();

		
	}
}
