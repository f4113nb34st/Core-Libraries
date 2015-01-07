package noise;

import java.util.ArrayList;
import java.util.HashSet;
import render.image.Image;
import util.Util;
import util.concurrent.ArrayTask;
import util.concurrent.ThreadPool;
import math.vector.IntVector2D;
import noise.voronoi.*;

/**
 * 
 * Stores static methods for filling NoiseArrays with advanced Voronoi noise.
 * 
 * @author F4113nb34st
 *
 */
public final class AdvancedVoronoiNoise extends Noise
{
	/**
	 * Simple class that stores a point and a src object.
	 */
	public static class Location
	{
		private IntVector2D point;
		private VoronoiObject src;
		
		public Location(IntVector2D p, VoronoiObject s)
		{
			point = p;
			src = s;
		}
		
		/**
		 * Overridden equals to make it play nice with hashmaps.
		 */
		@Override
		public boolean equals(Object obj)
		{
			return obj.hashCode() == hashCode();
		}
		
		/**
		 * Overridden hashcode to make it play nice with hashmaps.
		 */
		@Override
		public int hashCode()
		{
			int multi = 13937179;
			int value = 0;
			value = (value * multi) + point.hashCode();
			value = (value * multi) + src.hashCode();
		    return value;
		}
	}
	
	/**
	 * The list of Voronoi Objects to make the noise from.
	 */
	public ArrayList<VoronoiObject> objects;
	
	/**
	 * The distance function to use.
	 */
	public DistanceFunction disFunc = DefaultDistanceFunctions.Euclid;
	
	/**
	 * The combine function to use.
	 */
	public CombineFunction comFunc = DefaultCombineFunctions.F1;
	
	/**
	 * Past calculated locations.
	 */
	private HashSet<Location> past;
	
	/**
	 * Locations currently being processed.
	 */
	private HashSet<Location> current;
	
	/**
	 * Locations flagged for next generation.
	 */
	private HashSet<Location> flagged;
	
	/**
	 * Distances for each pixel.
	 */
	private DistanceEntry[][][] pixels;
	
	/**
	 * The maximum distance to draw to.
	 */
	public double maxDis = Double.POSITIVE_INFINITY;
	
	/**
	 * The maximum distance for the current render.
	 */
	private double currentMaxDis;
	
	/**
	 * Creates a new empty AdvancedVoronoiNoise.
	 */
	public AdvancedVoronoiNoise()
	{
		this(new ArrayList<VoronoiObject>());
	}
	
	/**
	 * Creates a new AdvancedVoronoiNoise for the given objects
	 * @param objs The Voronoi objects.
	 */
	public AdvancedVoronoiNoise(ArrayList<VoronoiObject> objs)
	{
		objects = objs;
	}
	
	/**
	 * Sets the DistanceFunction for this noise.
	 * @param dis The DistanceFunction.
	 * @return this
	 */
	public AdvancedVoronoiNoise setDisFunc(DistanceFunction dis)
	{
		disFunc = dis;
		return this;
	}
	
	/**
	 * Sets the CombineFunction for this noise.
	 * @param com The CombineFunction.
	 * @return this
	 */
	public AdvancedVoronoiNoise setComFunc(CombineFunction com)
	{
		comFunc = com;
		return this;
	}
	
	/**
	 * Unimplemented for AdvancedVoronoiNoise.
	 */
	@Override
	public double getValue(double x, double y)
	{
		return -1;
	}
	
