package render.image;

import java.util.Arrays;
import render.color.Color;
import render.color.DoubleARGB;

/**
 * 
 * A DoubleImage is a color Image with double storage.
 * 
 * @author F4113nb34st
 *
 */
public class DoubleImage extends Image
{
	/**
	 * The red storage.
	 */
	private double[][] red;
	
	/**
	 * The green storage.
	 */
	private double[][] green;
	
	/**
	 * The blue storage.
	 */
	private double[][] blue;
	
	/**
	 * The alpha storage.
	 */
	private double[][] alpha;
	
	//temp color for getting
	private DoubleARGB tempColor = new DoubleARGB();
	
	/**
	 * Creates a new NoiseImage of the given size.
	 * @param w The width.
	 * @param h The height.
	 */
	public DoubleImage(int w, int h)
	{
		super(w, h);
	}

	/**
	 * Creates a deep copy of the given image.
	 * @param image The image to copy.
	 */
	public DoubleImage(Image image)
	{
		super(image);
	}
	
	@Override
	public boolean hasAlpha()
	{
		return alpha != null;
	}

	@Override
	public boolean hasColor()
	{
		return true;
	}

	@Override
	public boolean hasGrey()
	{
		return true;
	}

	@Override
	public void resize(int w, int h)
	{
		width = w;
		height = h;
		red = new double[w][h];
		green = new double[w][h];
		blue = new double[w][h];
		clearClip();
	}
	
	private void initAlpha()
	{
		if(alpha == null)
		{
			alpha = new double[width][height];
			for(int i = 0; i < alpha.length; i++)
			{
				for(int j = 0; j < alpha[0].length; j++)
				{
					alpha[i][j] = 1;
				}
			}
		}
	}

	@Override
	public void set(int x, int y, Color color)
	{
		red[x][y] = color.getRed();
		green[x][y] = color.getGreen();
		blue[x][y] = color.getBlue();
		if(color.hasAlpha())
		{
			initAlpha();
		}
		if(alpha != null)
		{
			alpha[x][y] = color.getAlpha();
		}
	}

	@Override
	public DoubleARGB get(int x, int y)
	{
		tempColor.set(red[x][y], green[x][y], blue[x][y], (alpha != null ? alpha[x][y] : 255));
		return tempColor;
	}
	
	@Override
	public Color convertColor(Color color)
	{
		return DoubleARGB.pool.get().set(color);
	}
	
	//draw methods
	
	public void blit(Image image)
	{
		if(image instanceof DoubleImage)
		{
			DoubleImage dimage = (DoubleImage)image;
			view.clear();
			if(view.visible())
			{
				if(dimage.hasAlpha())
				{
					initAlpha();
				}
				for(int x = view.minX; x <= view.maxX; x++)
				{
					System.arraycopy(dimage.red[x], view.minY, red[x], view.minY, view.maxY - view.minY + 1);
					System.arraycopy(dimage.green[x], view.minY, green[x], view.minY, view.maxY - view.minY + 1);
					System.arraycopy(dimage.blue[x], view.minY, blue[x], view.minY, view.maxY - view.minY + 1);
					if(alpha != null)
					{
						System.arraycopy(dimage.alpha[x], view.minY, alpha[x], view.minY, view.maxY - view.minY + 1);
					}
				}
			}
		}else
		{
			super.blit(image);
		}
	}
}
