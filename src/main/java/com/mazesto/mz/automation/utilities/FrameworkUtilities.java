package com.mazesto.mz.automation.utilities;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.mazesto.automation.commons.GlobalConstants;
import com.mazesto.automation.commons.TestDataMapper;
import com.mazesto.automation.commons.ThreadConfigurationJobMapper;
import com.mazesto.automation.commons.ThreadTestDataMapper;
import com.mazesto.automation.listeners.Reporter;


/**
 * @author Dheeraj Choudhary
 *
 */
public class FrameworkUtilities {

	/**
	 * It will capture screenshot of the current browser instance. 
	 * Precondition is that the browser session has not ended.
	 * @param fileNameFormerPart - Former part of the file name to which the time stamp will be appended in the latter. 
	 * @return String - the path of the image file with screenshot.
	 */
	public String addScreenCaptureToReport(String fileNameFormerPart) {
		TakesScreenshot ts = (TakesScreenshot) ThreadConfigurationJobMapper.getConfigurationJob(Thread.currentThread()).getConfigurationJobData().getDriver();
		java.io.File source = ts.getScreenshotAs(OutputType.FILE);

		String reportingPath = TestDataMapper.getData(GlobalConstants.ConfigKey.REPORT_PATH);
		String timestamp = (LocalDate.now().toString() + "_" + LocalTime.now().toString()).replaceAll("[-:]+", "_");
		String dest = reportingPath + File.separator + "Screenshots" + File.separator + fileNameFormerPart + timestamp
				+ ".png";
		System.out.println("Attempting to save screenshot to "+dest);
		java.io.File destination = new java.io.File(dest);
		try {
			FileUtils.copyFile(source, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Adding Capture from "+"Screenshots\\" + fileNameFormerPart + timestamp + ".png");
		try {
			Reporter.addTestStepScreenCaptureFromPath("Screenshots\\" + fileNameFormerPart + timestamp + ".png");
		} catch (IOException e) {
			System.err.println("Unable to add screenshot to test step.." + e.getMessage()+" : " + e.getStackTrace());
		}
		return "Screenshots\\" + fileNameFormerPart + timestamp + ".png";
	}

	public List<String> trimList(List<String> listToTrim){
		return listToTrim.stream().map(String::trim).collect(Collectors.toList());
	}
	
	public List<String> sortList(List<String> listToSort){
		return listToSort.stream().sorted().collect(Collectors.toList());
	}

	public List<String> trimAndSortList(List<String> listToTrimAndSort){
		return listToTrimAndSort.stream().map(String::trim).sorted().collect(Collectors.toList());
	}

	public List<String> getDataSeparatedByDelimeter(String delimiter, String dataAccess) {
		System.out.println(dataAccess);
		String[] input_data = dataAccess.split(delimiter);
		List<String> list = Arrays.asList(input_data);
	
		return list;
	}
	
	public String charRemoveAt(String str, int p) {  
        return str.substring(0, p) + str.substring(p + 1);  
     }  
	
	public String removeWordFromString(String string, String word) 
    { 
  
		// Check if the word is present in string 
				// If found, remove it using removeAll() 
				if (string.contains(word)) { 

					if(word.equalsIgnoreCase("?"))
						word = "\\?";
					// To cover the case 
					// if the word is at the 
					// beginning of the string 
					// or anywhere in the middle 
					String tempWord = word; 
					string = string.replaceAll(tempWord, ""); 

				} 

				// Return the resultant string 
				return string; 
    }
	/**
	 * It will truncate the String based on the start and end characters.It will return a new String starting from 0 index till the index of
	 * number of characters from Start and number of characters from end with ... in between
	 * @param String stringToBeTruncated - actual String that needs to be truncated
	 * @param int numOfCharsFromStart 
	 * @param numOfCharsFromEnd
	 * @return String - the truncated string with start and end characters and ... in between
	 */
	public String getSubStringAfterTruncatingCharacters(String stringToBeTruncated,int numOfCharsFromStart,int numOfCharsFromEnd) {
		String s2=stringToBeTruncated.substring(numOfCharsFromStart, (stringToBeTruncated.length()-numOfCharsFromEnd));
		return stringToBeTruncated.replaceFirst(s2, "...");
	}
	
	/**This method will compare two linked hashmap as per their insertion order
	 * 
	 * @param activitySectionsWithCount
	 * @param actualFiltersWithCount
	 * @return
	 */
	public Boolean linkedEquals( LinkedHashMap<String, Integer> map1, LinkedHashMap<String, Integer> map2) {
		  Iterator<Entry<String, Integer>> leftItr = map1.entrySet().iterator();
		  Iterator<Entry<String, Integer>> rightItr = map2.entrySet().iterator();

		  while ( leftItr.hasNext() && rightItr.hasNext()) {
		     Entry<String, Integer> leftEntry = leftItr.next();
		     Entry<String, Integer> rightEntry = rightItr.next();

		     //AbstractList does null checks here but for maps we can assume you never get null entries
		     if (! leftEntry.equals(rightEntry))
		         return false;
		  }
		  return !(leftItr.hasNext() || rightItr.hasNext());
		}
	/**
	 * It will check if all values of a map are same and match with the parameter given- valueTo Match
	 * @param map
	 * @param valueToMatch
	 * @return
	 */
	public boolean hasAllSameValues(LinkedHashMap<String, Integer> map, Integer valueToMatch) {
		Set<Integer> values = new HashSet<Integer>(map.values());
		if(values.size() == 1 &&(values.contains(valueToMatch)))
		return true;
		return false;
	}
	public void reverseList(List<String> list) {
		Collections.reverse(list);
	}

}


