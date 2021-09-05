package acme.testing.manager.workplan;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import acme.testing.AcmeWorkplansTest;

public class ManagerWorkplanUpdateTest extends AcmeWorkplansTest{
	
	// Test cases -------------------------------------------------------------
	
		/* 
		 * This test signs in as a manager, navigates into update a workplan and update a workplan.
		 * There shouldn't be any error, all data matches with restrictions.
		 */
		@ParameterizedTest
		@CsvFileSource(resources = "/manager/workplan/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
		@Order(10)
		public void updatePositive(final int index, final String title,final String description,
			final String executionStart, final String executionEnd,
			final String workload,  final String isPublic, final String tasks) {		
			
			super.signIn("manager1", "manag3r");
			
			super.clickOnMenu("Manager", "Personal Workplans");
			super.clickOnListingRecord(index);
			
			/////////////// clear tasks list
			final List<WebElement> allTasks = this.driver.findElements(By.tagName("tr")); 
			for(final WebElement inputElement : allTasks) 
	        	{
					if(!inputElement.getText().startsWith("Tasks")) {  //avoid first line
						final WebElement input = inputElement.findElement(By.id("taskselect"));
						if(input.isSelected())
							input.click();
					}
					
	        	}
			//////////////////////////////////

			super.fillInputBoxIn("title", title);
			super.fillInputBoxIn("executionStart", executionStart);
			super.fillInputBoxIn("executionEnd", executionEnd);
			super.fillInputBoxIn("description", description);
			
			if(isPublic.equals("true")) {
				final WebElement inputLocator = this.driver.findElement(By.id("isPublic$proxy"));
				if(!inputLocator.isSelected())
					inputLocator.click();
			}
			else {
				final WebElement inputLocator = this.driver.findElement(By.id("isPublic$proxy"));
				if(inputLocator.isSelected())
					inputLocator.click();
			}
			
			final Set<String> workplanSet = Arrays.asList(tasks.split(";"))
				.stream().map(Object::toString).collect(Collectors.toSet());
			
			final List<WebElement> allInputElements = this.driver.findElements(By.tagName("tr")); 
			for(final WebElement inputElement : allInputElements) 
	        	{
					if(workplanSet.contains(inputElement.getText())) {
						final WebElement input = inputElement.findElement(By.id("taskselect"));
						input.click();
					}
	        	}
			super.clickOnSubmitButton("Update");
			super.clickOnMenu("Manager", "Personal Workplans");
			super.clickOnListingRecord(index);
			
			/// Check new values
			
			super.checkInputBoxHasValue("title", title);
			super.checkInputBoxHasValue("executionStart", executionStart);
			super.checkInputBoxHasValue("executionEnd", executionEnd);
			super.checkInputBoxHasValue("workload", workload);
			super.checkInputBoxHasValue("description", description);
			super.checkInputBoxHasValue("isPublic", isPublic);

			final List<WebElement> allInputElements2 = this.driver.findElements(By.tagName("tr")); 
			
			for(final WebElement inputElement : allInputElements2) 
	    	{
				if(workplanSet.contains(inputElement.getText())) {
					final WebElement input = inputElement.findElement(By.id("taskselect"));
					assert input.isSelected();
				}
	    	}
		}
		
		
		/* 
		 * This test signs in as a manager and tries to update an existing workplan.
		 * Every row in csv file is a different case with different errors:
		 * 
		 * 	- case 0: All fields blank, errors would rise in every inputbox.
		 * 
		 *  - case 1: Title and description considerated spam. Execution start and execution end
		 *  are invalid values.
		 *  
		 *  - case 2: Error would rise in execution end because start date must be earlier than end date.
		 *  
		 *  - case 3: Error would rise execution end because the period doesn't accomodate al tasks selected.
		 *  
		 *  - case 4: Error would rise because a public workplan cannot contain private tasks.
		 *  
		 */
		@ParameterizedTest
		@CsvFileSource(resources = "/manager/workplan/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
		@Order(20)
		public void updateNegative(final int index, final String title,final String description,
			final String executionStart, final String executionEnd,
			final String workload,  final String isPublic, final String tasks) {
			
			super.signIn("manager1", "manag3r");
			
			super.clickOnMenu("Manager", "Personal Workplans");
			super.clickOnListingRecord(0);
			
			/////////////// clear tasks list
			final List<WebElement> allTasks = this.driver.findElements(By.tagName("tr")); 
			for(final WebElement inputElement : allTasks) 
	        	{
					if(!inputElement.getText().startsWith("Tasks")) {  //avoid first line
						final WebElement input = inputElement.findElement(By.id("taskselect"));
						if(input.isSelected())
							input.click();
					}
					
	        	}
			//////////////////////////////////

			super.fillInputBoxIn("title", title);
			super.fillInputBoxIn("executionStart", executionStart);
			super.fillInputBoxIn("executionEnd", executionEnd);
			super.fillInputBoxIn("description", description);
			
			if(isPublic.equals("true")) {
				final WebElement inputLocator = this.driver.findElement(By.id("isPublic$proxy"));
				if(!inputLocator.isSelected())
					inputLocator.click();
			}
			else {
				final WebElement inputLocator = this.driver.findElement(By.id("isPublic$proxy"));
				if(inputLocator.isSelected())
					inputLocator.click();
			}
			
			if(tasks!=null) {
				final Set<String> workplanSet = Arrays.asList(tasks.split(";"))
					.stream().map(Object::toString).collect(Collectors.toSet());
				
				final List<WebElement> allInputElements = this.driver.findElements(By.tagName("tr")); 
				for(final WebElement inputElement : allInputElements) 
		        	{
						if(workplanSet.contains(inputElement.getText())) {
							final WebElement input = inputElement.findElement(By.id("taskselect"));
							input.click();
						}
		        	}
			}
			super.clickOnSubmitButton("Update");
			
			
			//check different errors for each row in csv file
			
			switch(index) {
					
			case 0:
				super.checkErrorsExist("title");
				super.checkErrorsExist("description");
				super.checkErrorsExist("executionStart");
				super.checkErrorsExist("executionEnd");
				break;
					
			case 1:
				super.checkErrorsExist("title");
				super.checkErrorsExist("description");
				super.checkErrorsExist("executionStart");
				super.checkErrorsExist("executionEnd");
				break;
					
			case 2:
				super.checkErrorsExist("executionEnd");
				break;
					
			case 3:
				super.checkErrorsExist("executionEnd");
				break;
					
			case 4:
				super.checkErrorsExist("isPublic");
				break;
			
			}
		}

}
