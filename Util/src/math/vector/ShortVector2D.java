package math.vector;

import util.pool.ObjectPool;
import math.DivideByZeroException;

/**
 * 
 * A comprehensive 2D Vector class with Short storage.
 * 
 * @author F4113nb34st
 *
 */
public class ShortVector2D extends Vector2D
{
	public static final ObjectPool<ShortVector2D> pool = new ObjectPool<ShortVector2D>(new ShortVector2D());
	
	public short x;
	public short y;
	
	/**
	 * Creates a new vector at (0, 0).
	 */
	public ShortVector2D()
	{
		set(0, 0);
	}
	
	/**
	 * Creates a new vector at (i, j).
	 * @param i The x coord of this vector.
	 * @param j The y coord of this vector.
	 */
	public ShortVector2D(short i, short j)
	{
		set(i, j);
	}
	
	/**
	 * Creates a new vector equal to the given vector.
	 * @param vec The vector to set this new one to.
	 */
	public ShortVector2D(Vector2D vec)
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
	
	@Override
	public short getShortY()
	{
		return y;
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public ShortVector2D copy()
	{
		return new ShortVector2D(this);
	}
	
	/**
	 * Sets the x value of this vector.
	 * @param i The x coord to set to.
	 * @return This vector.
	 */
	public ShortVector2D setX(short i)
	{
		x = i;
		return this;
	}
	
	/**
	 * Sets the y value of this vector.
	 * @param j The y coord to set to.
	 * @return This vector.
	 */
	public ShortVector2D setY(short j)
	{
		y = j;
		return this;
	}
	
	/**
	 * Sets this vector to (i, j).
	 * @param i The x coord to set to.
	 * @param j The y coord to set to.
	 * @return This vector.
	 */
	public ShortVector2D set(short i, short j)
	{
		x = i;
		y = j;
		return this;
	}
	
	/**
	 * Sets this vector to the given vector.
	 * @param vec The vector to set to.
	 * @return This vector.
	 */
	public ShortVector2D set(Vector2D vec)
	{
		return set(vec.getShortX(), vec.getShortY());
	}
	
	/**
	 * Negates this vector.
	 * @return This vector.
	 */
	public ShortVector2D negate()
	{
		return set(-x, -y);
	}
	
	/**
	 * Returns the length of this vector squared.
	 * @return The squared length.
	 */
	public double lengthSq()
	{
		return (x * x) + (y * y);
	}
	
	/**
	 * Returns the length of this vector.
	 * @return The length.
	 */
	public double length()
	{
		return Math.sqrt(lengthSq());
	}
	
	/**
	 * Reduces the length of this vector to 1, preserving direction.
	 * @return This vector.
	 */
	public ShortVector2D normalize()
	{
		return divide(length());
	}
	
	/**
	 * Adds the given value to this vector.
	 * @param xincrement The Short to add to x.
	 * @param yincrement The Short to add to y.
	 * @return This vector.
	 */
	public ShortVector2D add(short xincrement, short yincrement)
	{
		return set(x + xincrement, y + yincrement);
	}
	
	/**
	 * Adds the given vector to this vector.
	 * @param increment The vector to add.
	 * @return This vector.
	 */
	public ShortVector2D add(Vector2D increment)
	{
		return add(increment.getShortX(), increment.getShortY());
	}
	
	/**
	 * Subtracts the given value from this vector.
	 * @param increment The Short to subtract from x.
	 * @param increment The Short to subtract from y.
	 * @return This vector.
	 */
	public ShortVector2D subtract(short xincrement, short yincrement)
	{
		return add(-xincrement, -yincrement);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * @param increment The vector to subtract.
	 * @return This vector.
	 */
	public ShortVector2D subtract(Vector2D increment)
	{
		return subtract(increment.getShortX(), increment.getShortY());
	}
	
	/**
	 * Multiplies this vector by the given value.
	 * @param multi The Short to multiply by.
	 * @return This vector.
	 */
	public ShortVector2D multiply(short multi)
	{
		return set(x * multi, y * multi);
	}
	
	/**
	 * Multiplies this vector by the given vector.
	 * @param multi The vector to multiply by.
	 * @return This vector.
	 */
	public ShortVector2D multiply(Vector2D multi)
	{
		return set(x * multi.getShortX(), y * multi.getShortY());
	}
	
	/**
	 * Divides this vector by the given value.
	 * @param multi The Short to divide by.
	 * @return This vector.
	 */
	public ShortVector2D divide(short multi)
	{
		if(multi == 0)
		{
			System.out.println("[ShortVector2D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi, y / multi);
	}
	
	/**
	 * Divides this vector by the given vector.
	 * @param multi The vector to divide by.
	 * @return This vector.
	 */
	public ShortVector2D divide(Vector2D multi)
	{
		if(multi.getShortX() == 0 || multi.getShortY() == 0)
		{
			System.out.println("[ShortVector2D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi.getShortX(), y / multi.getShortY());
	}
	
	/**
	 * Rotates this vector by the given number of radians.
	 * @param radians The number of radians to rotate by.
	 * @return This vector.
	 */
	public ShortVector2D rotateRad(double radians)
	{
		double cos = Math.cos(radians);
		double sin = Math.sin(radians);
		
		return set((x * cos) - (y * sin), (x * sin) + (y * cos));
	}
	
	/**
	 * Rotates this vector by the given number of degrees.
	 * @param degrees The number of degrees to rotate by.
	 * @return This vector.
	 */
	public ShortVector2D rotateDeg(double degrees)
	{
		return rotateRad(degrees * Math.PI / 180);
	}
	
	public ShortVector2D(byte i, byte j) { set(i, j); } 
	public ShortVector2D(int i, int j) { set(i, j); } 
	public ShortVector2D(long i, long j) { set(i, j); } 
	public ShortVector2D(float i, float j) { set(i, j); } 
	public ShortVector2D(double i, double j) { set(i, j); } 
	@Override public byte getByteX() { return (byte)x; } 
	@Override public int getIntX() { return x; } 
	@Override public long getLongX() { return x; } 
	@Override public float getFloatX() { return x; } 
	@Override public double getDoubleX() { return x; } 
	@Override public byte getByteY() { return (byte)y; } 
	@Override public int getIntY() { return y; } 
	@Override public long getLongY() { return y; } 
	@Override public float getFloatY() { return y; } 
	@Override public double getDoubleY() { return y; } 
	public ShortVector2D setX(byte i) { x = i; return this; } 
	public ShortVector2D setX(int i) { x = (short)i; return this; } 
	public ShortVector2D setX(long i) { x = (short)i; return this; } 
	public ShortVector2D setX(float i) { x = (short)i; return this; } 
	public ShortVector2D setX(double i) { x = (short)i; return this; } 
	public ShortVector2D setY(byte j) { y = j; return this; } 
	public ShortVector2D setY(int j) { y = (short)j; return this; } 
	public ShortVector2D setY(long j) { y = (short)j; return this; } 
	public ShortVector2D setY(float j) { y = (short)j; return this; } 
	public ShortVector2D setY(double j) { y = (short)j; return this; } 
	public ShortVector2D set(byte i, byte j) { x = i; y = j; return this; } 
	public ShortVector2D set(int i, int j) { x = (short)i; y = (short)j; return this; } 
	public ShortVector2D set(long i, long j) { x = (short)i; y = (short)j; return this; } 
	public ShortVector2D set(float i, float j) { x = (short)i; y = (short)j; return this; } 
	public ShortVector2D set(double i, double j) { x = (short)i; y = (short)j; return this; } 
	public ShortVector2D add(byte xincrement, byte yincrement) { return set(x + xincrement, y + yincrement); } 
	public ShortVector2D add(int xincrement, int yincrement) { return set(x + xincrement, y + yincrement); } 
	public ShortVector2D add(long xincrement, long yincrement) { return set(x + xincrement, y + yincrement); } 
	public ShortVector2D add(float xincrement, float yincrement) { return set(x + xincrement, y + yincrement); } 
	public ShortVector2D add(double xincrement, double yincrement) { return set(x + xincrement, y + yincrement); } 
	public ShortVector2D subtract(byte xincrement, byte yincrement) { return add(-xincrement, -yincrement); } 
	public ShortVector2D subtract(int xincrement, int yincrement) { return add(-xincrement, -yincrement); } 
	public ShortVector2D subtract(long xincrement, long yincrement) { return add(-xincrement, -yincrement); } 
	public ShortVector2D subtract(float xincrement, float yincrement) { return add(-xincrement, -yincrement); } 
	public ShortVector2D subtract(double xincrement, double yincrement) { return add(-xincrement, -yincrement); } 
	public ShortVector2D multiply(byte multi) { return set(x * multi, y * multi); } 
	public ShortVector2D multiply(int multi) { return set(x * multi, y * multi); } 
	public ShortVector2D multiply(long multi) { return set(x * multi, y * multi); } 
	public ShortVector2D multiply(float multi) { return set(x * multi, y * multi); } 
	public ShortVector2D multiply(double multi) { return set(x * multi, y * multi); } 
	public ShortVector2D divide(byte multi) { if(multi == 0) { System.out.println("[ShortVector2D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi); } 
	public ShortVector2D divide(int multi) { if(multi == 0) { System.out.println("[ShortVector2D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi); } 
	public ShortVector2D divide(long multi) { if(multi == 0) { System.out.println("[ShortVector2D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi); } 
	public ShortVector2D divide(float multi) { if(multi == 0) { System.out.println("[ShortVector2D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi); } 
	public ShortVector2D divide(double multi) { if(multi == 0) { System.out.println("[ShortVector2D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi); } 
}
