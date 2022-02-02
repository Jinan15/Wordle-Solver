import java.util.*;
import java.io.*;

public class wordleSolver
{
    final static int numLetters = 11;

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
        int count = 0;
        int [] arr = new int[26];
        for(int i = 0; i < dictionary.size(); i++)
        {
            // System.out.println(dictionary.get(i));
            for(int j = 0; j < numLetters; j++)
            {
                arr[dictionary.get(i).charAt(j) - 'a']++;
            }
            count++;
        }
        System.out.println("Number of words remaining: " + count);
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
            //printFrequencies(arr);
            guessingLetters[index] = true;

            // for(int i = 0; i < temp.size(); i++)
            // {
            //     System.out.println(temp.get(i));
            // }

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
        for (int i = 0; i < possibleWordList.size(); i++)
        {
            System.out.println((i + 1) + ": " + possibleWordList.get(i));
        }
        System.out.print("Choose a word from this list (pick the number): ");
        int choice = Integer.parseInt(input.nextLine());
        String word = possibleWordList.get(choice - 1);
        System.out.println("The word you chose is: " + word);
        System.out.print("Input g, y, n: ");
        String solutions = input.nextLine();

        for(int i = 0; i < numLetters; i++)
        {
            if(solutions.charAt(i) == 'g')
            {
                list = inPosition(list, word.charAt(i), i);
                knownLetters[word.charAt(i) - 'a'] = true;
            }

            if(solutions.charAt(i) == 'y')
            {
                list = containsLetter(list, word.charAt(i));
                list = notInPosition(list, word.charAt(i), i);
                knownLetters[word.charAt(i) - 'a'] = true;
            }

            if(solutions.charAt(i) == 'n' && knownLetters[word.charAt(i) - 'a'] == false)
            {
                list = removeLetter(list, word.charAt(i));
            }
        }

        for(int i = 0; i < list.size(); i++)
        {
            System.out.println(list.get(i));
        }
        return list;
    }

    // main
    public static void main(String [] args) throws IOException
    {
        File file = new File("Collins_Scrabble_Words_2019.txt");
        Scanner input = new Scanner(file);
        ArrayList<String> dictionary = new ArrayList<>();
        boolean [] knownLetters = new boolean[26];

        while(input.hasNextLine())
        {
            String str = input.nextLine().toLowerCase();
            if(str.length() == numLetters)
                dictionary.add(str);
        }

        userInput(dictionary, newGuessList(dictionary, knownLetters), knownLetters);
        userInput(dictionary, newGuessList(dictionary, knownLetters), knownLetters);
        for(int i = 0; i < dictionary.size(); i++)
        {
            System.out.println(dictionary.get(i));
        }
        userInput(dictionary, newGuessList(dictionary, knownLetters), knownLetters);
        System.out.println("after 3rd attempt");
        for(int i = 0; i < dictionary.size(); i++)
        {
            System.out.println(dictionary.get(i));
        }
        userInput(dictionary, newGuessList(dictionary, knownLetters), knownLetters);
        userInput(dictionary, newGuessList(dictionary, knownLetters), knownLetters);
        userInput(dictionary, newGuessList(dictionary, knownLetters), knownLetters);

        for(int i = 0; i < dictionary.size(); i++)
            System.out.println(dictionary.get(i));
    }
}
