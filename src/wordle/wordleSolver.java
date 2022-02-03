package wordle;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import automation.Automation;

import java.io.*;

public class wordleSolver
{
	public static WebDriver driver;
    final static int numLetters = 9;

    
    // Getter for number of letters
    public static int getNumletters() {
		return numLetters;
	}

	public static ArrayList<String> readFile() throws IOException
    {
        File file = new File(".\\dictionaries\\" + numLetters + "LetterWords.txt");
        Scanner input = new Scanner(file);
        ArrayList<String> dictionary = new ArrayList<>();

        while(input.hasNextLine())
        {
            String str = input.nextLine().toLowerCase();
            if(str.length() == numLetters)
                dictionary.add(str);
        }

        input.close();
        return dictionary;
    }

    // Currently has problems with last letter
    // Removes all words that contain the given letter
    public static ArrayList<String> removeLetter(ArrayList<String> list, char letter)
    {
        boolean letterRemoved = false;

        for(int i = list.size() - 1; i >= 0; i--)
        {
            for(int j = 0; j < numLetters; j++)
            {
                if(letterRemoved == false && list.get(i).charAt(j) == letter)
                {
                    list.remove(i);
                    letterRemoved = true;
                }
            }
            letterRemoved = false;
        }
        return list;
    }

    //
    public static ArrayList<String> containsLetter(ArrayList<String> list, char letter)
    {
        boolean isIn = false;
        for(int i = list.size() - 1; i >= 0; i--)
        {
            for(int j = 0; j < list.get(i).length(); j++)
            {
                if(list.get(i).charAt(j) == letter)
                {
                    isIn = true;
                }
            }
            if(isIn == false)
            {
                list.remove(i);
            }
            isIn = false;
        }
        return list;
    }

    public static ArrayList<String> notInPosition(ArrayList<String> list, char letter, int position)
    {
        for(int i = list.size() - 1; i >= 0; i--)
            if(list.get(i).charAt(position) == letter)
                list.remove(i);
        return list;
    }

    public static ArrayList<String> inPosition(ArrayList<String> list, char letter, int position)
    {
        for(int i = list.size() - 1; i >= 0; i--)
            if(list.get(i).charAt(position) != letter)
                list.remove(i);
        return list;
    }

    // Returns a frequency array of number of letters
    public static int[] giveFrequencies(ArrayList<String> dictionary)
    {
        int [] arr = new int[26];
        for(int i = 0; i < dictionary.size(); i++)
            for(int j = 0; j < numLetters; j++)
                arr[dictionary.get(i).charAt(j) - 'a']++;
        return arr;
    }

    // Prints the frequency array of number of letters
    public static void printFrequencies(int [] arr)
    {
        for(int i = 0; i < arr.length; i++)
            System.out.println((char)('a'+i) + ": " + arr[i]);
    }

    public static ArrayList<String> newGuessList(ArrayList<String> list, boolean [] knownLetters)
    {
        if (list.size() == numLetters)
            return list;

        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < list.size(); i++)
            temp.add(list.get(i));

        int index = -1;
        int [] arr = giveFrequencies(temp);
        // printFrequencies(arr);

        boolean [] guessingLetters = new boolean[26];
        ArrayList<String> possibleWordList = new ArrayList<>();

        for(int totalTimes = 0; totalTimes < numLetters - 1; totalTimes++)
        {
            int max = 0;
            for(int i = 0; i < arr.length; i++)
            {
                if(guessingLetters[i] == false && arr[i] > max && knownLetters[i] == false)
                {
                    max = arr[i];
                    index = i;
                }
            }
            if (index == -1)
                return list;
            temp = containsLetter(temp, (char)('a' + index));
            arr = giveFrequencies(temp);

            guessingLetters[index] = true;

            if(temp.size() < 0)
            {
                return possibleWordList;
            }
            possibleWordList.clear();
            for(int i = 0; i < temp.size(); i++)
            {
                possibleWordList.add(temp.get(i));
            }
        }

