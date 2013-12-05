


package hangman.game;

import hangman.util.Callback;
import hangman.util.CallbackParam;
import hangman.util.Evaluator;
import java.util.ArrayList;





/**
 * Implements the hangman game logic.
 * Communication with the GUI is handled via callbacks.
 * @see Callback
 */
public class GameLogic
{
	private WordDatabase wordDatabase;
	private WordHistory  wordHistory;
	
	private boolean 		  gameActive;
	private Difficulty        difficulty;
	private Difficulty        difficultyNext;
	private String	          word;
	private ArrayList<String> displayedLetters;
	private ArrayList<String> usedLetters;
	
	private CallbackParam<String> onLetterUsed;
	private CallbackParam<String> onRedundantLetter;
	private Callback              onGoodGuess;
	private Callback              onBadGuess;
	private Callback              onWordComplete;
	private Callback              onGameOver;
	private Evaluator<Boolean>    hasGuessesLeft; 
	
	
	
	
	
	public GameLogic() {
		wordDatabase = new WordDatabase();
		wordHistory  = new WordHistory();
	}
	
	
	
	
	
	public void setGoodGuessCallback( Callback onGoodGuess ) {
		this.onGoodGuess = onGoodGuess;
	}	
	
	
	
	
	
	public void setBadGuessCallback( Callback onBadGuess ) {
		this.onBadGuess = onBadGuess;
	}
	
	
	
	
	
	public void setWordCompleteCallback( Callback onWordComplete ) {
		this.onWordComplete = onWordComplete;
	}
	
	
	
	
	
	public void setGameOverCallback( Callback onGameOver ) {
		this.onGameOver = onGameOver;
	}
	
	
	
	
	
	public void setLetterUsedCallback( CallbackParam<String> onLetterUsed ) {
		this.onLetterUsed = onLetterUsed;
	}
	
	
	
	
	
	public void setRedundantLetterCallback( CallbackParam<String> onRedundantLetter ) {
		this.onRedundantLetter = onRedundantLetter;
	}
	
	
	
	
	
	public void setHasGuessesLeftEvaluator( Evaluator<Boolean> hasGuessesLeft ) {
		this.hasGuessesLeft = hasGuessesLeft;
	}
	
	
	
	
	
	/**
	 * Get word for in-game display.  Hides unrevealed letters and so on.
	 */
	public String getWordFormatted() {
		return renderFormattedWordString();
	}
	
	
	
	
	
	/**
	 * Get word history information for display.
	 * Do NOT modify it.  (I wish Java had const...)
	 */
	public WordHistory.DiffMap getWordHistoryData() {
		return wordHistory.getData();
	}
	
	
	
	
	
	/**
	 * Get word verbatim.
	 */
	public String getWord() {
		return word;
	}
	
	
	
	
	
	/**
	 * Check whether the game logic is fully initialised.
	 * Doesn't check callbacks - which must ALL be set.
	 */
	public boolean isReady() {
		return word       != null
			&& difficulty != null
		    && gameActive;
	}
	
	
	
	
	
	public boolean isInProgress() {
		return gameActive;
	}
	
	
	
	
	
	/**
	 * Set the game difficulty.  Doesn't take effect until you start a new game.
	 */
	public void setDifficulty( Difficulty difficulty ) {
		this.difficultyNext = difficulty;
	}
	
	
	
	
	
	public void startNewGame() {
		gameActive  = true;
		difficulty  = difficultyNext;
		usedLetters = new ArrayList<String>();
		setWord( wordDatabase.getRandom( difficulty ) );
	}
	
	
	
	
	
	/**
	 * Make a guess.  Returns whether the letter is both valid and unused.
	 */
	public void doGuess( String letter ) {
		if ( ! isReady()
		||   ! letter.matches( "[A-Z]|[a-z]" ))
			return;
			
		
		if (usedLetters.contains( letter )) {
			onRedundantLetter.execute( letter );
			return;
		}
		
		
		boolean isGoodGuess = guessLetterAndReveal( letter );
		boolean isComplete  = ! getWordFormatted().contains( "_" );
		
		
		if (isGoodGuess) {
			 onGoodGuess.execute();
			 
			 if (isComplete)
				 endGame( true );
		}
		else {
			onBadGuess.execute();
			
			if ( ! hasGuessesLeft.evaluate())
				endGame( false );
		}
	}
	
	
	
	
	
	private void endGame( boolean succeeded ) {
		wordHistory.logAttempt( difficulty, word, succeeded );
		gameActive = false;
		
		if (succeeded)
			 onWordComplete.execute();
		else onGameOver    .execute();
	}
	
	
	
	
	
	private boolean guessLetterAndReveal( String letterIn ) {
		String  letter   = letterIn.toLowerCase();
		boolean revealed = false;
		
		usedLetters.add( letter );
		
		for (int i=0; i<word.length(); i++) {
			String cur = "" + word.charAt( i );
			
			if (letter.equals(cur)) {
				displayedLetters.set( i, cur );
				revealed = true;
			}
		}
		
		onLetterUsed.execute( letter );
		
		return revealed;
	}
	
	
	
	
	
	private void setWord( String word ) {
		this.word             = word.toLowerCase();
		this.displayedLetters = new ArrayList<String>();
		
		for (int i=0; i<word.length(); i++)
			displayedLetters.add( "_" );
	}
	
	
	
	
	
	private String renderFormattedWordString() {
		String str = "";
		
		if (displayedLetters != null) {
			if (difficulty == Difficulty.easy)
				guessLetterAndReveal( "" + word.charAt( word.length()-1 ) );
			
			if (difficulty == Difficulty.easy || difficulty == Difficulty.medium)
				guessLetterAndReveal( "" + word.charAt( 0 ) );
			
			for (String letter: displayedLetters)
				str += letter;
		}
		
		str = str.replaceAll( "_", "_ " ).replaceFirst( " $", "" );
		
		if ( ! str.contains( "_" ))
			str = "[ " + str + " ]";
		
		return str;
	}
}











































