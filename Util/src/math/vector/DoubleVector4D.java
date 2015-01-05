package math.vector;

import util.pool.ObjectPool;
import math.DivideByZeroException;

/**
 * 
 * A comprehensive 4D Vector class with Double storage.
 * 
 * @author F4113nb34st
 *
 */
public class DoubleVector4D extends Vector4D
{
	public static final ObjectPool<DoubleVector4D> pool = new ObjectPool<DoubleVector4D>(new DoubleVector4D());
	
	public double x;
	public double y;
	public double z;
	public double w;
	
	/**
	 * Creates a new vector at (0, 0, 0, 0).
	 */
	public DoubleVector4D()
	{
		set(0, 0, 0, 0);
	}
	
	/**
	 * Creates a new vector at (i, j, k, l).
	 * @param i The x coord of this vector.
	 * @param j The y coord of this vector.
	 * @param k The z coord of this vector.
	 * @param l The w coord of this vector.
	 */
	public DoubleVector4D(double i, double j, double k, double l)
	{
		set(i, j, k, l);
	}
	
	/**
	 * Creates a new vector equal to the given vector.
	 * @param vec The vector to set this new one to.
	 */
	public DoubleVector4D(Vector4D vec)
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
	
	@Override
	public double getDoubleZ()
	{
		return z;
	}
	
