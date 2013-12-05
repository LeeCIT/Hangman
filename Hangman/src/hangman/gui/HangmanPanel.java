


package hangman.gui;

import hangman.core.Time;
import hangman.util.Callback;
import hangman.util.Geo;
import hangman.util.Region;
import hangman.util.Vec2;
import java.awt.*;
import java.util.ArrayList;



/**
 * Draws the hangman graphics.  Scalable and animated.
 */
public class HangmanPanel extends OutlinePanel
{
	private int 			 	partState;
	private ArrayList<Callback> drawComs;
	private boolean             smileState;
	
	private boolean drawingEnabled;
	
	private Graphics g;
	
	private final double clearAnimDuration = 90;
    private       double clearAnimStartTime;
    
	private double lineWidth;
	private double deathFrac;
	
	private Vec2 baseLeft;
	private Vec2 baseRight;
	
	private Vec2 mastBottom;
	private Vec2 mastTop;
	
	private Vec2 upperLeft;
	private Vec2 upperRight;
	
	private Vec2 upperButtressTop;
	private Vec2 upperButtressBottom;
	
	private Vec2 lowerButtressTop;
	private Vec2 lowerButtressBottom;
	
	private Vec2 attachLeft;
	private Vec2 attachRight;
	private Vec2 attachBottom;
	
	private Vec2 ropeTop;
	private Vec2 ropeBottom;
	
	private double stickHeadRadius;
	private Vec2   stickHeadPos;
	private Vec2   stickHipPos;
	private Vec2   stickShoulderPos;
	private Vec2   stickArmPosA;
	private Vec2   stickArmPosB;
	private Vec2   stickLegPosA;
	private Vec2   stickLegPosB;
	
	private Vec2   smileLeft; 
	private Vec2   smileRight;
	private Vec2   smileC1;
	private Vec2   smileC2;
	
	private Vec2   faceEyePosA;
	private Vec2   faceEyePosB;
	
	
	
	
	
	public HangmanPanel() {
		super();
		setupDrawSequence();
		stateReset();
	}
	
	
	
	
	
	public void paint( Graphics g ) {
		super.paint( g );
		drawHangMan( g );
	}
	
	
	
	
	
	public void stateAdvance() {
		++partState;
	}
	
	
	
	
	
	public void stateReset() {
		partState          = -1;
		deathFrac          =  0.0;
		smileState         = false;
		clearAnimStartTime = Time.now;
	}
	
	
	
	
	
	/**
	 * Yeah!  You saved him!
	 */
	public void smile() {
		smileState = true;
	}
	
	
	
	
	
	public int getStatesRemaining() {
		return Math.max( 0, getStateCount() - partState );
	}
	
	
	
	
	
	public void setDrawingEnabled( boolean drawingEnabled ) {
		this.drawingEnabled = drawingEnabled;
	}
	
	
	
	
	private boolean isManDead() {
		return partState >= getStateCount();
	}
	
	
	
	
	
	private int getStateCount() {
		return drawComs.size();
	}
	
	
	
	
	
	private void drawHangMan( final Graphics graphics ) {
		this.g = graphics;
		
		if (drawingEnabled) {
			drawInit();
			drawFloor();
			drawConditionalComponents();
		}
	}
	
	
	
	
	
	private void drawInit() {
		Gfx.setIdentity( g );
		Gfx.setAntialiasingState( g, true );
		computeDrawVars();
	}
	
	
	
	
	
	private void drawConditionalComponents() {
		for (int i=0; i<=partState&&i<getStateCount(); i++)
			drawComs.get(i).execute();
		
		if (smileState 
		&& getStatesRemaining() <= 5
		&& !isManDead())
			drawSmile();
		
		drawClearAnimation();
	}
	
	
	
	
	
	private void drawFloor() {
		Gfx.drawThickRoundedLine( g, baseLeft, baseRight, lineWidth, true );
	}
	
	
	
	
	
	private void drawSmile() {
		double width = lineWidth / 5.0;
		Gfx.drawBezierCubic( g, smileLeft, smileRight, smileC1, smileC2, width );
	}
	
	
	
	
	
