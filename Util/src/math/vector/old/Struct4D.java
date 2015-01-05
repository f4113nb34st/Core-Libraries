package math.vector.old;

import util.Copyable;
import util.Util;

/**
 * 
 * Represents a 4D vector whether it be a point or a vector.
 * 
 * @author F4113nb34st
 *
 */
public abstract class Struct4D implements Copyable
{
	/**
	 * @return The x value of this 4D vector.
	 */
	public abstract double getX();
	/**
	 * @return The y value of this 4D vector.
	 */
	public abstract double getY();
	/**
	 * @return The z value of this 4D vector.
	 */
	public abstract double getZ();
	/**
	 * @return The w value of this 4D vector.
	 */
	public abstract double getW();
	
	/**
	 * @return The int x value of this 4D vector.
	 */
	public abstract int getIntX();
	/**
	 * @return The int y value of this 4D vector.
	 */
	public abstract int getIntY();
	/**
	 * @return The int z value of this 4D vector.
	 */
	public abstract int getIntZ();
	/**
	 * @return The int w value of this 4D vector.
	 */
	public abstract int getIntW();
	
	@Override
	public String toString()
	{
		return this.getClass().getSimpleName() + " (" + getX() + ", " + getY() + ", " + getZ() + ", " + getW() + ")";
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Struct4D))
		{
			return false;
		}
		Struct4D other = (Struct4D)obj;
		return getX() == other.getX() && getY() == other.getY() && getZ() == other.getZ() && getW() == other.getW();
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
		value = (value * multi) + getZ();
		value = (value * multi) + getW();
		value *= multi;
	    return (int)(value % Integer.MAX_VALUE);
	}
	
	/**
	 * Returns the distance squared to the given point.
	 * @param i The x coord.
	 * @param j The y coord.
	 * @param k The z coord.
	 * @param l The w coord.
	 * @return The squared distance.
	 */
	public double distanceToSq(double i, double j, double k, double l)
	{
		double dx = i - getX();
		double dy = j - getY();
		double dz = k - getZ();
		double dw = l - getW();
		return (dx * dx) + (dy * dy) + (dz * dz) + (dw * dw);
	}
	
	/**
	 * Returns the distance to the given point.
	 * @param i The x coord.
	 * @param j The y coord.
	 * @param k The z coord.
	 * @param l The w coord.
	 * @return The distance.
	 */
	public double distanceTo(double i, double j, double k, double l)
	{
		return Math.sqrt(distanceToSq(i, j, k, l));
	}
	
	/**
	 * Returns the distance squared between the two given 4D structs.
	 * @param v1 Struct 1.
	 * @param v2 Struct 2.
	 * @return The squared distance.
	 */
	public static final double distanceSq(Struct4D v1, Struct4D v2)
	{
		double xdif = v2.getX() - v1.getX();
		double ydif = v2.getY() - v1.getY();
		double zdif = v2.getZ() - v1.getZ();
		double wdif = v2.getW() - v1.getW();
		
		return (xdif * xdif) + (ydif * ydif) + (zdif * zdif) + (wdif * wdif);
	}
	
	/**
	 * Returns the distance between the two given 4D structs.
	 * @param v1 Struct 1.
	 * @param v2 Struct 2.
	 * @return The distance.
	 */
	public static final double distance(Struct4D v1, Struct4D v2)
	{
		return Math.sqrt(distanceSq(v1, v2));
	}
	
	/**
	 * Returns the dot product of the two given vectors.
	 * @param v1 Vector 1.
	 * @param v2 Vector 2.
	 * @return The dot product.
	 */
	public static final double dotProduct(Struct4D v1, Struct4D v2)
	{
		return (v1.getX() * v2.getX()) + (v1.getY() * v2.getY()) + (v1.getZ() * v2.getZ()) + (v1.getW() * v2.getW());
	}
	
	/**
	 * Projects the given point onto the given line.
	 * @param l1 The first point of the line.
	 * @param l2 The second point of the line.
	 * @param p The projection point.
	 * @param clip True to clip to the defined line segment only.
	 * @return The projected point.
	 */
	public static final Vector4D project(Struct4D l1, Struct4D l2, Struct4D p, boolean clip)
	{
		Vector4D relative = Vector4D.pool.get().set(p).subtract(l1);
		Vector4D dif = Vector4D.pool.get().set(l2).subtract(l1);
		double d = dotProduct(relative, dif) / dotProduct(dif, dif);
		if(clip)
		{
			d = Util.clip(d, 0, 1);
		}
		
		Vector4D result = Vector4D.pool.get().set(dif).multiply(d).add(l1);
		Vector4D.pool.release(relative);
		Vector4D.pool.release(dif);
		
		return result;
	}
}