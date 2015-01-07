package render.node;

import render.image.Image;

/**
 * 
 * A node is a step in a rendering pipeline. It can have zero or more inputs and has an output.
 * 
 * @author F4113nb34st
 *
 */
public interface Node
{
	/**
	 * Fills the given Image with the output of this node.
	 * @param image The image to fill.
	 */
	public void fill(Image image);
}
