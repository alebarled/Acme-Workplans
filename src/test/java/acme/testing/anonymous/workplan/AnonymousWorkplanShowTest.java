package acme.testing.anonymous.workplan;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import acme.testing.AcmeWorkplansTest;

public class AnonymousWorkplanShowTest extends AcmeWorkplansTest{
	
	// Test cases -------------------------------------------------------------
	
		/* 
		 * This test navigates into a public workplan, with not authorized credentials (public).
		 * It also verificates that all the parameters of the workplan are correct.
		 */
		@ParameterizedTest
		@CsvFileSource(resources = "/anonymous/workplan/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
		@Order(10)
		public void showPositive(final int index, final String title,
			final String executionStart, final String executionEnd,
			final String workload, final String description, final String isPublic, final String tasks) {		
			
			super.navigate("/anonymous/workplan/show", "id="+index);
			
			super.checkInputBoxHasValue("title", title);
			super.checkInputBoxHasValue("executionStart", executionStart);
			super.checkInputBoxHasValue("executionEnd", executionEnd);
			super.checkInputBoxHasValue("workload", workload);
			super.checkInputBoxHasValue("description", description);
			super.checkInputBoxHasValue("isPublic", isPublic);
	
			final Set<String> taskSet = Arrays.asList(tasks.split(";"))
				.stream().map(Object::toString).collect(Collectors.toSet());
			
			final List<WebElement> allInputElements = this.driver.findElements(By.tagName("tr")); 
			
			final Set<String> taskSet2 = allInputElements.subList(1, allInputElements.size())
				.stream().map(WebElement::getText).collect(Collectors.toSet());
		
			assert taskSet.equals(taskSet2);
			
			

			
		}
		
		/*
		 * This test navigates into a public workplan already finished.
		 * An error must rise, telling the user that has no permission to show the workplan 23 info.
		 */
		@Test
		@Order(20)
		public void showNegative() {		
			super.navigate("/anonymous/workplan/show", "id=23");
			super.checkPanicExists();

			
		}


}
