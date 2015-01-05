package util;

import render.color.Color;
import render.image.Image;

/**
 * 
 * Represents a method for Distorting.
 * 
 * @author F4113nb34st
 *
 */
public abstract class Distortion
{
	/**
	 * Distorts the given position using the distortion image.
	 * @param x The original x.
	 * @param y The original y.
	 * @param base The base image.
	 * @param distort The distortion image.
	 * @param amplitude The amplitude of the distortion.
	 * @return The distorted value.
	 */
	public abstract Color distort(int x, int y, Image base, Image distort, double amplitude);
	
	/**
	 * Distorts an image using the given distortion image and amplitude into the given output.
	 * @param input The input image.
	 * @param distort The distortion image.
	 * @param output The output image.
	 * @param amplitude The amplitude of the distortion.
	 */
	public void distort(Image input, Image distort, Image output, double amplitude)
	{
		for(int x = 0; x < output.getWidth(); x++)
		{
			for(int y = 0; y < output.getHeight(); y++)
			{
				output.set(x, y, distort(x, y, input, distort, amplitude));
			}
		}
	}
}
