package ui;

import util.Util;

/**
 * 
 * A UI Dimension is a dimension for a Component. This is dynamically calculated based on parent.
 * 
 * @author F4113nb34st
 *
 */
public class UIDimension
{
	/**
	 * The parent UIDimension.
	 */
	public UIDimension parentDim;
	
	/**
	 * The actual value of this Dimension. Do not set directly.
	 */
	public int value;
	
	/**
	 * The pixels to add to the result.
	 */
	public double pixels = 0;
	
	/**
	 * The ratio of the parent Dimension.
	 */
	public double ratio = 0;
	
	/**
	 * The minimum possible value.
	 */
	public int minSize = 1;
	
	/**
	 * The maximum possible value.
	 */
	public int maxSize = Integer.MAX_VALUE;
	
	//ID to keep track of whether we've been updated this frame or not
	protected long updateID = 0;
	
	/**
	 * @return The value of this Dimension.
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
		
		if(parentDim != null && ratio != 0)
		{
			parentDim.update(id);
			val += parentDim.get() * ratio;
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
