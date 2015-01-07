package noise;

import java.util.Iterator;
import math.vector.IntVector2D;
import render.image.Image;
import util.Util;
import util.concurrent.ThreadPool;

public final class ComplexVoronoiNoise extends Noise
{
	public Image baseArray;
	private PointQueue current = new PointQueue(0, 0);
	private PointQueue next = new PointQueue(0, 0);
	
	public ComplexVoronoiNoise(Image base)
	{
		baseArray = base;
	}

	/**
	 * Unimplemented.
	 */
	@Override
	public double getValue(double x, double y)
	{
		return -1;
	}
	
	@Override
	public void fill(Image noise, ThreadPool pool)
	{
		noise.fill(Double.POSITIVE_INFINITY);
		current.resize(noise.getWidth(), noise.getHeight());
		next.resize(noise.getWidth(), noise.getHeight());
		
		for(int x = 0; x < baseArray.getWidth(); x++)
		{
			for(int y = 0; y < baseArray.getHeight(); y++)
			{
				if(baseArray.get(x, y) == 0)
				{
					noise.set(x, y, 0);
					next.add(x, y);
				}
			}
		}
		while(!next.isEmpty())
		{
			PointQueue temp = current;
			current = next;
			next = temp;
			for(IntVector2D point : current)
			{
				process(point.x, point.y, noise);
			}
		}
	}
	
	private static final double sqrt2 = Math.sqrt(2);
	private double[][] neighbors = new double[3][3];
	
	private void process(int x, int y, Image noise)
	{
		double min = noise.get(x, y);
		for(int i = -1; i <= 1; i++)
		{
			for(int j = -1; j <= 1; j++)
			{
				double dis = noise.get(x + i, y + j);
				neighbors[i + 1][j + 1] = dis;
				if(i == 0 && j == 0)
				{
					//dis += 0;
				}else
				if(i == 0 || j == 0)
				{
					dis += 1;
				}else
				{
					dis += sqrt2;
				}
				min = Math.min(min, dis);
			}
		}
		
		noise.set(x, y, min);
		
		for(int i = -1; i <= 1; i++)
		{
			for(int j = -1; j <= 1; j++)
			{
				if(i == 0 && j == 0)
				{
					continue;
				}
				double dis = min;
				if(i == 0 || j == 0)
				{
					dis += 1;
				}else
				{
					dis += sqrt2;
				}
				if(dis < neighbors[i + 1][j + 1])
				{
					next.add(x + i, y + j);
				}
			}
		}
	}
	
	private static class PointQueue implements Iterable<IntVector2D>, Iterator<IntVector2D>
	{
		private boolean[][] buffer;
		private int size;
		
		private PointQueue(int width, int height)
		{
			resize(width, height);
		}
		
		private void resize(int width, int height)
		{
			if(buffer == null || buffer.length != width || buffer[0].length != height)
			{
				buffer = new boolean[width][height];
			}
		}
		
		public void add(int x, int y)
		{
			x = Util.wrap(x, 0, buffer.length - 1);
			y = Util.wrap(y, 0, buffer[0].length - 1);
			if(buffer[x][y] == false)
			{
				buffer[x][y] = true;
				size++;
			}
		}
		
		public int size()
		{
			return size;
		}
		
		public boolean isEmpty()
		{
			return size() <= 0;
		}

		@Override
		public Iterator<IntVector2D> iterator()
		{
			return this;
		}
		
		private int index = 0;
		private IntVector2D reuse = new IntVector2D();

		@Override
		public boolean hasNext()
		{
			final int maxIndex = (buffer.length * buffer[0].length);
			while(index < maxIndex && !buffer[index % buffer.length][index / buffer.length])
			{
				index++;
			}
			if(index >= maxIndex)
			{
				index = 0;
				return false;
			}else
			{
				buffer[index % buffer.length][index / buffer.length] = false;
				size--;
				return true;
			}
		}

		@Override
		public IntVector2D next()
		{
			return reuse.set(index % buffer.length, index / buffer.length);
		}

		@Override
		public void remove(){}
	}
}
