package com.mazesto.mz.automation.utilities;
import de.svenjacobs.loremipsum.LoremIpsum;

/*
 * Lorem ipsum text is similar to the natural typeface (of English) and thus more likely 
 * to uncover flaws in the design of desktop/web applications 
 * or web sites regarding word wrapping etc.
 * 
 * 
 */

public class LoremIpsumCreator {
  private LoremIpsum loremIpsum;
  
  public LoremIpsumCreator() {
    this.loremIpsum = new LoremIpsum();
  }


  public String getWords(int length) {
	  String randomText = this.loremIpsum.getWords(500);
	  return randomText.substring(0, length-1);
  }
  
  
}
