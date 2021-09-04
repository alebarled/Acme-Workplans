/*
 * EmployerApplicationLIstTest.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.anonymous.shout;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeWorkplansTest;

public class AnonymousShoutListTest extends AcmeWorkplansTest {
	
	/* 
	 * This test navigates into a shout list, as an anonymous.
	 * It also verificates that all the parameters of the shouts are correct.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/shout/list.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)	
	public void list(final int recordIndex, final String author, final String moment, final String text, final String info) {		
		
		super.clickOnMenu("Anonymous", "Shouts List");		
		
		super.checkColumnHasValue(recordIndex, 0, moment);
		super.checkColumnHasValue(recordIndex, 1, author);
		super.checkColumnHasValue(recordIndex, 2, text);
		super.checkColumnHasValue(recordIndex, 3, info);
		
	}
	
	/* 
	 * This test navigates into a shout list, as a manager and checks there is a panic.
	 */
	@Test
	@Order(20)	
	public void listNegative() {		
		
		super.signIn("manager1", "manag3r");		
		
		super.navigate("/anonymous/shout/list","");
		super.checkPanicExists();
		
	}

}
