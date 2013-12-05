


package hangman.util;

import java.awt.Color;





/**
 * Provides advanced geometric and mathematical utility functions.
 */
public class Geo
{
	/**
	 * Linear interpolate from A to B by fraction F.
	 */
	public static double lerp( double a, double b, double f ) {
		return a + (b-a) * f;
	}
	
	
	
	
	
	/**
	 * Linear interpolate from A to B by fraction F.
	 */
	public static Vec2 lerp( Vec2 a, Vec2 b, double f ) {
		return new Vec2( 
			lerp( a.x, b.x, f ),
			lerp( a.y, b.y, f )
		);
	}
	
	
	
	
	
	/**
	 * Linear interpolate colour from A to B by fraction F.
	 */
	public static Color lerp( Color a, Color b, double f ) {
		return new Color(
			(int) lerp( a.getRed(),   b.getRed(),   f ),
			(int) lerp( a.getGreen(), b.getGreen(), f ),
			(int) lerp( a.getBlue(),  b.getBlue(),  f )
		);
	}
	
	
	
	
	
	/**
	 * Transform a linear [0:1] interpolant into a hermite curve.
	 */
	public static double hermite( double f ) {
		return f * f * (3.0 - (2.0 * f));
	}
	
	
	
	
	
	/**
	 * Hermite interpolate from A to B by fraction F.
	 */
	public static double herp( double a, double b, double f ) {
		return lerp( a, b, hermite(f) );
	}
	
	
	
	
	
	/**
	 * Linear interpolate from A to B by fraction F.
	 * Interpolant is biased towards the end of the range, so it's slower at the beginning and sharp at the end.
	 */
	public static double sqerp( double a, double b, double f ) {
		return a + (b-a) * sqr(f);
	}
	
	
	
	
	
	/**
	 * Normalise given current value and min/max.
	 */
	public static double unlerp( double v, double minv, double maxv ) {
		double base  = v    - minv;
		double delta = maxv - minv;
		return base / delta;
	}
	
	
	
	
	
	/**
	 * Clamp to inclusive range. 
	 */
	public static double clamp( double v, double minv, double maxv  ) {
		return Math.min( Math.max(v,minv), maxv );
	}
	
	
	
	
	
	/**
	 * Same as unlerp() but clamps result to [0:1] range.
	 * Linear version of the well-known smoothstep function.
	 */
	public static double boxStep( double v, double minv, double maxv ) {
		double ul = unlerp( v, minv, maxv );
		return clamp( ul, 0, 1 );
	}
	
	
	
	
	
	/**
	 * Transform a monotonically increasing linear input into a sinewave.
	 * Waveform: One dip and rise per period, zero at edges and centre.
	 * Cycle:    Dip -> zero -> rise -> zero  [v^]
	 * Range:    [-1:+1] inclusive.
	 */
	public static double sineSync( double input, double wavelength ) {
	    double half = wavelength * 0.5;
	    double mod  = (input % wavelength) - half;
	    double pm1  = mod / half;
	    return Math.sin( pm1 * Math.PI );
	}
	
	
	
	
	
	/**
	 * Same as sineSync but with user-defined output range.
	 */
	public static double sineSync( double input, double wavelength, double low, double high ) {
	    double sine = sineSync( input, wavelength );
	    double f    = (sine * 0.5) + 0.5;
	    return lerp( low, high, f );
	}
	
	
	
	
	
	/**
	 * Get relative coordinate offset for given length and direction. 
	 */
	public static Vec2 lenDir( double len, double dir ) {
		double radians = Math.toRadians( dir );
		return new Vec2( Math.cos(radians) * len,
					    -Math.sin(radians) * len );
	}
	
	
	
	
	
	/**
	 * Get the average of two points, IE the middle of them.
	 */
	public static Vec2 getCentre( Vec2 a, Vec2 b ) {
		return lerp( a, b, 0.5 );
	}
	
	
	
	
	
	/**
	 * Get the euclidean distance between two points.
	 */
	public static double distance( Vec2 a, Vec2 b ) {
		return Math.sqrt( sqr(b.x-a.x) + sqr(b.y-a.y) );
	}
	
	
	
	
	
	/**
	 * Find the square of X.
	 */
	public static double sqr( double x ) {
		return x * x;
	}
	
	
	
	
	
	/**
	 * Find the 2D cross product of A,B.
	 */
	public static double cross( Vec2 a, Vec2 b ) {
		return (a.x * b.y) 
		     - (a.y * b.x);
	}
	
	
	
	
	
	/**
	 * Find the angle from A->B.  0 = East.  Increases anticlockwise. 
	 */
	public static double angleBetween( Vec2 a, Vec2 b ) {
		Vec2   d    = b.subtract( a );
		double rads = Math.atan2( d.y, -d.x );
		double degs = Math.toDegrees( rads );
		return (degs + 180.0) % 360.0;
	}
}












































