package csc450;

import java.util.ArrayList;
import edu.mit.jwi.morph.SimpleStemmer;

public class RelationFinder 
{
	
	static private final SimpleStemmer stemmer = new SimpleStemmer();
	
	/**
	 * This method creates a list of all stems for every word in the provided string.
	 * 
	 * @param str
	 * 			The string to be processed
	 * @return ArrayList<String> 
	 * 			Returns a list of all stems for words in the given string
	 * 
	 */
	static ArrayList<String> findStringStems(String str) 
	{
		ArrayList<String> stemsList = new ArrayList<String>();
		
		//Split the provided string into separate words, and find the stems of those words
		for (String word : str.split(" ", 0))
		{
			stemsList.add(word.toLowerCase());
			stemsList.addAll(stemmer.findStems(word, null));
		}
		return stemsList;
	}
	
	/**
	 * This method accepts two strings, and determines whether or not those strings share related words
	 * 
	 * @param str1 
	 * 			The first string to be compared, such as a user query
	 * @param str2
	 * 			The second string to be compared, such as the contents of a .txt file
	 * @return boolean 
	 * 			Returns true if a word from the first string has a related word in the second string
	 * 
	 */
	static boolean shareStems(String str1, String str2) 
	{
		//Create synonym arrays for both strings
		ArrayList<String> str1Stems = findStringStems(str1);
		ArrayList<String> str2Stems = findStringStems(str2);
		
		//If any words are shared between the synonym arrays, then the two strings share related words
		for (String word : str1Stems) 
		{
			if (str2Stems.contains(word))
			{
				return true;
			}
		}
		//If no related words are shared, return false
		return false;
	}
	
	/**
	 * This method accepts a string and list of stems, and determines if any words from the string
	 * have stems from the list.
	 * 
	 * @param str
	 * 			The string to be compared
	 * @param stemList
	 * 			A list of stems to be compared with the string
	 * @return boolean 
	 * 			Returns true if a word from the string has a stem in the list
	 * 
	 */
	static boolean shareStems(String str, ArrayList<String> stemList) 
	{
		//Create a synonym array for the string
		ArrayList<String> strStems = findStringStems(str);
				
		//If any words from the string have stems in the provided synonym list, return true
		for (String word : stemList) 
		{
			if (strStems.contains(word))
			{
				return true;
			}
		}
		//If no related words are shared, return false
		return false;
	}
	
	/**
	 * This method accepts two lists of stems, and determines if any words are shared between them.
	 * 
	 * @param stemList1
	 * 			The first list of stems to be compared 
	 * @param stemList2
	 * 			The second list of stems to be compared
	 * @return boolean 
	 * 			Returns true if any words are shared between the stems lists
	 * 
	 */
	static boolean shareStems(ArrayList<String> stemList1, ArrayList<String> stemList2) 
	{	
		//If any words from the first list are found in the second list, return true
		for (String word : stemList1) 
		{
			if (stemList2.contains(word))
			{
				return true;
			}
		}
		//If no related words are shared, return false
		return false;
	}
}
