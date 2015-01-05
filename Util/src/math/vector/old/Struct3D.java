package math.vector.old;

import util.Copyable;
import util.Util;

/**
 * 
 * Represents a 3D vector whether it be a point or a vector.
 * 
 * @author F4113nb34st
 *
 */
public abstract class Struct3D implements Copyable
{
	/**
	 * @return The x value of this 3D vector.
	 */
	public abstract double getX();
	/**
	 * @return The y value of this 3D vector.
	 */
	public abstract double getY();
	/**
	 * @return The z value of this 3D vector.
	 */
	public abstract double getZ();
	
	/**
	 * @return The int x value of this 3D vector.
	 */
	public abstract int getIntX();
	/**
	 * @return The int y value of this 3D vector.
	 */
	public abstract int getIntY();
	/**
	 * @return The int z value of this 3D vector.
	 */
	public abstract int getIntZ();
	
	@Override
	public String toString()
	{
		return this.getClass().getSimpleName() + " (" + getX() + ", " + getY() + ", " + getZ() + ")";
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Struct3D))
		{
			return false;
		}
		Struct3D other = (Struct3D)obj;
		return getX() == other.getX() && getY() == other.getY() && getZ() == other.getZ();
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
		value *= multi;
	    return (int)(value % Integer.MAX_VALUE);
	}
	
	/**
	 * Returns the distance squared to the given point.
	 * @param i The x coord.
	 * @param j The y coord.
	 * @param k The z coord.
	 * @return The squared distance.
	 */
	public double distanceToSq(double i, double j, double k)
	{
		double dx = i - getX();
		double dy = j - getY();
		double dz = k - getZ();
		return (dx * dx) + (dy * dy) + (dz * dz);
	}
	
	/**
	 * Returns the distance to the given point.
	 * @param i The x coord.
	 * @param j The y coord.
	 * @param k The z coord.
	 * @return The distance.
	 */
	public double distanceTo(double i, double j, double k)
	{
		return Math.sqrt(distanceToSq(i, j, k));
	}
	
	/**
	 * Returns the distance squared between the two given 3d structs.
	 * @param v1 Struct 1.
	 * @param v2 Struct 2.
	 * @return The squared distance.
	 */
	public static final double distanceSq(Struct3D v1, Struct3D v2)
	{
		double xdif = v2.getX() - v1.getX();
		double ydif = v2.getY() - v1.getY();
		double zdif = v2.getZ() - v1.getZ();
		
		return (xdif * xdif) + (ydif * ydif) + (zdif * zdif);
	}
	
	/**
	 * Returns the distance between the two given 3d structs.
	 * @param v1 Struct 1.
	 * @param v2 Struct 2.
	 * @return The distance.
	 */
	public static final double distance(Struct3D v1, Struct3D v2)
	{
		return Math.sqrt(distanceSq(v1, v2));
	}
	
	/**
	 * Returns the dot product of the two given vectors.
	 * @param v1 Vector 1.
	 * @param v2 Vector 2.
	 * @return The dot product.
	 */
	public static final double dotProduct(Struct3D v1, Struct3D v2)
	{
		return (v1.getX() * v2.getX()) + (v1.getY() * v2.getY()) + (v1.getZ() * v2.getZ());
	}
	
	/**
	 * Returns the cross product of the given vectors.
	 * @return The cross product vector.
	 */
	public static final Vector3D crossProduct(Struct3D v1, Struct3D v2)
	{
		return new Vector3D((v1.getY() * v2.getZ()) - (v1.getZ() * v2.getY()), (v1.getZ() * v2.getX()) - (v1.getX() * v2.getZ()), (v1.getX() * v2.getY()) - (v1.getY() * v2.getX()));
	}
	
	/**
	 * Projects the given point onto the given line.
	 * @param l1 The first point of the line.
	 * @param l2 The second point of the line.
	 * @param p The projection point.
	 * @param clip True to clip to the defined line segment only.
	 * @return The projected point.
	 */
	public static final Vector3D project(Struct3D l1, Struct3D l2, Struct3D p, boolean clip)
	{
		Vector3D relative = Vector3D.pool.get().set(p).subtract(l1);
		Vector3D dif = Vector3D.pool.get().set(l2).subtract(l1);
		double d = dotProduct(relative, dif) / dotProduct(dif, dif);
		if(clip)
		{
			d = Util.clip(d, 0, 1);
		}
		
		Vector3D result = Vector3D.pool.get().set(dif).multiply(d).add(l1);
		Vector3D.pool.release(relative);
		Vector3D.pool.release(dif);
		
		return result;
	}
}