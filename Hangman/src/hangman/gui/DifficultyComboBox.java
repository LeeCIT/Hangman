


package hangman.gui;

import hangman.game.Difficulty;
import javax.swing.*;





/**
 * ComboBox specialised for difficulty selection.
 */
public class DifficultyComboBox extends JComboBox<Difficulty>
{
	public DifficultyComboBox() {
		super( Difficulty.values() );
	}
	
	
	
	
	
	public Difficulty getSelectedDifficulty() {
		return (Difficulty) super.getSelectedItem();
	}
}