	@Override
	public void fill(final Image noise, ThreadPool pool)
	{
		if(disFunc == DefaultDistanceFunctions.Euclid)//for euclid we gotta increase the max dis to reflect the lack of sqrting
		{
			currentMaxDis = maxDis * maxDis;
		}else
		{
			currentMaxDis = maxDis;
		}
		
		for(int i = 0; i < objects.size(); i++)
		{
			objects.get(i).index = i;
		}
		//create our pixel array.
		pixels = new DistanceEntry[noise.getWidth()][noise.getHeight()][comFunc.getNumDistances()];
		
		//create past and flagged hashsets
		past = new HashSet<Location>();
		flagged = new HashSet<Location>();
		
		//list of all centers of the objects
		HashSet<IntVector2D> centers = new HashSet<IntVector2D>();
		
		//for all objects
		for(VoronoiObject obj : objects)
		{
			//clear previous centers
			centers.clear();
			//add all from the current object
			obj.addCenters(centers);
			//for all centers 
			for(IntVector2D point : centers)
			{
				//if within bounds
				if(point.x >= 0 && point.y >= 0 && point.x < pixels.length && point.y < pixels[0].length)
				{
					//add the location
					flagged.add(new Location(point, obj));
				}
			}
		}
		
		if(flagged.isEmpty())
		{
			noise.fill(0);
			return;
		}
		
		//add all starts to past, because we don't get them normally
		past.addAll(flagged);
		
		//while more generations
		while(!flagged.isEmpty())
		{
			//set current generation to the flagged one
			current = flagged;
			//make new flagged generation
			flagged = new HashSet<Location>();
		
			//for all values in the current generation
			for(Location loc : current)
			{
				//set the value and flag neighbors
				setAndProp(loc);
			}
		}
		
		if(pool == null)
		{
			//for each pixel
			for(int x = 0; x < noise.getWidth(); x++)
			{
				processColumn(x, noise);
			}
		}else
		{
			//add the task all threads will run
			pool.addGlobalTask(new ArrayTask(0, noise.getWidth() - 1)
			{
				@Override
				public void run(int x)
				{
					processColumn(x, noise);
				}
			});
			//start pool and wait for completion
			pool.startAndWait();
		}
		
		for(Location loc : past)
		{
			IntVector2D.pool.release(loc.point);
		}
	}
	
	private void processColumn(int x, Image noise)
	{
		for(int y = 0; y < noise.getHeight(); y++)
		{
			//get distances at current pixel
			DistanceEntry[] values = pixels[x][y];
			boolean broken = false;
			//check to see if we have enough values
			for(int i = 0; i < values.length; i++)
			{
				if(values[i] == null)
				{
					broken = true;
					break;
				}
				//perform euclid distance sqrts
				if(disFunc == DefaultDistanceFunctions.Euclid)
				{
					values[i].dis = Math.sqrt(values[i].dis);
				}
			}
			if(!broken)
			{
				//set to the combine function's return
				noise.set(x, y, comFunc.combineFunc(values));
			}else
			{
				//else we set to max dis
				noise.set(x, y, maxDis);
			}
		}
	}
	
	/**
	 * Adds the distance to the location's src to the location's distances, and if it was one of the mins, flag neighbors.
	 * @param loc The location to process.
	 */
	private void setAndProp(Location loc)
	{
		//get value for the current point
		double value = loc.src.personalDistanceTo(loc.point.x, loc.point.y, disFunc);
		//ignore everything beyond maxDis
		if(value <= currentMaxDis)
		{
			if(insert(pixels[loc.point.x][loc.point.y], new DistanceEntry(value, loc.src.index)))//if one of the least, tell neighbors
			{
				for(int i = -1; i <= 1; i++)
				{
					for(int j = -1; j <= 1; j++)
					{
						if(i == 0 && j == 0) continue;//don't tell ourself...
						
						propagate(loc.point.x + i, loc.point.y + j, loc.src);//tell neighbor
					}
				}
			}
		}
	}
	
	/**
	 * Flags the given location. Handles things like clipping the edges and not adding if a past value.
	 * @param x The x location of point.
	 * @param y The y location of point.
	 * @param src The src object.
	 */
	private void propagate(int x, int y, VoronoiObject src)
	{
		//clip values
		x = Util.clip(x, 0, pixels.length - 1);
		y = Util.clip(y, 0, pixels[0].length - 1);
		
		//create new location
		Location newLoc = new Location(IntVector2D.pool.get().set(x, y), src);
		//if not previously flagged
		if(!past.contains(newLoc))
		{
			//flag
			flagged.add(newLoc);
			past.add(newLoc);
		}
	}
    
    /**
     * Inserts a value into an array so that the array is sorted from least to greatest.
     * If the value is greater than the max value, it is not added.
     * Returns true if the value was added to the array.
     * @param array The array to add to.
     * @param value The value we might add.
     * @return True if added, false if discarded.
     */
    private boolean insert(DistanceEntry[] array, DistanceEntry value)
    {
    	DistanceEntry temp;
        for(int i = array.length - 1; i >= 0; i--)
        {
            if(array[i] != null && value.dis > array[i].dis) 
            {
            	if(i == array.length - 1)
            	{
            		return false;
            	}
           	 	break;
            }
            temp = array[i];
            array[i] = value;
            if (i + 1 < array.length) 
            {
           	 	array[i + 1] = temp;
            }
        }
        return true;
    }
}
