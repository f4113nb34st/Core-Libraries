package math.vector;

import util.Util;

/**
 * 
 * Represents a 2D vector with primitive storage.
 * 
 * @author F4113nb34st
 *
 */
public abstract class Vector2D extends Vector1D
{
	/**
	 * @return The y value of this 2D vector as a byte.
	 */
	public abstract byte getByteY();
	
	/**
	 * @return The y value of this 2D vector as a short.
	 */
	public abstract short getShortY();
	
	/**
	 * @return The y value of this 2D vector as a int.
	 */
	public abstract int getIntY(); 
	
	/**
	 * @return The y value of this 2D vector as a long.
	 */
	public abstract long getLongY();
	
	/**
	 * @return The y value of this 2D vector as a float.
	 */
	public abstract float getFloatY();
	
	/**
	 * @return The y value of this 2D vector as a double.
	 */
	public abstract double getDoubleY();
	
	/**
	 * Sets the y value of this vector.
	 * @param j The y value.
	 * @return This vector.
	 */
	public abstract Vector2D setY(byte j);
	
	/**
	 * Sets the y value of this vector.
	 * @param j The y value.
	 * @return This vector.
	 */
	public abstract Vector2D setY(short j);
	
	/**
	 * Sets the y value of this vector.
	 * @param j The y value.
	 * @return This vector.
	 */
	public abstract Vector2D setY(int j);
	
	/**
	 * Sets the y value of this vector.
	 * @param j The y value.
	 * @return This vector.
	 */
	public abstract Vector2D setY(long j);
	
	/**
	 * Sets the y value of this vector.
	 * @param j The y value.
	 * @return This vector.
	 */
	public abstract Vector2D setY(float j);
	
	/**
	 * Sets the y value of this vector.
	 * @param j The y value.
	 * @return This vector.
	 */
	public abstract Vector2D setY(double j);
	
	@Override
	public String toString()
	{
		if(isInteger())
		{
			return this.getClass().getSimpleName() + " (" + getLongX() + ", " + getLongY() + ")";
		}else
		{
			return this.getClass().getSimpleName() + " (" + getDoubleX() + ", " + getDoubleY() + ")";
		}
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Vector2D))
		{
			return false;
		}
		Vector2D other = (Vector2D)obj;
		if(isInteger() && other.isInteger())
		{
			return getLongX() == other.getLongX() && getLongY() == other.getLongY();
		}else
		{
			return getDoubleX() == other.getDoubleX() && getDoubleY() == other.getDoubleY();
		}
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
		value = (value * multi) + getDoubleX();
		value = (value * multi) + getDoubleY();
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
		double dx = i - getDoubleX();
		double dy = j - getDoubleY();
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
	public static final double distanceSq(Vector2D v1, Vector2D v2)
	{
		double xdif = v2.getDoubleX() - v1.getDoubleX();
		double ydif = v2.getDoubleY() - v1.getDoubleY();
		
		return (xdif * xdif) + (ydif * ydif);
	}
	
	/**
	 * Returns the distance between the two given 2d structs.
	 * @param v1 Struct 1.
	 * @param v2 Struct 2.
	 * @return The distance.
	 */
	public static final double distance(Vector2D v1, Vector2D v2)
	{
		return Math.sqrt(distanceSq(v1, v2));
	}
	
	/**
	 * Returns the dot product of the two given vectors.
	 * @param v1 Vector 1.
	 * @param v2 Vector 2.
	 * @return The dot product.
	 */
	public static final double dotProduct(Vector2D v1, Vector2D v2)
	{
		return (v1.getDoubleX() * v2.getDoubleX()) + (v1.getDoubleY() * v2.getDoubleY());
	}
	
	/**
	 * Projects the given point onto the given line.
	 * @param l1 The first point of the line.
	 * @param l2 The second point of the line.
	 * @param p The projection point.
	 * @param clip True to clip to the defined line segment only.
	 * @return The projected point.
	 */
	public static final DoubleVector2D project(Vector2D l1, Vector2D l2, Vector2D p, boolean clip)
	{
		DoubleVector2D relative = DoubleVector2D.pool.get().set(p).subtract(l1);
		DoubleVector2D dif = DoubleVector2D.pool.get().set(l2).subtract(l1);
		double d = dotProduct(relative, dif) / dotProduct(dif, dif);
		if(clip)
		{
			d = Util.clip(d, 0, 1);
		}
		
		DoubleVector2D result = DoubleVector2D.pool.get().set(dif).multiply(d).add(l1);
		DoubleVector2D.pool.release(relative);
		DoubleVector2D.pool.release(dif);
		
		return result;
	}
}
