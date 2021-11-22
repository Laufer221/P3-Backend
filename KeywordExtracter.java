package csc450;

import java.util.ArrayList;
import java.util.Arrays;

public class KeywordExtracter 
{
	static private final ArrayList<String> nonKeywords = new ArrayList<String>(Arrays.asList("is", "in", "that", "of", "a", "an", "the"));
	
	/**
	 * This method produces a list of keywords from a given query
	 * @param query
	 * 			A user query in the form of a string
	 * @return ArrayList<String>
	 * 			A list of all keywords from the user query
	 */
	static ArrayList<String> extractToList(String query)
	{
		ArrayList<String> queryKeywords = new ArrayList<String>();
		//Split the query into a list of words, excluding non-word characters like "@","!",";", etc.
		String[] queryWords = query.toLowerCase().split("\\W++");
		
		//Add any words that haven't been marked as non-important words to the list of keywords
		for (String word : queryWords)
		{
			if (!nonKeywords.contains(word)) 
			{
				queryKeywords.add(word);
			}
		}
		return queryKeywords;
	}

	/**
	 * This method produces a string containing the keywords from a given query
	 * @param query
	 * 			A user query in the form of a string
	 * @return String
	 * 			A string containing all the keywords from the user query
	 */
	static String extractToString(String query)
	{
		String queryKeywords = "";
		//Get a list of keywords
		ArrayList<String> keywordList = extractToList(query);
		//Append each keyword to the string
		for (String word : keywordList)
		{
			queryKeywords += word + " ";
		}
		return queryKeywords;
	}
}
