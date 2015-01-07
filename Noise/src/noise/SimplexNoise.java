package noise;

import math.FastMath;

/**
 * 
 * Class that generates 2D Simplex pseudo-random noise.
 * 
 * @author F4113nb34st
 *
 */
public final class SimplexNoise extends PeriodicNoise
{
	/**
	 * The simplex skew values.
	 */
	private static final double skew = (Math.sqrt(3) - 1) / 2;
	private static final double deskew = (3 - Math.sqrt(3)) / 6;
	/**
	 * Array of 16 unit directions.
	 */
	private static final double[][] gradients = new double[16][2];
	private static final int gradMask = gradients.length - 1;
	static
	{
		double theta = 0;
		double inc = Math.PI * 2 / gradients.length;
		for(int index = 0; index < gradients.length; index++, theta += inc)
		{
			gradients[index][0] = FastMath.cos(theta);
			gradients[index][1] = FastMath.sin(theta);
		}
	}
	/**
	 * The NoiseGenerator instance for this SimplexNoise.
	 */
	private IntNoiseGenerator gen = new IntNoiseGenerator();
	
	public SimplexNoise()
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
		double gradX = x / periodX;
		double gradY = y / periodY;
		
		double local_skew = (gradX + gradY) * skew;
		int i = (int)(gradX + local_skew);
		int j = (int)(gradY + local_skew);
		
		double local_deskew = (i + j) * deskew;
		double originX = i - local_deskew;
		double originY = j - local_deskew;
		
		double corner0X = gradX - originX;
		double corner0Y = gradY - originY;
		
		int stepX;
		int stepY;
		if(corner0X > corner0Y)
		{
			stepX = 1;
			stepY = 0;
		}else
		{
			stepX = 0;
			stepY = 1;
		}
		
		double corner1X = corner0X - stepX + deskew;
		double corner1Y = corner0Y - stepY + deskew;
		double corner2X = corner0X - 1 + (2 * deskew);
		double corner2Y = corner0Y - 1 + (2 * deskew);
		
		double value = 0;
		
		value += contribution(corner0X, corner0Y, gradients[gen.noise_gen(i, j) & gradMask]);
		value += contribution(corner1X, corner1Y, gradients[gen.noise_gen(i + stepX, j + stepY) & gradMask]);
		value += contribution(corner2X, corner2Y, gradients[gen.noise_gen(i + 1, j + 1) & gradMask]);
		
		return value * 70;
	}
	
	private double contribution(double difX, double difY, double[] gradient)
	{
		double radius = 0.5 - (difX * difX) - (difY * difY);
		
		if(radius <= 0)
		{
			return 0;
		}
		
		//perform radius^4
		radius *= radius;
		radius *= radius;
		
		return radius * dotProduct(gradient, difX, difY);
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
}
