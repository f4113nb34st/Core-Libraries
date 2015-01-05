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
public class FVector2D extends Struct2D
{
	public static final ObjectPool<FVector2D> pool = new ObjectPool<FVector2D>(new FVector2D());
	
	public float x;
	public float y;
	
	/**
	 * Creates a new vector at (0, 0).
	 */
	public FVector2D()
	{
		set(0, 0);
	}
	
	/**
	 * Creates a new vector at (i, j).
	 * @param i The x coord of this vector.
	 * @param j The y coord of this vector.
	 */
	public FVector2D(float i, float j)
	{
		set(i, j);
	}
	
	/**
	 * Creates a new vector equal to the given vector.
	 * @param vec The vector to set this new one to.
	 */
	public FVector2D(Struct2D vec)
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
		return Math.round(x);
	}
	
	@Override
	public int getIntY()
	{
		return Math.round(y);
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public FVector2D copy()
	{
		return new FVector2D(this);
	}
	
	/**
	 * Sets this vector to i, j.
	 * @param i The x coord to set to.
	 * @param j The y coord to set to.
	 * @return This vector.
	 */
	public FVector2D set(float i, float j)
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
	public FVector2D set(Struct2D vec)
	{
		return set((float)vec.getX(), (float)vec.getY());
	}
	
	/**
	 * Negates this vector.
	 * @return This vector.
	 */
	public FVector2D negate()
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
	public FVector2D normalize()
	{
		return divide((float)length());
	}
	
	/**
	 * Adds the given doubles to this vector.
	 * @param xincrement The double to add to x.
	 * @param yincrement The double to add to y.
	 * @return This vector.
	 */
	public FVector2D add(float xincrement, float yincrement)
	{
		return set(x + xincrement, y + yincrement);
	}
	
	/**
	 * Adds the given vector to this vector.
	 * @param increment The vector to add.
	 * @return This vector.
	 */
	public FVector2D add(Struct2D increment)
	{
		return add((float)increment.getX(), (float)increment.getY());
	}
	
	/**
	 * Subtracts the given doubles from this vector.
	 * @param increment The double to subtract from x.
	 * @param increment The double to subtract from y.
	 * @return This vector.
	 */
	public FVector2D subtract(float xincrement, float yincrement)
	{
		return add(-xincrement, -yincrement);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * @param increment The vector to subtract.
	 * @return This vector.
	 */
	public FVector2D subtract(Struct2D increment)
	{
		return subtract((float)increment.getX(), (float)increment.getY());
	}
	
	/**
	 * Multiplies this vector by the given double.
	 * @param multi The double to multiply by.
	 * @return This vector.
	 */
	public FVector2D multiply(float multi)
	{
		return set(x * multi, y * multi);
	}
	
	/**
	 * Multiplies this vector by the given vector.
	 * @param multi The vector to multiply by.
	 * @return This vector.
	 */
	public FVector2D multiply(Struct2D multi)
	{
		return set(x * (float)multi.getX(), y * (float)multi.getY());
	}
	
	/**
	 * Divides this vector by the given double.
	 * @param multi The double to divide by.
	 * @return This vector.
	 */
	public FVector2D divide(float multi)
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
	public FVector2D divide(Struct2D multi)
	{
		if(multi.getX() == 0 || multi.getY() == 0)
		{
			System.out.println("[Vector2D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / (float)multi.getX(), y / (float)multi.getY());
	}
	
	/**
	 * Rotates this vector by the given number of radians.
	 * @param radians The number of radians to rotate by.
	 * @return This vector.
	 */
	public FVector2D rotateRad(float radians)
	{
		float cos = (float)Math.cos(radians);
		float sin = (float)Math.sin(radians);
		
		return set((x * cos) - (y * sin), (x * sin) + (y * cos));
	}
	
	/**
	 * Rotates this vector by the given number of degrees.
	 * @param degrees The number of degrees to rotate by.
	 * @return This vector.
	 */
	public FVector2D rotateDeg(float degrees)
	{
		return rotateRad(degrees * (float)Math.PI / 180);
	}
}