	@Override
	public double getDoubleW()
	{
		return w;
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public DoubleVector4D copy()
	{
		return new DoubleVector4D(this);
	}
	
	/**
	 * Sets the x value of this vector.
	 * @param i The x coord to set to.
	 * @return This vector.
	 */
	public DoubleVector4D setX(double i)
	{
		x = i;
		return this;
	}
	
	/**
	 * Sets the y value of this vector.
	 * @param j The y coord to set to.
	 * @return This vector.
	 */
	public DoubleVector4D setY(double j)
	{
		y = j;
		return this;
	}
	
	/**
	 * Sets the z value of this vector.
	 * @param k The z coord to set to.
	 * @return This vector.
	 */
	public DoubleVector4D setZ(double k)
	{
		z = k;
		return this;
	}
	
	/**
	 * Sets the w value of this vector.
	 * @param l The w coord to set to.
	 * @return This vector.
	 */
	public DoubleVector4D setW(double l)
	{
		w = l;
		return this;
	}
	
	/**
	 * Sets this vector to (i, j, k, l).
	 * @param i The x coord to set to.
	 * @param j The y coord to set to.
	 * @param k The z coord to set to.
	 * @param l The w coord to set to.
	 * @return This vector.
	 */
	public DoubleVector4D set(double i, double j, double k, double l)
	{
		x = i;
		y = j;
		z = k;
		w = l;
		return this;
	}
	
	/**
	 * Sets this vector to the given vector.
	 * @param vec The vector to set to.
	 * @return This vector.
	 */
	public DoubleVector4D set(Vector4D vec)
	{
		return set(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ(), vec.getDoubleW());
	}
	
	/**
	 * Negates this vector.
	 * @return This vector.
	 */
	public DoubleVector4D negate()
	{
		return set(-x, -y, -z, -w);
	}
	
	/**
	 * Returns the length of this vector squared.
	 * @return The squared length.
	 */
	public double lengthSq()
	{
		return (x * x) + (y * y) + (z * z) + (w * w);
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
	public DoubleVector4D normalize()
	{
		return divide(length());
	}
	
	/**
	 * Adds the given value to this vector.
	 * @param xincrement The Double to add to x.
	 * @param yincrement The Double to add to y.
	 * @param zincrement The Double to add to z.
	 * @param wincrement The Double to add to w.
	 * @return This vector.
	 */
	public DoubleVector4D add(double xincrement, double yincrement, double zincrement, double wincrement)
	{
		return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement);
	}
	
	/**
	 * Adds the given vector to this vector.
	 * @param increment The vector to add.
	 * @return This vector.
	 */
	public DoubleVector4D add(Vector4D increment)
	{
		return add(increment.getDoubleX(), increment.getDoubleY(), increment.getDoubleZ(), increment.getDoubleW());
	}
	
	/**
	 * Subtracts the given value from this vector.
	 * @param increment The Double to subtract from x.
	 * @param increment The Double to subtract from y.
	 * @param increment The Double to subtract from z.
	 * @param increment The Double to subtract from w.
	 * @return This vector.
	 */
	public DoubleVector4D subtract(double xincrement, double yincrement, double zincrement, double wincrement)
	{
		return add(-xincrement, -yincrement, -zincrement, -wincrement);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * @param increment The vector to subtract.
	 * @return This vector.
	 */
	public DoubleVector4D subtract(Vector4D increment)
	{
		return subtract(increment.getDoubleX(), increment.getDoubleY(), increment.getDoubleZ(), increment.getDoubleW());
	}
	
	/**
	 * Multiplies this vector by the given value.
	 * @param multi The Double to multiply by.
	 * @return This vector.
	 */
	public DoubleVector4D multiply(double multi)
	{
		return set(x * multi, y * multi, z * multi, w * multi);
	}
	
	/**
	 * Multiplies this vector by the given vector.
	 * @param multi The vector to multiply by.
	 * @return This vector.
	 */
	public DoubleVector4D multiply(Vector4D multi)
	{
		return set(x * multi.getDoubleX(), y * multi.getDoubleY(), z * multi.getDoubleZ(), w * multi.getDoubleW());
	}
	
	/**
	 * Divides this vector by the given value.
	 * @param multi The Double to divide by.
	 * @return This vector.
	 */
	public DoubleVector4D divide(double multi)
	{
		if(multi == 0)
		{
			System.out.println("[DoubleVector4D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi, y / multi, z / multi, w / multi);
	}
	
	/**
	 * Divides this vector by the given vector.
	 * @param multi The vector to divide by.
	 * @return This vector.
	 */
	public DoubleVector4D divide(Vector4D multi)
	{
		if(multi.getDoubleX() == 0 || multi.getDoubleY() == 0 || multi.getDoubleZ() == 0 || multi.getDoubleW() == 0)
		{
			System.out.println("[DoubleVector4D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi.getDoubleX(), y / multi.getDoubleY(), z / multi.getDoubleZ(), w / multi.getDoubleW());
	}
	
	public DoubleVector4D(byte i, byte j, byte k, byte l) { set(i, j, k, l); } 
	public DoubleVector4D(short i, short j, short k, short l) { set(i, j, k, l); } 
	public DoubleVector4D(int i, int j, int k, int l) { set(i, j, k, l); } 
	public DoubleVector4D(long i, long j, long k, long l) { set(i, j, k, l); } 
	public DoubleVector4D(float i, float j, float k, float l) { set(i, j, k, l); } 
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
	@Override public byte getByteZ() { return (byte)z; } 
	@Override public short getShortZ() { return (short)z; } 
	@Override public int getIntZ() { return (int)z; } 
	@Override public long getLongZ() { return (long)z; } 
	@Override public float getFloatZ() { return (float)z; } 
	@Override public byte getByteW() { return (byte)w; } 
	@Override public short getShortW() { return (short)w; } 
	@Override public int getIntW() { return (int)w; } 
	@Override public long getLongW() { return (long)w; } 
	@Override public float getFloatW() { return (float)w; } 
	public DoubleVector4D setX(byte i) { x = i; return this; } 
	public DoubleVector4D setX(short i) { x = i; return this; } 
	public DoubleVector4D setX(int i) { x = i; return this; } 
	public DoubleVector4D setX(long i) { x = i; return this; } 
	public DoubleVector4D setX(float i) { x = i; return this; } 
	public DoubleVector4D setY(byte j) { y = j; return this; } 
	public DoubleVector4D setY(short j) { y = j; return this; } 
	public DoubleVector4D setY(int j) { y = j; return this; } 
	public DoubleVector4D setY(long j) { y = j; return this; } 
	public DoubleVector4D setY(float j) { y = j; return this; } 
	public DoubleVector4D setZ(byte k) { z = k; return this; } 
	public DoubleVector4D setZ(short k) { z = k; return this; } 
	public DoubleVector4D setZ(int k) { z = k; return this; } 
	public DoubleVector4D setZ(long k) { z = k; return this; } 
	public DoubleVector4D setZ(float k) { z = k; return this; } 
	public DoubleVector4D setW(byte l) { w = l; return this; } 
	public DoubleVector4D setW(short l) { w = l; return this; } 
	public DoubleVector4D setW(int l) { w = l; return this; } 
	public DoubleVector4D setW(long l) { w = l; return this; } 
	public DoubleVector4D setW(float l) { w = l; return this; } 
	public DoubleVector4D set(byte i, byte j, byte k, byte l) { x = i; y = j; z = k; w = l; return this; } 
	public DoubleVector4D set(short i, short j, short k, short l) { x = i; y = j; z = k; w = l; return this; } 
	public DoubleVector4D set(int i, int j, int k, int l) { x = i; y = j; z = k; w = l; return this; } 
	public DoubleVector4D set(long i, long j, long k, long l) { x = i; y = j; z = k; w = l; return this; } 
	public DoubleVector4D set(float i, float j, float k, float l) { x = i; y = j; z = k; w = l; return this; } 
	public DoubleVector4D add(byte xincrement, byte yincrement, byte zincrement, byte wincrement) { return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement); } 
	public DoubleVector4D add(short xincrement, short yincrement, short zincrement, short wincrement) { return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement); } 
	public DoubleVector4D add(int xincrement, int yincrement, int zincrement, int wincrement) { return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement); } 
	public DoubleVector4D add(long xincrement, long yincrement, long zincrement, long wincrement) { return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement); } 
	public DoubleVector4D add(float xincrement, float yincrement, float zincrement, float wincrement) { return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement); } 
	public DoubleVector4D subtract(byte xincrement, byte yincrement, byte zincrement, byte wincrement) { return add(-xincrement, -yincrement, -zincrement, -wincrement); } 
	public DoubleVector4D subtract(short xincrement, short yincrement, short zincrement, short wincrement) { return add(-xincrement, -yincrement, -zincrement, -wincrement); } 
	public DoubleVector4D subtract(int xincrement, int yincrement, int zincrement, int wincrement) { return add(-xincrement, -yincrement, -zincrement, -wincrement); } 
	public DoubleVector4D subtract(long xincrement, long yincrement, long zincrement, long wincrement) { return add(-xincrement, -yincrement, -zincrement, -wincrement); } 
	public DoubleVector4D subtract(float xincrement, float yincrement, float zincrement, float wincrement) { return add(-xincrement, -yincrement, -zincrement, -wincrement); } 
	public DoubleVector4D multiply(byte multi) { return set(x * multi, y * multi, z * multi, w * multi); } 
	public DoubleVector4D multiply(short multi) { return set(x * multi, y * multi, z * multi, w * multi); } 
	public DoubleVector4D multiply(int multi) { return set(x * multi, y * multi, z * multi, w * multi); } 
	public DoubleVector4D multiply(long multi) { return set(x * multi, y * multi, z * multi, w * multi); } 
	public DoubleVector4D multiply(float multi) { return set(x * multi, y * multi, z * multi, w * multi); } 
	public DoubleVector4D divide(byte multi) { if(multi == 0) { System.out.println("[DoubleVector4D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi, z / multi, w / multi); } 
	public DoubleVector4D divide(short multi) { if(multi == 0) { System.out.println("[DoubleVector4D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi, z / multi, w / multi); } 
	public DoubleVector4D divide(int multi) { if(multi == 0) { System.out.println("[DoubleVector4D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi, z / multi, w / multi); } 
	public DoubleVector4D divide(long multi) { if(multi == 0) { System.out.println("[DoubleVector4D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi, z / multi, w / multi); } 
	public DoubleVector4D divide(float multi) { if(multi == 0) { System.out.println("[DoubleVector4D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi, z / multi, w / multi); } 
}
