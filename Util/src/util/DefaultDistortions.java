package util;

import render.color.Color;
import render.image.Image;

/**
 * 
 * Contains a group of commonly used Distortions.
 * 
 * @author F4113nb34st
 *
 */
public class DefaultDistortions
{
	/**
	 * Distorts using the slope of the pixels
	 */
	public static final Distortion SLOPE = new Distortion()
	{
		@Override
		public Color distort(int x, int y, Image base, Image distort, double amplitude)
		{
			double slopeX = DefaultInterpolations.LINEAR.interpolate(distort.get(x, y).getGrey() - distort.get(x - 1, y).getGrey(), distort.get(x + 1, y).getGrey() - distort.get(x, y).getGrey(), .5);
			double slopeY = DefaultInterpolations.LINEAR.interpolate(distort.get(x, y).getGrey() - distort.get(x, y - 1).getGrey(), distort.get(x, y + 1).getGrey() - distort.get(x, y).getGrey(), .5);
			return base.get(x + (int)Math.round(slopeX * amplitude), y + (int)Math.round(slopeY * amplitude));
		}
	};
	
	/**
	 * Distorts on the x axis.
	 */
	public static final Distortion X_OFFSET = new Distortion()
	{
		@Override
		public Color distort(int x, int y, Image base, Image distort, double amplitude)
		{
			int offsetX = (int)Math.round((distort.get(x, y).getGrey() - .5) * 2 * amplitude);
			return base.get(x + offsetX, y);
		}
	};
	
	/**
	 * Distorts on the y axis.
	 */
	public static final Distortion Y_OFFSET = new Distortion()
	{
		@Override
		public Color distort(int x, int y, Image base, Image distort, double amplitude)
		{
			int offsetY = (int)Math.round((distort.get(x, y).getGrey() - .5) * 2 * amplitude);
			return base.get(x, y + offsetY);
		}
	};
	
	/**
	 * Distorts on both axis.
	 */
	public static final Distortion BOTH_OFFSET = new Distortion()
	{
		@Override
		public Color distort(int x, int y, Image base, Image distort, double amplitude)
		{
			int offsetX = (int)Math.round((distort.get(x, y).getGrey() - .5) * 2 * amplitude);
			int offsetY = (int)Math.round((distort.get(y, x).getGrey() - .5) * 2 * amplitude);
			return base.get(x + offsetX, y + offsetY);
		}
	};
}
