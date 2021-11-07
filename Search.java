package csc450;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.mit.jwi.DataSourceDictionary;
import edu.mit.jwi.data.FileProvider;

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
	static ArrayList<File> searchTextFiles(String query, ArrayList<File> textFileList) {
		//Setup dictionary for use in the synonym finder
		String url_name = ""; //TODO: Insert url of dictionary provider
		java.net.URL url = new java.net.URL(url_name);
		FileProvider dictionaryProvider = new FileProvider(url);
		DataSourceDictionary dict = new DataSourceDictionary(dictionaryProvider);
		RelationFinder synonymFinder = new RelationFinder(dict);
		
		ArrayList<File> resultsList = new ArrayList<File>();
		ArrayList<String> querySynonyms = synonymFinder.findStringSynonyms(query);
		
		for (File textFile : textFileList) {
			Scanner reader;
			try {
				reader = new Scanner(textFile);
			} catch (FileNotFoundException e) {
				continue;
			}
			String textFileContents = "";
			while (reader.hasNextLine()) {
				textFileContents += reader.nextLine();
			}
			if (synonymFinder.shareSynonyms(textFileContents, querySynonyms)) {
				resultsList.add(textFile);
			}
			reader.close();
		}
		
		return resultsList;
	}

}
