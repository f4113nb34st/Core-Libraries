package math.vector;

import util.pool.ObjectPool;
import math.DivideByZeroException;

/**
 * 
 * A comprehensive 4D Vector class with Byte storage.
 * 
 * @author F4113nb34st
 *
 */
public class ByteVector4D extends Vector4D
{
	public static final ObjectPool<ByteVector4D> pool = new ObjectPool<ByteVector4D>(new ByteVector4D());
	
	public byte x;
	public byte y;
	public byte z;
	public byte w;
	
	/**
	 * Creates a new vector at (0, 0, 0, 0).
	 */
	public ByteVector4D()
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
	public ByteVector4D(byte i, byte j, byte k, byte l)
	{
		set(i, j, k, l);
	}
	
	/**
	 * Creates a new vector equal to the given vector.
	 * @param vec The vector to set this new one to.
	 */
	public ByteVector4D(Vector4D vec)
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
	
	@Override
	public byte getByteY()
	{
		return y;
	}
	
	@Override
	public byte getByteZ()
	{
		return z;
	}
	
	@Override
	public byte getByteW()
	{
		return w;
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public ByteVector4D copy()
	{
		return new ByteVector4D(this);
	}
	
	/**
	 * Sets the x value of this vector.
	 * @param i The x coord to set to.
	 * @return This vector.
	 */
	public ByteVector4D setX(byte i)
	{
		x = i;
		return this;
	}
	
	/**
	 * Sets the y value of this vector.
	 * @param j The y coord to set to.
	 * @return This vector.
	 */
	public ByteVector4D setY(byte j)
	{
		y = j;
		return this;
	}
	
	/**
	 * Sets the z value of this vector.
	 * @param k The z coord to set to.
	 * @return This vector.
	 */
	public ByteVector4D setZ(byte k)
	{
		z = k;
		return this;
	}
	
	/**
	 * Sets the w value of this vector.
	 * @param l The w coord to set to.
	 * @return This vector.
	 */
	public ByteVector4D setW(byte l)
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
	public ByteVector4D set(byte i, byte j, byte k, byte l)
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
	public ByteVector4D set(Vector4D vec)
	{
		return set(vec.getByteX(), vec.getByteY(), vec.getByteZ(), vec.getByteW());
	}
	
	/**
	 * Negates this vector.
	 * @return This vector.
	 */
	public ByteVector4D negate()
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
	public ByteVector4D normalize()
	{
		return divide(length());
	}
	
	/**
	 * Adds the given value to this vector.
	 * @param xincrement The Byte to add to x.
	 * @param yincrement The Byte to add to y.
	 * @param zincrement The Byte to add to z.
	 * @param wincrement The Byte to add to w.
	 * @return This vector.
	 */
	public ByteVector4D add(byte xincrement, byte yincrement, byte zincrement, byte wincrement)
	{
		return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement);
	}
	
	/**
	 * Adds the given vector to this vector.
	 * @param increment The vector to add.
	 * @return This vector.
	 */
	public ByteVector4D add(Vector4D increment)
	{
		return add(increment.getByteX(), increment.getByteY(), increment.getByteZ(), increment.getByteW());
	}
	