        return temp;
    }

    public static ArrayList<String> userInput(ArrayList<String> list, ArrayList<String> possibleWordList, boolean [] knownLetters)
    {
        Scanner input = new Scanner(System.in);
        int indexOfWord = 0;
        String word = possibleWordList.get(indexOfWord);
        System.out.println("Input the word: " + word);
        
        boolean validWord = false;
        
        while(!validWord)
        {
        	System.out.println("Current size of possible word list: " + possibleWordList.size());
        	System.out.print("Is this a valid word? (y/n): ");
        	String ans = input.nextLine();
        	if (ans.charAt(0) == 'Y' || ans.charAt(0) == 'y')
        	{
        		validWord = true;
        	} else {
        		indexOfWord++;
        		System.out.println("Current size of possible word list: " + possibleWordList.size());
        		if (indexOfWord < possibleWordList.size())
        		{
        			word = possibleWordList.get(indexOfWord);
        			System.out.println("Input the word: " + word);
        		}
        	}
        }
        
        System.out.print("Input g, y, n: ");
        String solutions = input.nextLine();

        int count = 0;
        
        for(int i = 0; i < numLetters; i++)
        {
            if(solutions.charAt(i) == 'g')
            {
                count++;
                list = inPosition(list, word.charAt(i), i);
                knownLetters[word.charAt(i) - 'a'] = true;
            }
            if(solutions.charAt(i) == 'y')
            {
                list = containsLetter(list, word.charAt(i));
                list = notInPosition(list, word.charAt(i), i);
                knownLetters[word.charAt(i) - 'a'] = true;
            }
        }
        
        for(int i = 0; i < numLetters; i++)
        {
            if(solutions.charAt(i) == 'n' && knownLetters[word.charAt(i) - 'a'] == false)
            {
                list = removeLetter(list, word.charAt(i));
            }
        }

        if (count == numLetters)
            list.clear();

        return list;
    }

    public static ArrayList<String> solver(ArrayList<String> list, ArrayList<String> possibleWordList, boolean [] knownLetters, int currentAttempt, WebDriver driver) throws InterruptedException
    {
        //int indexOfWord = 0;
        String word = possibleWordList.get(0);
        
        String solutions = Automation.inputter(word, currentAttempt, driver);
        int count = 0;
        
        for(int i = 0; i < numLetters; i++)
        {
            if(solutions.charAt(i) == 'g')
            {
                count++;
                list = inPosition(list, word.charAt(i), i);
                knownLetters[word.charAt(i) - 'a'] = true;
            }
            if(solutions.charAt(i) == 'y')
            {
                list = containsLetter(list, word.charAt(i));
                list = notInPosition(list, word.charAt(i), i);
                knownLetters[word.charAt(i) - 'a'] = true;
            }
        }
        
        for(int i = 0; i < numLetters; i++)
        {
            if(solutions.charAt(i) == 'n' && knownLetters[word.charAt(i) - 'a'] == false)
            {
                list = removeLetter(list, word.charAt(i));
            }
        }

        if (count == numLetters)
            list.clear();

        return list;
    }
    
    public static void solveWordle() throws IOException
    {
        boolean [] knownLetters = new boolean[26];
        ArrayList<String> dictionary = new ArrayList<>();
        dictionary = readFile();
        while(dictionary.size() > 0)
        {
            userInput(dictionary, newGuessList(dictionary, knownLetters), knownLetters);
        }
    }
    
    public static void solveWordleAuto(WebDriver driver) throws IOException, InterruptedException
    {
        boolean [] knownLetters = new boolean[26];
        ArrayList<String> dictionary = new ArrayList<>();
        dictionary = readFile();
        int currentAttempt = 0;
        while(dictionary.size() > 0)
        {
        	currentAttempt++;
            solver(dictionary, newGuessList(dictionary, knownLetters), knownLetters, currentAttempt, driver);
        }
    }
    
    // main
    public static void main(String [] args) throws IOException, InterruptedException
    {
    	System.setProperty("webdriver.chrome.driver", ".\\Driver\\chromedriver.exe");
    	driver = new ChromeDriver();
    	driver.manage().window().maximize();
    	driver.get("https://wordlegame.org/" + numLetters + "-letter-words-wordle");

    	solveWordleAuto(driver);
    	while (true)
    	{
    		Thread.sleep(500);
    		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[7]/div[2]/div/div[3]/button")).click();
    		solveWordleAuto(driver);
    	}
        
    }
}
