package util;

import math.vector.Vector1D;
import math.vector.Vector2D;
import math.vector.Vector3D;
import math.vector.Vector4D;

/**
 * 
 * Simple util class for misc. methods.
 * 
 * @author F4113nb34st
 *
 */
public class Util
{
	/**
	 * Clips the given int to between the min and max.
	 * @param value The value to clip.
	 * @param min The min value.
	 * @param max The max value.
	 * @return The clipped value.
	 */
	public static final int clip(int value, int min, int max)
	{
		if(value < min)
		{
			return min;
		}else
		if(value > max)
		{
			return max;
		}else
		{
			return value;
		}
	}
	
	/**
	 * Wraps the given int to between the min and max.
	 * @param value The value to wrap.
	 * @param min The min value.
	 * @param max The max value.
	 * @return The wrapped value.
	 */
	public static final int wrap(int value, int min, int max)
	{
		int dif = max - min + 1;
		
		//naive implementation
		/*
		while(value < min)
		{
			value += dif;
		}
		while(value > max)
		{
			value -= dif;
		}
		*/
		
		//fast implementation
		value -= min;
		if(value < 0)//if less than 0 (aka % won't work)
		{
			value += ((-value / dif) + 1) * dif;//find exact amount needed to bring over 0
		}
		value %= dif;
		return value;
	}
	
	/**
	 * Clips the given double to between the min and max.
	 * @param value The value to clip.
	 * @param min The min value.
	 * @param max The max value.
	 * @return The clipped value.
	 */
	public static final double clip(double value, double min, double max)
	{
		if(value < min)
		{
			return min;
		}else
		if(value > max)
		{
			return max;
		}else
		{
			return value;
		}
	}
	
	/**
	 * Wraps the given double to between the min and max.
	 * @param value The value to wrap.
	 * @param min The min value.
	 * @param max The max value.
	 * @return The wrapped value.
	 */
	public static final double wrap(double value, double min, double max)
	{
		double dif = max - min;
		
		//fast implementation
		value -= min;
		if(value < 0)//if less than 0 (aka % won't work)
		{
			value += ((int)(-value / dif) + 1) * dif;//find exact amount needed to bring over 0
		}
		value %= dif;
		return value;
	}
	
	/**
	 * Clips the coords of the given 1D Vector to between the min and max.
	 * @param value The value to clip.
	 * @param min The min value.
	 * @param max The max value.
	 */
	public static final void clip(Vector1D value, double min, double max)
	{
		value.setX(clip(value.getDoubleX(), min, max));
	}
	
	/**
	 * Wraps the coords of the given 1D Vector to between the min and max.
	 * @param value The value to wrap.
	 * @param min The min value.
	 * @param max The max value.
	 */
	public static final void wrap(Vector1D value, double min, double max)
	{
		value.setX(wrap(value.getDoubleX(), min, max));
	}
	
	/**
	 * Clips the coords of the given 2D Vector to between the min and max.
	 * @param value The value to clip.
	 * @param min The min value.
	 * @param max The max value.
	 */
	public static final void clip(Vector2D value, double min, double max)
	{
		value.setX(clip(value.getDoubleX(), min, max));
		value.setY(clip(value.getDoubleY(), min, max));
	}
	
	/**
	 * Wraps the coords of the given 2D Vector to between the min and max.
	 * @param value The value to wrap.
	 * @param min The min value.
	 * @param max The max value.
	 */
	public static final void wrap(Vector2D value, double min, double max)
	{
		value.setX(wrap(value.getDoubleX(), min, max));
		value.setY(wrap(value.getDoubleY(), min, max));
	}
	
	/**
	 * Clips the coords of the given 3D Vector to between the min and max.
	 * @param value The value to clip.
	 * @param min The min value.
	 * @param max The max value.
	 */
	public static final void clip(Vector3D value, double min, double max)
	{
		value.setX(clip(value.getDoubleX(), min, max));
		value.setY(clip(value.getDoubleY(), min, max));
		value.setZ(clip(value.getDoubleZ(), min, max));
	}
	
	/**
	 * Wraps the coords of the given 3D Vector to between the min and max.
	 * @param value The value to wrap.
	 * @param min The min value.
	 * @param max The max value.
	 */
	public static final void wrap(Vector3D value, double min, double max)
	{
		value.setX(wrap(value.getDoubleX(), min, max));
		value.setY(wrap(value.getDoubleY(), min, max));
		value.setZ(wrap(value.getDoubleZ(), min, max));
	}
	
	/**
	 * Clips the coords of the given 3D Vector to between the min and max.
	 * @param value The value to clip.
	 * @param min The min value.
	 * @param max The max value.
	 */
	public static final void clip(Vector4D value, double min, double max)
	{
		value.setX(clip(value.getDoubleX(), min, max));
		value.setY(clip(value.getDoubleY(), min, max));
		value.setZ(clip(value.getDoubleZ(), min, max));
		value.setW(clip(value.getDoubleW(), min, max));
	}
	
