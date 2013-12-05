


package hangman.gui;

import hangman.core.GameCreator;
import javax.swing.*;





/**
 * The main frame.
 * @see GameCreator
 */
public class HangmanFrame extends JFrame
{
	public HangmanFrame() {
		super( "Hangman by Lee Coakley" );
		
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		setVisible( true );
	}
}