	/**
	 * Subtracts the given value from this vector.
	 * @param increment The Byte to subtract from x.
	 * @param increment The Byte to subtract from y.
	 * @param increment The Byte to subtract from z.
	 * @param increment The Byte to subtract from w.
	 * @return This vector.
	 */
	public ByteVector4D subtract(byte xincrement, byte yincrement, byte zincrement, byte wincrement)
	{
		return add(-xincrement, -yincrement, -zincrement, -wincrement);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * @param increment The vector to subtract.
	 * @return This vector.
	 */
	public ByteVector4D subtract(Vector4D increment)
	{
		return subtract(increment.getByteX(), increment.getByteY(), increment.getByteZ(), increment.getByteW());
	}
	
	/**
	 * Multiplies this vector by the given value.
	 * @param multi The Byte to multiply by.
	 * @return This vector.
	 */
	public ByteVector4D multiply(byte multi)
	{
		return set(x * multi, y * multi, z * multi, w * multi);
	}
	
	/**
	 * Multiplies this vector by the given vector.
	 * @param multi The vector to multiply by.
	 * @return This vector.
	 */
	public ByteVector4D multiply(Vector4D multi)
	{
		return set(x * multi.getByteX(), y * multi.getByteY(), z * multi.getByteZ(), w * multi.getByteW());
	}
	
	/**
	 * Divides this vector by the given value.
	 * @param multi The Byte to divide by.
	 * @return This vector.
	 */
	public ByteVector4D divide(byte multi)
	{
		if(multi == 0)
		{
			System.out.println("[ByteVector4D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi, y / multi, z / multi, w / multi);
	}
	
	/**
	 * Divides this vector by the given vector.
	 * @param multi The vector to divide by.
	 * @return This vector.
	 */
	public ByteVector4D divide(Vector4D multi)
	{
		if(multi.getByteX() == 0 || multi.getByteY() == 0 || multi.getByteZ() == 0 || multi.getByteW() == 0)
		{
			System.out.println("[ByteVector4D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi.getByteX(), y / multi.getByteY(), z / multi.getByteZ(), w / multi.getByteW());
	}
	
	public ByteVector4D(short i, short j, short k, short l) { set(i, j, k, l); } 
	public ByteVector4D(int i, int j, int k, int l) { set(i, j, k, l); } 
	public ByteVector4D(long i, long j, long k, long l) { set(i, j, k, l); } 
	public ByteVector4D(float i, float j, float k, float l) { set(i, j, k, l); } 
	public ByteVector4D(double i, double j, double k, double l) { set(i, j, k, l); } 
	@Override public short getShortX() { return x; } 
	@Override public int getIntX() { return x; } 
	@Override public long getLongX() { return x; } 
	@Override public float getFloatX() { return x; } 
	@Override public double getDoubleX() { return x; } 
	@Override public short getShortY() { return y; } 
	@Override public int getIntY() { return y; } 
	@Override public long getLongY() { return y; } 
	@Override public float getFloatY() { return y; } 
	@Override public double getDoubleY() { return y; } 
	@Override public short getShortZ() { return z; } 
	@Override public int getIntZ() { return z; } 
	@Override public long getLongZ() { return z; } 
	@Override public float getFloatZ() { return z; } 
	@Override public double getDoubleZ() { return z; } 
	@Override public short getShortW() { return w; } 
	@Override public int getIntW() { return w; } 
	@Override public long getLongW() { return w; } 
	@Override public float getFloatW() { return w; } 
	@Override public double getDoubleW() { return w; } 
	public ByteVector4D setX(short i) { x = (byte)i; return this; } 
	public ByteVector4D setX(int i) { x = (byte)i; return this; } 
	public ByteVector4D setX(long i) { x = (byte)i; return this; } 
	public ByteVector4D setX(float i) { x = (byte)i; return this; } 
	public ByteVector4D setX(double i) { x = (byte)i; return this; } 
	public ByteVector4D setY(short j) { y = (byte)j; return this; } 
	public ByteVector4D setY(int j) { y = (byte)j; return this; } 
	public ByteVector4D setY(long j) { y = (byte)j; return this; } 
	public ByteVector4D setY(float j) { y = (byte)j; return this; } 
	public ByteVector4D setY(double j) { y = (byte)j; return this; } 
	public ByteVector4D setZ(short k) { z = (byte)k; return this; } 
	public ByteVector4D setZ(int k) { z = (byte)k; return this; } 
	public ByteVector4D setZ(long k) { z = (byte)k; return this; } 
	public ByteVector4D setZ(float k) { z = (byte)k; return this; } 
	public ByteVector4D setZ(double k) { z = (byte)k; return this; } 
	public ByteVector4D setW(short l) { w = (byte)l; return this; } 
	public ByteVector4D setW(int l) { w = (byte)l; return this; } 
	public ByteVector4D setW(long l) { w = (byte)l; return this; } 
	public ByteVector4D setW(float l) { w = (byte)l; return this; } 
	public ByteVector4D setW(double l) { w = (byte)l; return this; } 
	public ByteVector4D set(short i, short j, short k, short l) { x = (byte)i; y = (byte)j; z = (byte)k; w = (byte)l; return this; } 
	public ByteVector4D set(int i, int j, int k, int l) { x = (byte)i; y = (byte)j; z = (byte)k; w = (byte)l; return this; } 
	public ByteVector4D set(long i, long j, long k, long l) { x = (byte)i; y = (byte)j; z = (byte)k; w = (byte)l; return this; } 
	public ByteVector4D set(float i, float j, float k, float l) { x = (byte)i; y = (byte)j; z = (byte)k; w = (byte)l; return this; } 
	public ByteVector4D set(double i, double j, double k, double l) { x = (byte)i; y = (byte)j; z = (byte)k; w = (byte)l; return this; } 
	public ByteVector4D add(short xincrement, short yincrement, short zincrement, short wincrement) { return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement); } 
	public ByteVector4D add(int xincrement, int yincrement, int zincrement, int wincrement) { return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement); } 
	public ByteVector4D add(long xincrement, long yincrement, long zincrement, long wincrement) { return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement); } 
	public ByteVector4D add(float xincrement, float yincrement, float zincrement, float wincrement) { return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement); } 
	public ByteVector4D add(double xincrement, double yincrement, double zincrement, double wincrement) { return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement); } 
	public ByteVector4D subtract(short xincrement, short yincrement, short zincrement, short wincrement) { return add(-xincrement, -yincrement, -zincrement, -wincrement); } 
	public ByteVector4D subtract(int xincrement, int yincrement, int zincrement, int wincrement) { return add(-xincrement, -yincrement, -zincrement, -wincrement); } 
	public ByteVector4D subtract(long xincrement, long yincrement, long zincrement, long wincrement) { return add(-xincrement, -yincrement, -zincrement, -wincrement); } 
	public ByteVector4D subtract(float xincrement, float yincrement, float zincrement, float wincrement) { return add(-xincrement, -yincrement, -zincrement, -wincrement); } 
	public ByteVector4D subtract(double xincrement, double yincrement, double zincrement, double wincrement) { return add(-xincrement, -yincrement, -zincrement, -wincrement); } 
	public ByteVector4D multiply(short multi) { return set(x * multi, y * multi, z * multi, w * multi); } 
	public ByteVector4D multiply(int multi) { return set(x * multi, y * multi, z * multi, w * multi); } 
	public ByteVector4D multiply(long multi) { return set(x * multi, y * multi, z * multi, w * multi); } 
	public ByteVector4D multiply(float multi) { return set(x * multi, y * multi, z * multi, w * multi); } 
	public ByteVector4D multiply(double multi) { return set(x * multi, y * multi, z * multi, w * multi); } 
	public ByteVector4D divide(short multi) { if(multi == 0) { System.out.println("[ByteVector4D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi, z / multi, w / multi); } 
	public ByteVector4D divide(int multi) { if(multi == 0) { System.out.println("[ByteVector4D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi, z / multi, w / multi); } 
	public ByteVector4D divide(long multi) { if(multi == 0) { System.out.println("[ByteVector4D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi, z / multi, w / multi); } 
	public ByteVector4D divide(float multi) { if(multi == 0) { System.out.println("[ByteVector4D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi, z / multi, w / multi); } 
	public ByteVector4D divide(double multi) { if(multi == 0) { System.out.println("[ByteVector4D] Divide by zero!"); throw new DivideByZeroException(); } return set(x / multi, y / multi, z / multi, w / multi); } 
}
