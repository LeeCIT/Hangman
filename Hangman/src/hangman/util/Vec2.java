


package hangman.util;





/**
 * Two-dimensional vector.
 */
public class Vec2
{
	public double x,y;
	
	
	
	
	
	public Vec2( double d ) {
		this.x = d;
		this.y = d;
	}
	
	
	
	
	
	public Vec2( double x, double y ) {
		this.x = x;
		this.y = y;
	}
	
	
	
	
	
	public Vec2( Vec2 v ) {
		this.x = v.x;
		this.y = v.y;
	}
	
	
	
	
	
	public Vec2 add( Vec2 v ) {
		return new Vec2( x + v.x,
						 y + v.y );
	}
	
	
	
	
	
	public Vec2 subtract( Vec2 v ) {
		return new Vec2( x - v.x,
						 y - v.y );
	}
	
	
	
	
	
	public Vec2 multiply( Vec2 v ) {
		return new Vec2( x * v.x,
						 y * v.y );
	}
	
	
	
	
	public Vec2 negate() {
		return new Vec2( -x, -y );
	}
	
	
	
	
	
	public double getSmallest() {
		return Math.min( x, y );
	}
	
	
	
	
	
	public double getBiggest() {
		return Math.max( x, y );
	}
	
	
	
	
	
	public Vec2 copy() {
		return new Vec2( x, y );
	}
}



























































