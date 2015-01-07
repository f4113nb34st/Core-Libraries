package filter;

import render.image.Image;
import render.node.MTNode;
import render.node.Node;
import render.node.PeriodicNode;
import render.node.SeededNode;

/**
 * 
 * A filter is a node that has one or more input.
 * 
 * @author F4113nb34st
 *
 */
public abstract class Filter implements Node, MTNode, SeededNode, PeriodicNode
{
	public Node[] inputs;
	
	public Filter(Node... in)
	{
		inputs = in;
	}
	
	@Override
	public void fill(Image array)
	{
		fill(array, null);
	}
	
	@Override
	public PeriodicNode setPeriods(double px, double py)
	{
		for(Node input : inputs)
		{
			if(input instanceof PeriodicNode)
			{
				((PeriodicNode)input).setPeriods(px, py);
			}
		}
		return this;
	}

	@Override
	public double getPeriodX()
	{
		for(Node input : inputs)
		{
			if(input instanceof PeriodicNode)
			{
				return ((PeriodicNode)input).getPeriodX();
			}
		}
		return 1;
	}

	@Override
	public double getPeriodY()
	{
		for(Node input : inputs)
		{
			if(input instanceof PeriodicNode)
			{
				return ((PeriodicNode)input).getPeriodY();
			}
		}
		return 1;
	}

	@Override
	public SeededNode setSeed(long seed)
	{
		for(Node input : inputs)
		{
			if(input instanceof SeededNode)
			{
				return ((SeededNode)input).setSeed(seed);
			}
		}
		return this;
	}
}
