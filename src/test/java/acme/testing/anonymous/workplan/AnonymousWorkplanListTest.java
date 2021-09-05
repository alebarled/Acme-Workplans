package acme.testing.anonymous.workplan;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import acme.testing.AcmeWorkplansTest;

public class AnonymousWorkplanListTest extends AcmeWorkplansTest{
	
	// Test cases -------------------------------------------------------------
	/* 
	 * This test signs in as an anonymous, navigates into the workplans list and tries reading some of them.
	 * There shouldn't be any error.
	*/
	
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/workplan/list-all.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void listPositive(final int index, final String title,
		final String executionStart, final String executionEnd,
		final String workload,final String isPublic) {		
		
		super.clickOnMenu("Anonymous", "Workplans");
		
		super.checkColumnHasValue(index, 0, title);
		super.checkColumnHasValue(index, 1, executionStart);
		super.checkColumnHasValue(index, 2, executionEnd);
		super.checkColumnHasValue(index, 3, workload);
		super.checkColumnHasValue(index, 4, isPublic);
	}
	
	/* 
	 * This test signs in as an anonymous, navigates into the workplans list and tries reading all of them comparing them with a private workplan.
	 * There shouldn't be any error, the workplan must not be in the list due to its visibility.
	*/
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/workplan/list-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void listPositiveNoWorkplanPrivate(final int index, final String title,
		final String executionStart, final String executionEnd,
		final String workload,final String isPublic) {
		
		super.clickOnMenu("Anonymous", "Workplans");
					
			final List<WebElement> allInputElements = this.driver.findElements(By.tagName("tr")); 
			for(final WebElement inputElement : allInputElements) 
	        	{
				assert !inputElement.getText().contains(title) ||
					!inputElement.getText().contains(executionStart) ||
					!inputElement.getText().contains(executionEnd) ||
					!inputElement.getText().contains(workload) ||
					!inputElement.getText().contains(isPublic);
	   }
	}
	
	/*
	 * This test signs in as a manager and navigate into the workplans list.
	 * A panic error should rise and this test checks it.
	 */
	@Test
	@Order(30)
	public void listNegative() {	
		
		super.signIn("manager1", "manag3r");
		super.navigate("/anonymous/workplan/list", "");
		
		super.checkPanicExists();

		
	}
}