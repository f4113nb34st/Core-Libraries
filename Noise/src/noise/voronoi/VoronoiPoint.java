package noise.voronoi;

import java.util.HashSet;
import math.vector.DoubleVector2D;
import math.vector.IntVector2D;

public class VoronoiPoint extends VoronoiObject
{
	public DoubleVector2D center;
	
	public VoronoiPoint(double i, double j)
	{
		center = DoubleVector2D.pool.get().set(i, j);
	}

	@Override
	public double getDistanceTo(double i, double j, DistanceFunction disFunc)
	{
		return disFunc.distanceFunc(center.x, center.y, i, j);
	}
	
	@Override
	public void addCenters(HashSet<IntVector2D> centers)
	{
		centers.add(IntVector2D.pool.get().set(center));
	}
}
