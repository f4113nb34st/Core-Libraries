package noise.voronoi;

import java.util.HashSet;
import math.vector.DoubleVector2D;
import math.vector.IntVector2D;

/**
 * 
 * VoronoiObject that represents a line segment.
 * 
 * @author F4113nb34st
 *
 */
public class VoronoiLine extends VoronoiObject
{
	/**
	 * The first point of the line.
	 */
	public DoubleVector2D p1;
	/**
	 * The second point of the line.
	 */
	public DoubleVector2D p2;
	/**
	 * The difference between the start and end
	 */
	private DoubleVector2D dif;
	/**
	 * The squared length of this line.
	 */
	private double lengthSq;
	
	/**
	 * Creates a new VoronoiLine with the given end points.
	 * @param p1x The x coord of the first point.
	 * @param p1y The y coord of the first point.
	 * @param p2x The x coord of the second point.
	 * @param p2y The y coord of the second point.
	 */
	public VoronoiLine(double p1x, double p1y, double p2x, double p2y)
	{
		this(new DoubleVector2D(p1x, p1y), new DoubleVector2D(p2x, p2y));
	}
	
	/**
	 * Creates a new VoronoiLine with the given end points.
	 * @param ep1 The first point.
	 * @param ep2 The second point.
	 */
	public VoronoiLine(DoubleVector2D ep1, DoubleVector2D ep2)
	{
		//set first point
		p1 = ep1;
		//set second point
		p2 = ep2;
		//set dif
		dif = p2.copy().subtract(p1);
		//set lengthSq
		lengthSq = dif.lengthSq();
	}

	@Override
	public double getDistanceTo(double x, double y, DistanceFunction disFunc)
	{
		//get projection length
		 double t = ((x - p1.x) * (p2.x - p1.x) + (y - p1.y) * (p2.y - p1.y)) / lengthSq;
		 //if t <= 0, return distance to first point
		 if(t <= 0) 
		 {
			 return disFunc.distanceFunc(x, y, p1.x, p1.y);
		 }
		 //if t >= 1, return distance to second point
		 if(t >= 1) 
		 {
			 return disFunc.distanceFunc(x, y, p2.x, p2.y);
		 }
		 //find closest point on line
		 double x2 = p1.x + (dif.x * t);
		 double y2 = p1.y + (dif.y * t);
		 //return distance to the point
		 return disFunc.distanceFunc(x, y, x2, y2);
	}

	@Override
	public void addCenters(HashSet<IntVector2D> centers)
	{
		//get the length of the line
		double length = Math.sqrt(lengthSq);
		//create temp vector
		DoubleVector2D temp = DoubleVector2D.pool.get();
		//for intervals along line
		for(double t = 0; t <= 1; t += (1 / length))
		{
			temp.set(p1).add((int)(t * dif.x), (int)(t * dif.y));
			//add a new center at point
			centers.add(IntVector2D.pool.get().set(temp));
		}
		DoubleVector2D.pool.release(temp);
	}
}