	/**
	 * Wraps the coords of the given 3D Vector to between the min and max.
	 * @param value The value to wrap.
	 * @param min The min value.
	 * @param max The max value.
	 */
	public static final void wrap(Vector4D value, double min, double max)
	{
		value.setX(wrap(value.getDoubleX(), min, max));
		value.setY(wrap(value.getDoubleY(), min, max));
		value.setZ(wrap(value.getDoubleZ(), min, max));
		value.setW(wrap(value.getDoubleW(), min, max));
	}
	
	/**
	 * Returns the smallest of the given double values.
	 * @param ds The doubles.
	 * @return The smallest double.
	 */
	public static final double min(double... ds)
	{
		double min = Double.POSITIVE_INFINITY;
		for(double d : ds)
		{
			min = Math.min(min, d);
		}
		return min;
	}
	
	/**
	 * Returns the greatest of the given double values.
	 * @param ds The doubles.
	 * @return The greatest double.
	 */
	public static final double max(double... ds)
	{
		double max = Double.NEGATIVE_INFINITY;
		for(double d : ds)
		{
			max = Math.max(max, d);
		}
		return max;
	}
	
	/**
	 * Returns the smallest of the given int values.
	 * @param is The ints.
	 * @return The smallest int.
	 */
	public static final int min(int... is)
	{
		int min = Integer.MAX_VALUE;
		for(int i : is)
		{
			min = Math.min(min, i);
		}
		return min;
	}
	
	/**
	 * Returns the greatest of the given int values.
	 * @param is The ints.
	 * @return The greatest int.
	 */
	public static final int max(int... is)
	{
		int max = Integer.MIN_VALUE;
		for(int i : is)
		{
			max = Math.max(max, i);
		}
		return max;
	}
	
	/**
	 * Fast Integer-based pow function.
	 * @param base The base
	 * @param exp The exponent
	 * @return base ^ exponent
	 */
	public static final double ipow(int base, int exp)
	{
		if(exp == 0)
		{
			return 1;
		}
		if(exp == 1)
		{
			return base;
		}
		if(base == 0 || base == 1)
		{
			return base;
		}
		int result = 1;
		if(exp >= 0)
		{
			while(exp != 0)
		    {
		        if((exp & 1) == 1)
		        {
		            result *= base;
		        }
		        exp >>= 1;
		        base *= base;
		    }
		}else
		{
			exp = -exp;
			while(exp != 0)
		    {
		        if((exp & 1) == 1)
		        {
		            result /= base;
		        }
		        exp >>= 1;
		        base *= base;
		    }
		}

	    return result;
	}
	
	/**
	 * Rounds the given value to the given number of decimals. (supports negative as well)
	 * @param value The value to round.
	 * @param digits The digits to round to.
	 * @return The rounded value.
	 */
	public static final double roundTo(double value, int digits)
	{
		double multi = ipow(10, digits);
		return Math.round(value * multi) / multi; 
	}
	
	/**
	 * Returns the given angle to between the two given angle measures. Takes into account the wrapping from 2PI to 0.
	 * @param angle The angle measure.
	 * @param min The minimum (most counter-clockwise) angle.
	 * @param max The maximum (most clockwise) angle.
	 * @return The clipped angle.
	 */
	public static final double angleClip(double angle, double min, double max)
	{
		angle = wrap(angle, 0, Math.PI * 2);
		min = wrap(min, 0, Math.PI * 2);
		max = wrap(max, 0, Math.PI * 2);
		if(min < max)
		{
			if(angle < min || angle > max)
			{
				if(angleDif(angle, min) < angleDif(angle, max))
				{
					angle = min;
				}else
				{
					angle = max;
				}
			}
		}else
		{
			if(angle > max && angle < min)
			{
				if(angleDif(angle, min) < angleDif(angle, max))
				{
					angle = min;
				}else
				{
					angle = max;
				}
			}
		}
		return angle;
	}
	
	/**
	 * Returns the angle difference between the two given angle measures. Takes into account the wrapping from 2PI to 0.
	 * @param a1 The first angle.
	 * @param a2 The second angle.
	 * @return The angle difference.
	 */
	public static final double angleDif(double a1, double a2)
	{
		return wrapDif(a1, a2, Math.PI * 2);
	}
	
	/**
	 * Returns the difference between the two given coords. Uses wrapping arround the given value.
	 * @param a1 The first coord.
	 * @param a2 The second coord.
	 * @return The wrapped difference.
	 */
	public static final double wrapDif(double a1, double a2, double wrap)
	{
		double d = Math.abs(a1 - a2) % wrap;
		if(d > wrap / 2)
		{
			d = wrap - d;
		}
		return d;
	}
}
