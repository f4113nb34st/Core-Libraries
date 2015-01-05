package math.vector;

import util.pool.ObjectPool;
import math.DivideByZeroException;

/**
 * 
 * A comprehensive 1D Vector class with Int storage.
 * 
 * @author F4113nb34st
 *
 */
public class IntVector1D extends Vector1D
{
	public static final ObjectPool<IntVector1D> pool = new ObjectPool<IntVector1D>(new IntVector1D());
	
	public int x;
	
	/**
	 * Creates a new vector at (0).
	 */
	public IntVector1D()
	{
		set(0);
	}
	
	/**
	 * Creates a new vector at (i).
	 * @param i The x coord of this vector.
	 */
	public IntVector1D(int i)
	{
		set(i);
	}
	
	/**
	 * Creates a new vector equal to the given vector.
	 * @param vec The vector to set this new one to.
	 */
	public IntVector1D(Vector1D vec)
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
	public int getIntX()
	{
		return x;
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public IntVector1D copy()
	{
		return new IntVector1D(this);
	}
	
	/**
	 * Sets the x value of this vector.
	 * @param i The x coord to set to.
	 * @return This vector.
	 */
	public IntVector1D setX(int i)
	{
		x = i;
		return this;
	}
	
	/**
	 * Sets this vector to i.
	 * @param i The x coord to set to.
	 * @return This vector.
	 */
	public IntVector1D set(int i)
	{
		x = i;
		return this;
	}
	
	/**
	 * Sets this vector to the given vector.
	 * @param vec The vector to set to.
	 * @return This vector.
	 */
	public IntVector1D set(Vector1D vec)
	{
		return set(vec.getIntX());
	}
	
	/**
	 * Negates this vector.
	 * @return This vector.
	 */
	public IntVector1D negate()
	{
		return set(-x);
	}
	
	/**
	 * Adds the given value to this vector.
	 * @param xincrement The Int to add to x.
	 * @return This vector.
	 */
	public IntVector1D add(int xincrement)
	{
		return set(x + xincrement);
	}
	
	/**
	 * Adds the given vector to this vector.
	 * @param increment The vector to add.
	 * @return This vector.
	 */
	public IntVector1D add(Vector1D increment)
	{
		return add(increment.getIntX());
	}
	
	/**
	 * Subtracts the given value from this vector.
	 * @param increment The Int to subtract from x.
	 * @return This vector.
	 */
	public IntVector1D subtract(int xincrement)
	{
		return add(-xincrement);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * @param increment The vector to subtract.
	 * @return This vector.
	 */
	public IntVector1D subtract(Vector1D increment)
	{
		return subtract(increment.getIntX());
	}
	
	/**
	 * Multiplies this vector by the given value.
	 * @param multi The Int to multiply by.
	 * @return This vector.
	 */
	public IntVector1D multiply(int multi)
	{
		return set(x * multi);
	}
	
	/**
	 * Multiplies this vector by the given vector.
	 * @param multi The vector to multiply by.
	 * @return This vector.
	 */
	public IntVector1D multiply(Vector1D multi)
	{
		return set(x * multi.getIntX());
	}
	
	/**
	 * Divides this vector by the given value.
	 * @param multi The Int to divide by.
	 * @return This vector.
	 */
	public IntVector1D divide(int multi)
	{
		if(multi == 0)
		{
			System.out.println("[IntVector1D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi);
	}
	
	/**
	 * Divides this vector by the given vector.
	 * @param multi The vector to divide by.
	 * @return This vector.
	 */
	public IntVector1D divide(Vector1D multi)
	{
		if(multi.getIntX() == 0)
		{
			System.out.println("[IntVector1D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi.getIntX());
	}
	
	public IntVector1D(byte i) { set(i); } 
	public IntVector1D(short i) { set(i); } 
	public IntVector1D(long i) { set(i); } 
	public IntVector1D(float i) { set(i); } 
	public IntVector1D(double i) { set(i); } 
	@Override public byte getByteX() { return (byte)x; } 
	@Override public short getShortX() { return (short)x; } 
	@Override public long getLongX() { return x; } 
	@Override public float getFloatX() { return x; } 
	@Override public double getDoubleX() { return x; } 
	public IntVector1D setX(byte i) { x = i; return this; } 
	public IntVector1D setX(short i) { x = i; return this; } 
	public IntVector1D setX(long i) { x = (int)i; return this; } 
	public IntVector1D setX(float i) { x = (int)i; return this; } 
	public IntVector1D setX(double i) { x = (int)i; return this; } 
	public IntVector1D set(byte i) { x = i; return this; } 
	public IntVector1D set(short i) { x = i; return this; } 
	public IntVector1D set(long i) { x = (int)i; return this; } 
	public IntVector1D set(float i) { x = (int)i; return this; } 
	public IntVector1D set(double i) { x = (int)i; return this; } 
	public IntVector1D add(byte xincrement) { return set(x + xincrement); } 
	public IntVector1D add(short xincrement) { return set(x + xincrement); } 
	public IntVector1D add(long xincrement) { return set(x + xincrement); } 
	public IntVector1D add(float xincrement) { return set(x + xincrement); } 
	public IntVector1D add(double xincrement) { return set(x + xincrement); } 
	public IntVector1D subtract(byte xincrement) { return add(-xincrement); } 
	public IntVector1D subtract(short xincrement) { return add(-xincrement); } 
	public IntVector1D subtract(long xincrement) { return add(-xincrement); } 
	public IntVector1D subtract(float xincrement) { return add(-xincrement); } 
	public IntVector1D subtract(double xincrement) { return add(-xincrement); } 
	public IntVector1D multiply(byte multi) { return set(x * multi); } 
	public IntVector1D multiply(short multi) { return set(x * multi); } 
	public IntVector1D multiply(long multi) { return set(x * multi); } 
	public IntVector1D multiply(float multi) { return set(x * multi); } 
	public IntVector1D multiply(double multi) { return set(x * multi); } 
	public IntVector1D divide(byte multi) { if(multi == 0) { System.out.println("[IntVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
	public IntVector1D divide(short multi) { if(multi == 0) { System.out.println("[IntVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
	public IntVector1D divide(long multi) { if(multi == 0) { System.out.println("[IntVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
	public IntVector1D divide(float multi) { if(multi == 0) { System.out.println("[IntVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
	public IntVector1D divide(double multi) { if(multi == 0) { System.out.println("[IntVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
}
