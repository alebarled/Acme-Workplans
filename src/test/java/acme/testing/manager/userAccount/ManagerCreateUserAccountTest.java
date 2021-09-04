package acme.testing.manager.userAccount;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import acme.testing.AcmeWorkplansTest;

public class ManagerCreateUserAccountTest extends AcmeWorkplansTest{
	
	/*
	 * This test creates a new user and then becomes a manager.
	 * We check if "Personal Tasks" link exists, because its part of the manager's menu.
	 */
	@Test
	@Order(10)
	public void managerAccountCreatePositive() {
		
		/// sign-up
		super.navigateHome();
		super.clickOnMenu("Sign up", null);  
		super.fillInputBoxIn("username", "user1");
		super.fillInputBoxIn("password", "user1");
		super.fillInputBoxIn("confirmation", "user1");
		super.fillInputBoxIn("identity.name", "Nombre");
		super.fillInputBoxIn("identity.surname", "Apellidos");
		super.fillInputBoxIn("identity.email", "email@us.es");
		super.fillInputBoxIn("accept", "true");
		super.clickOnSubmitButton("Sign up");
		
		//sign-in
		super.signIn("user1", "user1");
		
		//become a manager
		super.clickOnMenu("Account", "Become a manager");
		
		//can use this one instead, just for checking it in the same way as negative case
		//super.navigate("authenticated/manager/create", "");
		
		super.clickOnSubmitButton("Register");
		super.checkLinkExists("Personal Tasks");
	}
	
	/*
	 * This test tries to become a manager when it hasn't loged with any credentials.
	 * (a bit silly but checks it can't be done)
	 */
	@Test
	@Order(20)
	public void managerAccountCreateNegative() {
		
		super.navigate("/authenticated/manager/create", "");
		super.checkPanicExists();
		
	}
	

}