	private void drawClearAnimation() {
		double frac       = Geo.boxStep( Time.now, clearAnimStartTime, clearAnimStartTime + clearAnimDuration );
		double yThresh    = 0.30;
		double yState     = Geo.boxStep( frac, 0.0,   yThresh );
		double xState     = Geo.boxStep( frac, yThresh, 1.0   );
		Vec2   centre     = Geo.getCentre( baseLeft, baseRight );
		Region region     = new Region( this );
		Vec2   sizeBase   = new Vec2( region.getSize().x, region.getSize().y * 2 );
		Vec2   sizeEnd    = new Vec2( Geo.distance(baseLeft,baseRight), lineWidth );
		double height     = Geo.herp( sizeBase.y, sizeEnd.y, yState );
		double width      = Geo.herp( sizeBase.x, sizeEnd.x, xState );
		Color  col        = Geo.lerp( getBackground(), getForeground(), frac );
		Vec2   size       = new Vec2( width, height );
		
		if (frac < 1.0) {
			Gfx.setIdentity( g );
			Gfx.pushColorAndSet( g, col );
			Gfx.drawOrientedRect( g, centre, size, 0, true );
			Gfx.popColor( g );
		}
	}
	
	
	
	
	
	private void computeDrawVars() {
		if (isManDead()) {
			deathFrac += 1.0 / 50.0;
			deathFrac = Geo.clamp( deathFrac, 0, 1 );
		}
		
		Region region = new Region( this );
		
		Vec2   tl = Geo.lerp( region.tl, region.getCentre(), 0.1 );
		Vec2   br = Geo.lerp( region.br, region.getCentre(), 0.1 );
			   
	    double smallest  = region.getSmallest();
			   lineWidth = 15.0 * Geo.unlerp( smallest, 256, 540 );
			   
			   baseLeft  = new Vec2( tl.x, br.y );
			   baseRight = br.copy();
			   
			   mastBottom = Geo.lerp( baseLeft, baseRight, 0.15 );
			   mastTop    = new Vec2( mastBottom.x, tl.y );
			   
			   upperLeft  = mastTop.copy();
			   upperRight = new Vec2( Geo.lerp(mastTop.x,br.x,0.85), mastTop.y );
		
			   upperButtressTop    = Geo.lerp( upperLeft, upperRight, 0.33 );
		double upperButtressDist   = Geo.distance( upperLeft, upperButtressTop ); 
			   upperButtressBottom = upperLeft.add( new Vec2(0,upperButtressDist) );
		
		       lowerButtressTop    = Geo.lerp( mastTop,    upperButtressBottom, 0.5 );
		       lowerButtressBottom = Geo.lerp( mastBottom, baseLeft,			0.5 );
		       
		       attachLeft   = Geo.lerp( upperLeft, upperRight, 0.80 );
		       attachRight  = Geo.lerp( upperLeft, upperRight, 0.90 );
		Vec2   attachLevel  = Geo.lerp( mastTop,   mastBottom, 0.10 );
		       attachBottom = new Vec2( Geo.getCentre(attachLeft,attachRight).x, attachLevel.y );
		
		double ropeLength = (Geo.distance(mastBottom,mastTop) * 0.25) + ropeLerp(0,lineWidth*5,deathFrac);
		double ropeSwing  = Geo.lerp( 7.0, 1.0, deathFrac );
		double ropeAngle  = Geo.sineSync( Time.now, 240.0, 270-ropeSwing, 270+ropeSwing );
			   ropeTop    = attachBottom.copy();
			   ropeBottom = ropeTop.add( Geo.lenDir( ropeLength, ropeAngle ) );
		
		       stickHeadRadius     = Geo.distance( mastTop, mastBottom ) * 0.05;
		double stickAngle          = Geo.lerp( ropeAngle, 270, 0.75 );
		double stickArmAngleOffset = Geo.lerp( 45, 12, deathFrac );
		double stickLegAngleOffset = Geo.lerp( 30,  6, deathFrac );
		double stickArmLength      = stickHeadRadius * 2.5;
		double stickLegLength      = stickHeadRadius * 3.5;
			   stickHeadPos        = ropeBottom.copy();
			   stickHipPos         = stickHeadPos.add( Geo.lenDir( stickHeadRadius*4.2, stickAngle ) );
			   stickShoulderPos    = Geo.lerp( stickHeadPos, stickHipPos, 0.25 );
			   stickArmPosA        = stickShoulderPos.add( Geo.lenDir(stickArmLength,stickAngle+stickArmAngleOffset) );
			   stickArmPosB        = stickShoulderPos.add( Geo.lenDir(stickArmLength,stickAngle-stickArmAngleOffset) );
			   stickLegPosA        = stickHipPos     .add( Geo.lenDir(stickLegLength,stickAngle+stickLegAngleOffset) );
			   stickLegPosB        = stickHipPos     .add( Geo.lenDir(stickLegLength,stickAngle-stickLegAngleOffset) );
			   
			   smileLeft  = stickHeadPos.add( Geo.lenDir( stickHeadRadius*0.35, stickAngle-90 ) );
			   smileRight = stickHeadPos.add( Geo.lenDir( stickHeadRadius*0.35, stickAngle+90 ) );
			   smileC1    = stickHeadPos.add( Geo.lenDir( stickHeadRadius*0.75, stickAngle-45 ) );
			   smileC2    = stickHeadPos.add( Geo.lenDir( stickHeadRadius*0.75, stickAngle+45 ) );
		
		double eyeAngleOffset = Geo.herp( 135, 45, deathFrac );
			   faceEyePosA    = stickHeadPos.add( Geo.lenDir(stickHeadRadius*0.35,stickAngle-eyeAngleOffset) );
			   faceEyePosB    = stickHeadPos.add( Geo.lenDir(stickHeadRadius*0.35,stickAngle+eyeAngleOffset) );
	}
	
	
	
	
	
