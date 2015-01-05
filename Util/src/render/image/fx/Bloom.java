package render.image.fx;

import math.vector.DoubleVector4D;
import math.vector.Vector3D;
import render.color.Color;
import render.color.ColorUtil;
import render.color.DoubleARGB;
import render.image.DoubleImage;
import render.image.Image;
import util.DefaultInterpolations;
import util.Util;

public class Bloom implements ImageFilter
{
	public static final DoubleImage bloomStorage = new DoubleImage(1, 1);
	public double threshold = .25;
	public int blurRadius = 4;
	public double baseIntensity = 1;
	public double baseSaturation = 1;
	public double bloomIntensity = 1;
	public double bloomSaturation = 1;
	
	private GaussianBlur blur = new GaussianBlur();
	
	@Override
	public void filter(Image image)
	{
		//claim storage var
		synchronized(bloomStorage)
		{
			if(bloomStorage.getWidth() < image.getWidth() || bloomStorage.getHeight() < image.getHeight())
			{
				bloomStorage.resize(Math.max(bloomStorage.getWidth(), image.getWidth()), Math.max(bloomStorage.getHeight(), image.getHeight()));
			}
			bloomStorage.blit(image);
			for(int x = 0; x < image.getWidth(); x++)
			{
				for(int y = 0; y < image.getHeight(); y++)
				{
					DoubleARGB color = (DoubleARGB)bloomStorage.get(x, y).subtract(threshold, threshold, threshold, threshold).divide(1 - threshold);
					Util.clip(color, 0, 1);
					bloomStorage.set(x, y, color);
				}
			}
			blur.setRadius(blurRadius);
			blur.filter(bloomStorage);
			DoubleARGB baseColor = DoubleARGB.pool.get();
			DoubleARGB bloomColor = DoubleARGB.pool.get();
			DoubleVector4D multiplier = DoubleVector4D.pool.get();
			for(int x = 0; x < image.getWidth(); x++)
			{
				for(int y = 0; y < image.getHeight(); y++)
				{
					baseColor.set(image.get(x, y));
					bloomColor.set((Color)bloomStorage.get(x, y));
					
					adjustSaturation(baseColor, baseSaturation);
					adjustSaturation(bloomColor, bloomSaturation);
					
					baseColor.multiply(baseIntensity);
					if(baseIntensity != 0) baseColor.w /= baseIntensity;
					bloomColor.multiply(bloomIntensity);
					if(bloomIntensity != 0) bloomColor.w /= bloomIntensity;
					
					//darken down the base image in areas where there is a lot of bloom,
				    //to prevent things looking excessively burned-out.
					multiplier.set(1, 1, 1, 1).subtract(bloomColor);
					//only clip RGB channels
					Util.clip((Vector3D)multiplier, 0, 1);
					multiplier.w = 1;//keep alpha unchanged
					baseColor.multiply(multiplier);
					
					baseColor.add(bloomColor);
					Util.clip(baseColor, 0, 1);
					
					image.set(x, y, baseColor);
				}
			}
		}
	}
	
	private static final void adjustSaturation(DoubleARGB color, double saturation)
	{
		DoubleARGB grey = DoubleARGB.pool.get();
		grey.set((Color)color);
		grey.setGrey(grey.getGrey());
		
		Color result = ColorUtil.interpolate(grey, color, saturation, DefaultInterpolations.LINEAR);
		color.set(result);
		
		grey.dispose();
		result.dispose();
	}
}
