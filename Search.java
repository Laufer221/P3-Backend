package csc450;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import csc450.RelationFinder;


public class Search {
	
	/**
	 * This method searches through a list of text files, and returns a list of all files
	 * that contained words relating to a provided query.
	 * 
	 * @param query
	 * 			The user's query, in the form of a string
	 * @param textFileList
	 * 			A list of text files pulled from the database
	 * @return
	 * 			Returns a list of all the text files that matched the query
	 */
	static ArrayList<File> searchTextFiles(String query, ArrayList<File> textFileList) 
	{
		RelationFinder stemFinder = new RelationFinder();
		
		ArrayList<File> resultsList = new ArrayList<File>();
		ArrayList<String> queryStems = stemFinder.findStringStems(query);
		
		for (File textFile : textFileList) 
		{
			Scanner reader;
			try 
			{
				reader = new Scanner(textFile);
			} 
			catch (FileNotFoundException e) 
			{
				continue;
			}
			//
			String textFileContents = "";
			while (reader.hasNextLine()) 
			{
				textFileContents += reader.nextLine();
			}
			//
			if (stemFinder.shareStems(textFileContents, queryStems)) 
			{
				resultsList.add(textFile);
			}
			reader.close();
		}
		
		return resultsList;
	}

	/**
	 * This method searches through a list of strings, and returns a list of all strings
	 * that contained words relating to a provided query.
	 * 
	 * @param query
	 * 			The user's query, in the form of a string
	 * @param StringList
	 * 			A list of strings pulled from the database
	 * @return
	 * 			Returns a list of all the strings that matched the query
	 */
	static ArrayList<String> searchStrings(String query, ArrayList<String> StringList) 
	{
		RelationFinder stemFinder = new RelationFinder();
		
		ArrayList<String> resultsList = new ArrayList<String>();
		ArrayList<String> queryStems = stemFinder.findStringStems(query);
		
		for (String str : StringList) 
		{
			if (stemFinder.shareStems(str, queryStems)) 
			{
				resultsList.add(str);
			}
		}
		
		return resultsList;
	}
}
