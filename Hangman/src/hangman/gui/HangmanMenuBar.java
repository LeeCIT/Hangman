


package hangman.gui;

import hangman.util.Callback;
import java.awt.event.*;
import javax.swing.*;





/**
 * Implements the frame menu.  Communicates via Callback.
 */
public class HangmanMenuBar extends JMenuBar
{
	private JMenu	  menuFile;
	private JMenuItem itemExit;
	
	private JMenu     menuHelp;
	private JMenuItem itemAbout;
	
	private Callback onExit;
	private Callback onAbout;
	
	
	
	
	
	public HangmanMenuBar() {
		super();
		setupComponents();
		setupActions();
	}
	
	
	
	
	
	public void setExitCallback( Callback onExit ) {
		this.onExit = onExit;
	}
	
	
	
	
	
	public void setAboutCallback( Callback onAbout ) {
		this.onAbout = onAbout;
	}
	
	
	


	private void setupComponents() {
		createFileMenu();
		createHelpMenu();
		
		add( menuFile );
		add( menuHelp );
	}
	
	
	
	
	
	private void setupActions() {
		itemExit.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				onExit.execute();
			}
		});
		
		
		itemAbout.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				onAbout.execute();
			}
		});
	}
	
	
	
	
	
	private void createFileMenu() {
		itemExit = new JMenuItem( "Exit" );

		menuFile = new JMenu( "File" );
		menuFile.add( itemExit );
		
		itemExit.setMnemonic( KeyEvent.VK_X );
		menuFile.setMnemonic( KeyEvent.VK_F );
	}
	
	
	
	
	
	private void createHelpMenu() {
		itemAbout = new JMenuItem( "About..." );
		
		menuHelp = new JMenu( "Help" );
		menuHelp.add( itemAbout );
		
		menuHelp .setMnemonic( KeyEvent.VK_H );
		itemAbout.setMnemonic( KeyEvent.VK_A );
	}
}

