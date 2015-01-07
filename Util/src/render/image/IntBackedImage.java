package render.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import render.color.Color;
import render.color.IntARGB;

/**
 * 
 * An image that uses a backing BufferedImage for storage.
 * 
 * @author F4113nb34st
 *
 */
public final class IntBackedImage extends Image
{
	/**
	 * The backing BufferedImage.
	 */
	protected BufferedImage buffer;
	
	/**
	 * The pixels in the image.
	 */
	protected int[] data;
	
	//temp color for getting
	private IntARGB tempColor = new IntARGB();

	/**
	 * Creates a new BackedImage of the given size.
	 * @param w The width.
	 * @param h The height.
	 */
	public IntBackedImage(int w, int h)
	{
		super(w, h);
	}

	/**
	 * Creates a deep copy of the given image.
	 * @param image The image to copy.
	 */
	public IntBackedImage(Image image)
	{
		super(image);
	}

	/**
	 * Creates a new BackedImage with the given backing BufferedImage.
	 * @param image The backing BufferedImage.
	 */
	public IntBackedImage(BufferedImage image)
	{
		super(1, 1);
		width = image.getWidth();
		height = image.getHeight();
		if(image.getType() != BufferedImage.TYPE_INT_ARGB)
		{
			BufferedImage old = image;
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = image.createGraphics();
			g2.drawImage(old, 0, 0, null);
			g2.dispose();
		}
		buffer = image;
		data = ((DataBufferInt)buffer.getRaster().getDataBuffer()).getData();
	}
	
	@Override
	public boolean hasAlpha()
	{
		return true;
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
		buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		data = ((DataBufferInt)buffer.getRaster().getDataBuffer()).getData();
		clearClip();
	}
	
	@Override
	public void set(int x, int y, Color color)
	{
		data[x + (y * width)] = intColor(color);
	}

	@Override
	public IntARGB get(int x, int y)
	{
		tempColor.set(data[x + (y * width)]);
		return tempColor;
	}
	
	@Override
	public Color convertColor(Color color)
	{
		return IntARGB.pool.get().set(color);
	}
	
	/**
	 * @return The underlying BufferedImage.
	 */
	public BufferedImage getBufferImage()
	{
		return buffer;
	}
	
	//draw methods
	
	public void blit(Image image)
	{
		if(image instanceof IntBackedImage)
		{
			view.clear();
			if(view.visible())
			{
				for(int y = view.minY; y <= view.maxY; y++)
				{
					System.arraycopy(((IntBackedImage)image).data, view.minX + (image.width * y), data, view.minX + (width * y), view.maxX - view.minX + 1);
				}
			}
		}else
		{
			super.blit(image);
		}
	}
	
	protected void baseFillXScan(int x1, int x2, int y, Color color)
	{
		int col = intColor(color);
		for(int i = x1 + (y * width); i <= x2 + (y * width); i++)
		{
			data[i] = col;
		}
	}
	
	protected void baseFillYScan(int x, int y1, int y2, Color color)
	{
		int col = intColor(color);
		for(int i = x + (y1 * width); i <= x + (y2 * width); i++)
		{
			data[i] = col;
		}
	}
	
	private static final int intColor(Color color)
	{
		return ((color.getAlphaB() & 0xFF) << 24) + ((color.getRedB() & 0xFF) << 16) + ((color.getGreenB() & 0xFF) << 8) + (color.getBlueB() & 0xFF);
	}
}
