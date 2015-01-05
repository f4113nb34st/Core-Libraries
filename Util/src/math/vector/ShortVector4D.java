package math.vector;

import util.pool.ObjectPool;
import math.DivideByZeroException;

/**
 * 
 * A comprehensive 4D Vector class with Short storage.
 * 
 * @author F4113nb34st
 *
 */
public class ShortVector4D extends Vector4D
{
	public static final ObjectPool<ShortVector4D> pool = new ObjectPool<ShortVector4D>(new ShortVector4D());
	
	public short x;
	public short y;
	public short z;
	public short w;
	
	/**
	 * Creates a new vector at (0, 0, 0, 0).
	 */
	public ShortVector4D()
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
	public ShortVector4D(short i, short j, short k, short l)
	{
		set(i, j, k, l);
	}
	
	/**
	 * Creates a new vector equal to the given vector.
	 * @param vec The vector to set this new one to.
	 */
	public ShortVector4D(Vector4D vec)
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
	
	@Override
	public short getShortW()
	{
		return w;
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public ShortVector4D copy()
	{
		return new ShortVector4D(this);
	}
	
	/**
	 * Sets the x value of this vector.
	 * @param i The x coord to set to.
	 * @return This vector.
	 */
	public ShortVector4D setX(short i)
	{
		x = i;
		return this;
	}
	
	/**
	 * Sets the y value of this vector.
	 * @param j The y coord to set to.
	 * @return This vector.
	 */
	public ShortVector4D setY(short j)
	{
		y = j;
		return this;
	}
	
	/**
	 * Sets the z value of this vector.
	 * @param k The z coord to set to.
	 * @return This vector.
	 */
	public ShortVector4D setZ(short k)
	{
		z = k;
		return this;
	}
	
	/**
	 * Sets the w value of this vector.
	 * @param l The w coord to set to.
	 * @return This vector.
	 */
	public ShortVector4D setW(short l)
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
	public ShortVector4D set(short i, short j, short k, short l)
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
	public ShortVector4D set(Vector4D vec)
	{
		return set(vec.getShortX(), vec.getShortY(), vec.getShortZ(), vec.getShortW());
	}
	
	/**
	 * Negates this vector.
	 * @return This vector.
	 */
	public ShortVector4D negate()
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
	public ShortVector4D normalize()
	{
		return divide(length());
	}
	
	/**
	 * Adds the given value to this vector.
	 * @param xincrement The Short to add to x.
	 * @param yincrement The Short to add to y.
	 * @param zincrement The Short to add to z.
	 * @param wincrement The Short to add to w.
	 * @return This vector.
	 */
	public ShortVector4D add(short xincrement, short yincrement, short zincrement, short wincrement)
	{
		return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement);
	}
	
	/**
	 * Adds the given vector to this vector.
	 * @param increment The vector to add.
	 * @return This vector.
	 */
	public ShortVector4D add(Vector4D increment)
	{
		return add(increment.getShortX(), increment.getShortY(), increment.getShortZ(), increment.getShortW());
	}
	
