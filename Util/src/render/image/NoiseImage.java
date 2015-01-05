package render.image;

import render.color.Color;
import render.color.DoubleGrey;

/**
 * 
 * A NoiseImage is a greyscale Image with double storage used for Noise-related applications.
 * 
 * @author F4113nb34st
 *
 */
public class NoiseImage extends Image
{
	/**
	 * The double storage.
	 */
	private double[][] values;
	
	//temp color for getting
	private DoubleGrey tempColor = new DoubleGrey();
	
	/**
	 * Creates a new NoiseImage of the given size.
	 * @param w The width.
	 * @param h The height.
	 */
	public NoiseImage(int w, int h)
	{
		super(w, h);
	}

	/**
	 * Creates a deep copy of the given image.
	 * @param image The image to copy.
	 */
	public NoiseImage(Image image)
	{
		super(image);
	}
	
	@Override
	public boolean hasAlpha()
	{
		return false;
	}

	@Override
	public boolean hasColor()
	{
		return false;
	}

	@Override
	public boolean hasGrey()
	{
		return true;
	}

	/**
	 * @return The greyscale value array.
	 */
	public double[][] getValues()
	{
		return values;
	}

	@Override
	public void resize(int w, int h)
	{
		width = w;
		height = h;
		values = new double[w][h];
		clearClip();
	}
	
	public void setValue(int x, int y, double value)
	{
		values[x][y] = value;
	}

	public double getValue(int x, int y)
	{
		return values[x][y];
	}

	@Override
	public void set(int x, int y, Color color)
	{
		values[x][y] = color.getGrey();
	}

	@Override
	public DoubleGrey get(int x, int y)
	{
		tempColor.set(values[x][y]);
		return tempColor;
	}
	
	@Override
	public Color convertColor(Color color)
	{
		return DoubleGrey.pool.get().set(color);
	}
	
	//draw methods
	
	public void blit(Image image)
	{
		if(image instanceof NoiseImage)
		{
			view.clear();
			if(view.visible())
			{
				for(int x = view.minX; x <= view.maxX; x++)
				{
					System.arraycopy(((NoiseImage)image).values[x], view.minY, values[x], view.minY, view.maxY - view.minY + 1);
				}
			}
		}else
		{
			super.blit(image);
		}
	}
}
