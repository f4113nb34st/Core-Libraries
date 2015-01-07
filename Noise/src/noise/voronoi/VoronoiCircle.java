package noise.voronoi;

import java.util.HashSet;
import math.FastMath;
import math.vector.DoubleVector2D;
import math.vector.IntVector2D;

/**
 * 
 * VoronoiObject that represents a circle.
 * 
 * @author F4113nb34st
 *
 */
public class VoronoiCircle extends VoronoiObject
{
	/**
	 * The center of this circle.
	 */
	public DoubleVector2D center;
	
	/**
	 * The radius of this circle.
	 */
	public double radius;
	
	/**
	 * True if filled, false if ring.
	 */
	public boolean filled;
	
	/**
	 * Creates a new VoronoiCircle with the given center, radius, and fill.
	 * @param cx The x coord of the center.
	 * @param cy The y coord of the center.
	 * @param r The radius.
	 * @param fill True if filled, false if ring.
	 */
	public VoronoiCircle(double cx, double cy, double r, boolean fill)
	{
		this(new DoubleVector2D(cx, cy), r, fill);
	}
	
	/**
	 * Creates a new VoronoiCircle with the given center, radius, and fill.
	 * @param c The center point.
	 * @param r The radius.
	 * @param fill True if filled, false if ring.
	 */
	public VoronoiCircle(DoubleVector2D c, double r, boolean fill)
	{
		center = c;
		radius = r;
		filled = fill;
	}

	@Override
	public double getDistanceTo(double i, double j, DistanceFunction disFunc)
	{
		double x = i - center.x;
		double y = j - center.y;
		
		double length = Math.sqrt(x * x + y * y);
		if(length < radius && filled)
		{
			x = i;
			y = j;
		}else
		{
			x /= length;
			y /= length;
			
			x = (x * radius) + center.x;
			y = (y * radius) + center.y;
		}
		
		//return the distance
		return disFunc.distanceFunc(i, j, x, y);
	}

	@Override
	public void addCenters(HashSet<IntVector2D> centers)
	{
		//stores previous x and y values
		int px = Integer.MAX_VALUE;
		int py = Integer.MAX_VALUE;
		//the circumference of the circle
		double circum = Math.PI * radius * radius;
		//for each point on edge
		for(double theta = 0; theta < (Math.PI * 2); theta += (Math.PI * 2 / circum))
		{
			//find x and y values
			int x = (int)Math.round(center.x + (FastMath.cos(theta) * radius));
			int y = (int)Math.round(center.y + (FastMath.sin(theta) * radius));
			//no sense re-adding previous value
			if(x == px && y == py)
			{
				continue;
			}
			//add center
			centers.add(IntVector2D.pool.get().set(x, y));
			//set previouses
			px = x;
			py = y;
		}
		if(filled)
		{
			//add center
			centers.add(IntVector2D.pool.get().set(center));
		}
	}
}
