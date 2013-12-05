


package hangman.game;

import hangman.util.Storage;
import hangman.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;





/**
 * Provides access to a set of words characterised by their game difficulty.
 * Loads automatically.
 */
public class WordDatabase
{
	// Cures problems with type safety and shortens the absurdly long name...
	private static class DiffMap extends HashMap<Difficulty,ArrayList<String>> {}
	
	
	
	
	
	private static final String  fileWords = "words.txt";
	private 	         DiffMap diffMap;
	
	
	
	
	
	public WordDatabase() {
		load();
	}
	
	
	
	
	
	/**
	 * Load the word database.
	 */
	private void load() {
		ArrayList<String> all = Storage.read( fileWords );
		
		diffMap = new DiffMap();
		diffMap.put( Difficulty.easy,   findWordsWithLength( all,  5,  7 ) );
		diffMap.put( Difficulty.medium, findWordsWithLength( all,  8, 10 ) );
		diffMap.put( Difficulty.hard,   findWordsWithLength( all, 11, 13 ) );
	}
	
	
	
	
	
	/**
	 * Get a random word in accordance with the difficulty.
	 * @param difficulty
	 * @return A lowercase dictionary word.
	 */
	public String getRandom( Difficulty difficulty ) {
		return getRandomWordFromList( diffMap.get( difficulty ) );
	}
	
	
	
	
	
	private String getRandomWordFromList( ArrayList<String> list ) {
		return list.get( Util.randomIntRange( 0, list.size() ) );
	}
	
	
	
	
	
	private ArrayList<String> findWordsWithLength( ArrayList<String> srcList, int min, int max ) {
		ArrayList<String> out   = new ArrayList<String>();
		Pattern           regex = Pattern.compile( "^\\w{" + min + "," + max + "}$" );
		Matcher           match = regex.matcher( "" );
		
		for (String word: srcList)
			if (match.reset(word).matches())
				out.add( word );
		
		return out;
	}
}










































