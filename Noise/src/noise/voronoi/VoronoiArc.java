package noise.voronoi;

import java.util.HashSet;
import util.Util;
import math.FastMath;
import math.vector.DoubleVector2D;
import math.vector.IntVector2D;

public class VoronoiArc extends VoronoiObject
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
	 * The minimum theta value for the arc.
	 */
	public double minTheta;
	
	/**
	 * The maximum theta value for the arc.
	 */
	public double maxTheta;
	
	/**
	 * Creates a new VoronoiCircle with the given center, radius, fill, and min-max.
	 * @param cx The x coord of the center.
	 * @param cy The y coord of the center.
	 * @param r The radius.
	 * @param fill True if filled, false if ring.
	 * @param min The min theta value.
	 * @param max The max theta value.
	 */
	public VoronoiArc(double cx, double cy, double r, boolean fill, double min, double max)
	{
		this(new DoubleVector2D(cx, cy), r, fill, min, max);
	}
	
	/**
	 * Creates a new VoronoiCircle with the given center, radius, fill, and min-max.
	 * @param c The center point.
	 * @param r The radius.
	 * @param fill True if filled, false if ring.
	 * @param min The min theta value.
	 * @param max The max theta value.
	 */
	public VoronoiArc(DoubleVector2D c, double r, boolean fill, double min, double max)
	{
		center = c;
		radius = r;
		filled = fill;
		minTheta = min;
		maxTheta = max;
	}
	
	@Override
	public double getDistanceTo(double i, double j, DistanceFunction disFunc)
	{
		double x = i - center.x;
		double y = j - center.y;
		double angle = FastMath.atan2(y, x);
		
		angle = Util.angleClip(angle, minTheta, maxTheta);
		
		double dx = Math.cos(angle);
		double dy = Math.sin(angle);
		double rad = radius;
		if(filled)
		{
			rad = (dx * x) + (dy * y);
			rad = Util.clip(rad, 0, radius);
		}
		
		x = (dx * rad) + center.x;
		y = (dy * rad) + center.y;
		
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
		for(double theta = minTheta; theta < maxTheta; theta += (Math.PI * 2 / circum))
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
