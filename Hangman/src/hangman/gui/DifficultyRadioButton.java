


package hangman.gui;

import hangman.game.Difficulty;
import javax.swing.JRadioButton;




/**
 * Radio button specialised for difficulty selection.
 */
public class DifficultyRadioButton extends JRadioButton
{
	private Difficulty difficulty;
	
	
	
	
	
	public DifficultyRadioButton( Difficulty diff ) {
		super( diff.toString() );
		this.difficulty = diff;
	}
	
	
	
	
	
	public Difficulty getDifficulty() {
		return difficulty;
	}
}
