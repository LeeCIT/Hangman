


package hangman.game;

import hangman.gui.GameGUI;
import hangman.util.Callback;
import hangman.util.CallbackParam;
import hangman.util.Evaluator;





/**
 * Controls interaction between GameLogic and GameGUI.
 * @see GameLogic, GameGUI
 */
public class GameController
{
	private GameLogic game;
	private GameGUI   gui;
	
	
	
	
	
	public GameController( GameLogic game, GameGUI gui ) {
		this.game = game;
		this.gui  = gui;
		
		setupGuiCallbacks();
		setupGameCallbacks();
		onStartup();
	}
	
	
	
	
	
	private void setupGuiCallbacks() {
		gui.setDifficultySelectCallback( new CallbackParam<Difficulty>() {
			public void execute( Difficulty difficulty ) {
				onDifficultySelect( difficulty );
			}
		});
		
		
		gui.setHistoryPressCallback( new Callback() {
			public void execute() {
				onHistoryPress();
			}
		});
		
		
		gui.setNewGamePressCallback( new Callback() {
			public void execute() {
				onNewGamePress();
			}
		});
		
		
		gui.setLetterGuessCallback( new CallbackParam<String>() {
			public void execute( String letter ) {
				onLetterGuess( letter );
			}
		});
	}
	
	
	
	
	
	private void setupGameCallbacks() {
		game.setGoodGuessCallback( new Callback() {
			public void execute( ) {
				onGoodGuess();
			}
		});		
		
		
		game.setBadGuessCallback( new Callback() {
			public void execute() {
				onBadGuess();
			}
		});
		
		
		game.setLetterUsedCallback( new CallbackParam<String>() {
			public void execute( String letter ) {
				onLetterUsed( letter );
			}
		});
		
		
		game.setWordCompleteCallback( new Callback() {
			public void execute() {
				onGameEndGood();
			}
		});
		
		
		game.setGameOverCallback( new Callback() {
			public void execute() {
				onGameEndBad();
			}
		});
		
		
		game.setRedundantLetterCallback( new CallbackParam<String>() {
			public void execute( String letter ) {
				onLetterRedundant( letter );
			}
		});
		
		
		game.setHasGuessesLeftEvaluator( new Evaluator<Boolean>() {
			public Boolean evaluate() {
				return gui.getGuessesLeft() > 0;
			}
		});
	}
	
	
	
	
	
	private void onStartup() {
		gui.setFeedbackText( "Select a difficulty to begin a new game." );
	}
	
	
	
	
	
	private void onDifficultySelect( Difficulty diff ) {
		game.setDifficulty( diff );
		
		if ( ! game.isInProgress())
			gui.setNewGameButtonEnabled( true );
	}
	
	
	
	
	
	private void onNewGamePress() {
		game.startNewGame();
		
		gui.resetGameDisplays();
		gui.setWordDisplay( game.getWordFormatted() );
		gui.setNewGameButtonEnabled     ( false );
		gui.setDifficultySelectorEnabled( false );
		gui.setFeedbackText( "Use your keyboard to guess letters." );
	}
	
	
	
	
	
	private void onLetterGuess( String letter ) {
		game.doGuess( letter );
	}
	
	
	
	
	
	private void onGoodGuess() {
		gui.setWordDisplay( game.getWordFormatted() ); 
		gui.setFeedbackText( "Good guess!" );
	}
	
	
	
	
	
	private void onBadGuess() {
		gui.addHangmanPart();
		gui.setFeedbackText( "Bad guess!  " + gui.getGuessesLeft() + " left." );
	}





	private void onLetterUsed( String letter ) {
		gui.addUsedLetter( letter );
	}
	
	
	
	
	
	private void onLetterRedundant( String letter ) {
		gui.setFeedbackText( letter.toUpperCase() + " is already used." );
	}
	
	
	
	
	
	private void onHistoryPress() {
		gui.showWordHistoryFrame( game.getWordHistoryData() );
	}
	
	
	
	
	
	private void onGameEnd() {
		gui.setNewGameButtonEnabled     ( true );
		gui.setDifficultySelectorEnabled( true );
		gui.updateHistoryFrame( game.getWordHistoryData() );
	}
	
	
	
	
	
	private void onGameEndGood() {
		onGameEnd();
		gui.makeHangmanSmile();
		gui.setFeedbackText( "Alright! You got it! :D" );
		
	}
	
	
	
	
	
	private void onGameEndBad() {
		onGameEnd();
		gui.setFeedbackText( "Game over. :(  The word was '" + game.getWord() + "'." );
	}
}

