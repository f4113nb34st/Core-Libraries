package math.vector.old;

import util.Copyable;
import util.Util;

/**
 * 
 * Represents a 2D vector whether it be a point or a vector.
 * 
 * @author F4113nb34st
 *
 */
public abstract class Struct2D implements Copyable
{
	/**
	 * @return The x value of this 2D vector.
	 */
	public abstract double getX();
	/**
	 * @return The y value of this 2D vector.
	 */
	public abstract double getY();
	
	/**
	 * @return The int x value of this 2D vector.
	 */
	public abstract int getIntX();
	/**
	 * @return The int y value of this 2D vector.
	 */
	public abstract int getIntY();
	
	@Override
	public String toString()
	{
		return this.getClass().getSimpleName() + " (" + getX() + ", " + getY() + ")";
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Struct2D))
		{
			return false;
		}
		Struct2D other = (Struct2D)obj;
		return getX() == other.getX() && getY() == other.getY();
	}
	
	/**
	 * Hashes this point so the hash is based on the values of x and y.
	 * @return The hash of this point.
	 */
	@Override
	public int hashCode()
	{
		int multi = 997991;
		double value = 0;
		value = (value * multi) + getX();
		value = (value * multi) + getY();
		value *= multi;
	    return (int)(value % Integer.MAX_VALUE);
	}
	
	/**
	 * Returns the distance squared to the given point.
	 * @param i The x coord.
	 * @param j The y coord.
	 * @return The squared distance.
	 */
	public double distanceToSq(double i, double j)
	{
		double dx = i - getX();
		double dy = j - getY();
		return (dx * dx) + (dy * dy);
	}
	
	/**
	 * Returns the distance to the given point.
	 * @param i The x coord.
	 * @param j The y coord.
	 * @return The distance.
	 */
	public double distanceTo(double i, double j)
	{
		return Math.sqrt(distanceToSq(i, j));
	}
	
	/**
	 * Returns the distance squared between the two given 2d structs.
	 * @param v1 Struct 1.
	 * @param v2 Struct 2.
	 * @return The squared distance.
	 */
	public static final double distanceSq(Struct2D v1, Struct2D v2)
	{
		double xdif = v2.getX() - v1.getX();
		double ydif = v2.getY() - v1.getY();
		
		return (xdif * xdif) + (ydif * ydif);
	}
	
	/**
	 * Returns the distance between the two given 2d structs.
	 * @param v1 Struct 1.
	 * @param v2 Struct 2.
	 * @return The distance.
	 */
	public static final double distance(Struct2D v1, Struct2D v2)
	{
		return Math.sqrt(distanceSq(v1, v2));
	}
	
	/**
	 * Returns the dot product of the two given vectors.
	 * @param v1 Vector 1.
	 * @param v2 Vector 2.
	 * @return The dot product.
	 */
	public static final double dotProduct(Struct2D v1, Struct2D v2)
	{
		return (v1.getX() * v2.getX()) + (v1.getY() * v2.getY());
	}
	
	/**
	 * Projects the given point onto the given line.
	 * @param l1 The first point of the line.
	 * @param l2 The second point of the line.
	 * @param p The projection point.
	 * @param clip True to clip to the defined line segment only.
	 * @return The projected point.
	 */
	public static final Vector2D project(Struct2D l1, Struct2D l2, Struct2D p, boolean clip)
	{
		Vector2D relative = Vector2D.pool.get().set(p).subtract(l1);
		Vector2D dif = Vector2D.pool.get().set(l2).subtract(l1);
		double d = dotProduct(relative, dif) / dotProduct(dif, dif);
		if(clip)
		{
			d = Util.clip(d, 0, 1);
		}
		
		Vector2D result = Vector2D.pool.get().set(dif).multiply(d).add(l1);
		Vector2D.pool.release(relative);
		Vector2D.pool.release(dif);
		
		return result;
	}
}
