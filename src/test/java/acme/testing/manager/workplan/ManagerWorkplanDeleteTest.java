package acme.testing.manager.workplan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import acme.testing.AcmeWorkplansTest;

public class ManagerWorkplanDeleteTest extends AcmeWorkplansTest{
	
	//Test cases ------------------------------------------------------------
	
		/*
		 * In this test, the deletion of a workplan is performed (action that a manager can perform on its own workplans). 
		 * To do so, we register as a manager and enter a workplan of the manager and delete it, 
		 * and confirm that the workplan no longer exists in the list.
		 */
		@Test
		@Order(10)
		public void deletePositive() {
			
			super.signIn("manager1", "manag3r");
			
			super.navigate("/manager/workplan/show", "id=61");
			
			super.clickOnSubmitButton("Delete");
			
			super.navigate("/manager/workplan/show", "id=61");
			
			super.checkErrorsExist();
			super.navigateHome();
			super.signOut();
			
			//For security reasons we restore the database again.
			this.signIn("administrator", "administrator");
			super.clickOnMenu("Administrator", "Populate DB (initial)");
			super.clickOnMenu("Administrator", "Populate DB (samples)");	
			this.signOut();

		}
		/*
		 * This test checks that a manager cannot delete the workplans of other managers.
		 */
		
		@Test
		@Order(20)
		public void deleteNegative() {
			super.signIn("manager2", "manag3r");
			super.navigate("/manager/workplan/delete", "id=61");
			super.checkPanicExists();
		}

}
