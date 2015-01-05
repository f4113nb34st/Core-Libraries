package math.vector.old;

import util.pool.ObjectPool;
import math.DivideByZeroException;

/**
 * 
 * A comprehensive 4D Point class.
 * 
 * @author F4113nb34st
 *
 */
public class Point4D extends Struct4D
{
	public static final ObjectPool<Point4D> pool = new ObjectPool<Point4D>(new Point4D());
	
	public int x;
	public int y;
	public int z;
	public int w;
	
	/**
	 * Creates a new vector at (0, 0, 0, 0).
	 */
	public Point4D()
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
	public Point4D(int i, int j, int k, int l)
	{
		set(i, j, k, l);
	}
	
	/**
	 * Creates a new vector at (s.x, s.y, k, l).
	 * @param s The 2D Struct for x and y coords.
	 * @param k The z coord of this vector.
	 * @param l The w coord of this vector.
	 */
	public Point4D(Struct2D s, int k, int l)
	{
		set(s.getIntX(), s.getIntY(), k, l);
	}
	
	/**
	 * Creates a new vector at (i, s.x, s.y, l).
	 * @param i The x coord of this vector.
	 * @param s The 2D Struct for y and z coords.
	 * @param l The w coord of this vector.
	 */
	public Point4D(int i, Struct2D s, int l)
	{
		set(i, s.getIntX(), s.getIntY(), l);
	}
	
	/**
	 * Creates a new vector at (i, j, s.x, s.y).
	 * @param i The x coord of this vector.
	 * @param j The y coord of this vector.
	 * @param s The 2D Struct for z and w coords.
	 */
	public Point4D(int i, int j, Struct2D s)
	{
		set(i, j, s.getIntX(), s.getIntY());
	}
	
	/**
	 * Creates a new vector at (s1.x, s1.y, s2.x, s2.y).
	 * @param s1 The 2D Struct for x and y coords.
	 * @param s2 The 2D Struct for z and w coords.
	 */
	public Point4D(Struct2D s1, Struct2D s2)
	{
		set(s1.getIntX(), s1.getIntY(), s2.getIntX(), s2.getIntY());
	}
	
	/**
	 * Creates a new vector at (s.x, s.y, s.z, k).
	 * @param s The 3D Struct for x, y, and z coords.
	 * @param l The w coord of this vector.
	 */
	public Point4D(Struct3D s, int l)
	{
		set(s.getIntX(), s.getIntY(), s.getIntZ(), l);
	}
	
	/**
	 * Creates a new vector at (i, s.x, s.y, s.z).
	 * @param i The x coord of this vector.
	 * @param s The 2D Struct for y, z, and w coords.
	 */
	public Point4D(int i, Struct3D s)
	{
		set(i, s.getIntX(), s.getIntY(), s.getIntZ());
	}
	
	/**
	 * Creates a new vector equal to the given vector.
	 * @param vec The vector to set this new one to.
	 */
	public Point4D(Struct4D vec)
	{
		set(vec);
	}
	
	/**
	 * Returns a Vector3D version of this Point.
	 * @return The Vector3D.
	 */
	public Vector4D toVector4D()
	{
		return new Vector4D(this);
	}
	
	@Override
	public double getX()
	{
		return x;
	}
	
	@Override
	public double getY()
	{
		return y;
	}
	
	@Override
	public double getZ()
	{
		return z;
	}
	
	@Override
	public double getW()
	{
		return w;
	}
	
	@Override
	public int getIntX()
	{
		return x;
	}
	
	@Override
	public int getIntY()
	{
		return y;
	}
	
	@Override
	public int getIntZ()
	{
		return z;
	}
	
	@Override
	public int getIntW()
	{
		return w;
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public Point4D copy()
	{
		return new Point4D(this);
	}
	
	/**
	 * Sets this vector to i, j, k, l.
	 * @param i The x coord to set to.
	 * @param j The y coord to set to.
	 * @param k The z coord to set to.
	 * @param l The w coord to set to.
	 * @return This vector.
	 */
	public Point4D set(int i, int j, int k, int l)
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
	public Point4D set(Struct4D vec)
	{
		return set(vec.getIntX(), vec.getIntY(), vec.getIntZ(), vec.getIntW());
	}
	
	/**
	 * Negates this vector.
	 * @return This vector.
	 */
	public Point4D negate()
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
	public Point4D normalize()
	{
		return divide(length());
	}
	
	/**
	 * Adds the given doubles to this vector.
	 * @param xincrement The int to add to x.
	 * @param yincrement The int to add to y.
	 * @param zincrement The int to add to z.
	 * @param wincrement The int to add to w.
	 * @return This vector.
	 */
	public Point4D add(int xincrement, int yincrement, int zincrement, int wincrement)
	{
		return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement);
	}
	
	/**
	 * Adds the given vector to this vector.
	 * @param increment The vector to add.
	 * @return This vector.
	 */
	public Point4D add(Struct4D increment)
	{
		return add(increment.getIntX(), increment.getIntY(), increment.getIntZ(), increment.getIntW());
	}
	
	/**
	 * Subtracts the given doubles from this vector.
	 * @param xincrement The int to subtract from x.
	 * @param yincrement The int to subtract from y.
	 * @param zincrement The int to subtract from z.
	 * @param wincrement The int to subtract from w.
	 * @return This vector.
	 */
	public Point4D subtract(int xincrement, int yincrement, int zincrement, int wincrement)
	{
		return add(-xincrement, -yincrement, -zincrement, -wincrement);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * @param increment The vector to subtract.
	 * @return This vector.
	 */
	public Point4D subtract(Struct4D increment)
	{
		return subtract(increment.getIntX(), increment.getIntY(), increment.getIntZ(), increment.getIntW());
	}
	
	/**
	 * Multiplies this vector by the given double.
	 * @param multi The double to multiply by.
	 * @return This vector.
	 */
	public Point4D multiply(double multi)
	{
		return set((int)(x * multi), (int)(y * multi), (int)(z * multi), (int)(w * multi));
	}
	
	/**
	 * Multiplies this vector by the given vector.
	 * @param multi The vector to multiply by.
	 * @return This vector.
	 */
	public Point4D multiply(Struct4D multi)
	{
		return set((int)(x * multi.getX()), (int)(y * multi.getY()), (int)(z * multi.getZ()), (int)(w * multi.getW()));
	}
	
	/**
	 * Divides this vector by the given double.
	 * @param multi The double to divide by.
	 * @return This vector.
	 */
	public Point4D divide(double multi)
	{
		if(multi == 0)
		{
			System.out.println("[Point4D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set((int)(x / multi), (int)(y / multi), (int)(z / multi), (int)(w / multi));
	}
	
	/**
	 * Divides this vector by the given vector.
	 * @param multi The vector to divide by.
	 * @return This vector.
	 */
	public Point4D divide(Struct4D multi)
	{
		if(multi.getX() == 0 || multi.getY() == 0 || multi.getZ() == 0 || multi.getW() == 0)
		{
			System.out.println("[Point4D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set((int)(x / multi.getX()),(int) (y / multi.getY()), (int)(z / multi.getZ()), (int)(w / multi.getW()));
	}
}
