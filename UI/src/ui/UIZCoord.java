package ui;

import util.Util;

/**
 * 
 * A UI Z Coord is a z coord for a Component. This is dynamically calculated based on parent.
 * 
 * @author F4113nb34st
 *
 */
public class UIZCoord
{
	/**
	 * The parent UIZCoord. Null for absolute values.
	 */
	public UIZCoord parentZ;
	
	/**
	 * The actual value of this coord.
	 */
	private double value;
	
	/**
	 * The z offset.
	 */
	public double offset = 1;
	
	/**
	 * The minimum possible value.
	 */
	public double minZ = Double.NEGATIVE_INFINITY;
	
	/**
	 * The maximum possible value.
	 */
	public double maxZ = Double.POSITIVE_INFINITY;
	
	//ID to keep track of whether we've been updated this frame or not
	protected long updateID = 0;
	
	/**
	 * @return The value of this Location.
	 */
	public synchronized double get()
	{
		return value;
	}
	
	/**
	 * Updates this Dimension's value.
	 */
	public synchronized void update(long id)
	{
		if(id >= 0)
		{
			if(updateID == id)
			{
				return;
			}
			updateID = id;
		}
		
		double val = offset;
		
		if(parentZ != null)
		{
			parentZ.update(id);
			val += parentZ.get();
		}

		value = Util.clip(val, minZ, maxZ);
	}
}
