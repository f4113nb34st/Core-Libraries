package filter;

import render.image.Image;
import render.image.NoiseImage;
import render.node.*;
import util.DefaultInterpolations;
import util.Interpolation;
import util.concurrent.ArrayTask;
import util.concurrent.ThreadPool;

public class InterpFilter extends Filter
{
	/**
	 * The interpolation function to use.
	 */
	public Interpolation interp = DefaultInterpolations.LINEAR;
	/**
	 * The x period.
	 */
	public double periodX = 8;
	/**
	 * The y period.
	 */
	public double periodY = 8;
	
	public InterpFilter()
	{
		super();
	}
	
	public InterpFilter(Node in)
	{
		super(in);
	}

	/**
	 * Sets the Interpolation of this Filter.
	 * @param inter The Interpolation function.
	 */
	public InterpFilter setInterp(Interpolation inter)
	{
		interp = inter;
		return this;
	}
	
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
	
	@Override
	public void fill(final Image array, ThreadPool pool)
	{
		//calculate the base width and height (no need to calculate more values than this in base array)
		int baseW = (int)Math.ceil(array.getWidth() / periodX);
		int baseH = (int)Math.ceil(array.getHeight() / periodY);
		
		//create our base noise array
		final Image inputArray = new NoiseImage(baseW, baseH);
		
		//fill with basic noise
		if(inputs[0] instanceof MTNode)
		{
			((MTNode)inputs[0]).fill(inputArray, pool);
		}else
		{
			inputs[0].fill(inputArray);
		}
		
		//if multi-threading is possible
		if(pool != null)
		{
			pool.addGlobalTask(new ArrayTask(0, array.getWidth() - 1)
			{
				@Override
				public void run(int x)
				{
					evaluateColumn(array, inputArray, x);
				}
			});
			pool.startAndWait();
		}else//do it the normal way
		{
			for(int x = 0; x < array.getWidth(); x++)
			{
				evaluateColumn(array, inputArray, x);
			}
		}
	}
	
	private void evaluateColumn(Image output, Image input, int x)
	{
		//if only needs top and bottom values
		if(!interp.extended())
		{
			//get botX
			int bottomX = (int)(x / periodX);
			//get topX
			int topX = bottomX + 1;
			//get blend part for x
			double blendX = (x / periodX) - bottomX;
			
			//for all rows
			for(int y = 0; y < output.getHeight(); y++)
			{
				//get botY
				int bottomY = (int)(y / periodY);
				//get topY
				int topY = bottomY + 1;
				//get blend part for y
				double blendY = (y / periodY) - bottomY;
				
				//interp between xbots and xtops
				double xBotInterp = interp.interpolate(input.get(bottomX, bottomY), input.get(bottomX, topY), blendY);
				double xTopInterp = interp.interpolate(input.get(topX, bottomY),    input.get(topX, topY), blendY);
				
				//interp interps
				output.set(x, y, interp.interpolate(xBotInterp, xTopInterp, blendX));
			}
		}else//we need past and future values too
		{
			//get botX
			int bottomX = (int)(x / periodX);
			//get topX
			int topX = bottomX + 1;
			//get pastX
			int pastX = bottomX - 1;
			//get futureX
			int futureX = topX + 1;
			//get blend part for x
			double blendX = (x / periodX) - bottomX;
			
			for(int y = 0; y < output.getHeight(); y++)
			{
				//get botY
				int bottomY = (int)(y / periodY);
				//get topY
				int topY = bottomY + 1;
				//get pastY
				int pastY = bottomY - 1;
				//get futureY
				int futureY = topY + 1;
				//get blend part for y
				double blendY = (y / periodY) - bottomY;
				
				//interp between xbots, xtops, xpasts, and xfutures
				double xPastInterp   = interp.interpolate(input.get(pastX, pastY),   input.get(pastX, bottomY),   input.get(pastX, topY),   input.get(pastX, futureY), blendY);
				double xBotInterp    = interp.interpolate(input.get(bottomX, pastY), input.get(bottomX, bottomY), input.get(bottomX, topY), input.get(bottomX, futureY), blendY);
				double xTopInterp    = interp.interpolate(input.get(topX, pastY),    input.get(topX, bottomY),    input.get(topX, topY),    input.get(topX, futureY), blendY);
				double xFutureInterp = interp.interpolate(input.get(futureX, pastY), input.get(futureX, bottomY), input.get(futureX, topY), input.get(futureX, futureY), blendY);
				
				//interp interps
				output.set(x, y, interp.interpolate(xPastInterp, xBotInterp, xTopInterp, xFutureInterp, blendX));
			}
		}
	}
}
