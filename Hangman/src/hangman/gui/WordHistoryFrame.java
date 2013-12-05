


package hangman.gui;

import hangman.game.Difficulty;
import hangman.game.WordHistory;
import hangman.util.Callback;
import java.awt.Dimension;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Set;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.miginfocom.swing.MigLayout;





/**
 * Frame which displays game statistics. 
 */
public class WordHistoryFrame extends JFrame
{
	private DifficultyComboBox  difficultyBox;
	private JList<String> 	    wordList;
	private JLabel              summaryLabel;
	private JTextArea           infoArea;
	private Callback            onClose;
	private WordHistory.DiffMap diffMap;
	
	
	
	
	
	public WordHistoryFrame( WordHistory.DiffMap diffMap, Difficulty initialSelection, Callback onClose ) {
		super( "Hangman - Word History" );
		
		setupComponents( initialSelection );
		setupLayout();
		setupActions();
		setHistoryData( diffMap );
		
		this.onClose = onClose;
		
		setSize       ( new Dimension( 360, 420 ) );
		setMinimumSize( new Dimension( 290, 320 ) );
		
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );
	}
	
	
	
	
	
	public void setHistoryData( WordHistory.DiffMap diffMap ) {
		this.diffMap = diffMap;
		populateWordsFromSelectedDifficulty();
		updateSummary();
	}
	
	
	
	
	
	private void setupComponents( Difficulty diff ) {
		difficultyBox = new DifficultyComboBox();
		difficultyBox.setSelectedItem( (diff!=null) ? diff : Difficulty.easy );
		
		summaryLabel = new JLabel();
		wordList     = new JList<String>();
		
		infoArea = new JTextArea();
		infoArea.setEditable( false );
	}
	
	
	
	
	
	private void setupLayout() {
		setContentPane( new OutlinePanel() );
		setLayout( new MigLayout( "", "[grow][grow]", "[][][grow]" ) );
		
		add( difficultyBox, 		     "growx" );
		add( summaryLabel,				 "alignx 100%, wrap" );
		add( new JLabel("Words:"),	     ""     );
		add( new JLabel("Word stats:"),  "wrap" );
		add( wordList,				     "grow" );
		add( new JScrollPane(infoArea),  "grow" );
	}
	
	
	
	
	
	private void setupActions() {
		difficultyBox.addItemListener( new ItemListener() {
			public void itemStateChanged( ItemEvent ev ) {
				populateWordsFromSelectedDifficulty();
			}
		});
		
		
		wordList.addListSelectionListener( new ListSelectionListener() {
			public void valueChanged( ListSelectionEvent ev) {
				populateInfoFromSelectedWord();
			}
		});
		
		
		addWindowListener( new WindowAdapter() {
			public void windowClosing( WindowEvent ev ) {
				onClose.execute();
			}
		});
	}
	
	
	
	
	
	private void populateWordsFromSelectedDifficulty() {
		Difficulty diff = difficultyBox.getSelectedDifficulty();
		
		Set<String> wordSet   = diffMap.get(diff).keySet();
		String[]    wordArray = wordSet.toArray( new String[1] );
		Arrays.sort( wordArray );
		wordList.setListData( wordArray );
	}
	
	
	
	
	
	private void populateInfoFromSelectedWord() {
		WordHistory.WordMap wordMap = diffMap.get( difficultyBox.getSelectedDifficulty() );
		WordHistory.Status  status  = wordMap.get( wordList.getSelectedValue() );
		
		if (status != null)
			 infoArea.setText( "" + status );
		else infoArea.setText( "" );
	}
	
	
	
	
	
	private void updateSummary() {
		double totalWords   = 0;
		double totalCorrect = 0;
		
		for (Difficulty diff: diffMap.keySet())
			for (WordHistory.Status status: diffMap.get(diff).values()) {
				totalWords   += status.attempts;
				totalCorrect += status.successes;
			}
		
		double percent = (totalWords != 0.0)                 ?
						 (totalCorrect / totalWords) * 100.0 :
						 0.0                                 ;
		
		DecimalFormat format = new DecimalFormat( "#.#" );
		String 		  p 	 = format.format( percent );
		
		summaryLabel.setText( "Your overall success rate: " + p + "%" );
	}
}
















































