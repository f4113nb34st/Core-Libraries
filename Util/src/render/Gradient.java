package render;

import render.color.ByteARGB;
import render.color.Color;
import render.color.ColorUtil;
import util.DefaultInterpolations;
import util.Interpolation;
import util.Util;

/**
 * 
 * Class for storing and manipulating multi-color gradients.
 * All position values should be in the 0 to 1 range.
 * 
 * @author F4113nb34st
 *
 */
public final class Gradient
{
	private final Query query = new Query();
	
	/**
	 * Array of the different color entries in this gradient.
	 */
	private Entry[] entries = new Entry[0];
	
	/**
	 * The Interpolation function to use.
	 */
	public Interpolation interp = DefaultInterpolations.LINEAR;
	
	/**
	 * Scales the gradient to fit in the 0 to 1 range.
	 */
	public Gradient fit()
	{	
		if(entries.length < 2)
		{
			return this;
		}
		
		double start = Double.MAX_VALUE;
		double end = -Double.MAX_VALUE;
		
		for(Entry e : entries)
		{
			if(e.position < start)
			{
				start = e.position;
			}
			if(e.position > end)
			{
				end = e.position;
			}
		}
		
		double size = end - start;
		
		for(Entry e : entries)
		{
			e.position = (e.position - start) / size;
		}
		return this;
	}

	/**
	 * Adds a color entry to this gradient.
	 */
	public Gradient addEntry(Color color, float pos)
	{
		color = ByteARGB.pool.get().set(color);
		if(entries.length == 0)
		{
			entries = new Entry[1];
			entries[0] = new Entry(color, pos);
			return this;
		}
		int index = binarySearch(pos);
		Entry[] old = entries;
		entries = new Entry[old.length + 1];
		if(index > 0)
		{
			System.arraycopy(old, 0, entries, 0, index);
		}
		entries[index] = new Entry(color, pos);
		if(index < old.length)
		{
			System.arraycopy(old, index, entries, index + 1, old.length - index);
		}
		return this;
	}
	
	public static int index;
	public static double muVal;
	
	/**
	 * Returns the color at the given pos.
	 */
	public Color getColor(double pos)
	{
		//quick returns
		if(entries.length == 0)
		{
			return Color.BLACK;
		}
		if(pos < entries[0].position)
		{
			return entries[0].color;
		}
		if(pos > entries[entries.length - 1].position)
		{
			return entries[entries.length - 1].color;
		}
		synchronized(query)//just to keep things thread safe :)
		{
			Query query = query(pos);
			if(query.top.position == query.bottom.position)//if same entry
			{
				return query.bottom.color;
			}
			double mu = (pos - query.bottom.position) / (query.top.position - query.bottom.position);
			if(!interp.extended())
			{
				return ColorUtil.interpolate(query.bottom.color, query.top.color, mu, interp);
			}else
			{
				return ColorUtil.interpolate(query.past.color, query.bottom.color, query.top.color, query.future.color, mu, interp);
			}
		}
	}
	
	/**
	 * Retrieves a neighbor query from the array.
	 * @param pos The position to look for.
	 * @return The query.
	 */
	private Query query(double pos)
	{
		int index = binarySearch(pos);
		query.past = entries[Util.clip(index - 2, 0, entries.length - 1)];
		query.bottom = entries[Util.clip(index - 1, 0, entries.length - 1)];
		query.top = entries[Util.clip(index, 0, entries.length - 1)];
		query.future = entries[Util.clip(index + 1, 0, entries.length - 1)];
		return query;
	}
	
	/**
	 * Returns the index the given position would go in when inserted.
	 * @param pos The position.
	 * @return The insertion index.
	 */
	private int binarySearch(double pos)
	{
		if(pos <= entries[0].position)//quick less than min return
		{
			return 0;
		}
		if(pos >= entries[entries.length - 1].position)//quick greater than max return
		{
			return entries.length;
		}
		int min = 0;
		int max = entries.length;
		while(max - min > 1)
		{
			if(entries[(max + min) / 2].position > pos)//go down
			{
				max = (max + min) / 2;
			}else//go up
			{
				min = (max + min) / 2;
			}
		}
		return max;//return the one right above it, because all biggers will get shoved right
	}

	private static final class Entry
	{
		public Color color;
		public double position;

		public Entry(Color c, double pos)
		{
			color = c;
			position = pos;
		}
	}
	
	private static final class Query
	{
		private Entry past;
		private Entry bottom;
		private Entry top;
		private Entry future;
	}
}