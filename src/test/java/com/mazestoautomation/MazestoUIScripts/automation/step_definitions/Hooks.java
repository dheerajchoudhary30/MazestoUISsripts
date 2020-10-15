package com.mazestoautomation.MazestoUIScripts.automation.step_definitions;

import com.mazesto.automation.helpers.WebDriverHelper;

import io.cucumber.java.After;
import io.cucumber.java.Before;


public class Hooks {

	@Before
	public void initialize() {
		
		WebDriverHelper.initiateRequiredDrivers();

	}

	@After
	public void teardown() throws InterruptedException {

		Thread.sleep(5000);
		WebDriverHelper.destroyProcessedDrivers();
		
	}

}
