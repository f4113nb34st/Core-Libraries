package noise.voronoi;

public class DefaultDistanceFunctions
{
	/**
	 * "Real" distance. Produces a scale-like pattern.
	 */
	public static final DistanceFunction Euclid = new DistanceFunction()
	{
		@Override
		public double distanceFunc(double x1, double y1, double x2, double y2)
		{
			return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);//returns the square distance, sqrt's are calculated later for Euclidean distance.
		}
	};
	/**
	 * Produces a dark scale-like pattern.
	 */
	public static final DistanceFunction EuclidSq = new DistanceFunction()
	{
		@Override
		public double distanceFunc(double x1, double y1, double x2, double y2)
		{
			return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
		}
	};
	/**
	 * Produces a square pattern.
	 */
	public static final DistanceFunction Manhattan = new DistanceFunction()
	{
		@Override
		public double distanceFunc(double x1, double y1, double x2, double y2)
		{
			return Math.abs(x1 - x2) + Math.abs(y1 - y2);
		}
	};
	/**
	 * Produces a diamond pattern.
	 */
	public static final DistanceFunction Chebyshev = new DistanceFunction()
	{
		@Override
		public double distanceFunc(double x1, double y1, double x2, double y2)
		{
			return Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
		}
	}; 
	/**
	 * Produces a star pattern.
	 */
	public static final DistanceFunction Minkowski0_5 = new DistanceFunction()
	{
		@Override
		public double distanceFunc(double x1, double y1, double x2, double y2)
		{
			double x = Math.sqrt(Math.abs(x1 - x2));
			double y = Math.sqrt(Math.abs(y1 - y2));
			return (x+y) * (x+y);
		}
	};
}
