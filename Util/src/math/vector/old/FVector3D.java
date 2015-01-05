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
public class FVector3D extends Struct3D
{
	public static final ObjectPool<FVector3D> pool = new ObjectPool<FVector3D>(new FVector3D());
	
	public float x;
	public float y;
	public float z;
	
	/**
	 * Creates a new vector at (0, 0, 0).
	 */
	public FVector3D()
	{
		set(0, 0, 0);
	}
	
	/**
	 * Creates a new vector at (i, j, k).
	 * @param i The x coord of this vector.
	 * @param j The y coord of this vector.
	 * @param k The z coord of this vector.
	 */
	public FVector3D(float i, float j, float k)
	{
		set(i, j, k);
	}
	
	/**
	 * Creates a new vector at (s.x, s.y, k).
	 * @param s The 2D Struct for x and y coords.
	 * @param k The z coord of this vector.
	 */
	public FVector3D(Struct2D s, int k)
	{
		set((float)s.getX(), (float)s.getY(), k);
	}
	
	/**
	 * Creates a new vector at (i, s.x, s.y).
	 * @param i The x coord of this vector.
	 * @param s The 2D Struct for y and z coords.
	 */
	public FVector3D(int i, Struct2D s)
	{
		set(i, (float)s.getX(), (float)s.getY());
	}
	
	/**
	 * Creates a new vector equal to the given vetor.
	 * @param vec The vector to set this new one to.
	 */
	public FVector3D(Struct3D vec)
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
		return Math.round(x);
	}
	
	@Override
	public int getIntY()
	{
		return Math.round(y);
	}
	
	@Override
	public int getIntZ()
	{
		return Math.round(z);
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public FVector3D copy()
	{
		return new FVector3D(this);
	}
	
	/**
	 * Sets this vector to i, j, k.
	 * @param i The x coord to set to.
	 * @param j The y coord to set to.
	 * @param k The z coord to set to.
	 * @return This vector.
	 */
	public FVector3D set(float i, float j, float k)
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
	public FVector3D set(Struct3D vec)
	{
		return set((float)vec.getX(), (float)vec.getY(), (float)vec.getZ());
	}
	
	/**
	 * Negates this vector.
	 * @return This vector.
	 */
	public FVector3D negate()
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
	public FVector3D normalize()
	{
		return divide((float)length());
	}
	
	/**
	 * Adds the given doubles to this vector.
	 * @param xincrement The double to add to x.
	 * @param yincrement The double to add to y.
	 * @param zincrement The double to add to z.
	 * @return This vector.
	 */
	public FVector3D add(float xincrement, float yincrement, float zincrement)
	{
		return set(x + xincrement, y + yincrement, z + zincrement);
	}
	
	/**
	 * Adds the given vector to this vector.
	 * @param increment The vector to add.
	 * @return This vector.
	 */
	public FVector3D add(Struct3D increment)
	{
		return add((float)increment.getX(), (float)increment.getY(), (float)increment.getZ());
	}
	
	/**
	 * Subtracts the given doubles from this vector.
	 * @param xincrement The double to subtract from x.
	 * @param yincrement The double to subtract from y.
	 * @param zincrement The double to subtract from to z.
	 * @return This vector.
	 */
	public FVector3D subtract(float xincrement, float yincrement, float zincrement)
	{
		return add(-xincrement, -yincrement, -zincrement);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * @param increment The vector to subtract.
	 * @return This vector.
	 */
	public FVector3D subtract(Struct3D increment)
	{
		return subtract((float)increment.getX(), (float)increment.getY(), (float)increment.getZ());
	}
	
	/**
	 * Multiplies this vector by the given double.
	 * @param multi The double to multiply by.
	 * @return This vector.
	 */
	public FVector3D multiply(float multi)
	{
		return set(x * multi, y * multi, z * multi);
	}
	
	/**
	 * Multiplies this vector by the given vector.
	 * @param multi The vector to multiply by.
	 * @return This vector.
	 */
	public FVector3D multiply(Struct3D multi)
	{
		return set(x * (float)multi.getX(), y * (float)multi.getY(), z * (float)multi.getZ());
	}
	
	/**
	 * Divides this vector by the given double.
	 * @param multi The double to divide by.
	 * @return This vector.
	 */
	public FVector3D divide(float multi)
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
	public FVector3D divide(Struct3D multi)
	{
		if(multi.getX() == 0 || multi.getY() == 0 || multi.getZ() == 0)
		{
			System.out.println("[Vector3D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / (float)multi.getX(), y / (float)multi.getY(), z / (float)multi.getZ());
	}
	
	/**
	 * Rotates this vector by the given number of radians around the x axis.
	 * @param radians The number of radians to rotate by.
	 * @return This vector.
	 */
	public FVector3D rotateXRad(float radians)
	{
		float cos = (float)Math.cos(radians);
		float sin = (float)Math.sin(radians);
		
		return set(x, (y * cos) - (z * sin), (y * sin) + (z * cos));
	}
	
	/**
	 * Rotates this vector by the given number of radians around the y axis.
	 * @param radians The number of radians to rotate by.
	 * @return This vector.
	 */
	public FVector3D rotateYRad(float radians)
	{
		float cos = (float)Math.cos(radians);
		float sin = (float)Math.sin(radians);
		
		return set((z * sin) + (x * cos), y, (z * cos) - (x * sin));
	}
	
	/**
	 * Rotates this vector by the given number of radians around the z axis.
	 * @param radians The number of radians to rotate by.
	 * @return This vector.
	 */
	public FVector3D rotateZRad(float radians)
	{
		float cos = (float)Math.cos(radians);
		float sin = (float)Math.sin(radians);
		
		return set((x * cos) - (y * sin), (x * sin) + (y * cos), z);
	}
	
	/**
	 * Rotates this vector by the given number of degrees around the x axis.
	 * @param degrees The number of degrees to rotate by.
	 * @return This vector.
	 */
	public FVector3D rotateXDeg(float degrees)
	{
		return rotateXRad(degrees * (float)Math.PI / 180);
	}
	
	/**
	 * Rotates this vector by the given number of degrees around the y axis.
	 * @param degrees The number of degrees to rotate by.
	 * @return This vector.
	 */
	public FVector3D rotateYDeg(float degrees)
	{
		return rotateYRad(degrees * (float)Math.PI / 180);
	}
	
	/**
	 * Rotates this vector by the given number of degrees around the z axis.
	 * @param degrees The number of degrees to rotate by.
	 * @return This vector.
	 */
	public FVector3D rotateZDeg(float degrees)
	{
		return rotateZRad(degrees * (float)Math.PI / 180);
	}
}
