


package hangman.core;

import hangman.game.*;
import hangman.gui.*;
import hangman.util.*;
import java.awt.*;
import javax.swing.JOptionPane;





/**
 * Creates a background thread, GameGUI and GameLogic. 
 */
public class GameCreator
{
	public static void createInFrame() {
		HangmanFrame frame = new HangmanFrame();
		setupMenuBar( frame );
		createGameInContainer( frame );
		frame.pack();
		setGUIBaseOffset( frame );
	}
	
	
	
	
	
	// TODO HACK.  I should have just used component-local coordinates.
	// Maybe I'll patch it later.
	private static void setGUIBaseOffset( HangmanFrame frame ) {
		Rectangle rect = frame.getContentPane().getBounds();
		GUI.baseOffset = new Vec2( rect.getX(), rect.getY() );
	}
	
	
	
	
	
	private static void setupMenuBar( final HangmanFrame frame ) {
		HangmanMenuBar hangmanMenuBar = new HangmanMenuBar();
		frame.setJMenuBar( hangmanMenuBar );
		
		
		hangmanMenuBar.setExitCallback( new Callback() {
			public void execute() {
				frame.dispose();
				System.exit( 0 );
			}
		});
		
		
		hangmanMenuBar.setAboutCallback( new Callback() {
			public void execute() {
				JOptionPane.showMessageDialog( frame, "Created by Lee Coakley" );
			}
		});
	}
	
	
	
	
	
	/**
	 * Create the whole game in the specified GUI container.
	 * @param container Any Container derived instance, such as a JFrame or JApplet.
	 */
	private static void createGameInContainer( final Container container ) {
		final long redrawInterval = 1000 / 60;
		
		final Dimension      size = new Dimension( 656, 720 );
		final GameLogic      game = new GameLogic();
		final GameGUI        gui  = new GameGUI();
		final GameController ctrl = new GameController( game, gui );
		
		container.add( gui );
		container.setMinimumSize( size );
		
		new CallbackThread( redrawInterval, new Callback() {
			public void execute() {
				container.repaint();
				Time.now++;
			}
		});
	}
}











































