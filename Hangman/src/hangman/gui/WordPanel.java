


package hangman.gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;





/**
 * Panel which displays the current word state in Hangman. 
 */
public class WordPanel extends OutlinePanel
{
	private String word = "";
	
	
	
	
	
	public void setWordString( String str ) {
		this.word = str;
	}
	
	
	
	
	
	public void paint( Graphics g ) {
		super.paint( g );
		
		Font        fontPrev = g.getFont();
		Font        font     = new Font( "Arial", Font.BOLD, 48 );
		Rectangle2D rect     = g.getFontMetrics(font).getStringBounds( word, g );
		int         width    = (int) rect.getWidth ();
		int         height   = (int) rect.getHeight();
		int         xpos     = (getWidth ()/2) - (width /2);
		int         ypos	 = (getHeight()/2) + (height/2) - (height/4);
		
		Gfx.setAntialiasingState( g, true );
		
		g.setFont( font );
		g.drawString( word, xpos, ypos );
		g.setFont( fontPrev );
	}
}


































