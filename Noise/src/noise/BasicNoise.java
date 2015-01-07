package noise;

import render.node.SeededNode;

/**
 * 
 * Stores methods for generating pseudo-random noise.
 * 
 * @author F4113nb34st
 *
 */
public final class BasicNoise extends SeededNoise
{
	/**
	 * The NoiseGenerator instance for this BasicNoise.
	 */
	private NoiseGenerator gen = new NoiseGenerator();
	
	/**
	 * Sets the seed for this noise.
	 * @param s The seed.
	 * @return this
	 */
	@Override
	public SeededNode setSeed(long s)
	{
		gen.setSeed(s);
		return super.setSeed(s);
	}
	
	@Override
	public double getValue(double x, double y)
	{
		return gen.noise_gen(x, y);
	}
}
