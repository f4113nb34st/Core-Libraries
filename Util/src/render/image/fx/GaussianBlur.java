package render.image.fx;

import render.color.DoubleARGB;
import render.image.DoubleImage;
import render.image.Image;
import util.Util;

public class GaussianBlur implements ImageFilter
{
	private static final Image storage = new DoubleImage(1, 1);
	private int radius;
	private double[] weights;
	
	public GaussianBlur()
	{
		setRadius(1);
	}
	
	/**
	 * Sets the radius of the blurring.
	 * @param rad The radius.
	 */
	public void setRadius(int rad)
	{
		radius = rad;
		weights = new double[(radius * 2) + 1];
		double total = 1;
		double prev;
		double next = 1;
		weights[radius] = 1;
		for(int i = 1; i <= radius; i++)
		{
			prev = next;
			if(i == radius - 1)
			{
				next = 0;
			}else
			{
				next = Math.exp(-((i + 1) * (i + 1)) / (double)(2 * radius * radius));
			}
			weights[radius + i] = (prev + next) / 2D;
			weights[radius - i] = (prev + next) / 2D;
			total += prev + next;
		}
		for(int i = 0; i < weights.length; i++)
		{
			weights[i] /= total;
		}
	}
	
	@Override
	public void filter(Image image)
	{
		//claim storage var
		synchronized(storage)
		{
			if(storage.getWidth() < image.getWidth() || storage.getHeight() < image.getHeight())
			{
				storage.resize(Math.max(storage.getWidth(), image.getWidth()), Math.max(storage.getHeight(), image.getHeight()));
			}
			DoubleARGB blurColor = DoubleARGB.pool.get();
			DoubleARGB pixelColor = DoubleARGB.pool.get();
			for(int x = 0; x < image.getWidth(); x++)
			{
				for(int y = 0; y < image.getHeight(); y++)
				{
					blurColor.set(0, 0, 0, 0);
					for(int i = -radius; i <= radius; i++)
					{
						pixelColor.set(image.get(Util.clip(x + i, 0, image.getWidth() - 1), y));
						pixelColor.multiply(weights[i + radius]);
						blurColor.add(pixelColor);
					}
					storage.set(x, y, blurColor);
				}
			}
			for(int x = 0; x < image.getWidth(); x++)
			{
				for(int y = 0; y < image.getHeight(); y++)
				{
					blurColor.set(0, 0, 0, 0);
					for(int i = -radius; i <= radius; i++)
					{
						pixelColor.set(storage.get(x, Util.clip(y + i, 0, image.getHeight() - 1)));
						pixelColor.multiply(weights[i + radius]);
						blurColor.add(pixelColor);
					}
					image.set(x, y, blurColor);
				}
			}
			blurColor.dispose();
			pixelColor.dispose();
		}
	}
}
