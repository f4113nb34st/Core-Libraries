package math.vector.old;

import util.pool.ObjectPool;
import math.DivideByZeroException;

/**
 * 
 * A comprehensive 3D Vector class.
 * 
 * @author F4113nb34st
 *
 */
public class Vector3D extends Struct3D
{
	public static final ObjectPool<Vector3D> pool = new ObjectPool<Vector3D>(new Vector3D());
	
	public double x;
	public double y;
	public double z;
	
	/**
	 * Creates a new vector at (0, 0, 0).
	 */
	public Vector3D()
	{
		set(0, 0, 0);
	}
	
	/**
	 * Creates a new vector at (i, j, k).
	 * @param i The x coord of this vector.
	 * @param j The y coord of this vector.
	 * @param k The z coord of this vector.
	 */
	public Vector3D(double i, double j, double k)
	{
		set(i, j, k);
	}
	
	/**
	 * Creates a new vector at (s.x, s.y, k).
	 * @param s The 2D Struct for x and y coords.
	 * @param k The z coord of this vector.
	 */
	public Vector3D(Struct2D s, int k)
	{
		set(s.getX(), s.getY(), k);
	}
	
	/**
	 * Creates a new vector at (i, s.x, s.y).
	 * @param i The x coord of this vector.
	 * @param s The 2D Struct for y and z coords.
	 */
	public Vector3D(int i, Struct2D s)
	{
		set(i, s.getX(), s.getY());
	}
	
	/**
	 * Creates a new vector equal to the given vetor.
	 * @param vec The vector to set this new one to.
	 */
	public Vector3D(Struct3D vec)
	{
		set(vec);
	}
	
	/**
	 * Returns a Point3D version of this Vector.
	 * @return The Point3D.
	 */
	public Point3D toPoint3D()
	{
		return new Point3D(this);
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
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public Vector3D copy()
	{
		return new Vector3D(this);
	}
	
	/**
	 * Sets this vector to i, j, k.
	 * @param i The x coord to set to.
	 * @param j The y coord to set to.
	 * @param k The z coord to set to.
	 * @return This vector.
	 */
	public Vector3D set(double i, double j, double k)
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
	public Vector3D set(Struct3D vec)
	{
		return set(vec.getX(), vec.getY(), vec.getZ());
	}
	
	/**
	 * Negates this vector.
	 * @return This vector.
	 */
	public Vector3D negate()
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
	public Vector3D normalize()
	{
		return divide(length());
	}
	
	/**
	 * Adds the given doubles to this vector.
	 * @param xincrement The double to add to x.
	 * @param yincrement The double to add to y.
	 * @param zincrement The double to add to z.
	 * @return This vector.
	 */
	public Vector3D add(double xincrement, double yincrement, double zincrement)
	{
		return set(x + xincrement, y + yincrement, z + zincrement);
	}
	
	/**
	 * Adds the given vector to this vector.
	 * @param increment The vector to add.
	 * @return This vector.
	 */
	public Vector3D add(Struct3D increment)
	{
		return add(increment.getX(), increment.getY(), increment.getZ());
	}
	
	/**
	 * Subtracts the given doubles from this vector.
	 * @param xincrement The double to subtract from x.
	 * @param yincrement The double to subtract from y.
	 * @param zincrement The double to subtract from to z.
	 * @return This vector.
	 */
	public Vector3D subtract(double xincrement, double yincrement, double zincrement)
	{
		return add(-xincrement, -yincrement, -zincrement);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * @param increment The vector to subtract.
	 * @return This vector.
	 */
	public Vector3D subtract(Struct3D increment)
	{
		return subtract(increment.getX(), increment.getY(), increment.getZ());
	}
	
	/**
	 * Multiplies this vector by the given double.
	 * @param multi The double to multiply by.
	 * @return This vector.
	 */
	public Vector3D multiply(double multi)
	{
		return set(x * multi, y * multi, z * multi);
	}
	
	/**
	 * Multiplies this vector by the given vector.
	 * @param multi The vector to multiply by.
	 * @return This vector.
	 */
	public Vector3D multiply(Struct3D multi)
	{
		return set(x * multi.getX(), y * multi.getY(), z * multi.getZ());
	}
	
	/**
	 * Divides this vector by the given double.
	 * @param multi The double to divide by.
	 * @return This vector.
	 */
	public Vector3D divide(double multi)
	{
		if(multi == 0)
		{
			System.out.println("[Vector3D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi, y / multi, z / multi);
	}
	
	/**
	 * Divides this vector by the given vector.
	 * @param multi The vector to divide by.
	 * @return This vector.
	 */
	public Vector3D divide(Struct3D multi)
	{
		if(multi.getX() == 0 || multi.getY() == 0 || multi.getZ() == 0)
		{
			System.out.println("[Vector3D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi.getX(), y / multi.getY(), z / multi.getZ());
	}
	
	/**
	 * Rotates this vector by the given number of radians around the x axis.
	 * @param radians The number of radians to rotate by.
	 * @return This vector.
	 */
	public Vector3D rotateXRad(double radians)
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
	public Vector3D rotateYRad(double radians)
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
	public Vector3D rotateZRad(double radians)
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
	public Vector3D rotateXDeg(double degrees)
	{
		return rotateXRad(degrees * Math.PI / 180);
	}
	
	/**
	 * Rotates this vector by the given number of degrees around the y axis.
	 * @param degrees The number of degrees to rotate by.
	 * @return This vector.
	 */
	public Vector3D rotateYDeg(double degrees)
	{
		return rotateYRad(degrees * Math.PI / 180);
	}
	
	/**
	 * Rotates this vector by the given number of degrees around the z axis.
	 * @param degrees The number of degrees to rotate by.
	 * @return This vector.
	 */
	public Vector3D rotateZDeg(double degrees)
	{
		return rotateZRad(degrees * Math.PI / 180);
	}
}
