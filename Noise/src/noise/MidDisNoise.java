package noise;

import render.image.Image;
import render.node.SeededNode;
import util.concurrent.ThreadPool;

/**
 * 
 * Class that generates Midpoint Displacement noise.
 * 
 * @author F4113nb34st
 *
 */
public final class MidDisNoise extends SeededNoise
{
	/**
	 * The sentinel value for seeded noise.
	 */
	public static final double SENTINEL = Double.POSITIVE_INFINITY;
	/**
	 * The NoiseGenerator instance for this MidpointDisplacementNoise.
	 */
	private NoiseGenerator gen = new NoiseGenerator();
	/**
	 * The starting amplitude of this noise.
	 */
	public double startingAmplitude = 1;
	/**
	 * The persistance of the iterations.
	 */
	public double persistance = .5; 
	/**
	 * True if working with seeded noise.
	 */
	public boolean seeded = false;
	
	@Override
	public SeededNode setSeed(long s)
	{
		gen.setSeed(s);
		return super.setSeed(s);
	}
	
	public MidDisNoise setAmplitude(double amp)
	{
		startingAmplitude = amp;
		return this;
	}
	
	public MidDisNoise setPersistence(double persis)
	{
		persistance = persis;
		return this;
	}
	
	public MidDisNoise setSeeded(boolean s)
	{
		seeded = s;
		return this;
	}

	@Override
	public void fill(Image noise, ThreadPool pool)
	{
		//only set the 
		if(!seeded || noise.get(0, 0) == SENTINEL)
		{
			noise.set(0, 0, .5 + ((gen.noise_gen(0, 0) - .5) * 2 * startingAmplitude));
		}
		calculate(noise);
	}
	
	/**
	 * Clips the size to the form (2^i).
	 * @param size The previous size.
	 * @return The clipped size.
	 */
	private int fixSize(int size)
	{
		int actingSize = 2;
		while(actingSize < size)
		{
			actingSize *= 2;
		}
		return actingSize;
	}
	
	private void calculate(Image noise)
	{
		//get whatever value of 2^i is greater than or equal to the max dimension of the noise
		int totalSize = fixSize(Math.max(noise.getWidth(), noise.getHeight()));
		
		int halfSize = totalSize / 2;
		double amplitude = startingAmplitude * persistance;
		while(halfSize > 0)
		{
			//do square step
			for(int x = halfSize; x < totalSize; x += (halfSize * 2))
			{
				for(int y = halfSize; y < totalSize; y += (halfSize * 2))
				{
					squareStep(noise, x, y, halfSize, amplitude);
				}
			}
			//do diamond steps
			for(int x = halfSize; x < totalSize; x += (halfSize * 2))
			{
				for(int y = halfSize; y < totalSize; y += (halfSize * 2))
				{
					diamondStep(noise, x - halfSize, y, halfSize, amplitude);
					diamondStep(noise, x, y - halfSize, halfSize, amplitude);
				}
			}
			halfSize /= 2;
			amplitude *= persistance;
		}
	}
	
	private void squareStep(Image noise, int x, int y, int halfSize, double amplitude)
	{
		//skip pre-seeded values
		if(seeded && noise.get(x, y) != SENTINEL)
		{
			return;
		}
		
		double value = 0;
		//no need to wrap, NoiseArray handles that for us
		value += noise.get(x - halfSize, y - halfSize);
		value += noise.get(x + halfSize, y - halfSize);
		value += noise.get(x - halfSize, y + halfSize);
		value += noise.get(x + halfSize, y + halfSize);
		value /= 4;
		
		noise.set(x, y, value + ((gen.noise_gen(x, y) - .5) * 2 * amplitude));
	}
	
	private void diamondStep(Image noise, int x, int y, int halfSize, double amplitude)
	{
		//skip pre-seeded values
		if(seeded && noise.get(x, y) != SENTINEL)
		{
			return;
		}
		
		double value = 0;
		//no need to wrap, NoiseArray handles that for us
		value += noise.get(x - halfSize, y);
		value += noise.get(x + halfSize, y);
		value += noise.get(x, y - halfSize);
		value += noise.get(x, y + halfSize);
		value /= 4;
		
		noise.set(x, y, value + ((gen.noise_gen(x, y) - .5) * 2 * amplitude));
	}

	/**
	 * Unimplemented for MidDisNoise.
	 */
	@Override
	public double getValue(double x, double y)
	{
		return -1;
	}
}
