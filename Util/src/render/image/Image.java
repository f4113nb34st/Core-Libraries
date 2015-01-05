package render.image;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import render.color.Color;

/**
 * 
 * Represents an image that stores 2d arrays for information.
 * 
 * @author F4113nb34st
 *
 */
public abstract class Image
{
	/**
	 * Width of the image.
	 */
	protected int width;
	
	/**
	 * Height of the image.
	 */
	protected int height;
	
	/**
	 * Creates a new NoiseImage of the given size.
	 * @param w The width.
	 * @param h The height.
	 */
	public Image(int w, int h)
	{
		resize(w, h);
	}

	/**
	 * Creates a deep copy of the given image.
	 * @param image The image to copy.
	 */
	public Image(Image image)
	{
		this(image.getWidth(), image.getHeight());
		blit(image);
	}
	
	/**
	 * @return True if this Image has an alpha channel.
	 */
	public abstract boolean hasAlpha();
	
	/**
	 * @return True if this Image has a color channel.
	 */
	public abstract boolean hasColor();
	
	/**
	 * @return True if this Image has a grey channel.
	 */
	public abstract boolean hasGrey();
	
	/**
	 * Resizes this Image.
	 * @param w The new width.
	 * @param h The new height.
	 */
	public abstract void resize(int w, int h);

	/**
	 * Sets the given pixel to the given color.
	 * @param x The x location.
	 * @param y The y location.
	 * @param color The color.
	 */
	public abstract void set(int x, int y, Color color);

	/**
	 * Returns the color for the given pixel. Typically the result is storage-unsafe.
	 * @param x The x location.
	 * @param y The y location.
	 * @return The color.
	 */
	public abstract Color get(int x, int y);
	
	/**
	 * Converts the given color to this Image's native type.
	 * @param color The color.
	 * @return The native color.
	 */
	public abstract Color convertColor(Color color);
	
	/**
	 * @return The width of this Image.
	 */
	public final int getWidth()
	{
		return width;
	}
	
	/**
	 * @return The height of this Image.
	 */
	public final int getHeight()
	{
		return height;
	}
	
