package filter;

import render.image.Image;
import render.image.NoiseImage;
import render.node.MTNode;
import render.node.Node;
import render.node.PeriodicNode;
import util.DefaultDistortions;
import util.Distortion;
import util.concurrent.ThreadPool;

/**
 * 
 * A filter that Distorts the input Node using another Node and a Distortion.
 * 
 * @author F4113nb34st
 *
 */
public class DistortionFilter extends Filter implements PeriodicNode
{
	public Distortion distortion = DefaultDistortions.BOTH_OFFSET;
	public double amplitude = 1;
	
	/**
	 * Creates a new DistortionFilter with the given input and distortion nodes.
	 * @param i The input node.
	 * @param d The distortion node.
	 */
	public DistortionFilter(Node i, Node d)
	{
		super(i, d);
	}
	
	/**
	 * Sets the Distortion of this Filter.
	 * @param distortFunc The Distortion function.
	 */
	public DistortionFilter setDistortion(Distortion distortFunc)
	{
		distortion = distortFunc;
		return this;
	}
	
	/**
	 * Sets the amplitude of this Filter.
	 * @param amp The amplitude.
	 */
	public DistortionFilter setAmplitude(double amp)
	{
		amplitude = amp;
		return this;
	}

	@Override
	public void fill(Image array, ThreadPool pool)
	{
		//create temp images
		NoiseImage base = new NoiseImage(array.getWidth(), array.getHeight());
		NoiseImage distort = new NoiseImage(array.getWidth(), array.getHeight());
		
		//fill the input image
		if(inputs[0] instanceof MTNode)
		{
			((MTNode)inputs[0]).fill(base, pool);
		}else
		{
			inputs[0].fill(base);
		}
		
		//
		if(inputs[1] instanceof MTNode)
		{
			((MTNode)inputs[1]).fill(distort, pool);
		}else
		{
			inputs[1].fill(distort);
		}
		
		distort.normalize();
		
		distortion.distort(base, distort, array, amplitude);
	}
}
