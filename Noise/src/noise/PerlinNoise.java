package noise;

import util.DefaultInterpolations;

/**
 * 
 * Class that generates 2D Perlin pseudo-random noise.
 * 
 * @author F4113nb34st
 *
 */
public class PerlinNoise extends PeriodicNoise
{
	/**
	 * Array of 16 unit directions.
	 */
	private static final double[][] gradients = new double[16][2];
	private static final int gradMask = gradients.length - 1;
	static
	{
		//populate the gradient array
		double inc = 2D / 4D;
		int index = 0;
		for(double x = -1; x <= 1; x += inc)
		{
			for(double y = -1; y <= 1; y += inc)
			{
				if(Math.abs(x) != 1 && Math.abs(y) != 1)
				{
					continue;
				}
				double l = Math.sqrt((x * x) + (y * y));
				gradients[index][0] = x / l;
				gradients[index][1] = y / l;
				index++;
			}
		}
	}
	/**
	 * The NoiseGenerator instance for this PerlinNoise.
	 */
	private IntNoiseGenerator gen = new IntNoiseGenerator();
	
	public PerlinNoise()
	{
		setSeed(0);
	}
	
	@Override
	public SeededNoise setSeed(long s)
	{
		super.setSeed(s);
		gen.setSeed(s);
		
		return this;
	}

	@Override
	public double getValue(double x, double y)
	{
		//find botX
		int botX = (int)(x / periodX);
		//find topX
		int topX = botX + 1;
		//find x fraction portion
		double fracX = (x / periodX) - botX;
		
		//find botY
		int botY = (int)(y / periodY);
		//find topY
		int topY = botY + 1;
		//find y fraction portion
		double fracY = (y / periodY) - botY;
		
		//find values for x's and y's
		double valBXBY = dotProduct(gradients[gen.noise_gen(botX, botY) & gradMask], fracX, fracY);
		double valTXBY = dotProduct(gradients[gen.noise_gen(topX, botY) & gradMask], fracX - 1D, fracY);
		double valBXTY = dotProduct(gradients[gen.noise_gen(botX, topY) & gradMask], fracX, fracY - 1D);
		double valTXTY = dotProduct(gradients[gen.noise_gen(topX, topY) & gradMask], fracX - 1D, fracY - 1D);
		
		//fade fracs
		double newFracX = fade(fracX);
		double newFracY = fade(fracY);
		
		//perform y interps
		double yBotInterp = DefaultInterpolations.LINEAR.interpolate(valBXBY, valTXBY, newFracX);
		double yTopInterp = DefaultInterpolations.LINEAR.interpolate(valBXTY, valTXTY, newFracX);
		
		//return value
		return DefaultInterpolations.LINEAR.interpolate(yBotInterp, yTopInterp, newFracY);
	}
	
	/**
	 * Performs a dotProduct on the given gradient and point.
	 * @param gradient The gradient.
	 * @param x The x value of the point.
	 * @param y The y value of the point.
	 * @return The dot product.
	 */
	private double dotProduct(double[] gradient, double x, double y)
	{
		return (gradient[0] * x) + (gradient[1] * y);
	}
	
	/**
	 * Fade function. It is important not to use Math.pow, because it is not optimized for int powers.
	 * This version is ~100x faster than its Math.pow equivalent.
	 * @param value The value to fade.
	 * @return The faded value.
	 */
	private double fade(double value)
	{
		//return 6x^5 - 15x^4 + 10x^3;
		double val3 = value * value * value;
		double val4 = val3 * value;
		double val5 = val4 * value;
		return (6 * val5) - (15 * val4) + (10 * val3);
	}
}
