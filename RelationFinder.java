package csc450;

import java.util.ArrayList;

import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;

public class RelationFinder {
	
	private final IDictionary dict;
	private final WordnetStemmer stemmer;
	
	/**
	 * Constructs a RelationFinder that requires a Wordnet dictionary.
	 * 
	 * @param dict
	 * 			The dictionary to be used; may not be <code>null</code>
	 * @throws NullPointerException
	 *			If the specified dictionary is <code>null</code>
	 */
	public RelationFinder(IDictionary dict) {
        if(dict == null)
        	throw new NullPointerException();
        this.dict = dict;
        this.stemmer = new WordnetStemmer(dict);
    }
	
	/** 
     * Returns the dictionary in use by the relation finder; will not return <code>null</code>
     *
     * @return the dictionary in use by this relation finder
     */
    public IDictionary getDictionary(){
    	return dict;
    }
	
	/**
	 * This method creates a list of all synonyms for every word in the provided string.
	 * Based on the synonym-finding method provided by the user neo m in:
	 * https://stackoverflow.com/questions/25339856/get-synonym-of-word-with-jwi
	 * 
	 * @param str
	 * 			The string to be processed
	 * @return ArrayList<String> 
	 * 			Returns a list of all synonyms for words in the given string
	 * 
	 */
	public ArrayList<String> findStringSynonyms(String str) {
		ArrayList<String> stemsList = new ArrayList<String>();
		ArrayList<String> synonymList = new ArrayList<String>();
		
		//Split the provided string into separate words, and find the stems of those words
		for (String word : str.split(" ", 0)) {
			for (String stem : stemmer.findStems(word, null)) {
				stemsList.add(stem);
			}
		}
		
		//For each word stem, find all of the synonyms and add them to the list, skipping redundancies
		for (String stem : stemsList){
			for (POS p : POS.values()) {
				IIndexWord idxWord = dict.getIndexWord(stem, p);
				if (idxWord != null) {
        			IWordID wordID = idxWord.getWordIDs().get(0);
       				IWord word = dict.getWord(wordID);
        			ISynset synset = word.getSynset();
        			for (IWord w : synset.getWords()) {
            			if (!synonymList.contains(w.getLemma())) {
            				synonymList.add(w.getLemma());
            			}
        			}
				}
			}
		}
		return synonymList;
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
	public boolean relatedInString(String str1, String str2) {
		//Create synonym arrays for both strings
		ArrayList<String> str1Synonyms = findStringSynonyms(str1);
		ArrayList<String> str2Synonyms = findStringSynonyms(str2);
		
		//If any words are shared between the synonym arrays, then the two strings share related words
		for (String word : str1Synonyms) {
			if (str2Synonyms.contains(word)){
				return true;
			}
		}
		//If no related words are shared, return false
		return false;
	}
	
	/**
	 * This method accepts a string and list of synonyms, and determines if any words from the string
	 * have synonyms from the list.
	 * 
	 * @param str
	 * 			The string to be compared
	 * @param synonymList
	 * 			A list of synonyms to be compared with the string
	 * @return boolean 
	 * 			Returns true if a word from the string has a synonym in the list
	 * 
	 */
	public boolean relatedInString(String str, ArrayList<String> synonymList) {
		//Create a synonym array for the string
		ArrayList<String> strSynonyms = findStringSynonyms(str);
				
		//If any words from the string have synonyms in the provided synonym list, return true
		for (String word : synonymList) {
			if (strSynonyms.contains(word)){
				return true;
			}
		}
		//If no related words are shared, return false
		return false;
	}
}
