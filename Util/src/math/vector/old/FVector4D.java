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
public class FVector4D extends Struct4D
{
	public static final ObjectPool<FVector4D> pool = new ObjectPool<FVector4D>(new FVector4D());
	
	public float x;
	public float y;
	public float z;
	public float w;
	
	/**
	 * Creates a new vector at (0, 0, 0, 0).
	 */
	public FVector4D()
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
	public FVector4D(float i, float j, float k, float l)
	{
		set(i, j, k, l);
	}
	
	/**
	 * Creates a new vector at (s.x, s.y, k, l).
	 * @param s The 2D Struct for x and y coords.
	 * @param k The z coord of this vector.
	 * @param l The w coord of this vector.
	 */
	public FVector4D(Struct2D s, float k, float l)
	{
		set((float)s.getX(), (float)s.getY(), k, l);
	}
	
	/**
	 * Creates a new vector at (i, s.x, s.y, l).
	 * @param i The x coord of this vector.
	 * @param s The 2D Struct for y and z coords.
	 * @param l The w coord of this vector.
	 */
	public FVector4D(float i, Struct2D s, float l)
	{
		set(i, (float)s.getX(), (float)s.getY(), l);
	}
	
	/**
	 * Creates a new vector at (i, j, s.x, s.y).
	 * @param i The x coord of this vector.
	 * @param j The y coord of this vector.
	 * @param s The 2D Struct for z and w coords.
	 */
	public FVector4D(float i, float j, Struct2D s)
	{
		set(i, j, (float)s.getX(), (float)s.getY());
	}
	
	/**
	 * Creates a new vector at (s1.x, s1.y, s2.x, s2.y).
	 * @param s1 The 2D Struct for x and y coords.
	 * @param s2 The 2D Struct for z and w coords.
	 */
	public FVector4D(Struct2D s1, Struct2D s2)
	{
		set((float)s1.getX(), (float)s1.getY(), (float)s2.getX(), (float)s2.getY());
	}
	
	/**
	 * Creates a new vector at (s.x, s.y, s.z, k).
	 * @param s The 3D Struct for x, y, and z coords.
	 * @param l The w coord of this vector.
	 */
	public FVector4D(Struct3D s, float l)
	{
		set((float)s.getX(), (float)s.getY(), (float)s.getZ(), l);
	}
	
	/**
	 * Creates a new vector at (i, s.x, s.y, s.z).
	 * @param i The x coord of this vector.
	 * @param s The 2D Struct for y, z, and w coords.
	 */
	public FVector4D(float i, Struct3D s)
	{
		set(i, (float)s.getX(), (float)s.getY(), (float)s.getZ());
	}
	
	/**
	 * Creates a new vector equal to the given vector.
	 * @param vec The vector to set this new one to.
	 */
	public FVector4D(Struct4D vec)
	{
		set(vec);
	}
	
	/**
	 * Returns a Vector3D version of this Podouble.
	 * @return The Vector3D.
	 */
	public FVector4D toVector4D()
	{
		return new FVector4D(this);
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
	
	@Override
	public int getIntW()
	{
		return Math.round(w);
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public FVector4D copy()
	{
		return new FVector4D(this);
	}
	
	/**
	 * Sets this vector to i, j, k, l.
	 * @param i The x coord to set to.
	 * @param j The y coord to set to.
	 * @param k The z coord to set to.
	 * @param l The w coord to set to.
	 * @return This vector.
	 */
	public FVector4D set(float i, float j, float k, float l)
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
	public FVector4D set(Struct4D vec)
	{
		return set((float)vec.getX(), (float)vec.getY(), (float)vec.getZ(), (float)vec.getW());
	}
	
	/**
	 * Negates this vector.
	 * @return This vector.
	 */
	public FVector4D negate()
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
	public FVector4D normalize()
	{
		return divide((float)length());
	}
	
	/**
	 * Adds the given doubles to this vector.
	 * @param xincrement The double to add to x.
	 * @param yincrement The double to add to y.
	 * @param zincrement The double to add to z.
	 * @param wincrement The double to add to w.
	 * @return This vector.
	 */
	public FVector4D add(float xincrement, float yincrement, float zincrement, float wincrement)
	{
		return set(x + xincrement, y + yincrement, z + zincrement, w + wincrement);
	}
	
	/**
	 * Adds the given vector to this vector.
	 * @param increment The vector to add.
	 * @return This vector.
	 */
	public FVector4D add(Struct4D increment)
	{
		return add((float)increment.getX(), (float)increment.getY(), (float)increment.getZ(), (float)increment.getW());
	}
	
	/**
	 * Subtracts the given doubles from this vector.
	 * @param xincrement The double to subtract from x.
	 * @param yincrement The double to subtract from y.
	 * @param zincrement The double to subtract from z.
	 * @param wincrement The double to subtract from w.
	 * @return This vector.
	 */
	public FVector4D subtract(float xincrement, float yincrement, float zincrement, float wincrement)
	{
		return add(-xincrement, -yincrement, -zincrement, -wincrement);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * @param increment The vector to subtract.
	 * @return This vector.
	 */
	public FVector4D subtract(Struct4D increment)
	{
		return subtract((float)increment.getX(), (float)increment.getY(), (float)increment.getZ(), (float)increment.getW());
	}
	
	/**
	 * Multiplies this vector by the given double.
	 * @param multi The double to multiply by.
	 * @return This vector.
	 */
	public FVector4D multiply(float multi)
	{
		return set(x * multi, y * multi, z * multi, w * multi);
	}
	
	/**
	 * Multiplies this vector by the given vector.
	 * @param multi The vector to multiply by.
	 * @return This vector.
	 */
	public FVector4D multiply(Struct4D multi)
	{
		return set(x * (float)multi.getX(), y * (float)multi.getY(), z * (float)multi.getZ(), w * (float)multi.getW());
	}
	
	/**
	 * Divides this vector by the given double.
	 * @param multi The double to divide by.
	 * @return This vector.
	 */
	public FVector4D divide(float multi)
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
	public FVector4D divide(Struct4D multi)
	{
		if(multi.getX() == 0 || multi.getY() == 0 || multi.getZ() == 0 || multi.getW() == 0)
		{
			System.out.println("[Vector4D] Divide by zero!");
			throw new DivideByZeroException();
		}
		return set(x / (float)multi.getX(), y / (float)multi.getY(), z / (float)multi.getZ(), w / (float)multi.getW());
	}
}
