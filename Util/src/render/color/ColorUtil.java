package render.color;

import math.vector.DoubleVector3D;
import util.Interpolation;
import util.Util;

/**
 * 
 * Provides utility functions for manipulating colors.
 * 
 * @author F4113nb34st
 *
 */
public class ColorUtil
{
	public static final Color interpolate(Color bottom, Color top, double mu, Interpolation interp)
	{
		Color result = Color.pool_map.get(bottom.getClass()).get();
		if(result.hasAlpha())
		{
			result.setAlpha(interp.interpolate(bottom.getAlpha(), top.getAlpha(), mu));
		}
		if(result.hasColor())
		{
			result.setRed(interp.interpolate(bottom.getRed(), top.getRed(), mu));
			result.setGreen(interp.interpolate(bottom.getGreen(), top.getGreen(), mu));
			result.setBlue(interp.interpolate(bottom.getBlue(), top.getBlue(), mu));
		}else
		if(result.hasGrey())
		{
			result.setGrey(interp.interpolate(bottom.getGrey(), top.getGrey(), mu));
		}
		return result;
	}
	
	public static final Color interpolate(Color past, Color bottom, Color top, Color future, double mu, Interpolation interp)
	{
		if(!interp.extended())
		{
			return interpolate(bottom, top, mu, interp);
		}
		Color result = Color.pool_map.get(bottom.getClass()).get();
		if(result.hasAlpha())
		{
			result.setAlpha(interp.interpolate(past.getAlpha(), bottom.getAlpha(), top.getAlpha(), future.getAlpha(), mu));
		}
		if(result.hasColor())
		{
			result.setRed(interp.interpolate(past.getRed(), bottom.getRed(), top.getRed(), future.getRed(), mu));
			result.setGreen(interp.interpolate(past.getGreen(), bottom.getGreen(), top.getGreen(), future.getGreen(), mu));
			result.setBlue(interp.interpolate(past.getBlue(), bottom.getBlue(), top.getBlue(), future.getBlue(), mu));
		}else
		if(result.hasGrey())
		{
			result.setGrey(interp.interpolate(past.getGrey(), bottom.getGrey(), top.getGrey(), future.getGrey(), mu));
		}
		return result;
	}
	
	public static final void setHue(Color c, double h)
	{
		DoubleVector3D hsl = toHSL(c);
		hsl.x = h;
		fromHSL(hsl, c);
		DoubleVector3D.pool.release(hsl);
	}
	
	public static final void setSaturation(Color c, double s)
	{
		DoubleVector3D hsl = toHSL(c);
		hsl.y = s;
		fromHSL(hsl, c);
		DoubleVector3D.pool.release(hsl);
	}
	
	public static final void setLightness(Color c, double l)
	{
		DoubleVector3D hsl = toHSL(c);
		hsl.z = l;
		fromHSL(hsl, c);
		DoubleVector3D.pool.release(hsl);
	}
	
	public static final void setHSL(Color c, double h, double s, double l)
	{
		DoubleVector3D hsl = DoubleVector3D.pool.get().set(h, s, l);
		fromHSL(hsl, c);
		DoubleVector3D.pool.release(hsl);
	}
	
	private static final DoubleVector3D toHSL(Color c)
	{
		DoubleVector3D color = DoubleVector3D.pool.get().set(c.getRed(), c.getGreen(), c.getBlue());
		//max
		double max = Util.max(color.x, color.y, color.z);
		//min
		double min = Util.min(color.x, color.y, color.z);
		//delta
		double delta = max - min;
		
		DoubleVector3D hsl = DoubleVector3D.pool.get();
		
		//lum
		hsl.z = (max + min) / 2;
		
		if(delta == 0)
		{
			//hue and sat = 0
			hsl.x = hsl.y = 0;
		}else
		{
			if(max == color.x)
			{
				//hue
				hsl.x = (color.y - color.z) / delta;
			}else
			if(max == color.y)
			{
				//hue
				hsl.x = (color.y - color.x) / delta + 2;
			}else
			{
				//hue
				hsl.x = (color.x - color.y) / delta + 4;
			}
			hsl.x /= 6;
			
			//sat
			hsl.y = delta / (1 - Math.abs((2 * hsl.z) - 1));
		}
		
		DoubleVector3D.pool.release(color);
		
		return hsl;
	}
	
	private static final void fromHSL(DoubleVector3D hsl, Color c)
	{
		double C = (1 - Math.abs(2 * hsl.z - 1)) * hsl.y;
		double X = C * (1 - Math.abs(((hsl.x * 6) % 2) - 1));
		double m = hsl.z - (C / 2);
		
		int hue = (int)(hsl.x * 6);
		switch(hue)
		{
			case 0:
			{
				c.setRed(C + m);
				c.setGreen(X + m);
				c.setBlue(m);
			}
			case 1:
			{
				c.setRed(X + m);
				c.setGreen(C + m);
				c.setBlue(m);
			}
			case 2:
			{
				c.setRed(m);
				c.setGreen(C + m);
				c.setBlue(X + m);
			}
			case 3:
			{
				c.setRed(m);
				c.setGreen(X + m);
				c.setBlue(C + m);
			}
			case 4:
			{
				c.setRed(X + m);
				c.setGreen(m);
				c.setBlue(C + m);
			}
			case 5:
			{
				c.setRed(C + m);
				c.setGreen(m);
				c.setBlue(X + m);
			}
		}
	}
}
