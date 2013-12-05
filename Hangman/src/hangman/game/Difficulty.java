


package hangman.game;

import hangman.util.Util;





/**
 * Codifies the three difficulty levels of the game.
 */
public enum Difficulty
{
	easy,
	medium,
	hard;
	
	
	
	
	
	public String toString() {
		return Util.capitalise( name() );
	}
}
