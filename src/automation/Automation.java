package automation;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import automation.Keyboard;
import wordle.wordleSolver;

public class Automation {

	
	public static int numLetter = wordleSolver.getNumletters();
	
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
	
	public static String inputter(String word, int currentAttempt, WebDriver driver) throws InterruptedException
	{
		for (int i = 0; i < numLetter; i++)
		{
			Keyboard.enterLetter(driver, word.charAt(i));
		}
		
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[3]/div[9]")).click();
		Thread.sleep(250);
		String gyn = "";
		
		for (int i = 1; i <= numLetter; i++)
		{
			String color = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[3]/div[" + currentAttempt + "]/div[" + i + "]")).getAttribute("class");
			gyn = gyn + letterColor(color);
		}
		return gyn;
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		// driver.close();

	}
}
