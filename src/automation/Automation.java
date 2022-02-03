//	Automation for Wordle-Solver powered by Selenium
//	Made by:
//	Jinan Patel, Sebastian Scharager

package automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import automation.Keyboard;
import wordle.wordleSolver;

public class Automation {

	public static int numLetters = wordleSolver.getNumletters();
	
	// Takes the class name from a letters input box html/css
	// Turns it into g for green, y for yellow and n for no color
	public static String letterColor(String str)
	{
		String temp = "";
		str = str.substring(18, str.length());
		if (str.equals("correct"))
		{
			temp = "g";
		}
		else if (str.equals("elsewhere"))
		{
			temp = "y";
		}
		else if (str.equals("absent"))
		{
			temp = "n";
		}
		return temp;
	}
	
	// Inputs word into website and gets class attribute on each individual letter
	// returns a string of n, y, and g
	public static String inputter(String word, int currentAttempt, WebDriver driver) throws InterruptedException
	{
		// Enters letter into wordle website
		for (int i = 0; i < numLetters; i++)
			Keyboard.enterLetter(driver, word.charAt(i));
		
		// clicks enter button
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[3]/div[9]")).click();
		Thread.sleep(250);
		
		// Gets whether a character is green, yellow or not in word
		String gynString = "";
		
		for (int i = 1; i <= numLetters; i++)
		{
			String classAttribute = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[3]/div[" + currentAttempt + "]/div[" + i + "]")).getAttribute("class");
			gynString = gynString + letterColor(classAttribute);
		}
		
		// Returns gyn character sequence
		return gynString;
	}
}
