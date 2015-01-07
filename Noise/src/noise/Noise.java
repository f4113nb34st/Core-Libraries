package noise;

import render.image.Image;
import render.node.MTNode;
import render.node.Node;
import util.concurrent.ArrayTask;
import util.concurrent.ThreadPool;

/**
 * 
 * Superclass of all noise generators.
 * 
 * @author F4113nb34st
 *
 */
public abstract class Noise implements Node, MTNode
{
	/**
	 * Gets the value of the noise at the given location.
	 * @param x The x coord.
	 * @param y The y coord.
	 * @param channel The color channel. (0 = grey, 1 = red, 2 = green, 3 = blue)
	 * @return The noise value at the point.
	 */
	public abstract double getValue(double x, double y, double channel);
	
	@Override
	public void fill(Image noise)
	{
		fill(noise, null);
	}
	
	@Override
	public void fill(final Image image, ThreadPool pool)
	{
		if(pool == null)
		{
			//for all pixels
			for(int x = 0; x < image.getWidth(); x++)
			{
				for(int y = 0; y < image.getHeight(); y++)
				{
					//set to value
					if(image.hasColor())
					{
						image.set(x, y, getValue(x, y));
					}else
					if(image.hasGrey())
					{
						image.set(x, y, getValue(x, y));
					}
				}
			}
		}else
		{
			//add the task all threads will run
			pool.addGlobalTask(new ArrayTask(0, image.getWidth() - 1)
			{
				@Override
				public void run(int x)
				{
					for(int y = 0; y < image.getHeight(); y++)
					{
						//set to value
						image.set(x, y, getValue(x, y));
					}
				}
			});
			//start pool and wait for completion
			pool.startAndWait();
		}
	}
}
