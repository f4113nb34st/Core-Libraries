package math.vector;

import util.Copyable;

/**
 * 
 * Represents a 1D vector with primitive storage.
 * 
 * @author F4113nb34st
 *
 */
public abstract class Vector1D implements Copyable
{
	/**
	 * @return True if the storage primative of this Vector1D only stores whole numbers
	 */
	public abstract boolean isInteger();
	
	/**
	 * @return The x value of this 1D vector as a byte.
	 */
	public abstract byte getByteX();
	
	/**
	 * @return The x value of this 1D vector as a short.
	 */
	public abstract short getShortX();
	
	/**
	 * @return The x value of this 1D vector as a int.
	 */
	public abstract int getIntX(); 
	
	/**
	 * @return The x value of this 1D vector as a long.
	 */
	public abstract long getLongX();
	
	/**
	 * @return The x value of this 1D vector as a float.
	 */
	public abstract float getFloatX();
	
	/**
	 * @return The x value of this 1D vector as a double.
	 */
	public abstract double getDoubleX();
	
	/**
	 * Sets the x value of this vector.
	 * @param i The x value.
	 * @return This vector.
	 */
	public abstract Vector1D setX(byte i);
	
	/**
	 * Sets the x value of this vector.
	 * @param i The x value.
	 * @return This vector.
	 */
	public abstract Vector1D setX(short i);
	
	/**
	 * Sets the x value of this vector.
	 * @param i The x value.
	 * @return This vector.
	 */
	public abstract Vector1D setX(int i);
	
	/**
	 * Sets the x value of this vector.
	 * @param i The x value.
	 * @return This vector.
	 */
	public abstract Vector1D setX(long i);
	
	/**
	 * Sets the x value of this vector.
	 * @param i The x value.
	 * @return This vector.
	 */
	public abstract Vector1D setX(float i);
	
	/**
	 * Sets the x value of this vector.
	 * @param i The x value.
	 * @return This vector.
	 */
	public abstract Vector1D setX(double i);
	
	/**
	 * Disposes of this Vector. Often puts it back into a common pool.
	 */
	public abstract void dispose();
	
	@Override
	public String toString()
	{
		if(isInteger())
		{
			return this.getClass().getSimpleName() + " (" + getLongX() + ")";
		}else
		{
			return this.getClass().getSimpleName() + " (" + getDoubleX() + ")";
		}
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Vector1D))
		{
			return false;
		}
		Vector1D other = (Vector1D)obj;
		if(isInteger() && other.isInteger())
		{
			return getLongX() == other.getLongX();
		}else
		{
			return getDoubleX() == other.getDoubleX();
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
		value *= multi;
	    return (int)(value % Integer.MAX_VALUE);
	}
}
