package ui;

import java.util.ArrayList;
import java.util.HashSet;
import util.Util;
import math.vector.old.Vector3D;

/**
 * 
 * A UI Color is a color for a Component. This is dynamically calculated based on parent. This dynamically updates dependant components.
 * 
 * @author F4113nb34st
 *
 */
public class UIColor
{
	/**
	 * The parent UIColors.
	 */
	private ArrayList<ParentEntry> parentCols = new ArrayList<ParentEntry>();
	
	/**
	 * The actual value of this Color.
	 */
	private Vector3D value = new Vector3D();
	
	/**
	 * The base values to add to the result.
	 */
	public Vector3D base = new Vector3D(0, 0, 0);
	
	/**
	 * The minimum possible component values.
	 */
	public Vector3D minColor = new Vector3D(0, 0, 0);
	
	/**
	 * The maximum possible component values.
	 */
	public Vector3D maxColor = new Vector3D(1, 1, 1);
	
	//ID to keep track of whether we've been updated this frame or not
	protected long updateID = 0;
	
	/**
	 * A set of components marked dirty when this color changes.
	 */
	public HashSet<Component> subscribers = new HashSet<Component>();
	
	/**
	 * @return The value of this color.
	 */
	public synchronized Vector3D get()
	{
		return value;
	}
	
	/**
	 * @return The red value of this color.
	 */
	public synchronized int getRed()
	{
		return (int)(value.x * 255);
	}
	
	/**
	 * @return The green value of this color.
	 */
	public synchronized int getGreen()
	{
		return (int)(value.y * 255);
	}
	
	/**
	 * @return The blue value of this color.
	 */
	public synchronized int getBlue()
	{
		return (int)(value.z * 255);
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
		
		Vector3D val = new Vector3D(base);
		
		for(ParentEntry pe : parentCols)
		{
			double weight = pe.weight;
			if(weight != 0)
			{
				pe.parent.update(id);
				Vector3D parentCol = pe.parent.get();
				val.add(parentCol.x * weight, parentCol.y * weight, parentCol.z * weight);
			}
		}

		val.x = Util.clip(val.x, minColor.x, maxColor.x);
		val.y = Util.clip(val.y, minColor.y, maxColor.y);
		val.z = Util.clip(val.z, minColor.z, maxColor.z);
		if(!value.equals(val))
		{
			for(Component comp : subscribers)
			{
				comp.dirty = true;
			}
		}
		value.set(val);
	}
	
	/**
	 * Adds the given parent color with the given weight.
	 * @param color The color to add.
	 * @param weight The weight for the color.
	 * @return The ParentEntry that stores the Color. Should be captured for subsequent editing of the weight.
	 */
	public synchronized ParentEntry addParent(UIColor color, double weight)
	{
		ParentEntry pe = new ParentEntry(color, weight);
		parentCols.add(pe);
		return pe;
	}
	
	/**
	 * Clears the entire list of parent colors.
	 */
	public synchronized void clearParents()
	{
		parentCols.clear();
	}
	
	private static class ParentEntry
	{
		public UIColor parent;
		public double weight;
		
		public ParentEntry(UIColor p, double w)
		{
			parent = p;
			weight = w;
		}
	}
}
