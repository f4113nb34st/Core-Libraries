package render.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import render.color.ByteARGB;
import render.color.Color;

/**
 * 
 * An image that uses a backing BufferedImage for storage.
 * 
 * @author F4113nb34st
 *
 */
public final class BackedImage extends Image
{
	/**
	 * The backing BufferedImage.
	 */
	protected BufferedImage buffer;
	
	/**
	 * The pixels in the image.
	 */
	protected byte[] data;
	
	private boolean usesAlpha = false;
	
	//temp color for getting
	private ByteARGB tempColor = new ByteARGB();

	/**
	 * Creates a new BackedImage of the given size.
	 * @param w The width.
	 * @param h The height.
	 */
	public BackedImage(int w, int h)
	{
		super(w, h);
	}

	/**
	 * Creates a deep copy of the given image.
	 * @param image The image to copy.
	 */
	public BackedImage(Image image)
	{
		super(image);
	}

	/**
	 * Creates a new BackedImage with the given backing BufferedImage.
	 * @param image The backing BufferedImage.
	 */
	public BackedImage(BufferedImage image)
	{
		super(1, 1);
		width = image.getWidth();
		height = image.getHeight();
		if(image.getType() != BufferedImage.TYPE_4BYTE_ABGR)
		{
			BufferedImage old = image;
			image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g2 = image.createGraphics();
			g2.drawImage(old, 0, 0, null);
			g2.dispose();
		}
		buffer = image;
		data = ((DataBufferByte)buffer.getRaster().getDataBuffer()).getData();
		
	}
	
	@Override
	public boolean hasAlpha()
	{
		return usesAlpha;
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
		buffer = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		data = ((DataBufferByte)buffer.getRaster().getDataBuffer()).getData();
		usesAlpha = false;
		clearClip();
	}

	@Override
	public void set(int x, int y, Color color)
	{
		data[((x + (y * width)) * 4) + 0] = color.getAlphaB();
		data[((x + (y * width)) * 4) + 3] = color.getRedB();
		data[((x + (y * width)) * 4) + 2] = color.getGreenB();
		data[((x + (y * width)) * 4) + 1] = color.getBlueB();
		usesAlpha |= color.hasAlpha();
	}
	
	@Override
	public ByteARGB get(int x, int y)
	{
		tempColor.set(data[((x + (y * width)) * 4) + 3], 
	             	  data[((x + (y * width)) * 4) + 2], 
	             	  data[((x + (y * width)) * 4) + 1], 
	             	  data[((x + (y * width)) * 4) + 0]);
		return tempColor;
	}
	
	@Override
	public Color convertColor(Color color)
	{
		return ByteARGB.pool.get().set(color);
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
		if(image instanceof BackedImage)
		{
			view.clear();
			if(view.visible())
			{
				for(int y = view.minY; y <= view.maxY; y++)
				{
					System.arraycopy(((BackedImage)image).data, view.minX + (image.width * y), data, view.minX + (width * y), view.maxX - view.minX + 1);
				}
			}
		}else
		{
			super.blit(image);
		}
	}
}
