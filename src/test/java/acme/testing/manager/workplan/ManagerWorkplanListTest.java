package acme.testing.manager.workplan;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import acme.testing.AcmeWorkplansTest;

public class ManagerWorkplanListTest extends AcmeWorkplansTest{
	
	// Test cases -------------------------------------------------------------
		/* 
		 * This test signs in as an manager, navigates into the workplans list and tries reading some of them.
		 * There shouldn't be any error.
		*/
		
		@ParameterizedTest
		@CsvFileSource(resources = "/manager/workplan/list-all.csv", encoding = "utf-8", numLinesToSkip = 1)
		@Order(10)
		public void listPositive(final int index, final String title,
			final String executionStart, final String executionEnd,
			final String workload,final String isPublic) {		
			super.signIn("manager1", "manag3r");
			super.clickOnMenu("Manager", "Personal Workplans");
			
			super.checkColumnHasValue(index, 0, title);
			super.checkColumnHasValue(index, 1, executionStart);
			super.checkColumnHasValue(index, 2, executionEnd);
			super.checkColumnHasValue(index, 3, workload);
			super.checkColumnHasValue(index, 4, isPublic);
		}
		
		/* 
		 * This test signs in as an manager, navigates into the workplans list and tries reading all of them comparing them with a private workplan.
		 * There shouldn't be any error, the workplan must not be in the list due to its visibility.
		*/
		@ParameterizedTest
		@CsvFileSource(resources = "/manager/workplan/list-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
		@Order(20)
		public void listPositiveNoWorkplanPrivate(final int index, final String title,
			final String executionStart, final String executionEnd,
			final String workload,final String isPublic) {
			super.signIn("manager1", "manag3r");
			super.clickOnMenu("Manager", "Personal Workplans");
						
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
		 * This test tries to get manager list without login in.
		 * A panic error should rise and this test checks it.
		 */
		@Test
		@Order(30)
		public void listNegative() {	
			
			super.navigate("/manager/workplan/list", "");
			super.checkPanicExists();

			
		}

}
