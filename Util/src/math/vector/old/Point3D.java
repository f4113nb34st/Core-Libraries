package math.vector.old;

import util.pool.ObjectPool;
import math.DivideByZeroException;

/**
 * 
 * A comprehensive 3D Point class.
 * 
 * @author F4113nb34st
 *
 */
public class Point3D extends Struct3D
{
	public static final ObjectPool<Point3D> pool = new ObjectPool<Point3D>(new Point3D());
	
	public int x;
	public int y;
	public int z;
	
	/**
	 * Creates a new vector at (0, 0, 0).
	 */
	public Point3D()
	{
		set(0, 0, 0);
	}
	
	/**
	 * Creates a new vector at (i, j, k).
	 * @param i The x coord of this vector.
	 * @param j The y coord of this vector.
	 * @param k The z coord of this vector.
	 */
	public Point3D(int i, int j, int k)
	{
		set(i, j, k);
	}
	
	/**
	 * Creates a new vector at (s.x, s.y, k).
	 * @param s The 2D Struct for x and y coords.
	 * @param k The z coord of this vector.
	 */
	public Point3D(Struct2D s, int k)
	{
		set(s.getIntX(), s.getIntY(), k);
	}
	
	/**
	 * Creates a new vector at (i, s.x, s.y).
	 * @param i The x coord of this vector.
	 * @param s The 2D Struct for y and z coords.
	 */
	public Point3D(int i, Struct2D s)
	{
		set(i, s.getIntX(), s.getIntY());
	}
	
	/**
	 * Creates a new vector equal to the given vector.
	 * @param vec The vector to set this new one to.
	 */
	public Point3D(Struct3D vec)
	{
		set(vec);
	}
	
	/**
	 * Returns a Vector3D version of this Point.
	 * @return The Vector3D.
	 */
	public Vector3D toVector3D()
	{
		return new Vector3D(this);
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
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public Point3D copy()
	{
		return new Point3D(this);
	}
	
	/**
	 * Sets this vector to i, j, k.
	 * @param i The x coord to set to.
	 * @param j The y coord to set to.
	 * @param k The z coord to set to.
	 * @return This vector.
	 */
	public Point3D set(int i, int j, int k)
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
	public Point3D set(Struct3D vec)
	{
		return set(vec.getIntX(), vec.getIntY(), vec.getIntZ());
	}
	
	/**
	 * Negates this vector.
	 * @return This vector.
	 */
	public Point3D negate()
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
	public Point3D normalize()
	{
		return divide(length());
	}
	
	/**
	 * Adds the given doubles to this vector.
	 * @param xincrement The int to add to x.
	 * @param yincrement The int to add to y.
	 * @param zincrement The int to add to z.
	 * @return This vector.
	 */
	public Point3D add(int xincrement, int yincrement, int zincrement)
	{
		return set(x + xincrement, y + yincrement, z + zincrement);
	}
	
	/**
	 * Adds the given vector to this vector.
	 * @param increment The vector to add.
	 * @return This vector.
	 */
	public Point3D add(Struct3D increment)
	{
		return add(increment.getIntX(), increment.getIntY(), increment.getIntZ());
	}
	
	/**
	 * Subtracts the given doubles from this vector.
	 * @param xincrement The int to subtract from x.
	 * @param yincrement The int to subtract from y.
	 * @param zincrement The int to subtract from to z.
	 * @return This vector.
	 */
	public Point3D subtract(int xincrement, int yincrement, int zincrement)
	{
		return add(-xincrement, -yincrement, -zincrement);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * @param increment The vector to subtract.
	 * @return This vector.
	 */
	public Point3D subtract(Struct3D increment)
	{
		return subtract(increment.getIntX(), increment.getIntY(), increment.getIntZ());
	}
	
	/**
	 * Multiplies this vector by the given double.
	 * @param multi The double to multiply by.
	 * @return This vector.
	 */
	public Point3D multiply(double multi)
	{
		return set((int)(x * multi), (int)(y * multi), (int)(z * multi));
	}
	
	/**
	 * Multiplies this vector by the given vector.
	 * @param multi The vector to multiply by.
	 * @return This vector.
	 */
	public Point3D multiply(Struct3D multi)
	{
		return set((int)(x * multi.getX()), (int)(y * multi.getY()), (int)(z * multi.getZ()));
	}
	
	/**
	 * Divides this vector by the given double.
	 * @param multi The double to divide by.
	 * @return This vector.
	 */
	public Point3D divide(double multi)
	{
		if(multi == 0)
		{
			System.out.println("[Point3D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set((int)(x / multi), (int)(y / multi), (int)(z / multi));
	}
	
	/**
	 * Divides this vector by the given vector.
	 * @param multi The vector to divide by.
	 * @return This vector.
	 */
	public Point3D divide(Struct3D multi)
	{
		if(multi.getX() == 0 || multi.getY() == 0 || multi.getZ() == 0)
		{
			System.out.println("[Point3D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set((int)(x / multi.getX()),(int) (y / multi.getY()), (int)(z / multi.getZ()));
	}
}
