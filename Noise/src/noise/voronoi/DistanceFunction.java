package noise.voronoi;

/**
 * 
 * A distance function to use in Voronoi calculations.
 * 
 * @author F4113nb34st
 * 
 */
public interface DistanceFunction
{
	/**
	 * Returns the distance between the given points.
	 * Note: depending on the function, may not be linear distance.
	 */
	public double distanceFunc(double x1, double y1, double x2, double y2);
}
