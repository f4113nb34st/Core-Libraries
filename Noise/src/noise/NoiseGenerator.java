package noise;

import util.FinalRandom;

/**
 * 
 * Class that generates noise values from seeds and coords. 
 * Uses a more "random" algorithm than the default NoiseGenerator.
 * 
 * @author F4113nb34st
 *
 */
public class NoiseGenerator
{
	//random instance for rand values generation
	private FinalRandom rand = new FinalRandom();
	private long seed = 0;
	
	/**
	 * Sets the current seed of this NoiseGenerator.
	 * @param seed The seed to set to.
	 */
	public void setSeed(long s)
	{
		seed = s;
	}
	
	/**
	 * Generates a consistent, pseudo-random value between 0 and 1 for the given coords.
	 * @param coords The coords of the noise point.
	 * @return The resulting noise value.
	 */
	public synchronized double noise_gen(double... coords)
	{
		rand.setSeed(seed);
		double storage = 0;
		for(double coord : coords)
		{
			storage += coord;
			storage *= (27 * rand.nextDouble()) + 31;
		}
		//mangle inputs beyond recognition
		return mangle((int)storage);
	}
	
	/**
	 * Mangle the given integer.
	 * @param n The value to mangle.
	 * @return The result.
	 */
	private static double mangle(int n)
	{
		n = (n << 13) ^ n;
		return ((1.0 - ((n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0) * .5) + .5;
	}
}
