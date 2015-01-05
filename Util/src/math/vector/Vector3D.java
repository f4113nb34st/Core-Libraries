package math.vector;

import util.Util;

/**
 * 
 * Represents a 3D vector with primitive storage.
 * 
 * @author F4113nb34st
 *
 */
public abstract class Vector3D extends Vector2D
{
	/**
	 * @return The z value of this 2D vector as a byte.
	 */
	public abstract byte getByteZ();
	
	/**
	 * @return The z value of this 2D vector as a short.
	 */
	public abstract short getShortZ();
	
	/**
	 * @return The z value of this 2D vector as a int.
	 */
	public abstract int getIntZ(); 
	
	/**
	 * @return The z value of this 2D vector as a long.
	 */
	public abstract long getLongZ();
	
	/**
	 * @return The z value of this 2D vector as a float.
	 */
	public abstract float getFloatZ();
	
	/**
	 * @return The z value of this 2D vector as a double.
	 */
	public abstract double getDoubleZ();
	
	/**
	 * Sets the z value of this vector.
	 * @param k The z value.
	 * @return This vector.
	 */
	public abstract Vector3D setZ(byte k);
	
	/**
	 * Sets the z value of this vector.
	 * @param k The z value.
	 * @return This vector.
	 */
	public abstract Vector3D setZ(short k);
	
	/**
	 * Sets the z value of this vector.
	 * @param k The z value.
	 * @return This vector.
	 */
	public abstract Vector3D setZ(int k);
	
	/**
	 * Sets the z value of this vector.
	 * @param k The z value.
	 * @return This vector.
	 */
	public abstract Vector3D setZ(long k);
	
	/**
	 * Sets the z value of this vector.
	 * @param k The z value.
	 * @return This vector.
	 */
	public abstract Vector3D setZ(float k);
	
	/**
	 * Sets the z value of this vector.
	 * @param k The z value.
	 * @return This vector.
	 */
	public abstract Vector3D setZ(double k);
	
	@Override
	public String toString()
	{
		if(isInteger())
		{
			return this.getClass().getSimpleName() + " (" + getLongX() + ", " + getLongY() + ", " + getLongZ() + ")";
		}else
		{
			return this.getClass().getSimpleName() + " (" + getDoubleX() + ", " + getDoubleY() + ", " + getDoubleZ() + ")";
		}
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Vector3D))
		{
			return false;
		}
		Vector3D other = (Vector3D)obj;
		if(isInteger() && other.isInteger())
		{
			return getLongX() == other.getLongX() && getLongY() == other.getLongY() && getLongZ() == other.getLongZ();
		}else
		{
			return getDoubleX() == other.getDoubleX() && getDoubleY() == other.getDoubleY() && getDoubleZ() == other.getDoubleZ();
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
		value = (value * multi) + getDoubleZ();
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
		double dx = i - getDoubleX();
		double dy = j - getDoubleY();
		double dz = k - getDoubleZ();
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
	 * Returns the distance squared between the two given 2d structs.
	 * @param v1 Struct 1.
	 * @param v2 Struct 2.
	 * @return The squared distance.
	 */
	public static final double distanceSq(Vector3D v1, Vector3D v2)
	{
		double xdif = v2.getDoubleX() - v1.getDoubleX();
		double ydif = v2.getDoubleY() - v1.getDoubleY();
		double zdif = v2.getDoubleZ() - v1.getDoubleZ();
		
		return (xdif * xdif) + (ydif * ydif) + (zdif * zdif);
	}
	
	/**
	 * Returns the distance between the two given 2d structs.
	 * @param v1 Struct 1.
	 * @param v2 Struct 2.
	 * @return The distance.
	 */
	public static final double distance(Vector3D v1, Vector3D v2)
	{
		return Math.sqrt(distanceSq(v1, v2));
	}
	
	/**
	 * Returns the dot product of the two given vectors.
	 * @param v1 Vector 1.
	 * @param v2 Vector 2.
	 * @return The dot product.
	 */
	public static final double dotProduct(Vector3D v1, Vector3D v2)
	{
		return (v1.getDoubleX() * v2.getDoubleX()) + (v1.getDoubleY() * v2.getDoubleY()) + (v1.getDoubleZ() * v2.getDoubleZ());
	}
	
	/**
	 * Returns the cross product of the given vectors.
	 * @return The cross product vector.
	 */
	public static final Vector3D crossProduct(Vector3D v1, Vector3D v2)
	{
		return DoubleVector3D.pool.get().set((v1.getDoubleY() * v2.getDoubleZ()) - (v1.getDoubleZ() * v2.getDoubleY()), (v1.getDoubleZ() * v2.getDoubleX()) - (v1.getDoubleX() * v2.getDoubleZ()), (v1.getDoubleX() * v2.getDoubleY()) - (v1.getDoubleY() * v2.getDoubleX()));
	}
	
	/**
	 * Projects the given point onto the given line.
	 * @param l1 The first point of the line.
	 * @param l2 The second point of the line.
	 * @param p The projection point.
	 * @param clip True to clip to the defined line segment only.
	 * @return The projected point.
	 */
	public static final Vector3D project(Vector3D l1, Vector3D l2, Vector3D p, boolean clip)
	{
		DoubleVector3D relative = DoubleVector3D.pool.get().set(p).subtract(l1);
		DoubleVector3D dif = DoubleVector3D.pool.get().set(l2).subtract(l1);
		double d = dotProduct(relative, dif) / dotProduct(dif, dif);
		if(clip)
		{
			d = Util.clip(d, 0, 1);
		}
		
		DoubleVector3D result = DoubleVector3D.pool.get().set(dif).multiply(d).add(l1);
		DoubleVector3D.pool.release(relative);
		DoubleVector3D.pool.release(dif);
		
		return result;
	}
}
