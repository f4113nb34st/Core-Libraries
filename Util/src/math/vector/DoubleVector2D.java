package math.vector;

import util.pool.ObjectPool;
import math.DivideByZeroException;

/**
 * 
 * A comprehensive 2D Vector class with Double storage.
 * 
 * @author F4113nb34st
 *
 */
public class DoubleVector2D extends Vector2D
{
	public static final ObjectPool<DoubleVector2D> pool = new ObjectPool<DoubleVector2D>(new DoubleVector2D());
	
	public double x;
	public double y;
	
	/**
	 * Creates a new vector at (0, 0).
	 */
	public DoubleVector2D()
	{
		set(0, 0);
	}
	
	/**
	 * Creates a new vector at (i, j).
	 * @param i The x coord of this vector.
	 * @param j The y coord of this vector.
	 */
	public DoubleVector2D(double i, double j)
	{
		set(i, j);
	}
	
	/**
	 * Creates a new vector equal to the given vector.
	 * @param vec The vector to set this new one to.
	 */
	public DoubleVector2D(Vector2D vec)
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
	
	@Override
	public double getDoubleY()
	{
		return y;
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public DoubleVector2D copy()
	{
		return new DoubleVector2D(this);
	}
	
	/**
	 * Sets the x value of this vector.
	 * @param i The x coord to set to.
	 * @return This vector.
	 */
	public DoubleVector2D setX(double i)
	{
		x = i;
		return this;
	}
	
	/**
	 * Sets the y value of this vector.
	 * @param j The y coord to set to.
	 * @return This vector.
	 */
	public DoubleVector2D setY(double j)
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
	public DoubleVector2D set(double i, double j)
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
	public DoubleVector2D set(Vector2D vec)
	{
		return set(vec.getDoubleX(), vec.getDoubleY());
	}
	
	/**
	 * Negates this vector.
	 * @return This vector.
	 */
	public DoubleVector2D negate()
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
	public DoubleVector2D normalize()
	{
		return divide(length());
	}
	
	/**
	 * Adds the given value to this vector.
	 * @param xincrement The Double to add to x.
	 * @param yincrement The Double to add to y.
	 * @return This vector.
	 */
	public DoubleVector2D add(double xincrement, double yincrement)
	{
		return set(x + xincrement, y + yincrement);
	}
	
	/**
	 * Adds the given vector to this vector.
	 * @param increment The vector to add.
	 * @return This vector.
	 */
	public DoubleVector2D add(Vector2D increment)
	{
		return add(increment.getDoubleX(), increment.getDoubleY());
	}
	
	/**
	 * Subtracts the given value from this vector.
	 * @param increment The Double to subtract from x.
	 * @param increment The Double to subtract from y.
	 * @return This vector.
	 */
	public DoubleVector2D subtract(double xincrement, double yincrement)
	{
		return add(-xincrement, -yincrement);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * @param increment The vector to subtract.
	 * @return This vector.
	 */
	public DoubleVector2D subtract(Vector2D increment)
	{
		return subtract(increment.getDoubleX(), increment.getDoubleY());
	}
	
	/**
	 * Multiplies this vector by the given value.
	 * @param multi The Double to multiply by.
	 * @return This vector.
	 */
	public DoubleVector2D multiply(double multi)
	{
		return set(x * multi, y * multi);
	}
	
	/**
	 * Multiplies this vector by the given vector.
	 * @param multi The vector to multiply by.
	 * @return This vector.
	 */
	public DoubleVector2D multiply(Vector2D multi)
	{
		return set(x * multi.getDoubleX(), y * multi.getDoubleY());
	}
	
	/**
	 * Divides this vector by the given value.
	 * @param multi The Double to divide by.
	 * @return This vector.
	 */
	public DoubleVector2D divide(double multi)
	{
		if(multi == 0)
		{
			System.out.println("[DoubleVector2D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi, y / multi);
	}
	
	/**
	 * Divides this vector by the given vector.
	 * @param multi The vector to divide by.
	 * @return This vector.
	 */
	public DoubleVector2D divide(Vector2D multi)
	{
		if(multi.getDoubleX() == 0 || multi.getDoubleY() == 0)
		{
			System.out.println("[DoubleVector2D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi.getDoubleX(), y / multi.getDoubleY());
	}
	
	/**
	 * Rotates this vector by the given number of radians.
	 * @param radians The number of radians to rotate by.
	 * @return This vector.
	 */
	public DoubleVector2D rotateRad(double radians)
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
	public DoubleVector2D rotateDeg(double degrees)
	{
		return rotateRad(degrees * Math.PI / 180);
	}
	
	public DoubleVector2D(byte i, byte j) { set(i, j); } 
	public DoubleVector2D(short i, short j) { set(i, j); } 
	public DoubleVector2D(int i, int j) { set(i, j); } 
	public DoubleVector2D(long i, long j) { set(i, j); } 
	public DoubleVector2D(float i, float j) { set(i, j); } 
	@Override public byte getByteX() { return (byte)x; } 
	@Override public short getShortX() { return (short)x; } 
	@Override public int getIntX() { return (int)x; } 
	@Override public long getLongX() { return (long)x; } 
	@Override public float getFloatX() { return (float)x; } 
	@Override public byte getByteY() { return (byte)y; } 
	@Override public short getShortY() { return (short)y; } 
	@Override public int getIntY() { return (int)y; } 
	@Override public long getLongY() { return (long)y; } 
	@Override public float getFloatY() { return (float)y; } 
	public DoubleVector2D setX(byte i) { x = i; return this; } 
	public DoubleVector2D setX(short i) { x = i; return this; } 
	public DoubleVector2D setX(int i) { x = i; return this; } 
	public DoubleVector2D setX(long i) { x = i; return this; } 
	public DoubleVector2D setX(float i) { x = i; return this; } 
	public DoubleVector2D setY(byte j) { y = j; return this; } 
	public DoubleVector2D setY(short j) { y = j; return this; } 
	public DoubleVector2D setY(int j) { y = j; return this; } 
	public DoubleVector2D setY(long j) { y = j; return this; } 
	public DoubleVector2D setY(float j) { y = j; return this; } 
	public DoubleVector2D set(byte i, byte j) { x = i; y = j; return this; } 
	public DoubleVector2D set(short i, short j) { x = i; y = j; return this; } 
	public DoubleVector2D set(int i, int j) { x = i; y = j; return this; } 
	public DoubleVector2D set(long i, long j) { x = i; y = j; return this; } 
	public DoubleVector2D set(float i, float j) { x = i; y = j; return this; } 
	public DoubleVector2D add(byte xincrement, byte yincrement) { return set(x + xincrement, y + yincrement); } 
	public DoubleVector2D add(short xincrement, short yincrement) { return set(x + xincrement, y + yincrement); } 
	public DoubleVector2D add(int xincrement, int yincrement) { return set(x + xincrement, y + yincrement); } 
	public DoubleVector2D add(long xincrement, long yincrement) { return set(x + xincrement, y + yincrement); } 
	public DoubleVector2D add(float xincrement, float yincrement) { return set(x + xincrement, y + yincrement); } 
	public DoubleVector2D subtract(byte xincrement, byte yincrement) { return add(-xincrement, -yincrement); } 
	public DoubleVector2D subtract(short xincrement, short yincrement) { return add(-xincrement, -yincrement); } 
	public DoubleVector2D subtract(int xincrement, int yincrement) { return add(-xincrement, -yincrement); } 
	public DoubleVector2D subtract(long xincrement, long yincrement) { return add(-xincrement, -yincrement); } 
	public DoubleVector2D subtract(float xincrement, float yincrement) { return add(-xincrement, -yincrement); } 
	public DoubleVector2D multiply(byte multi) { return set(x * multi, y * multi); } 
	public DoubleVector2D multiply(short multi) { return set(x * multi, y * multi); } 
	public DoubleVector2D multiply(int multi) { return set(x * multi, y * multi); } 
	public DoubleVector2D multiply(long multi) { return set(x * multi, y * multi); } 
	public DoubleVector2D multiply(float multi) { return set(x * multi, y * multi); } 
	public DoubleVector2D divide(byte multi) { if(multi == 0) { System.out.println("[DoubleVector2D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi); } 
	public DoubleVector2D divide(short multi) { if(multi == 0) { System.out.println("[DoubleVector2D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi); } 
	public DoubleVector2D divide(int multi) { if(multi == 0) { System.out.println("[DoubleVector2D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi); } 
	public DoubleVector2D divide(long multi) { if(multi == 0) { System.out.println("[DoubleVector2D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi); } 
	public DoubleVector2D divide(float multi) { if(multi == 0) { System.out.println("[DoubleVector2D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi); } 
}
