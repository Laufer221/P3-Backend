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
		ArrayList<File> resultsList = new ArrayList<File>();
		ArrayList<String> queryStems = RelationFinder.findStringStems(query);
		
		for (File textFile : textFileList) 
		{
			//Attempt to open the text file
			Scanner reader;
			try 
			{
				reader = new Scanner(textFile);
			} 
			catch (FileNotFoundException e) 
			{
				continue;
			}
			//Read the file's contents into a string
			String textFileContents = "";
			while (reader.hasNextLine()) 
			{
				textFileContents += reader.nextLine();
			}
			//If any keywords are shared between the file and the query, add the file to the list of results
			if (RelationFinder.shareStems(textFileContents, queryStems)) 
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
		ArrayList<String> resultsList = new ArrayList<String>();
		ArrayList<String> queryStems = RelationFinder.findStringStems(query);
		
		for (String str : StringList) 
		{
			//If any keywords are shared between the string and the query, add the string to the list of results
			if (RelationFinder.shareStems(str, queryStems)) 
			{
				resultsList.add(str);
			}
		}
		
		return resultsList;
	}
}
