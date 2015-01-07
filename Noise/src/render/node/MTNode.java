package render.node;

import render.image.Image;
import util.concurrent.ThreadPool;

/**
 * 
 * A MTNode is a Node that can utilize multi-threading.
 * 
 * @author F4113nb34st
 *
 */
public interface MTNode extends Node
{
	/**
	 * Multi-threadedly fills the given Image with the output of this node using the given ThreadPool.
	 * @param image The Image to fill.
	 * @param pool The ThreadPool to execute tasks on. May be null, if so use normal means to fill the array.
	 */
	public void fill(final Image image, ThreadPool pool);
}
