package noise.voronoi;

/**
 * 
 * A combination method for Voronoi Noise calculations.
 * 
 * @author F4113nb34st
 *
 */
public interface CombineFunction
{
	/**
	 * Returns the value to use based on the given distances.
	 */
	double combineFunc(DistanceEntry[] values);

	/**
	 * Returns how many distance values must be calculated for this function.
	 */
	int getNumDistances();
}
