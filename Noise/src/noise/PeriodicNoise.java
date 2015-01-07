package noise;

import render.node.PeriodicNode;

/**
 * 
 * Superclass of all noise that uses periods for generation.
 * 
 * @author F4113nb34st
 *
 */
public abstract class PeriodicNoise extends SeededNoise implements PeriodicNode
{
	/**
	 * The x period of this noise.
	 */
	public double periodX;
	/**
	 * The y period of this noise.
	 */
	public double periodY;
	
	@Override
	public PeriodicNode setPeriods(double px, double py)
	{
		periodX = px;
		periodY = py;
		return this;
	}
	
	@Override
	public double getPeriodX()
	{
		return periodX;
	}

	@Override
	public double getPeriodY()
	{
		return periodY;
	}
}
