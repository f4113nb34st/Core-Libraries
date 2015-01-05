package math.vector;

import util.pool.ObjectPool;
import math.DivideByZeroException;

/**
 * 
 * A comprehensive 1D Vector class with Double storage.
 * 
 * @author F4113nb34st
 *
 */
public class DoubleVector1D extends Vector1D
{
	public static final ObjectPool<DoubleVector1D> pool = new ObjectPool<DoubleVector1D>(new DoubleVector1D());
	
	public double x;
	
	/**
	 * Creates a new vector at (0).
	 */
	public DoubleVector1D()
	{
		set(0);
	}
	
	/**
	 * Creates a new vector at (i).
	 * @param i The x coord of this vector.
	 */
	public DoubleVector1D(double i)
	{
		set(i);
	}
	
	/**
	 * Creates a new vector equal to the given vector.
	 * @param vec The vector to set this new one to.
	 */
	public DoubleVector1D(Vector1D vec)
	{
		set(vec);
	}
	
	@Override
	public boolean isInteger()
	{
		return false;
	}
	
	@Override
	public void dispose()
	{
		pool.release(this);
	}
	
	@Override
	public double getDoubleX()
	{
		return x;
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public DoubleVector1D copy()
	{
		return new DoubleVector1D(this);
	}
	
	/**
	 * Sets the x value of this vector.
	 * @param i The x coord to set to.
	 * @return This vector.
	 */
	public DoubleVector1D setX(double i)
	{
		x = i;
		return this;
	}
	
	/**
	 * Sets this vector to i.
	 * @param i The x coord to set to.
	 * @return This vector.
	 */
	public DoubleVector1D set(double i)
	{
		x = i;
		return this;
	}
	
	/**
	 * Sets this vector to the given vector.
	 * @param vec The vector to set to.
	 * @return This vector.
	 */
	public DoubleVector1D set(Vector1D vec)
	{
		return set(vec.getDoubleX());
	}
	
	/**
	 * Negates this vector.
	 * @return This vector.
	 */
	public DoubleVector1D negate()
	{
		return set(-x);
	}
	
	/**
	 * Adds the given value to this vector.
	 * @param xincrement The Double to add to x.
	 * @return This vector.
	 */
	public DoubleVector1D add(double xincrement)
	{
		return set(x + xincrement);
	}
	
	/**
	 * Adds the given vector to this vector.
	 * @param increment The vector to add.
	 * @return This vector.
	 */
	public DoubleVector1D add(Vector1D increment)
	{
		return add(increment.getDoubleX());
	}
	
	/**
	 * Subtracts the given value from this vector.
	 * @param increment The Double to subtract from x.
	 * @return This vector.
	 */
	public DoubleVector1D subtract(double xincrement)
	{
		return add(-xincrement);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * @param increment The vector to subtract.
	 * @return This vector.
	 */
	public DoubleVector1D subtract(Vector1D increment)
	{
		return subtract(increment.getDoubleX());
	}
	
	/**
	 * Multiplies this vector by the given value.
	 * @param multi The Double to multiply by.
	 * @return This vector.
	 */
	public DoubleVector1D multiply(double multi)
	{
		return set(x * multi);
	}
	
	/**
	 * Multiplies this vector by the given vector.
	 * @param multi The vector to multiply by.
	 * @return This vector.
	 */
	public DoubleVector1D multiply(Vector1D multi)
	{
		return set(x * multi.getDoubleX());
	}
	
	/**
	 * Divides this vector by the given value.
	 * @param multi The Double to divide by.
	 * @return This vector.
	 */
	public DoubleVector1D divide(double multi)
	{
		if(multi == 0)
		{
			System.out.println("[DoubleVector1D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi);
	}
	
	/**
	 * Divides this vector by the given vector.
	 * @param multi The vector to divide by.
	 * @return This vector.
	 */
	public DoubleVector1D divide(Vector1D multi)
	{
		if(multi.getDoubleX() == 0)
		{
			System.out.println("[DoubleVector1D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi.getDoubleX());
	}
	
	public DoubleVector1D(byte i) { set(i); } 
	public DoubleVector1D(short i) { set(i); } 
	public DoubleVector1D(int i) { set(i); } 
	public DoubleVector1D(long i) { set(i); } 
	public DoubleVector1D(float i) { set(i); } 
	@Override public byte getByteX() { return (byte)x; } 
	@Override public short getShortX() { return (short)x; } 
	@Override public int getIntX() { return (int)x; } 
	@Override public long getLongX() { return (long)x; } 
	@Override public float getFloatX() { return (float)x; } 
	public DoubleVector1D setX(byte i) { x = i; return this; } 
	public DoubleVector1D setX(short i) { x = i; return this; } 
	public DoubleVector1D setX(int i) { x = i; return this; } 
	public DoubleVector1D setX(long i) { x = i; return this; } 
	public DoubleVector1D setX(float i) { x = i; return this; } 
	public DoubleVector1D set(byte i) { x = i; return this; } 
	public DoubleVector1D set(short i) { x = i; return this; } 
	public DoubleVector1D set(int i) { x = i; return this; } 
	public DoubleVector1D set(long i) { x = i; return this; } 
	public DoubleVector1D set(float i) { x = i; return this; } 
	public DoubleVector1D add(byte xincrement) { return set(x + xincrement); } 
	public DoubleVector1D add(short xincrement) { return set(x + xincrement); } 
	public DoubleVector1D add(int xincrement) { return set(x + xincrement); } 
	public DoubleVector1D add(long xincrement) { return set(x + xincrement); } 
	public DoubleVector1D add(float xincrement) { return set(x + xincrement); } 
	public DoubleVector1D subtract(byte xincrement) { return add(-xincrement); } 
	public DoubleVector1D subtract(short xincrement) { return add(-xincrement); } 
	public DoubleVector1D subtract(int xincrement) { return add(-xincrement); } 
	public DoubleVector1D subtract(long xincrement) { return add(-xincrement); } 
	public DoubleVector1D subtract(float xincrement) { return add(-xincrement); } 
	public DoubleVector1D multiply(byte multi) { return set(x * multi); } 
	public DoubleVector1D multiply(short multi) { return set(x * multi); } 
	public DoubleVector1D multiply(int multi) { return set(x * multi); } 
	public DoubleVector1D multiply(long multi) { return set(x * multi); } 
	public DoubleVector1D multiply(float multi) { return set(x * multi); } 
	public DoubleVector1D divide(byte multi) { if(multi == 0) { System.out.println("[DoubleVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
	public DoubleVector1D divide(short multi) { if(multi == 0) { System.out.println("[DoubleVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
	public DoubleVector1D divide(int multi) { if(multi == 0) { System.out.println("[DoubleVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
	public DoubleVector1D divide(long multi) { if(multi == 0) { System.out.println("[DoubleVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
	public DoubleVector1D divide(float multi) { if(multi == 0) { System.out.println("[DoubleVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
}
