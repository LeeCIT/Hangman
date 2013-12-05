


package hangman.gui;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;





/**
 * Displays a unique set of characters in a vertically oriented TextArea.
 */
public class UsedLettersPanel extends OutlinePanel
{
	private JScrollPane     scrollPane;
	private JTextArea       textArea;
	private HashSet<String> letters;
	
	
	
	
	
	public UsedLettersPanel() {
		super();
		setLayout( new FlowLayout() );
		
		letters = new HashSet<String>();
		
		setupComponents();
		setupLayout();
		setupActions();
	}
	
	
	
	
	
	public void addLetter( String letter ) {
		letters.add( letter );
		renderLetters();
	}
	
	
	
	
	
	public void resetLetters() {
		letters.clear();
		renderLetters();
	}
	
	
	
	
	
	private void renderLetters() {
		String str = "";
		
		for (String letter: sortLetters())
			str += " " + letter.toUpperCase() + "\n";
			
		textArea.setText( str );
	}
	
	
	
	
	
	private ArrayList<String> sortLetters() {
		ArrayList<String> sort = new ArrayList<String>();
		
		for (String letter: letters)
			sort.add( letter );
			
		Collections.sort( sort );
		
		return sort;
	}
	
	
	
	
	
	private void setupComponents() {
		textArea   = new JTextArea();
		scrollPane = new JScrollPane( textArea );
		
		textArea.setEditable( false );
	}
	
	
	
	
	
	private void setupLayout() {
		setLayout( new MigLayout( "", "[grow,fill]", "[][grow,fill]" ) );
		add( new JLabel("Used Letters:"), "wrap" );
		add( scrollPane );
	}
	
	
	
	
	
	private void setupActions() {
		
	}
}
