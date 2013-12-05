


package hangman.game;

import hangman.util.Storage;
import java.io.Serializable;
import java.util.HashMap;





/**
 * Tracks information about word guesses.
 * Saves and loads automatically.
 */
public class WordHistory implements Serializable
{
	public static class Status implements Serializable {
		public int attempts;
		public int successes;
		public int failures;
		
		public String toString() {
			return " Times occurred:  \t"   + attempts  + "\n"
				 + " Times guessed:   \t"   + successes + "\n"
				 + " Times failed:    \t\t" + failures;
		}
	};
	
	
	
	public static class WordMap extends HashMap<String,Status>      {}
	public static class DiffMap extends HashMap<Difficulty,WordMap> {}
	
	
	
	
	
	private final String  file    = "wordHistory.blob";
	private       DiffMap diffMap = null;
	
	
	
	
	
	public WordHistory() {
		load();
	}
	
	
	
	
	
	public DiffMap getData() {
		return diffMap;
	}
	
	
	
	
	
	/**
	 * Log an attempt and save to disk.
	 */
	public void logAttempt( Difficulty difficulty, String word, boolean succeeded ) {
		WordMap wordMap = diffMap.get( difficulty );
		Status  status  = new Status();
		
		if (wordMap.containsKey( word ))
			 status = wordMap.get( word );
		else          wordMap.put( word, status );
		
		status.attempts++;
		
		if (succeeded)
			 status.successes++;
		else status.failures ++;
		
		save();
	}
	
	
	
	
	
	private void save() {
		Storage.write( file, diffMap );
	}
	
	
	
	
	
	private void load() {
		if (Storage.exists( file ))
			 diffMap = Storage.read( file, DiffMap.class );
		else initialiseDiffMap();
	}
	
	
	
	
	
	// Ensures all difficulties are always present in the map.
	private void initialiseDiffMap() {
		diffMap = new DiffMap();
		diffMap.put( Difficulty.easy,   new WordMap() );
		diffMap.put( Difficulty.medium, new WordMap() );
		diffMap.put( Difficulty.hard,   new WordMap() );
	}
}







































