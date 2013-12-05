


package hangman.gui;

import hangman.game.Difficulty;
import hangman.util.CallbackParam;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;





/**
 * Panel for picking a difficulty using radio buttons.
 */
public class DifficultyPanel extends OutlinePanel
{
	private DifficultyRadioButton diffRadioEasy;
	private DifficultyRadioButton diffRadioMedium;
	private DifficultyRadioButton diffRadioHard;
	
	private CallbackParam<Difficulty> callback;
	
	
	
	
	
	public DifficultyPanel() {
		super();
		
		setupComponents();
		setupLayout();
		setupActions();
	}
	
	
	
	
	
	public void setDifficultySelectCallback( CallbackParam<Difficulty> callback ) {
		this.callback = callback;
	}
	
	
	
	
	
	public Difficulty getDifficulty() {
		DifficultyRadioButton[] butts = {
			diffRadioEasy,
			diffRadioMedium,
			diffRadioHard
		};
		
		for (DifficultyRadioButton butt: butts)
			if (butt.isSelected())
				return butt.getDifficulty();
		
		return null;
	}
	
	
	
	
	
	public void setEnabled( boolean enabled ) {
		diffRadioEasy  .setEnabled( enabled );
		diffRadioMedium.setEnabled( enabled );
		diffRadioHard  .setEnabled( enabled );
	}
	
	
	
	
	
	private void setupComponents() {
		diffRadioEasy   = new DifficultyRadioButton( Difficulty.easy   );
		diffRadioMedium = new DifficultyRadioButton( Difficulty.medium );
		diffRadioHard   = new DifficultyRadioButton( Difficulty.hard   );
		
		ButtonGroup diffRadioGroup = new ButtonGroup();
		diffRadioGroup.add( diffRadioEasy   );
		diffRadioGroup.add( diffRadioMedium );
		diffRadioGroup.add( diffRadioHard   );
	}
	
	
	
	
	
	private void setupLayout() {
		setLayout( new MigLayout( "gap 0px", "[]", "[][][]" ) );
		
		add( new JLabel("Difficulty:"), "wrap" );
		add( diffRadioEasy,   "wrap" );
		add( diffRadioMedium, "wrap" );
		add( diffRadioHard,   "wrap" );
	}
	
	
	
	
	
	private void setupActions() {
		ActionListener difficultyListener = new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				if (callback != null)
					callback.execute( ((DifficultyRadioButton) e.getSource()).getDifficulty() );
			}
		};
		
		diffRadioEasy  .addActionListener( difficultyListener );
		diffRadioMedium.addActionListener( difficultyListener );
		diffRadioHard  .addActionListener( difficultyListener );
	}
}































