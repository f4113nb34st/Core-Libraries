package math.vector;

import util.Util;

/**
 * 
 * Represents a 4D vector with primitive storage.
 * 
 * @author F4113nb34st
 *
 */
public abstract class Vector4D extends Vector3D
{
	/**
	 * @return The w value of this 2D vector as a byte.
	 */
	public abstract byte getByteW();
	
	/**
	 * @return The w value of this 2D vector as a short.
	 */
	public abstract short getShortW();
	
	/**
	 * @return The w value of this 2D vector as a int.
	 */
	public abstract int getIntW(); 
	
	/**
	 * @return The w value of this 2D vector as a long.
	 */
	public abstract long getLongW();
	
	/**
	 * @return The w value of this 2D vector as a float.
	 */
	public abstract float getFloatW();
	
	/**
	 * @return The w value of this 2D vector as a double.
	 */
	public abstract double getDoubleW();
	
	/**
	 * Sets the w value of this vector.
	 * @param l The w value.
	 * @return This vector.
	 */
	public abstract Vector4D setW(byte l);
	
	/**
	 * Sets the w value of this vector.
	 * @param l The w value.
	 * @return This vector.
	 */
	public abstract Vector4D setW(short l);
	
	/**
	 * Sets the w value of this vector.
	 * @param l The w value.
	 * @return This vector.
	 */
	public abstract Vector4D setW(int l);
	
	/**
	 * Sets the w value of this vector.
	 * @param l The w value.
	 * @return This vector.
	 */
	public abstract Vector4D setW(long l);
	
	/**
	 * Sets the w value of this vector.
	 * @param l The w value.
	 * @return This vector.
	 */
	public abstract Vector4D setW(float l);
	
	/**
	 * Sets the w value of this vector.
	 * @param l The w value.
	 * @return This vector.
	 */
	public abstract Vector4D setW(double l);
	
	@Override
	public String toString()
	{
		if(isInteger())
		{
			return this.getClass().getSimpleName() + " (" + getLongX() + ", " + getLongY() + ", " + getLongZ() + ", " + getLongW() + ")";
		}else
		{
			return this.getClass().getSimpleName() + " (" + getDoubleX() + ", " + getDoubleY() + ", " + getDoubleZ() + ", " + getDoubleW() + ")";
		}
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Vector4D))
		{
			return false;
		}
		Vector4D other = (Vector4D)obj;
		if(isInteger() && other.isInteger())
		{
			return getLongX() == other.getLongX() && getLongY() == other.getLongY() && getLongZ() == other.getLongZ() && getLongW() == other.getLongW();
		}else
		{
			return getDoubleX() == other.getDoubleX() && getDoubleY() == other.getDoubleY() && getDoubleZ() == other.getDoubleZ() && getDoubleW() == other.getDoubleW();
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
		value = (value * multi) + getDoubleW();
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
		double dx = i - getDoubleX();
		double dy = j - getDoubleY();
		double dz = k - getDoubleZ();
		double dw = l - getDoubleW();
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
	 * Returns the distance squared between the two given 2d structs.
	 * @param v1 Struct 1.
	 * @param v2 Struct 2.
	 * @return The squared distance.
	 */
	public static final double distanceSq(Vector4D v1, Vector4D v2)
	{
		double xdif = v2.getDoubleX() - v1.getDoubleX();
		double ydif = v2.getDoubleY() - v1.getDoubleY();
		double zdif = v2.getDoubleZ() - v1.getDoubleZ();
		double wdif = v2.getDoubleW() - v1.getDoubleW();
		
		return (xdif * xdif) + (ydif * ydif) + (zdif * zdif) + (wdif * wdif);
	}
	
	/**
	 * Returns the distance between the two given 2d structs.
	 * @param v1 Struct 1.
	 * @param v2 Struct 2.
	 * @return The distance.
	 */
	public static final double distance(Vector4D v1, Vector4D v2)
	{
		return Math.sqrt(distanceSq(v1, v2));
	}
	
	/**
	 * Returns the dot product of the two given vectors.
	 * @param v1 Vector 1.
	 * @param v2 Vector 2.
	 * @return The dot product.
	 */
	public static final double dotProduct(Vector4D v1, Vector4D v2)
	{
		return (v1.getDoubleX() * v2.getDoubleX()) + (v1.getDoubleY() * v2.getDoubleY()) + (v1.getDoubleZ() * v2.getDoubleZ()) + (v1.getDoubleW() * v2.getDoubleW());
	}
	
	/**
	 * Projects the given point onto the given line.
	 * @param l1 The first point of the line.
	 * @param l2 The second point of the line.
	 * @param p The projection point.
	 * @param clip True to clip to the defined line segment only.
	 * @return The projected point.
	 */
	public static final Vector4D project(Vector4D l1, Vector4D l2, Vector4D p, boolean clip)
	{
		DoubleVector4D relative = DoubleVector4D.pool.get().set(p).subtract(l1);
		DoubleVector4D dif = DoubleVector4D.pool.get().set(l2).subtract(l1);
		double d = dotProduct(relative, dif) / dotProduct(dif, dif);
		if(clip)
		{
			d = Util.clip(d, 0, 1);
		}
		
		DoubleVector4D result = DoubleVector4D.pool.get().set(dif).multiply(d).add(l1);
		DoubleVector4D.pool.release(relative);
		DoubleVector4D.pool.release(dif);
		
		return result;
	}
}
