package com.mazestoautomation.MazestoUIScripts.automation.Runner;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.mazesto.automation.beans.Suite;
import com.mazesto.automation.beans.fileparser.XMLFileParserImpl;

public class Runner {
	
	static{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hhmmss");
        System.setProperty("current.date", dateFormat.format(new Date()));
    }
	    
	private static Logger log  = LogManager.getLogger("Runner");

	public static void main(String args[]) {
			
		String suiteParameter = System.getProperty("suiteParam");
		final String startConfigFilesPath = "src" + File.separator + "test" + File.separator+"resources" + File.separator + "ConfigFiles"+ File.separator;
		final String xmlFileExtension = ".xml";
		final String execution = "execution";
		Suite suite = null;
		if(suiteParameter==null) {
			suiteParameter="";
		}
		
		//Files.exists(Paths.get(startConfigFilesPath + suiteParameter + execution+ xmlFileExtension
		
		if( "dheeraj".equalsIgnoreCase("Dheeraj")) {
			XMLFileParserImpl xmlParser = new XMLFileParserImpl();
			System.out.println("Using config file: "+startConfigFilesPath + suiteParameter + execution+ xmlFileExtension);
			suite = xmlParser.parseFile(startConfigFilesPath + suiteParameter + execution+ xmlFileExtension);
		} else {
			System.out.println("Parameter not parsed or No proper config file found, please check!!");
			System.out.println("System will now exit!!");
			System.exit(1);
		}
		
		if (suite != null)
			suite.execute();
		
	}
}
