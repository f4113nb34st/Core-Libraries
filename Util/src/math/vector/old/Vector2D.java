package math.vector.old;

import util.pool.ObjectPool;
import math.DivideByZeroException;

/**
 * 
 * A comprehensive 2D Vector class.
 * 
 * @author F4113nb34st
 *
 */
public class Vector2D extends Struct2D
{
	public static final ObjectPool<Vector2D> pool = new ObjectPool<Vector2D>(new Vector2D());
	
	public double x;
	public double y;
	
	/**
	 * Creates a new vector at (0, 0).
	 */
	public Vector2D()
	{
		set(0, 0);
	}
	
	/**
	 * Creates a new vector at (i, j).
	 * @param i The x coord of this vector.
	 * @param j The y coord of this vector.
	 */
	public Vector2D(double i, double j)
	{
		set(i, j);
	}
	
	/**
	 * Creates a new vector equal to the given vector.
	 * @param vec The vector to set this new one to.
	 */
	public Vector2D(Struct2D vec)
	{
		set(vec);
	}
	
	/**
	 * Returns a Point2D version of this Vector.
	 * @return The Point2D.
	 */
	public Point2D toPoint2D()
	{
		return new Point2D(this);
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
	public int getIntX()
	{
		return (int)Math.round(x);
	}
	
	@Override
	public int getIntY()
	{
		return (int)Math.round(y);
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public Vector2D copy()
	{
		return new Vector2D(this);
	}
	
	/**
	 * Sets this vector to i, j.
	 * @param i The x coord to set to.
	 * @param j The y coord to set to.
	 * @return This vector.
	 */
	public Vector2D set(double i, double j)
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
	public Vector2D set(Struct2D vec)
	{
		return set(vec.getX(), vec.getY());
	}
	
	/**
	 * Negates this vector.
	 * @return This vector.
	 */
	public Vector2D negate()
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
	public Vector2D normalize()
	{
		return divide(length());
	}
	
	/**
	 * Adds the given doubles to this vector.
	 * @param xincrement The double to add to x.
	 * @param yincrement The double to add to y.
	 * @return This vector.
	 */
	public Vector2D add(double xincrement, double yincrement)
	{
		return set(x + xincrement, y + yincrement);
	}
	
	/**
	 * Adds the given vector to this vector.
	 * @param increment The vector to add.
	 * @return This vector.
	 */
	public Vector2D add(Struct2D increment)
	{
		return add(increment.getX(), increment.getY());
	}
	
	/**
	 * Subtracts the given doubles from this vector.
	 * @param increment The double to subtract from x.
	 * @param increment The double to subtract from y.
	 * @return This vector.
	 */
	public Vector2D subtract(double xincrement, double yincrement)
	{
		return add(-xincrement, -yincrement);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * @param increment The vector to subtract.
	 * @return This vector.
	 */
	public Vector2D subtract(Struct2D increment)
	{
		return subtract(increment.getX(), increment.getY());
	}
	
	/**
	 * Multiplies this vector by the given double.
	 * @param multi The double to multiply by.
	 * @return This vector.
	 */
	public Vector2D multiply(double multi)
	{
		return set(x * multi, y * multi);
	}
	
	/**
	 * Multiplies this vector by the given vector.
	 * @param multi The vector to multiply by.
	 * @return This vector.
	 */
	public Vector2D multiply(Struct2D multi)
	{
		return set(x * multi.getX(), y * multi.getY());
	}
	
	/**
	 * Divides this vector by the given double.
	 * @param multi The double to divide by.
	 * @return This vector.
	 */
	public Vector2D divide(double multi)
	{
		if(multi == 0)
		{
			System.out.println("[Vector2D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi, y / multi);
	}
	
	/**
	 * Divides this vector by the given vector.
	 * @param multi The vector to divide by.
	 * @return This vector.
	 */
	public Vector2D divide(Struct2D multi)
	{
		if(multi.getX() == 0 || multi.getY() == 0)
		{
			System.out.println("[Vector2D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / multi.getX(), y / multi.getY());
	}
	
	/**
	 * Rotates this vector by the given number of radians.
	 * @param radians The number of radians to rotate by.
	 * @return This vector.
	 */
	public Vector2D rotateRad(double radians)
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
	public Vector2D rotateDeg(double degrees)
	{
		return rotateRad(degrees * Math.PI / 180);
	}
}