	/**
	 * Loads an image from the given file.
	 * @param file The file to load from.
	 */
	public static final Image loadImage(File file)
	{
		try
		{
			return new IntBackedImage(ImageIO.read(file));
		} catch(IOException ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Loads an image from the given file.
	 * @param file The file to load from.
	 */
	public static final void saveImage(File file, Image image)
	{
		if(!(image instanceof IntBackedImage))
		{
			image = new IntBackedImage(image);
		}
		try
		{
			ImageIO.write(((IntBackedImage)image).getBufferImage(), "png", file);
		} catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	//draw methods
	
	//the clip coords that are used behind the scenes
	protected int clipX1;
	protected int clipY1;
	protected int clipX2;
	protected int clipY2;
	//viewport used by the rendering methods
	protected Viewport view = new Viewport();
	protected class Viewport
	{
		protected int minX;
		protected int minY;
		protected int maxX;
		protected int maxY;
		
		protected boolean visible()
		{
			return maxX >= minX && maxY >= minY;
		}
		
		protected void clear()
		{
			minX = clipX1;
			minY = clipY1;
			maxX = clipX2;
			maxY = clipY2;
		}
		
		protected void set(int x1, int y1, int x2, int y2)
		{
			minX = Math.max(x1, clipX1);
			minY = Math.max(y1, clipY1);
			maxX = Math.min(x2, clipX2);
			maxY = Math.min(y2, clipY2);
		}
	}
	
	/**
	 * Sets the clipping bounds for drawing operations.
	 * @param x The clip x loc.
	 * @param y The clip y loc.
	 * @param w The clip width.
	 * @param h The clip height.
	 */
	public void setClip(int x, int y, int w, int h)
	{
		clipX1 = Math.max(x, 0);
		clipY1 = Math.max(y, 0);
		clipX2 = Math.min(x + w - 1, width - 1);
		clipY2 = Math.min(y + h - 1, height - 1);
	}
	
	/**
	 * Clears the clipping bounds for drawing operations.
	 */
	public void clearClip()
	{
		setClip(0, 0, width, height);
	}
	
	/**
	 * Copys the given image. Assumes equal sizes.
	 * @param image The image.
	 */
	public void blit(Image image)
	{
		view.clear();
		view.maxX = Math.max(view.maxX, image.getWidth() - 1);
		view.maxY = Math.max(view.maxY, image.getHeight() - 1);
		if(view.visible())
		{
			for(int x = view.minX; x <= view.maxX; x++)
			{
				for(int y = view.minY; y <= view.maxY; y++)
				{
					set(x, y, image.get(x, y));
				}
			}
		}
	}
	
	/**
	 * Normalizes the grey values to 0<x<1.
	 */
	public void normalize()
	{
		view.clear();
		if(view.visible())
		{
			double min = Double.POSITIVE_INFINITY;
			double max = Double.NEGATIVE_INFINITY;
			for(int x = view.minX; x <= view.maxX; x++)
			{
				for(int y = view.minY; y <= view.maxY; y++)
				{
					min = Math.min(min, get(x, y).getGrey());
					max = Math.max(max, get(x, y).getGrey());
				}
			}
			//if min == max, then everything is already "normalized"
			if(min != max)
			{
				for(int x = view.minX; x <= view.maxX; x++)
				{
					for(int y = view.minY; y <= view.maxY; y++)
					{
						Color color = get(x, y);
						double val = color.getGrey();
						val -= min;
						val /= (max - min);
						color.setGrey(val);
						set(x, y, color);
					}
				}
			}
		}
	}
	
	/**
	 * Fills a horizontal scan line.
	 * @param x1 The min edge.
	 * @param x2 The max edge.
	 * @param y The y coord.
	 * @param color The color.
	 */
	public void fillXScan(int x1, int x2, int y, Color color)
	{
		view.set(x1, y, x2, y);
		if(view.visible())
		{
			color = convertColor(color);
			baseFillXScan(view.minX, view.maxX, view.minY, color);
			color.dispose();
		}
	}
	
	/**
	 * Fills a vertical scan line.
	 * @param x The x coord.
	 * @param y1 The min edge.
	 * @param y2 The max edge.
	 * @param color The color.
	 */
	public void fillYScan(int x, int y1, int y2, Color color)
	{
		view.set(x, y1, x, y2);
		if(view.visible())
		{
			color = convertColor(color);
			baseFillXScan(view.minX, view.minY, view.maxY, color);
			color.dispose();
		}
	}
	
	/**
	 * Fills a horizontal scan line. Skips view checking. Internal use only.
	 */
	protected void baseFillXScan(int x1, int x2, int y, Color color)
	{
		for(int x = x1; x <= x2; x++)
		{
			set(x, y, color);
		}
	}
	
	/**
	 * Fills a vertical scan line. Skips view checking. Internal use only.
	 */
	protected void baseFillYScan(int x, int y1, int y2, Color color)
	{
		for(int y = y1; y <= y2; y++)
		{
			set(x, y, color);
		}
	}
	
	/**
	 * Fills with the given color.
	 * @param color The color.
	 */
	public void fill(Color color)
	{
		view.clear();
		if(view.visible())
		{
			color = convertColor(color);
			for(int x = view.minX; x <= view.maxX; x++)
			{
				for(int y = view.minY; y <= view.maxY; y++)
				{
					set(x, y, color);
				}
			}
			color.dispose();
		}
	}
	
	/**
	 * Fills the given rectangle with the given color.
	 * @param x The x loc.
	 * @param y The y loc.
	 * @param w The width.
	 * @param h The height.
	 * @param color The color.
	 */
	public void fillRect(int x, int y, int w, int h, Color color)
	{
		view.set(x, y, x + w - 1, y + h - 1);
		if(view.visible())
		{
			color = convertColor(color);
			for(int j = view.minY; j <= view.maxY; j++)
			{
				fillXScan(view.minX, view.maxX, j, color);
			}
			color.dispose();
		}
	}
	
	/**
	 * Fills the given rounded rectangle with the given color.
	 * @param x The x loc.
	 * @param y The y loc.
	 * @param w The width.
	 * @param h The height.
	 * @param xradius The x rounding radius.
	 * @param yradius The y rounding radius.
	 * @param color The color.
	 */
	public void fillRoundedRect(int x, int y, int w, int h, double xradius, double yradius, Color color)
	{
		view.set(x, y, x + w - 1, y + h - 1);
		if(view.visible())
		{
			color = convertColor(color);
			for(int j = view.minY; j <= view.maxY; j++)
			{
				//find edges
				int left;
				int right;
				if(j >= y + yradius && j <= y + h - 1 - yradius)
				{
					left = x;
					right = x + w - 1;
				}else
				{
					double dy = Math.max((y + yradius) - j, j - (y + h - 1 - yradius)) / yradius;
					double dx = Math.sqrt(1 - dy * dy);
					left = (int)(x + xradius - (dx * xradius));
					right = (int)(x + w - 1 - xradius + (dx * xradius));
				}
				left = Math.max(left, view.minX);
				right = Math.min(right, view.maxX);
				
				//fill scan
				baseFillXScan(left, right, j, color);
			}
			color.dispose();
		}
	}
	
	/**
	 * Fills the given ellipse with the given color.
	 * @param x The x loc.
	 * @param y The y loc.
	 * @param w The width.
	 * @param h The height.
	 * @param color The color.
	 */
	public void fillOval(int x, int y, int w, int h, Color color)
	{
		fillRoundedRect(x, y, w, h, w / 2D, h / 2D, color);
	}
	
	/**
	 * Fills the given circle with the given color.
	 * @param x The center x loc.
	 * @param y The center y loc.
	 * @param radius The radius.
	 * @param color The color.
	 */
	public void fillCircle(int x, int y, int radius, Color color)
	{
		fillOval(x - radius, y - radius, radius * 2, radius * 2, color);
	}
}
