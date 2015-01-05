package math.vector;

import util.pool.ObjectPool;
import math.DivideByZeroException;

/**
 * 
 * A comprehensive 1D Vector class with Byte storage.
 * 
 * @author F4113nb34st
 *
 */
public class ByteVector1D extends Vector1D
{
	public static final ObjectPool<ByteVector1D> pool = new ObjectPool<ByteVector1D>(new ByteVector1D());
	
	public byte x;
	
	/**
	 * Creates a new vector at (0).
	 */
	public ByteVector1D()
	{
		set(0);
	}
	
	/**
	 * Creates a new vector at (i).
	 * @param i The x coord of this vector.
	 */
	public ByteVector1D(byte i)
	{
		set(i);
	}
	
	/**
	 * Creates a new vector equal to the given vector.
	 * @param vec The vector to set this new one to.
	 */
	public ByteVector1D(Vector1D vec)
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
	public byte getByteX()
	{
		return x;
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public ByteVector1D copy()
	{
		return new ByteVector1D(this);
	}
	
	/**
	 * Sets the x value of this vector.
	 * @param i The x coord to set to.
	 * @return This vector.
	 */
	public ByteVector1D setX(byte i)
	{
		x = i;
		return this;
	}
	
	/**
	 * Sets this vector to i.
	 * @param i The x coord to set to.
	 * @return This vector.
	 */
	public ByteVector1D set(byte i)
	{
		x = i;
		return this;
	}
	
	/**
	 * Sets this vector to the given vector.
	 * @param vec The vector to set to.
	 * @return This vector.
	 */
	public ByteVector1D set(Vector1D vec)
	{
		return set(vec.getByteX());
	}
	
	/**
	 * Negates this vector.
	 * @return This vector.
	 */
	public ByteVector1D negate()
	{
		return set(-x);
	}
	
	/**
	 * Adds the given value to this vector.
	 * @param xincrement The Byte to add to x.
	 * @return This vector.
	 */
	public ByteVector1D add(byte xincrement)
	{
		return set(x + xincrement);
	}
	
	/**
	 * Adds the given vector to this vector.
	 * @param increment The vector to add.
	 * @return This vector.
	 */
	public ByteVector1D add(Vector1D increment)
	{
		return add(increment.getByteX());
	}
	
	/**
	 * Subtracts the given value from this vector.
	 * @param increment The Byte to subtract from x.
	 * @return This vector.
	 */
	public ByteVector1D subtract(byte xincrement)
	{
		return add(-xincrement);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * @param increment The vector to subtract.
	 * @return This vector.
	 */
	public ByteVector1D subtract(Vector1D increment)
	{
		return subtract(increment.getByteX());
	}
	
	/**
	 * Multiplies this vector by the given value.
	 * @param multi The Byte to multiply by.
	 * @return This vector.
	 */
	public ByteVector1D multiply(byte multi)
	{
		return set(x * multi);
	}
	
	/**
	 * Multiplies this vector by the given vector.
	 * @param multi The vector to multiply by.
	 * @return This vector.
	 */
	public ByteVector1D multiply(Vector1D multi)
	{
		return set(x * multi.getByteX());
	}
	
	/**
	 * Divides this vector by the given value.
	 * @param multi The Byte to divide by.
	 * @return This vector.
	 */
	public ByteVector1D divide(byte multi)
	{
		if(multi == 0)
		{
			System.out.println("[ByteVector1D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi);
	}
	
	/**
	 * Divides this vector by the given vector.
	 * @param multi The vector to divide by.
	 * @return This vector.
	 */
	public ByteVector1D divide(Vector1D multi)
	{
		if(multi.getByteX() == 0)
		{
			System.out.println("[ByteVector1D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi.getByteX());
	}
	
	public ByteVector1D(short i) { set(i); } 
	public ByteVector1D(int i) { set(i); } 
	public ByteVector1D(long i) { set(i); } 
	public ByteVector1D(float i) { set(i); } 
	public ByteVector1D(double i) { set(i); } 
	@Override public short getShortX() { return x; } 
	@Override public int getIntX() { return x; } 
	@Override public long getLongX() { return x; } 
	@Override public float getFloatX() { return x; } 
	@Override public double getDoubleX() { return x; } 
	public ByteVector1D setX(short i) { x = (byte)i; return this; } 
	public ByteVector1D setX(int i) { x = (byte)i; return this; } 
	public ByteVector1D setX(long i) { x = (byte)i; return this; } 
	public ByteVector1D setX(float i) { x = (byte)i; return this; } 
	public ByteVector1D setX(double i) { x = (byte)i; return this; } 
	public ByteVector1D set(short i) { x = (byte)i; return this; } 
	public ByteVector1D set(int i) { x = (byte)i; return this; } 
	public ByteVector1D set(long i) { x = (byte)i; return this; } 
	public ByteVector1D set(float i) { x = (byte)i; return this; } 
	public ByteVector1D set(double i) { x = (byte)i; return this; } 
	public ByteVector1D add(short xincrement) { return set(x + xincrement); } 
	public ByteVector1D add(int xincrement) { return set(x + xincrement); } 
	public ByteVector1D add(long xincrement) { return set(x + xincrement); } 
	public ByteVector1D add(float xincrement) { return set(x + xincrement); } 
	public ByteVector1D add(double xincrement) { return set(x + xincrement); } 
	public ByteVector1D subtract(short xincrement) { return add(-xincrement); } 
	public ByteVector1D subtract(int xincrement) { return add(-xincrement); } 
	public ByteVector1D subtract(long xincrement) { return add(-xincrement); } 
	public ByteVector1D subtract(float xincrement) { return add(-xincrement); } 
	public ByteVector1D subtract(double xincrement) { return add(-xincrement); } 
	public ByteVector1D multiply(short multi) { return set(x * multi); } 
	public ByteVector1D multiply(int multi) { return set(x * multi); } 
	public ByteVector1D multiply(long multi) { return set(x * multi); } 
	public ByteVector1D multiply(float multi) { return set(x * multi); } 
	public ByteVector1D multiply(double multi) { return set(x * multi); } 
	public ByteVector1D divide(short multi) { if(multi == 0) { System.out.println("[ByteVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
	public ByteVector1D divide(int multi) { if(multi == 0) { System.out.println("[ByteVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
	public ByteVector1D divide(long multi) { if(multi == 0) { System.out.println("[ByteVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
	public ByteVector1D divide(float multi) { if(multi == 0) { System.out.println("[ByteVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
	public ByteVector1D divide(double multi) { if(multi == 0) { System.out.println("[ByteVector1D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi); } 
}
