package render.image;

import render.color.Color;
import render.color.DoubleAlpha;

/**
 * 
 * A MaskImage is an image that represents an alpha mask.
 * 
 * @author F4113nb34st
 *
 */
public class MaskImage extends Image
{
	/**
	 * The double storage.
	 */
	private double[][] values;
	
	//temp color for getting
	private DoubleAlpha tempColor = new DoubleAlpha();
	
	/**
	 * Creates a new MaskImage of the given size.
	 * @param w The width.
	 * @param h The height.
	 */
	public MaskImage(int w, int h)
	{
		super(w, h);
	}

	/**
	 * Creates a deep copy of the given image.
	 * @param image The image to copy.
	 */
	public MaskImage(Image image)
	{
		super(image);
	}

	@Override
	public boolean hasAlpha()
	{
		return true;
	}

	@Override
	public boolean hasColor()
	{
		return false;
	}

	@Override
	public boolean hasGrey()
	{
		return false;
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

	@Override
	public void set(int x, int y, Color color)
	{
		values[x][y] = color.getAlpha();
	}

	@Override
	public DoubleAlpha get(int x, int y)
	{
		tempColor.set(values[x][y]);
		return tempColor;
	}
	
	@Override
	public Color convertColor(Color color)
	{
		return DoubleAlpha.pool.get().set(color);
	}
	
	//draw methods
	
	public void blit(Image image)
	{
		if(image instanceof MaskImage)
		{
			view.clear();
			if(view.visible())
			{
				for(int x = view.minX; x <= view.maxX; x++)
				{
					System.arraycopy(((MaskImage)image).values[x], view.minY, values[x], view.minY, view.maxY - view.minY + 1);
				}
			}
		}else
		{
			super.blit(image);
		}
	}
}
