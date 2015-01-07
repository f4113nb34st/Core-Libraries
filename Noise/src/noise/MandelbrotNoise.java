package noise;

import render.image.Image;
import math.Complex;
import math.ComplexMath;
import util.concurrent.ThreadPool;

/**
 * 
 * Stores methods for generating mandlebrot noise.
 * 
 * @author F4113nb34st
 *
 */
public class MandelbrotNoise extends Noise
{
	public int iterations = 1;
	public double bound = 1;
	public double centerX = 0;
	public double centerY = 0;
	
	public double width = 0;
	public double height = 0;
	public double size = 1;
	
	public MandelbrotNoise setIterations(int it)
	{
		iterations = it;
		return this;
	}
	
	public MandelbrotNoise setBound(double b)
	{
		bound = b;
		return this;
	}
	
	@Override
	public void fill(final Image noise, ThreadPool pool)
	{
		width = noise.getWidth();
		height = noise.getHeight();
		size = Math.min(width / 2D, height / 2D) / bound;
		
		super.fill(noise, pool);
	}

	@Override
	public double getValue(double x, double y)
	{
		Complex c = new Complex(((x - (width / 2)) / size) - centerX, ((y - (height / 2)) / size) - centerY);
		Complex num = new Complex(c.real, c.imaginary);
		
		num = new Complex(num.imaginary, num.real);
		c = new Complex(c.imaginary, c.real);
		
		int outIt = iterations;
		
		for(int it = 0; it < iterations; it++)
		{
			num = mandlebrot(num, c);
			
			if(!contained(num))
			{
				outIt = it;
				break;
			}
		}
		
		//return noise value
		return outIt / (double)iterations;
	}
	
	private boolean contained(Complex num)
	{
		//really just if num.length < 4, the first two tests are just optimizations
		return num.real < 2 && num.imaginary < 2 && num.length() < 4;
	}
	
	public boolean one = false;
	
	private Complex mandlebrot(Complex z, Complex c)
	{
		//c.real = z.real;
		//c.imaginary = z.imaginary;
		
		//z.sin().cos().exp().divide(c).exp().multiply(c);
		//z.exp().divide(c).sin().cos().exp().multiply(c);
		//z.multiply(z).sin().exp().cos().exp().multiply(c);
		//z.multiply(z).sin().exp().cos().multiply(c);
		//z.multiply(z).sin().cos().exp().multiply(c);
		//z.multiply(z).sin().cos().exp()
		//z.exp().cos().sin();
		//z.multiply(z).add(c);
		if(one)
		{
			return z.multiply(z).add(c);
		}
		return ComplexMath.cos(ComplexMath.sqrt(z)).add(c);
	}
}
