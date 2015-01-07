package render.node;

import java.awt.image.BufferedImage;
import render.image.Image;
import render.image.IntBackedImage;
import render.image.NoiseImage;

/**
 * 
 * Basic Util for Nodes.
 * 
 * @author F4113nb34st
 *
 */
public class NodeUtil
{
	/**
	 * Creates an image using the given node's output.
	 * @param node The input node.
	 * @param width The width of the output.
	 * @param height The height of the output.
	 * @return The resulting Image.
	 */
	public static final BufferedImage getImage(Node node, int width, int height)
	{
		Image image = new NoiseImage(width, height);
		node.fill(image);
		((NoiseImage)image).normalize();
		
		image = new IntBackedImage(image);
		
		return ((IntBackedImage)image).getBufferImage();
	}
}
