


package hangman.gui;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;





/**
 * Panel which is automatically given an outline border on creation.
 */
public class OutlinePanel extends JPanel
{
	public OutlinePanel() {
		super( new GridLayout(1,1) );
		setBorder( BorderFactory.createLineBorder( getForeground() ) );
	}
}
