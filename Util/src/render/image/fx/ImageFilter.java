package render.image.fx;

import render.image.Image;

/**
 * 
 * A filter for an image.
 * 
 * @author F4113nb34st
 *
 */
public interface ImageFilter
{
	/**
	 * Filters the given image in-place.
	 * @param image The image to filter.
	 */
	public void filter(Image image);
}
