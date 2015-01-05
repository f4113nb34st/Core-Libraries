package math.vector.old;

import util.pool.ObjectPool;
import math.DivideByZeroException;

/**
 * 
 * A comprehensive 2D Point class.
 * 
 * @author F4113nb34st
 *
 */
public class Point2D extends Struct2D
{
	public static final ObjectPool<Point2D> pool = new ObjectPool<Point2D>(new Point2D());
	
	public int x;
	public int y;
	
	/**
	 * Creates a new point at (0, 0).
	 */
	public Point2D()
	{
		set(0, 0);
	}
	
	/**
	 * Creates a new point at (i, j).
	 * @param i The x coord of this point.
	 * @param j The y coord of this point.
	 */
	public Point2D(int i, int j)
	{
		set(i, j);
	}
	
	/**
	 * Creates a new point equal to the given point.
	 * @param point The point to set this new one to.
	 */
	public Point2D(Struct2D struct)
	{
		set(struct);
	}
	
	/**
	 * Returns a Vector2D version of this Point.
	 * @return The Vector2D.
	 */
	public Vector2D toVector2D()
	{
		return new Vector2D(this);
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
		return x;
	}
	
	@Override
	public int getIntY()
	{
		return y;
	}
	
	/**
	 * Returns a copy of this point.
	 * @return The copy of this point.
	 */
	@Override
	public Point2D copy()
	{
		return new Point2D(this);
	}
	
	/**
	 * Sets this point to i, j.
	 * @param i The x coord to set to.
	 * @param j The y coord to set to.
	 * @return This point.
	 */
	public Point2D set(int i, int j)
	{
		x = i;
		y = j;
		return this;
	}
	
	/**
	 * Sets this point to the given point.
	 * @param vec The point to set to.
	 * @return This point.
	 */
	public Point2D set(Struct2D vec)
	{
		return set(vec.getIntX(), vec.getIntY());
	}
	
	/**
	 * Negates this point.
	 * @return This point.
	 */
	public Point2D negate()
	{
		return set(-x, -y);
	}
	
	/**
	 * Returns the length of this point squared.
	 * @return The squared length.
	 */
	public double lengthSq()
	{
		return (x * x) + (y * y);
	}
	
	/**
	 * Returns the length of this point.
	 * @return The length.
	 */
	public double length()
	{
		return Math.sqrt(lengthSq());
	}
	
	/**
	 * Adds the given ints to this point.
	 * @param xincrement The int to add to x.
	 * @param yincrement The int to add to y.
	 * @return This point.
	 */
	public Point2D add(int xincrement, int yincrement)
	{
		return set(x + xincrement, y + yincrement);
	}
	
	/**
	 * Adds the given point to this point.
	 * @param increment The point to add.
	 * @return This point.
	 */
	public Point2D add(Struct2D increment)
	{
		return add(increment.getIntX(), increment.getIntY());
	}
	
	/**
	 * Subtracts the given ints from this vector.
	 * @param increment The int to subtract from x.
	 * @param increment The int to subtract from y.
	 * @return This vector.
	 */
	public Point2D subtract(int xincrement, int yincrement)
	{
		return add(-xincrement, -yincrement);
	}
	
	/**
	 * Subtracts the given point from this point.
	 * @param increment The point to subtract.
	 * @return This point.
	 */
	public Point2D subtract(Struct2D increment)
	{
		return subtract(increment.getIntX(), increment.getIntY());
	}
	
	/**
	 * Multiplies this point by the given double.
	 * @param multi The double to multiply by.
	 * @return This point.
	 */
	public Point2D multiply(double multi)
	{
		return set((int)(x * multi), (int)(y * multi));
	}
	
	/**
	 * Multiplies this point by the given point.
	 * @param multi The point to multiply by.
	 * @return This point.
	 */
	public Point2D multiply(Struct2D multi)
	{
		return set((int)(x * multi.getX()), (int)(y * multi.getY()));
	}
	
	/**
	 * Divides this point by the given double.
	 * @param multi The double to divide by.
	 * @return This point.
	 */
	public Point2D divide(double multi)
	{
		if(multi == 0)
		{
			System.out.println("[Point2D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set((int)(x / multi), (int)(y / multi));
	}
	
	/**
	 * Divides this point by the given point.
	 * @param multi The point to divide by.
	 * @return This point.
	 */
	public Point2D divide(Struct2D multi)
	{
		if(multi.getX() == 0 || multi.getY() == 0)
		{
			System.out.println("[Point2D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set((int)(x / multi.getX()), (int)(y / multi.getY()));
	}
	
	/**
	 * Rotates this vector by the given number of radians.
	 * @param radians The number of radians to rotate by.
	 * @return This vector.
	 */
	public Point2D rotateRad(double radians)
	{
		double cos = Math.cos(radians);
		double sin = Math.sin(radians);
		
		return set((int)((x * cos) - (y * sin)), (int)((x * sin) + (y * cos)));
	}
	
	/**
	 * Rotates this vector by the given number of degrees.
	 * @param degrees The number of degrees to rotate by.
	 * @return This vector.
	 */
	public Point2D rotateDeg(double degrees)
	{
		return rotateRad(degrees * Math.PI / 180);
	}
}
