


package hangman.gui;

import hangman.core.Time;
import hangman.util.Geo;
import hangman.util.Region;
import hangman.util.Vec2;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JTextField;





/**
 * Field which animates when setText() is called.
 */
public class FeedbackField extends JTextField
{
	private final double  flickerDuration = 45;
    private       double  refTime;
	
	
	
	
	
	public void setText( String t ) {
		super.setText( t );
		
		refTime = Time.now;
	}
	
	
	
	
	
	public void paint( Graphics g ) {
		super.paint( g );
		
		Region r       = new Region( this );
		double frac    = 1.0 - Geo.boxStep( Time.now, refTime, refTime+flickerDuration );
		double invFrac = 1.0 - frac;
		double radius  = r.getBiggest() * 0.25 * Geo.sqr(frac);
			   radius  = Geo.lerp( radius, r.getSmallest() * 0.5, invFrac );
		Vec2   centre  = r.getCentre();
		Vec2   posA    = new Vec2( r.tl.x, centre.y );
		Vec2   posB    = new Vec2( r.br.x, centre.y );
		Vec2   pos     = Geo.lerp( posA, posB, invFrac );
		
		if (frac > 0.0) {
			Gfx.setIdentity( g );
			Gfx.pushColorAndSet( g, Geo.lerp( Color.WHITE, getBackground(), invFrac ) );
			Gfx.drawCircle( g, pos, radius, true );
			Gfx.popColor( g );
		}
	}
}
