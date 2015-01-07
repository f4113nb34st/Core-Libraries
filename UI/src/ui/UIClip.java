package ui;

import render.image.Image;
import util.Util;

/**
 * 
 * A UI Clip is a clipping window for a Component. This is dynamically calculated based on parent.
 * 
 * @author F4113nb34st
 *
 */
public class UIClip
{
	/**
	 * The parent UIClip.
	 */
	public UIClip parentClip;
	
	/**
	 * The actual min x value of this Clip. Do not set directly.
	 */
	public int valueMinX;
	/**
	 * The actual min y value of this Clip. Do not set directly.
	 */
	public int valueMinY;
	/**
	 * The actual max x value of this Clip. Do not set directly.
	 */
	public int valueMaxX;
	/**
	 * The actual max y value of this Clip. Do not set directly.
	 */
	public int valueMaxY;
	
	/**
	 * The min x value of this Clip.
	 */
	public int minX = Integer.MIN_VALUE / 2;
	/**
	 * The min y value of this Clip.
	 */
	public int minY = Integer.MIN_VALUE / 2;
	/**
	 * The max x value of this Clip.
	 */
	public int maxX = Integer.MAX_VALUE / 2;
	/**
	 * The max y value of this Clip.
	 */
	public int maxY = Integer.MAX_VALUE / 2;
	
	/**
	 * True to ignore any parent values.
	 */
	public boolean ignoreParent = false;
	
	//ID to keep track of whether we've been updated this frame or not
	protected long updateID = 0;
	
	/**
	 * @return The min x value of this Clip.
	 */
	public synchronized int getMinX()
	{
		return valueMinX;
	}
	
	/**
	 * @return The min y value of this Clip.
	 */
	public synchronized int getMinY()
	{
		return valueMinY;
	}
	
	/**
	 * @return The max x value of this Clip.
	 */
	public synchronized int getMaxX()
	{
		return valueMaxX;
	}
	
	/**
	 * @return The max y value of this Clip.
	 */
	public synchronized int getMaxY()
	{
		return valueMaxY;
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
		
		valueMinX = minX;
		valueMinY = minY;
		valueMaxX = maxX;
		valueMaxY = maxY;
		
		if(!ignoreParent && parentClip != null)
		{
			parentClip.update(id);
			valueMinX = Math.max(valueMinX, parentClip.getMinX());
			valueMinY = Math.max(valueMinY, parentClip.getMinY());
			valueMaxX = Math.min(valueMaxX, parentClip.getMaxX());
			valueMaxY = Math.min(valueMaxY, parentClip.getMaxY());
		}
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