	/**
	 * Subtracts the given value from this vector.
	 * @param increment The Short to subtract from x.
	 * @param increment The Short to subtract from y.
	 * @param increment The Short to subtract from z.
	 * @param increment The Short to subtract from w.
	 * @return This vector.
	 */
	public ShortVector4D subtract(short xincrement, short yincrement, short zincrement, short wincrement)
	{
		return add(-xincrement, -yincrement, -zincrement, -wincrement);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * @param increment The vector to subtract.
	 * @return This vector.
	 */
	public ShortVector4D subtract(Vector4D increment)
	{
		return subtract(increment.getShortX(), increment.getShortY(), increment.getShortZ(), increment.getShortW());
	}
	
	/**
	 * Multiplies this vector by the given value.
	 * @param multi The Short to multiply by.
	 * @return This vector.
	 */
	public ShortVector4D multiply(short multi)
	{
		return set(x * multi, y * multi, z * multi, w * multi);
	}
	
	/**
	 * Multiplies this vector by the given vector.
	 * @param multi The vector to multiply by.
	 * @return This vector.
	 */
	public ShortVector4D multiply(Vector4D multi)
	{
		return set(x * multi.getShortX(), y * multi.getShortY(), z * multi.getShortZ(), w * multi.getShortW());
	}
	
	/**
	 * Divides this vector by the given value.
	 * @param multi The Short to divide by.
	 * @return This vector.
	 */
	public ShortVector4D divide(short multi)
	{
		if(multi == 0)
		{
			System.out.println("[ShortVector4D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi, y / multi, z / multi, w / multi);
	}
	
	/**
	 * Divides this vector by the given vector.
	 * @param multi The vector to divide by.
	 * @return This vector.
	 */
	public ShortVector4D divide(Vector4D multi)
	{
		if(multi.getShortX() == 0 || multi.getShortY() == 0 || multi.getShortZ() == 0 || multi.getShortW() == 0)
		{
			System.out.println("[ShortVector4D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi.getShortX(), y / multi.getShortY(), z / multi.getShortZ(), w / multi.getShortW());
	}
	
	public ShortVector4D(byte i, byte j, byte k, byte l) { set(i, j, k, l); } 
	public ShortVector4D(int i, int j, int k, int l) { set(i, j, k, l); } 
	public ShortVector4D(long i, long j, long k, long l) { set(i, j, k, l); } 
	public ShortVector4D(float i, float j, float k, float l) { set(i, j, k, l); } 
	public ShortVector4D(double i, double j, double k, double l) { set(i, j, k, l); } 
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
	@Override public byte getByteW() { return (byte)w; } 
	@Override public int getIntW() { return w; } 
	@Override public long getLongW() { return w; } 
	@Override public float getFloatW() { return w; } 
	@Override public double getDoubleW() { return w; } 
	public ShortVector4D setX(byte i) { x = i; return this; } 
	public ShortVector4D setX(int i) { x = (short)i; return this; } 
	public ShortVector4D setX(long i) { x = (short)i; return this; } 
	public ShortVector4D setX(float i) { x = (short)i; return this; } 
	public ShortVector4D setX(double i) { x = (short)i; return this; } 
	public ShortVector4D setY(byte j) { y = j; return this; } 
	public ShortVector4D setY(int j) { y = (short)j; return this; } 
	public ShortVector4D setY(long j) { y = (short)j; return this; } 
	public ShortVector4D setY(float j) { y = (short)j; return this; } 
	public ShortVector4D setY(double j) { y = (short)j; return this; } 
	public ShortVector4D setZ(byte k) { z = k; return this; } 
	public ShortVector4D setZ(int k) { z = (short)k; return this; } 
	public ShortVector4D setZ(long k) { z = (short)k; return this; } 
	public ShortVector4D setZ(float k) { z = (short)k; return this; } 
	public ShortVector4D setZ(double k) { z = (short)k; return this; } 
	public ShortVector4D setW(byte l) { w = l; return this; } 
	public ShortVector4D setW(int l) { w = (short)l; return this; } 
	public ShortVector4D setW(long l) { w = (short)l; return this; } 
	public ShortVector4D setW(float l) { w = (short)l; return this; } 
	public ShortVector4D setW(double l) { w = (short)l; return this; } 
	public ShortVector4D set(byte i, byte j, byte k, byte l) { x = i; y = j; z = k; w = l; return this; } 
	public ShortVector4D set(int i, int j, int k, int l) { x = (short)i; y = (short)j; z = (short)k; w = (short)l; return this; } 
	public ShortVector4D set(long i, long j, long k, long l) { x = (short)i; y = (short)j; z = (short)k; w = (short)l; return this; } 
	public ShortVector4D set(float i, float j, float k, float l) { x = (short)i; y = (short)j; z = (short)k; w = (short)l; return this; } 
	public ShortVector4D set(double i, double j, double k, double l) { x = (short)i; y = (short)j; z = (short)k; w = (short)l; return this; } 
	public ShortVector4D add(byte xincrement, byte yincrement, byte zincrement, byte wincrement) { return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement); } 
	public ShortVector4D add(int xincrement, int yincrement, int zincrement, int wincrement) { return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement); } 
	public ShortVector4D add(long xincrement, long yincrement, long zincrement, long wincrement) { return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement); } 
	public ShortVector4D add(float xincrement, float yincrement, float zincrement, float wincrement) { return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement); } 
	public ShortVector4D add(double xincrement, double yincrement, double zincrement, double wincrement) { return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement); } 
	public ShortVector4D subtract(byte xincrement, byte yincrement, byte zincrement, byte wincrement) { return add(-xincrement, -yincrement, -zincrement, -wincrement); } 
	public ShortVector4D subtract(int xincrement, int yincrement, int zincrement, int wincrement) { return add(-xincrement, -yincrement, -zincrement, -wincrement); } 
	public ShortVector4D subtract(long xincrement, long yincrement, long zincrement, long wincrement) { return add(-xincrement, -yincrement, -zincrement, -wincrement); } 
	public ShortVector4D subtract(float xincrement, float yincrement, float zincrement, float wincrement) { return add(-xincrement, -yincrement, -zincrement, -wincrement); } 
	public ShortVector4D subtract(double xincrement, double yincrement, double zincrement, double wincrement) { return add(-xincrement, -yincrement, -zincrement, -wincrement); } 
	public ShortVector4D multiply(byte multi) { return set(x * multi, y * multi, z * multi, w * multi); } 
	public ShortVector4D multiply(int multi) { return set(x * multi, y * multi, z * multi, w * multi); } 
	public ShortVector4D multiply(long multi) { return set(x * multi, y * multi, z * multi, w * multi); } 
	public ShortVector4D multiply(float multi) { return set(x * multi, y * multi, z * multi, w * multi); } 
	public ShortVector4D multiply(double multi) { return set(x * multi, y * multi, z * multi, w * multi); } 
	public ShortVector4D divide(byte multi) { if(multi == 0) { System.out.println("[ShortVector4D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi, z / multi, w / multi); } 
	public ShortVector4D divide(int multi) { if(multi == 0) { System.out.println("[ShortVector4D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi, z / multi, w / multi); } 
	public ShortVector4D divide(long multi) { if(multi == 0) { System.out.println("[ShortVector4D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi, z / multi, w / multi); } 
	public ShortVector4D divide(float multi) { if(multi == 0) { System.out.println("[ShortVector4D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi, z / multi, w / multi); } 
	public ShortVector4D divide(double multi) { if(multi == 0) { System.out.println("[ShortVector4D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi, z / multi, w / multi); } 
}
