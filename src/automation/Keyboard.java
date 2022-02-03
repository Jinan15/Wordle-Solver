package automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Keyboard {

	// Enters letter into wordle solver
	public static void enterLetter(WebDriver driver, char letter)
	{
		letter = Character.toLowerCase(letter);
		
		switch (letter)
		{
			case 'q':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[1]/div[1]")).click();;
				break;
			case 'w':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[1]/div[2]")).click();;
				break;
			case 'e':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[1]/div[3]")).click();;
				break;
			case 'r':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[1]/div[4]")).click();;
				break;
			case 't':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[1]/div[5]")).click();;
				break;
			case 'y':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[1]/div[6]")).click();;
				break;
			case 'u':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[1]/div[7]")).click();;
				break;
			case 'i':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[1]/div[8]")).click();;
				break;
			case 'o':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[1]/div[9]")).click();;
				break;
			case 'p':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[1]/div[10]")).click();;
				break;
			case 'a':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[2]/div[1]")).click();;
				break;
			case 's':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[2]/div[2]")).click();;
				break;
			case 'd':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[2]/div[3]")).click();;
				break;
			case 'f':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[2]/div[4]")).click();;
				break;
			case 'g':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[2]/div[5]")).click();;
				break;
			case 'h':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[2]/div[6]")).click();;
				break;
			case 'j':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[2]/div[7]")).click();;
				break;
			case 'k':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[2]/div[8]")).click();;
				break;
			case 'l':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[2]/div[9]")).click();;
				break;
			case 'z':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[3]/div[2]")).click();;
				break;
			case 'x':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[3]/div[3]")).click();;
				break;
			case 'c':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[3]/div[4]")).click();;
				break;
			case 'v':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[3]/div[5]")).click();;
				break;
			case 'b':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[3]/div[6]")).click();;
				break;
			case 'n':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[3]/div[7]")).click();;
				break;
			case 'm':
				driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[5]/div[3]/div[8]")).click();;
				break;
			default:
				System.out.println("Not a Letter!");
				break;
		}
	}
}
