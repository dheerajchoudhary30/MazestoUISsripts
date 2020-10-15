package com.mazestoautomation.MazestoUIScripts.automation.step_definitions;

import com.mazesto.automation.beans.vo.StepDefinition;
import com.mazesto.mz.automation.application.Home;

import io.cucumber.java.en.Given;

public class TestFileStepDef extends StepDefinition {

	@Given("I want to write a step with precondition")
	public void i_want_to_write_a_step_with_precondition() {
		System.out.println("Inside STep Def");
	}

	@Given("user navigate to the portal")
	public void user_navidate_to_the_portal() {

		Home home = new Home();
		home.navigateToHome();

	}

}
