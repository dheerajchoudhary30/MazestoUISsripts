package com.mazesto.mz.automation.application;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import com.mazesto.automation.beans.vo.Page;
import com.mazesto.automation.commons.ThreadTestDataMapper;
import com.mazesto.mz.automation.beans.GlobalConstants;
import com.mazesto.mz.automation.utilities.Utilities;

public class Home extends Page {
	
	Utilities utilities = new Utilities();
	private Logger log = LogManager.getLogger(this.getClass());

	public Home navigateToHome() {

		String env = ThreadTestDataMapper.getData(null, "executionEnv");
		System.out.println("Executing env : " + env);
		driver.get(getApplicationURLForEnv(env));
		log.info("Navigating to Home Page-Login window");
		utilities.getFrameworkUtilities().addScreenCaptureToReport("Login screen Dims");
		return null;

	}

	public String getApplicationURLForEnv(String env) {

		String applicationURL = "";
		switch (env) {

		case "MQA":
			applicationURL = GlobalConstants.Environments.QA1;
			break;

		default:

			applicationURL = GlobalConstants.Environments.QA1;

		}

		return applicationURL;

	}

	private class PageElements {

		public PageElements() {

			PageFactory.initElements(driver, this);

		}

	}

}
