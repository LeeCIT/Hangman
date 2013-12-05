


package hangman.gui;

import hangman.util.Vec2;
import javax.swing.UIManager;





/**
 * Static GUI utility class. 
 */
public class GUI
{
	public static Vec2 baseOffset = new Vec2( 0 );
	
	
	
	
	
	public static void setNativeStyle() {
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		}
		catch (Exception ex) {
			System.out.println( ex );
		}	
	}
}
