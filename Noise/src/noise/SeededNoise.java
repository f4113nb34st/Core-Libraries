package noise;

import render.node.SeededNode;

/**
 * 
 * Superclass of all noise that is seeded.
 * 
 * @author F4113nb34st
 *
 */
public abstract class SeededNoise extends Noise implements SeededNode
{
	/**
	 * The seed of this noise.
	 */
	public long seed = 0;
	
	/**
	 * Sets the seed for this noise.
	 * @param s The seed.
	 * @return this
	 */
	@Override
	public SeededNode setSeed(long s)
	{
		seed = s;
		return this;
	}
}
