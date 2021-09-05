package acme.testing.manager.task;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import acme.testing.AcmeWorkplansTest;

public class ManagerTaskListTest extends AcmeWorkplansTest{
	
	// Test cases -------------------------------------------------------------
		/* 
		 * This test signs in as a manager, navigates into the tasks list and tries reading some of them.
		 * There shouldn't be any error.
		*/
		
		@ParameterizedTest
		@CsvFileSource(resources = "/manager/task/list-all.csv", encoding = "utf-8", numLinesToSkip = 1)
		@Order(10)
		public void listPositive(final int index, final String title,
			final String executionStart, final String executionEnd,
			final String workload, final String description, final String link) {		
			super.signIn("manager2", "manag3r");
			super.clickOnMenu("Manager", "Personal Tasks");
			
			super.checkColumnHasValue(index, 0, title);
			super.checkColumnHasValue(index, 1, executionStart);
			super.checkColumnHasValue(index, 2, executionEnd);
			super.checkColumnHasValue(index, 3, workload);
			super.checkColumnHasValue(index, 4, description);
			super.checkColumnHasValue(index, 5, link);
		}
		
		/* 
		 * This test signs in as a manager, navigates into the tasks list and tries reading all of them comparing them with tasks of other manager.
		 * There shouldn't be any error, the task must not be in the list due to its visibility.
		*/
		@ParameterizedTest
		@CsvFileSource(resources = "/manager/task/list-all.csv", encoding = "utf-8", numLinesToSkip = 1)
		@Order(20)
		public void listPositiveNoTaskPrivate(final int index, final String title,
			final String executionStart, final String executionEnd,
			final String workload, final String description, final String link) {
			
			super.signIn("manager1", "manag3r");
			
			super.clickOnMenu("Manager", "Personal Tasks");
						
				final List<WebElement> allInputElements = this.driver.findElements(By.tagName("tr")); 
				for(final WebElement inputElement : allInputElements) 
		        	{
					assert !inputElement.getText().contains(title) ||
						!inputElement.getText().contains(executionStart) ||
						!inputElement.getText().contains(executionEnd) ||
						!inputElement.getText().contains(workload) ||
						!inputElement.getText().contains(description) ||
						!inputElement.getText().contains(link);
		   }
		}
		
		/*
		 * This test tries to list manager tasks without login in as manager.
		 * A panic error should rise and this test checks it.
		 */
		@Test
		@Order(30)
		public void listNegative() {	
			
			super.navigate("/manager/task/list", "");
			
			super.checkPanicExists();

			
		}

}
