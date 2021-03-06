package math.vector;

import util.pool.ObjectPool;
import math.DivideByZeroException;

/**
 * 
 * A comprehensive 3D Vector class with Short storage.
 * 
 * @author F4113nb34st
 *
 */
public class ShortVector3D extends Vector3D
{
	public static final ObjectPool<ShortVector3D> pool = new ObjectPool<ShortVector3D>(new ShortVector3D());
	
	public short x;
	public short y;
	public short z;
	
	/**
	 * Creates a new vector at (0, 0, 0).
	 */
	public ShortVector3D()
	{
		set(0, 0, 0);
	}
	
	/**
	 * Creates a new vector at (i, j, k).
	 * @param i The x coord of this vector.
	 * @param j The y coord of this vector.
	 * @param k The z coord of this vector.
	 */
	public ShortVector3D(short i, short j, short k)
	{
		set(i, j, k);
	}
	
	/**
	 * Creates a new vector equal to the given vector.
	 * @param vec The vector to set this new one to.
	 */
	public ShortVector3D(Vector3D vec)
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
	
	@Override
	public short getShortZ()
	{
		return z;
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public ShortVector3D copy()
	{
		return new ShortVector3D(this);
	}
	
	/**
	 * Sets the x value of this vector.
	 * @param i The x coord to set to.
	 * @return This vector.
	 */
	public ShortVector3D setX(short i)
	{
		x = i;
		return this;
	}
	
	/**
	 * Sets the y value of this vector.
	 * @param j The y coord to set to.
	 * @return This vector.
	 */
	public ShortVector3D setY(short j)
	{
		y = j;
		return this;
	}
	
	/**
	 * Sets the z value of this vector.
	 * @param k The z coord to set to.
	 * @return This vector.
	 */
	public ShortVector3D setZ(short k)
	{
		z = k;
		return this;
	}
	
	/**
	 * Sets this vector to (i, j, k).
	 * @param i The x coord to set to.
	 * @param j The y coord to set to.
	 * @param k The z coord to set to.
	 * @return This vector.
	 */
	public ShortVector3D set(short i, short j, short k)
	{
		x = i;
		y = j;
		z = k;
		return this;
	}
	
	/**
	 * Sets this vector to the given vector.
	 * @param vec The vector to set to.
	 * @return This vector.
	 */
	public ShortVector3D set(Vector3D vec)
	{
		return set(vec.getShortX(), vec.getShortY(), vec.getShortZ());
	}
	
	/**
	 * Negates this vector.
	 * @return This vector.
	 */
	public ShortVector3D negate()
	{
		return set(-x, -y, -z);
	}
	
	/**
	 * Returns the length of this vector squared.
	 * @return The squared length.
	 */
	public double lengthSq()
	{
		return (x * x) + (y * y) + (z * z);
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
	public ShortVector3D normalize()
	{
		return divide(length());
	}
	
	/**
	 * Adds the given value to this vector.
	 * @param xincrement The Short to add to x.
	 * @param yincrement The Short to add to y.
	 * @param zincrement The Short to add to z.
	 * @return This vector.
	 */
	public ShortVector3D add(short xincrement, short yincrement, short zincrement)
	{
		return set(x + xincrement, y + yincrement, z + zincrement);
	}
	
	/**
	 * Adds the given vector to this vector.
	 * @param increment The vector to add.
	 * @return This vector.
	 */
	public ShortVector3D add(Vector3D increment)
	{
		return add(increment.getShortX(), increment.getShortY(), increment.getShortZ());
	}
	
	/**
	 * Subtracts the given value from this vector.
	 * @param increment The Short to subtract from x.
	 * @param increment The Short to subtract from y.
	 * @param increment The Short to subtract from z.
	 * @return This vector.
	 */
	public ShortVector3D subtract(short xincrement, short yincrement, short zincrement)
	{
		return add(-xincrement, -yincrement, -zincrement);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * @param increment The vector to subtract.
	 * @return This vector.
	 */
	public ShortVector3D subtract(Vector3D increment)
	{
		return subtract(increment.getShortX(), increment.getShortY(), increment.getShortZ());
	}
	
	/**
	 * Multiplies this vector by the given value.
	 * @param multi The Short to multiply by.
	 * @return This vector.
	 */
	public ShortVector3D multiply(short multi)
	{
		return set(x * multi, y * multi, z * multi);
	}
	
	/**
	 * Multiplies this vector by the given vector.
	 * @param multi The vector to multiply by.
	 * @return This vector.
	 */
	public ShortVector3D multiply(Vector3D multi)
	{
		return set(x * multi.getShortX(), y * multi.getShortY(), z * multi.getShortZ());
	}
	
	/**
	 * Divides this vector by the given value.
	 * @param multi The Short to divide by.
	 * @return This vector.
	 */
	public ShortVector3D divide(short multi)
	{
		if(multi == 0)
		{
			System.out.println("[ShortVector3D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi, y / multi, z / multi);
	}
	
	/**
	 * Divides this vector by the given vector.
	 * @param multi The vector to divide by.
	 * @return This vector.
	 */
	public ShortVector3D divide(Vector3D multi)
	{
		if(multi.getShortX() == 0 || multi.getShortY() == 0 || multi.getShortZ() == 0)
		{
			System.out.println("[ShortVector3D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi.getShortX(), y / multi.getShortY(), z / multi.getShortZ());
	}
	
	/**
	 * Rotates this vector by the given number of radians around the x axis.
	 * @param radians The number of radians to rotate by.
	 * @return This vector.
	 */
	public ShortVector3D rotateXRad(double radians)
	{
		double cos = Math.cos(radians);
		double sin = Math.sin(radians);
		
		return set(x, (y * cos) - (z * sin), (y * sin) + (z * cos));
	}
	
	/**
	 * Rotates this vector by the given number of radians around the y axis.
	 * @param radians The number of radians to rotate by.
	 * @return This vector.
	 */
	public ShortVector3D rotateYRad(double radians)
	{
		double cos = Math.cos(radians);
		double sin = Math.sin(radians);
		
		return set((z * sin) + (x * cos), y, (z * cos) - (x * sin));
	}
	
	/**
	 * Rotates this vector by the given number of radians around the z axis.
	 * @param radians The number of radians to rotate by.
	 * @return This vector.
	 */
	public ShortVector3D rotateZRad(double radians)
	{
		double cos = Math.cos(radians);
		double sin = Math.sin(radians);
		
		return set((x * cos) - (y * sin), (x * sin) + (y * cos), z);
	}
	
	/**
	 * Rotates this vector by the given number of degrees around the x axis.
	 * @param degrees The number of degrees to rotate by.
	 * @return This vector.
	 */
	public ShortVector3D rotateXDeg(double degrees)
	{
		return rotateXRad(degrees * Math.PI / 180);
	}
	
	/**
	 * Rotates this vector by the given number of degrees around the y axis.
	 * @param degrees The number of degrees to rotate by.
	 * @return This vector.
	 */
	public ShortVector3D rotateYDeg(double degrees)
	{
		return rotateYRad(degrees * Math.PI / 180);
	}
	
	/**
	 * Rotates this vector by the given number of degrees around the z axis.
	 * @param degrees The number of degrees to rotate by.
	 * @return This vector.
	 */
	public ShortVector3D rotateZDeg(double degrees)
	{
		return rotateZRad(degrees * Math.PI / 180);
	}
	
	public ShortVector3D(byte i, byte j, byte k) { set(i, j, k); } 
	public ShortVector3D(int i, int j, int k) { set(i, j, k); } 
	public ShortVector3D(long i, long j, long k) { set(i, j, k); } 
	public ShortVector3D(float i, float j, float k) { set(i, j, k); } 
	public ShortVector3D(double i, double j, double k) { set(i, j, k); } 
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
	@Override public byte getByteZ() { return (byte)z; } 
	@Override public int getIntZ() { return z; } 
	@Override public long getLongZ() { return z; } 
	@Override public float getFloatZ() { return z; } 
	@Override public double getDoubleZ() { return z; } 
	public ShortVector3D setX(byte i) { x = i; return this; } 
	public ShortVector3D setX(int i) { x = (short)i; return this; } 
	public ShortVector3D setX(long i) { x = (short)i; return this; } 
	public ShortVector3D setX(float i) { x = (short)i; return this; } 
	public ShortVector3D setX(double i) { x = (short)i; return this; } 
	public ShortVector3D setY(byte j) { y = j; return this; } 
	public ShortVector3D setY(int j) { y = (short)j; return this; } 
	public ShortVector3D setY(long j) { y = (short)j; return this; } 
	public ShortVector3D setY(float j) { y = (short)j; return this; } 
	public ShortVector3D setY(double j) { y = (short)j; return this; } 
	public ShortVector3D setZ(byte k) { z = k; return this; } 
	public ShortVector3D setZ(int k) { z = (short)k; return this; } 
	public ShortVector3D setZ(long k) { z = (short)k; return this; } 
	public ShortVector3D setZ(float k) { z = (short)k; return this; } 
	public ShortVector3D setZ(double k) { z = (short)k; return this; } 
	public ShortVector3D set(byte i, byte j, byte k) { x = i; y = j; z = k; return this; } 
	public ShortVector3D set(int i, int j, int k) { x = (short)i; y = (short)j; z = (short)k; return this; } 
	public ShortVector3D set(long i, long j, long k) { x = (short)i; y = (short)j; z = (short)k; return this; } 
	public ShortVector3D set(float i, float j, float k) { x = (short)i; y = (short)j; z = (short)k; return this; } 
	public ShortVector3D set(double i, double j, double k) { x = (short)i; y = (short)j; z = (short)k; return this; } 
	public ShortVector3D add(byte xincrement, byte yincrement, byte zincrement) { return set(x + xincrement, y + yincrement, z + zincrement); } 
	public ShortVector3D add(int xincrement, int yincrement, int zincrement) { return set(x + xincrement, y + yincrement, z + zincrement); } 
	public ShortVector3D add(long xincrement, long yincrement, long zincrement) { return set(x + xincrement, y + yincrement, z + zincrement); } 
	public ShortVector3D add(float xincrement, float yincrement, float zincrement) { return set(x + xincrement, y + yincrement, z + zincrement); } 
	public ShortVector3D add(double xincrement, double yincrement, double zincrement) { return set(x + xincrement, y + yincrement, z + zincrement); } 
	public ShortVector3D subtract(byte xincrement, byte yincrement, byte zincrement) { return add(-xincrement, -yincrement, -zincrement); } 
	public ShortVector3D subtract(int xincrement, int yincrement, int zincrement) { return add(-xincrement, -yincrement, -zincrement); } 
	public ShortVector3D subtract(long xincrement, long yincrement, long zincrement) { return add(-xincrement, -yincrement, -zincrement); } 
	public ShortVector3D subtract(float xincrement, float yincrement, float zincrement) { return add(-xincrement, -yincrement, -zincrement); } 
	public ShortVector3D subtract(double xincrement, double yincrement, double zincrement) { return add(-xincrement, -yincrement, -zincrement); } 
	public ShortVector3D multiply(byte multi) { return set(x * multi, y * multi, z * multi); } 
	public ShortVector3D multiply(int multi) { return set(x * multi, y * multi, z * multi); } 
	public ShortVector3D multiply(long multi) { return set(x * multi, y * multi, z * multi); } 
	public ShortVector3D multiply(float multi) { return set(x * multi, y * multi, z * multi); } 
	public ShortVector3D multiply(double multi) { return set(x * multi, y * multi, z * multi); } 
	public ShortVector3D divide(byte multi) { if(multi == 0) { System.out.println("[ShortVector3D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi, z / multi); } 
	public ShortVector3D divide(int multi) { if(multi == 0) { System.out.println("[ShortVector3D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi, z / multi); } 
	public ShortVector3D divide(long multi) { if(multi == 0) { System.out.println("[ShortVector3D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi, z / multi); } 
	public ShortVector3D divide(float multi) { if(multi == 0) { System.out.println("[ShortVector3D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi, z / multi); } 
	public ShortVector3D divide(double multi) { if(multi == 0) { System.out.println("[ShortVector3D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi, z / multi); } 
}
