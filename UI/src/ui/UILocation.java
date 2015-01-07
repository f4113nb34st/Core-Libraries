package ui;

import util.Util;

/**
 * 
 * A UI Location is a location for a Component. This is dynamically calculated based on parent.
 * 
 * @author F4113nb34st
 *
 */
public class UILocation
{
	/**
	 * The parent UILocation.
	 */
	public UILocation parentLoc;
	
	/**
	 * The parent UIDimension.
	 */
	public UIDimension parentDim;
	
	/**
	 * Our own UIDimension.
	 */
	public UIDimension dimension;
	
	/**
	 * The actual value of this Dimension.
	 */
	private int value;
	
	/**
	 * The pixels to add to the result.
	 */
	public double pixels = 0;
	
	/**
	 * The ratio of the parent Location.
	 */
	public double locRatio = 1;
	
	/**
	 * The ratio of the parent Dimension.
	 */
	public double ratio = 0;
	
	/**
	 * The ratio of our own Dimension.
	 */
	public double offset = 0;
	
	/**
	 * The minimum possible value.
	 */
	public int minSize = Integer.MIN_VALUE;
	
	/**
	 * The maximum possible value.
	 */
	public int maxSize = Integer.MAX_VALUE;
	
	//ID to keep track of whether we've been updated this frame or not
	protected long updateID = 0;
	
	/**
	 * @return The value of this Location.
	 */
	public synchronized int get()
	{
		return value;
	}
	
	/**
	 * Updates this Dimension's value.
	 */
	public synchronized void update(long id)
	{
		if(!checkID(id))
		{
			return;
		}
		
		double val = pixels;
		
		if(parentLoc != null && locRatio != 0)
		{
			parentLoc.update(id);
			val += parentLoc.get() * locRatio;
		}
		if(parentDim != null && ratio != 0)
		{
			parentDim.update(id);
			val += parentDim.get() * ratio;
		}
		if(dimension != null && offset != 0)
		{
			dimension.update(id);
			val += dimension.get() * offset;
		}
		
		val = filterValue(val, id);

		value = (int)Math.round(val);
		value = Util.clip(value, minSize, maxSize);
	}
	
	/**
	 * A custom method for easy value modification in subclasses.
	 * @param val The previous value.
	 * @return The new value.
	 */
	public double filterValue(double val, long id)
	{
		return val;
	}
	
	/**
	 * Performs a simple id check.
	 * @param id The update id.
	 * @return True if we need to update.
	 */
	public boolean checkID(long id)
	{
		if(id >= 0)
		{
			if(updateID == id)
			{
				return false;
			}
			updateID = id;
		}
		return true;
	}
}
