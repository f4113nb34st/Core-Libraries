package math.vector;

import util.pool.ObjectPool;
import math.DivideByZeroException;

/**
 * 
 * A comprehensive 1D Vector class with Short storage.
 * 
 * @author F4113nb34st
 *
 */
public class ShortVector1D extends Vector1D
{
	public static final ObjectPool<ShortVector1D> pool = new ObjectPool<ShortVector1D>(new ShortVector1D());
	
	public short x;
	
	/**
	 * Creates a new vector at (0).
	 */
	public ShortVector1D()
	{
		set(0);
	}
	
	/**
	 * Creates a new vector at (i).
	 * @param i The x coord of this vector.
	 */
	public ShortVector1D(short i)
	{
		set(i);
	}
	
	/**
	 * Creates a new vector equal to the given vector.
	 * @param vec The vector to set this new one to.
	 */
	public ShortVector1D(Vector1D vec)
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
	public short getShortX()
	{
		return x;
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public ShortVector1D copy()
	{
		return new ShortVector1D(this);
	}
	
	/**
	 * Sets the x value of this vector.
	 * @param i The x coord to set to.
	 * @return This vector.
	 */
	public ShortVector1D setX(short i)
	{
		x = i;
		return this;
	}
	
	/**
	 * Sets this vector to i.
	 * @param i The x coord to set to.
	 * @return This vector.
	 */
	public ShortVector1D set(short i)
	{
		x = i;
		return this;
	}
	
	/**
	 * Sets this vector to the given vector.
	 * @param vec The vector to set to.
	 * @return This vector.
	 */
	public ShortVector1D set(Vector1D vec)
	{
		return set(vec.getShortX());
	}
	
	/**
	 * Negates this vector.
	 * @return This vector.
	 */
	public ShortVector1D negate()
	{
		return set(-x);
	}
	
	/**
	 * Adds the given value to this vector.
	 * @param xincrement The Short to add to x.
	 * @return This vector.
	 */
	public ShortVector1D add(short xincrement)
	{
		return set(x + xincrement);
	}
	
	/**
	 * Adds the given vector to this vector.
	 * @param increment The vector to add.
	 * @return This vector.
	 */
	public ShortVector1D add(Vector1D increment)
	{
		return add(increment.getShortX());
	}
	
	/**
	 * Subtracts the given value from this vector.
	 * @param increment The Short to subtract from x.
	 * @return This vector.
	 */
	public ShortVector1D subtract(short xincrement)
	{
		return add(-xincrement);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * @param increment The vector to subtract.
	 * @return This vector.
	 */
	public ShortVector1D subtract(Vector1D increment)
	{
		return subtract(increment.getShortX());
	}
	
	/**
	 * Multiplies this vector by the given value.
	 * @param multi The Short to multiply by.
	 * @return This vector.
	 */
	public ShortVector1D multiply(short multi)
	{
		return set(x * multi);
	}
	
	/**
	 * Multiplies this vector by the given vector.
	 * @param multi The vector to multiply by.
	 * @return This vector.
	 */
	public ShortVector1D multiply(Vector1D multi)
	{
		return set(x * multi.getShortX());
	}
	
	/**
	 * Divides this vector by the given value.
	 * @param multi The Short to divide by.
	 * @return This vector.
	 */
	public ShortVector1D divide(short multi)
	{
		if(multi == 0)
		{
			System.out.println("[ShortVector1D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi);
	}
	
	/**
	 * Divides this vector by the given vector.
	 * @param multi The vector to divide by.
	 * @return This vector.
	 */
	public ShortVector1D divide(Vector1D multi)
	{
		if(multi.getShortX() == 0)
		{
			System.out.println("[ShortVector1D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi.getShortX());
	}
	
	public ShortVector1D(byte i) { set(i); } 
	public ShortVector1D(int i) { set(i); } 
	public ShortVector1D(long i) { set(i); } 
	public ShortVector1D(float i) { set(i); } 
	public ShortVector1D(double i) { set(i); } 
	@Override public byte getByteX() { return (byte)x; } 
	@Override public int getIntX() { return x; } 
	@Override public long getLongX() { return x; } 
	@Override public float getFloatX() { return x; } 
	@Override public double getDoubleX() { return x; } 
	public ShortVector1D setX(byte i) { x = i; return this; } 
	public ShortVector1D setX(int i) { x = (short)i; return this; } 
	public ShortVector1D setX(long i) { x = (short)i; return this; } 
	public ShortVector1D setX(float i) { x = (short)i; return this; } 
	public ShortVector1D setX(double i) { x = (short)i; return this; } 
	public ShortVector1D set(byte i) { x = i; return this; } 
	public ShortVector1D set(int i) { x = (short)i; return this; } 
	public ShortVector1D set(long i) { x = (short)i; return this; } 
	public ShortVector1D set(float i) { x = (short)i; return this; } 
	public ShortVector1D set(double i) { x = (short)i; return this; } 
	public ShortVector1D add(byte xincrement) { return set(x + xincrement); } 
	public ShortVector1D add(int xincrement) { return set(x + xincrement); } 
	public ShortVector1D add(long xincrement) { return set(x + xincrement); } 
	public ShortVector1D add(float xincrement) { return set(x + xincrement); } 
	public ShortVector1D add(double xincrement) { return set(x + xincrement); } 
	public ShortVector1D subtract(byte xincrement) { return add(-xincrement); } 
	public ShortVector1D subtract(int xincrement) { return add(-xincrement); } 
	public ShortVector1D subtract(long xincrement) { return add(-xincrement); } 
	public ShortVector1D subtract(float xincrement) { return add(-xincrement); } 
	public ShortVector1D subtract(double xincrement) { return add(-xincrement); } 
	public ShortVector1D multiply(byte multi) { return set(x * multi); } 
	public ShortVector1D multiply(int multi) { return set(x * multi); } 
	public ShortVector1D multiply(long multi) { return set(x * multi); } 
	public ShortVector1D multiply(float multi) { return set(x * multi); } 
	public ShortVector1D multiply(double multi) { return set(x * multi); } 
	public ShortVector1D divide(byte multi) { if(multi == 0) { System.out.println("[ShortVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
	public ShortVector1D divide(int multi) { if(multi == 0) { System.out.println("[ShortVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
	public ShortVector1D divide(long multi) { if(multi == 0) { System.out.println("[ShortVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
	public ShortVector1D divide(float multi) { if(multi == 0) { System.out.println("[ShortVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
	public ShortVector1D divide(double multi) { if(multi == 0) { System.out.println("[ShortVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
}
