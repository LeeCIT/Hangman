


package hangman.gui;

import hangman.game.Difficulty;
import hangman.game.WordHistory;
import hangman.util.Callback;
import hangman.util.CallbackParam;
import javax.swing.*;
import java.awt.event.*;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import net.miginfocom.swing.MigLayout;





/**
 * The game GUI panel.
 */
public class GameGUI extends OutlinePanel
{
	private HangmanPanel     hangmanPanel;
	private WordPanel        wordPanel;
	private JButton          buttNewGame;
	private JButton          buttHistory;
	private DifficultyPanel  difficultyPanel;
	private FeedbackField    feedbackField;
	private UsedLettersPanel usedLettersPanel;
	private WordHistoryFrame historyFrame;
	private Point			 historyPosLast;
	
	private CallbackParam<String>     onLetterGuess;
	private CallbackParam<Difficulty> onDifficultySelect;
	private Callback 				  onNewGamePress;
	private Callback 				  onHistoryPress;
	
	
	
	
	
	public GameGUI() {
		super();
		setupComponents();
		setupLayout();
		setupActions();
	}
	
	
	
	
	
	/**
	 * Give player textual feedback with a flashy indicator effect.
	 */
	public void setFeedbackText( String str ) {
		feedbackField.setText( str );
	}
	
	
	
	
	
	/**
	 * Show the word statistics window.
	 */
	public void showWordHistoryFrame( WordHistory.DiffMap map ) {
		createHistoryFrame( map );
	}
	
	
	
	
	
	/**
	 * Set the displayed game string.
	 */
	public void setWordDisplay( String word ) {
		wordPanel.setWordString( word );
	}
	
	
	
	
	
	/**
	 * Enable/disable the new game button.
	 */
	public void setNewGameButtonEnabled( boolean state ) {
		buttNewGame.setEnabled( state );
	}
	
	
	
	
	
	/**
	 * Enable/disable the difficulty selector.
	 */
	public void setDifficultySelectorEnabled( boolean state ) {
		difficultyPanel.setEnabled( state );
	}
	
	
	
	
	
	/**
	 * Reset the hangman and used letter displays.
	 */
	public void resetGameDisplays() {
		hangmanPanel.stateReset();
		hangmanPanel.setDrawingEnabled( true );
		usedLettersPanel.resetLetters();
	}
	
	
	
	
	
	/**
	 * Add a used letter to the displayed list.
	 */
	public void addUsedLetter( String letter ) {
		usedLettersPanel.addLetter( letter );
	}
	
	
	
	
	
	/**
	 * Add another part to the hangman drawing.
	 */
	public void addHangmanPart() {
		hangmanPanel.stateAdvance();
	}
	
	
	
	
	
	/**
	 * Get the number of times another part can be added to the hangman before he dies.
	 */
	public int getGuessesLeft() {
		return hangmanPanel.getStatesRemaining();
	}
	
	
	
	
	
	/**
	 * Update the history frame with new data.
	 * Doesn't matter whether it exists or not.
	 */
	public void updateHistoryFrame( WordHistory.DiffMap data ) {
		if (historyFrame != null)
			historyFrame.setHistoryData( data );
	}
	
	
	
	
	
	/**
	 * If the hangman stickman's head is visible, he will smile.
	 */
	public void makeHangmanSmile() {
		hangmanPanel.smile();
	}
	
	
	
	
	
	/**
	 * What to do when the player types a letter.
	 */
	public void setLetterGuessCallback( CallbackParam<String> onLetterGuess ) {
		this.onLetterGuess = onLetterGuess;
	}
	
	
	
	
	
	/**
	 * What to do when the player selects a difficulty level.
	 */
	public void setDifficultySelectCallback( CallbackParam<Difficulty> onDifficultySelected ) {
		this.onDifficultySelect = onDifficultySelected;
	}
	
	
	
	
	
	/**
	 * What to do when the player presses "New Game".
	 */
	public void setNewGamePressCallback( Callback onNewGamePressed ) {
		this.onNewGamePress = onNewGamePressed;
	}
	
	
	
	
	
	/**
	 * What to do when the player presses "Show History"
	 */
	public void setHistoryPressCallback( Callback onHistoryPressed ) {
		this.onHistoryPress = onHistoryPressed;
	}
	
	
	
	
	
	private void setupComponents() {
		hangmanPanel    = new HangmanPanel();
		wordPanel       = new WordPanel();
		difficultyPanel = new DifficultyPanel();
		
		buttNewGame = new JButton( "New Game" );
		buttNewGame.setEnabled( false );
		
		buttHistory = new JButton( "Show History" );
		
		feedbackField = new FeedbackField();
		feedbackField.setEditable( false );
		
		usedLettersPanel = new UsedLettersPanel();
	}
	
	
	
	
	
	private void setupLayout() {
		setLayout( new MigLayout( "", "[grow][]", "[][][][grow][]" ) );
		
		add( hangmanPanel,     "grow,  spany 4, wrap" );
		add( wordPanel,        "growx, hmin 72, cell 0 4" );
		add( usedLettersPanel, "grow,  wmin 64, cell 1 3, spany 2" );
		add( feedbackField,    "growx, spanx 2, cell 0 5" );
		
		add( difficultyPanel,  "growx, cell 1 0" );
		add( buttNewGame,      "growx, cell 1 1" );
		add( buttHistory,      "growx, cell 1 2" );
	}
	
	
	
	
	
	private void setupActions() {
		difficultyPanel.setDifficultySelectCallback( new CallbackParam<Difficulty>() {
			public void execute( Difficulty diff ) {
				onDifficultySelect.execute( diff );
			}
		});
		
		
		buttNewGame.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				onNewGamePress.execute();
			}
		});
		
		
		buttHistory.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				onHistoryPress.execute();
			}
		});
		
		
		KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		kfm.addKeyEventDispatcher( new KeyEventDispatcher() {
			public boolean dispatchKeyEvent( KeyEvent event ) {
				if (event.getID() == KeyEvent.KEY_TYPED)
					onLetterGuess.execute( "" + event.getKeyChar() );
				return false; // Allow normal event propogation
			}
		});
	}
	
	
	
	
	
	private void createHistoryFrame( WordHistory.DiffMap map ) {
		buttHistory.setEnabled( false );
		
		Callback onHistoryClose = new Callback() {
			public void execute() {
				buttHistory.setEnabled( true );
				historyPosLast = historyFrame.getLocation();
				historyFrame   = null;
			}
		};
		
		historyFrame = new WordHistoryFrame(
			map,
			difficultyPanel.getDifficulty(),
			onHistoryClose
		);
		
		if (historyPosLast != null)
			 historyFrame.setLocation( historyPosLast );
		else historyFrame.setLocationRelativeTo( buttHistory );
		
		historyFrame.setVisible( true );
	}
}





