	/**
	 * Determines the rope length, used to animate the fall and neck break.  Very morbid!
	 */
	private double ropeLerp( double a, double b, double f ) {
		double fallThresh = 0.35;
		
		if (f < fallThresh) {
			 double frac = Geo.unlerp( f, 0.0, fallThresh );
			 return Geo.lerp( a, b, frac );
		}
		else {
			double ref        = Geo.lerp( a, b, f );
			double normalised = Geo.unlerp( ref, b, 1.0 );
			return Geo.sqerp( ref, b, normalised );
		}
	}





	private void setupDrawSequence() {
		drawComs = new ArrayList<Callback>();
		
		// Mast
		drawComs.add( new Callback() {
			public void execute() {
				Gfx.drawThickRoundedLine( g, mastTop, mastBottom, lineWidth, true );
			}
		});
		
		
		// Lower buttress
		drawComs.add( new Callback() {
			public void execute() {
				Gfx.drawThickRoundedLine( g, lowerButtressTop, lowerButtressBottom, lineWidth, true );
			}
		});
		
		
		// Upper buttress
		drawComs.add( new Callback() {
			public void execute() {
				Gfx.drawThickRoundedLine( g, upperButtressTop, upperButtressBottom, lineWidth, true );
			}
		});
		
		
		// Upper beam
		drawComs.add( new Callback() {
			public void execute() {
				Gfx.drawThickRoundedLine( g, upperLeft, upperRight, lineWidth, true );
			}
		});
		
		
		// Rope attachment
		drawComs.add( new Callback() {
			public void execute() {
				Gfx.drawThickRoundedLine( g, attachLeft,  attachBottom, lineWidth, true );
				Gfx.drawThickRoundedLine( g, attachRight, attachBottom, lineWidth, true );
			}
		});
		
		
		// Rope + connector
		drawComs.add( new Callback() {
			public void execute() {
				Gfx.drawThickRoundedLine( g, ropeTop, ropeBottom, lineWidth*0.5, true );
				Gfx.drawCircle( g, ropeTop, lineWidth*0.85, true );
				Gfx.drawCircle( g, ropeTop, lineWidth*0.35, new Color(255,255,255), true );
			}
		});
		
		
		// Body + Head
		drawComs.add( new Callback() {
			public void execute() {
				Gfx.drawThickRoundedLine( g, stickHeadPos, stickHipPos, lineWidth*1.25, true );
				Gfx.drawCircle( g, stickHeadPos, stickHeadRadius,      true );
				Gfx.drawCircle( g, stickHeadPos, stickHeadRadius*0.55, new Color(255,255,255), true );
				Gfx.drawCircle( g, faceEyePosA,  stickHeadRadius*0.1,  true );
				Gfx.drawCircle( g, faceEyePosB,  stickHeadRadius*0.1,  true );
			}
		});
		
		
		// Arms
		drawComs.add( new Callback() {
			public void execute() {
				Gfx.drawThickRoundedLine( g, stickShoulderPos, stickArmPosA, lineWidth, true );
			}
		});
		
		drawComs.add( new Callback() {
			public void execute() {
				Gfx.drawThickRoundedLine( g, stickShoulderPos, stickArmPosB, lineWidth, true );
			}
		});
		
		
		// Legs
		drawComs.add( new Callback() {
			public void execute() {
				Gfx.drawThickRoundedLine( g, stickHipPos, stickLegPosA, lineWidth, true );
			}
		});
		
		drawComs.add( new Callback() { public void execute() {
				Gfx.drawThickRoundedLine( g, stickHipPos, stickLegPosB, lineWidth, true );
			}
		});
	}
}



































