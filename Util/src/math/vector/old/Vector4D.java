package math.vector.old;

import util.pool.ObjectPool;
import math.DivideByZeroException;

/**
 * 
 * A comprehensive 4D Vector class.
 * 
 * @author F4113nb34st
 *
 */
public class Vector4D extends Struct4D
{
	public static final ObjectPool<Vector4D> pool = new ObjectPool<Vector4D>(new Vector4D());
	
	public double x;
	public double y;
	public double z;
	public double w;
	
	/**
	 * Creates a new vector at (0, 0, 0, 0).
	 */
	public Vector4D()
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
	public Vector4D(double i, double j, double k, double l)
	{
		set(i, j, k, l);
	}
	
	/**
	 * Creates a new vector at (s.x, s.y, k, l).
	 * @param s The 2D Struct for x and y coords.
	 * @param k The z coord of this vector.
	 * @param l The w coord of this vector.
	 */
	public Vector4D(Struct2D s, double k, double l)
	{
		set(s.getX(), s.getY(), k, l);
	}
	
	/**
	 * Creates a new vector at (i, s.x, s.y, l).
	 * @param i The x coord of this vector.
	 * @param s The 2D Struct for y and z coords.
	 * @param l The w coord of this vector.
	 */
	public Vector4D(double i, Struct2D s, double l)
	{
		set(i, s.getX(), s.getY(), l);
	}
	
	/**
	 * Creates a new vector at (i, j, s.x, s.y).
	 * @param i The x coord of this vector.
	 * @param j The y coord of this vector.
	 * @param s The 2D Struct for z and w coords.
	 */
	public Vector4D(double i, double j, Struct2D s)
	{
		set(i, j, s.getX(), s.getY());
	}
	
	/**
	 * Creates a new vector at (s1.x, s1.y, s2.x, s2.y).
	 * @param s1 The 2D Struct for x and y coords.
	 * @param s2 The 2D Struct for z and w coords.
	 */
	public Vector4D(Struct2D s1, Struct2D s2)
	{
		set(s1.getX(), s1.getY(), s2.getX(), s2.getY());
	}
	
	/**
	 * Creates a new vector at (s.x, s.y, s.z, k).
	 * @param s The 3D Struct for x, y, and z coords.
	 * @param l The w coord of this vector.
	 */
	public Vector4D(Struct3D s, double l)
	{
		set(s.getX(), s.getY(), s.getZ(), l);
	}
	
	/**
	 * Creates a new vector at (i, s.x, s.y, s.z).
	 * @param i The x coord of this vector.
	 * @param s The 2D Struct for y, z, and w coords.
	 */
	public Vector4D(double i, Struct3D s)
	{
		set(i, s.getX(), s.getY(), s.getZ());
	}
	
	/**
	 * Creates a new vector equal to the given vector.
	 * @param vec The vector to set this new one to.
	 */
	public Vector4D(Struct4D vec)
	{
		set(vec);
	}
	
	/**
	 * Returns a Vector3D version of this Podouble.
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
		return (int)Math.round(x);
	}
	
	@Override
	public int getIntY()
	{
		return (int)Math.round(y);
	}
	
	@Override
	public int getIntZ()
	{
		return (int)Math.round(z);
	}
	
	@Override
	public int getIntW()
	{
		return (int)Math.round(w);
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public Vector4D copy()
	{
		return new Vector4D(this);
	}
	
	/**
	 * Sets this vector to i, j, k, l.
	 * @param i The x coord to set to.
	 * @param j The y coord to set to.
	 * @param k The z coord to set to.
	 * @param l The w coord to set to.
	 * @return This vector.
	 */
	public Vector4D set(double i, double j, double k, double l)
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
	public Vector4D set(Struct4D vec)
	{
		return set(vec.getX(), vec.getY(), vec.getZ(), vec.getW());
	}
	
	/**
	 * Negates this vector.
	 * @return This vector.
	 */
	public Vector4D negate()
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
	public Vector4D normalize()
	{
		return divide(length());
	}
	
	/**
	 * Adds the given doubles to this vector.
	 * @param xincrement The double to add to x.
	 * @param yincrement The double to add to y.
	 * @param zincrement The double to add to z.
	 * @param wincrement The double to add to w.
	 * @return This vector.
	 */
	public Vector4D add(double xincrement, double yincrement, double zincrement, double wincrement)
	{
		return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement);
	}
	
	/**
	 * Adds the given vector to this vector.
	 * @param increment The vector to add.
	 * @return This vector.
	 */
	public Vector4D add(Struct4D increment)
	{
		return add(increment.getX(), increment.getY(), increment.getZ(), increment.getW());
	}
	
	/**
	 * Subtracts the given doubles from this vector.
	 * @param xincrement The double to subtract from x.
	 * @param yincrement The double to subtract from y.
	 * @param zincrement The double to subtract from z.
	 * @param wincrement The double to subtract from w.
	 * @return This vector.
	 */
	public Vector4D subtract(double xincrement, double yincrement, double zincrement, double wincrement)
	{
		return add(-xincrement, -yincrement, -zincrement, -wincrement);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * @param increment The vector to subtract.
	 * @return This vector.
	 */
	public Vector4D subtract(Struct4D increment)
	{
		return subtract(increment.getX(), increment.getY(), increment.getZ(), increment.getW());
	}
	
	/**
	 * Multiplies this vector by the given double.
	 * @param multi The double to multiply by.
	 * @return This vector.
	 */
	public Vector4D multiply(double multi)
	{
		return set(x * multi, y * multi, z * multi, w * multi);
	}
	
	/**
	 * Multiplies this vector by the given vector.
	 * @param multi The vector to multiply by.
	 * @return This vector.
	 */
	public Vector4D multiply(Struct4D multi)
	{
		return set(x * multi.getX(), y * multi.getY(), z * multi.getZ(), w * multi.getW());
	}
	
	/**
	 * Divides this vector by the given double.
	 * @param multi The double to divide by.
	 * @return This vector.
	 */
	public Vector4D divide(double multi)
	{
		if(multi == 0)
		{
			System.out.println("[Vector4D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi, y / multi, z / multi, w / multi);
	}
	
	/**
	 * Divides this vector by the given vector.
	 * @param multi The vector to divide by.
	 * @return This vector.
	 */
	public Vector4D divide(Struct4D multi)
	{
		if(multi.getX() == 0 || multi.getY() == 0 || multi.getZ() == 0 || multi.getW() == 0)
		{
			System.out.println("[Vector4D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi.getX(), y / multi.getY(), z / multi.getZ(), w / multi.getW());
	}
}
