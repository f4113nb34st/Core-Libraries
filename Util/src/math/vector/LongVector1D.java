package math.vector;

import util.pool.ObjectPool;
import math.DivideByZeroException;

/**
 * 
 * A comprehensive 1D Vector class with Long storage.
 * 
 * @author F4113nb34st
 *
 */
public class LongVector1D extends Vector1D
{
	public static final ObjectPool<LongVector1D> pool = new ObjectPool<LongVector1D>(new LongVector1D());
	
	public long x;
	
	/**
	 * Creates a new vector at (0).
	 */
	public LongVector1D()
	{
		set(0);
	}
	
	/**
	 * Creates a new vector at (i).
	 * @param i The x coord of this vector.
	 */
	public LongVector1D(long i)
	{
		set(i);
	}
	
	/**
	 * Creates a new vector equal to the given vector.
	 * @param vec The vector to set this new one to.
	 */
	public LongVector1D(Vector1D vec)
	{
		set(vec);
	}
	
	@Override
	public boolean isInteger()
	{
		return true;
	}
	
	@Override
	public void dispose()
	{
		pool.release(this);
	}
	
	@Override
	public long getLongX()
	{
		return x;
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public LongVector1D copy()
	{
		return new LongVector1D(this);
	}
	
	/**
	 * Sets the x value of this vector.
	 * @param i The x coord to set to.
	 * @return This vector.
	 */
	public LongVector1D setX(long i)
	{
		x = i;
		return this;
	}
	
	/**
	 * Sets this vector to i.
	 * @param i The x coord to set to.
	 * @return This vector.
	 */
	public LongVector1D set(long i)
	{
		x = i;
		return this;
	}
	
	/**
	 * Sets this vector to the given vector.
	 * @param vec The vector to set to.
	 * @return This vector.
	 */
	public LongVector1D set(Vector1D vec)
	{
		return set(vec.getLongX());
	}
	
	/**
	 * Negates this vector.
	 * @return This vector.
	 */
	public LongVector1D negate()
	{
		return set(-x);
	}
	
	/**
	 * Adds the given value to this vector.
	 * @param xincrement The Long to add to x.
	 * @return This vector.
	 */
	public LongVector1D add(long xincrement)
	{
		return set(x + xincrement);
	}
	
	/**
	 * Adds the given vector to this vector.
	 * @param increment The vector to add.
	 * @return This vector.
	 */
	public LongVector1D add(Vector1D increment)
	{
		return add(increment.getLongX());
	}
	
	/**
	 * Subtracts the given value from this vector.
	 * @param increment The Long to subtract from x.
	 * @return This vector.
	 */
	public LongVector1D subtract(long xincrement)
	{
		return add(-xincrement);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * @param increment The vector to subtract.
	 * @return This vector.
	 */
	public LongVector1D subtract(Vector1D increment)
	{
		return subtract(increment.getLongX());
	}
	
	/**
	 * Multiplies this vector by the given value.
	 * @param multi The Long to multiply by.
	 * @return This vector.
	 */
	public LongVector1D multiply(long multi)
	{
		return set(x * multi);
	}
	
	/**
	 * Multiplies this vector by the given vector.
	 * @param multi The vector to multiply by.
	 * @return This vector.
	 */
	public LongVector1D multiply(Vector1D multi)
	{
		return set(x * multi.getLongX());
	}
	
	/**
	 * Divides this vector by the given value.
	 * @param multi The Long to divide by.
	 * @return This vector.
	 */
	public LongVector1D divide(long multi)
	{
		if(multi == 0)
		{
			System.out.println("[LongVector1D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi);
	}
	
	/**
	 * Divides this vector by the given vector.
	 * @param multi The vector to divide by.
	 * @return This vector.
	 */
	public LongVector1D divide(Vector1D multi)
	{
		if(multi.getLongX() == 0)
		{
			System.out.println("[LongVector1D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi.getLongX());
	}
	
	public LongVector1D(byte i) { set(i); } 
	public LongVector1D(short i) { set(i); } 
	public LongVector1D(int i) { set(i); } 
	public LongVector1D(float i) { set(i); } 
	public LongVector1D(double i) { set(i); } 
	@Override public byte getByteX() { return (byte)x; } 
	@Override public short getShortX() { return (short)x; } 
	@Override public int getIntX() { return (int)x; } 
	@Override public float getFloatX() { return x; } 
	@Override public double getDoubleX() { return x; } 
	public LongVector1D setX(byte i) { x = i; return this; } 
	public LongVector1D setX(short i) { x = i; return this; } 
	public LongVector1D setX(int i) { x = i; return this; } 
	public LongVector1D setX(float i) { x = (long)i; return this; } 
	public LongVector1D setX(double i) { x = (long)i; return this; } 
	public LongVector1D set(byte i) { x = i; return this; } 
	public LongVector1D set(short i) { x = i; return this; } 
	public LongVector1D set(int i) { x = i; return this; } 
	public LongVector1D set(float i) { x = (long)i; return this; } 
	public LongVector1D set(double i) { x = (long)i; return this; } 
	public LongVector1D add(byte xincrement) { return set(x + xincrement); } 
	public LongVector1D add(short xincrement) { return set(x + xincrement); } 
	public LongVector1D add(int xincrement) { return set(x + xincrement); } 
	public LongVector1D add(float xincrement) { return set(x + xincrement); } 
	public LongVector1D add(double xincrement) { return set(x + xincrement); } 
	public LongVector1D subtract(byte xincrement) { return add(-xincrement); } 
	public LongVector1D subtract(short xincrement) { return add(-xincrement); } 
	public LongVector1D subtract(int xincrement) { return add(-xincrement); } 
	public LongVector1D subtract(float xincrement) { return add(-xincrement); } 
	public LongVector1D subtract(double xincrement) { return add(-xincrement); } 
	public LongVector1D multiply(byte multi) { return set(x * multi); } 
	public LongVector1D multiply(short multi) { return set(x * multi); } 
	public LongVector1D multiply(int multi) { return set(x * multi); } 
	public LongVector1D multiply(float multi) { return set(x * multi); } 
	public LongVector1D multiply(double multi) { return set(x * multi); } 
	public LongVector1D divide(byte multi) { if(multi == 0) { System.out.println("[LongVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
	public LongVector1D divide(short multi) { if(multi == 0) { System.out.println("[LongVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
	public LongVector1D divide(int multi) { if(multi == 0) { System.out.println("[LongVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
	public LongVector1D divide(float multi) { if(multi == 0) { System.out.println("[LongVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
	public LongVector1D divide(double multi) { if(multi == 0) { System.out.println("[LongVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
}
