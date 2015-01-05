package util;

import math.FastMath;

/**
 * 
 * Contains a group of commonly used Interpolations.
 * 
 * @author F4113nb34st
 *
 */
public class DefaultInterpolations
{
	/**
	 * Simple interpolation.
	 * Not continuous, nor is derivative.
	 */
	public static final Interpolation LINEAR = new Interpolation()
	{
		@Override
		public boolean extended()
		{
			return false;
		}

		@Override
		public double interpolate(double bottom, double top, double mu)
		{
			return (bottom * (1 - mu)) + (top * mu);
		}

		@Override public double interpolate(double past, double bottom, double top, double future, double mu){return interpolate(bottom, top, mu);}
		@Override public double interpolate(double past, double bottom, double top, double future, double mu, double tension, double bias){return interpolate(bottom, top, mu);}
	};
	
	/**
	 * Smoothed interpolation.
	 * Continuous, but derivative isn't.
	 */
	public static final Interpolation COSINE = new Interpolation()
	{
		@Override
		public boolean extended()
		{
			return false;
		}

		@Override
		public double interpolate(double bottom, double top, double mu)
		{
			mu = (1 - Math.cos(mu * Math.PI)) / 2;
			return (bottom * (1 - mu)) + (top * mu);
		}

		@Override public double interpolate(double past, double bottom, double top, double future, double mu){return interpolate(bottom, top, mu);}
		@Override public double interpolate(double past, double bottom, double top, double future, double mu, double tension, double bias){return interpolate(bottom, top, mu);}
	};
	
	/**
	 * Smoothed interpolation.
	 * Continuous, but derivative isn't.
	 * Uses a faster, but less precise cosine function.
	 */
	public static final Interpolation FAST_COSINE = new Interpolation()
	{
		@Override
		public boolean extended()
		{
			return false;
		}

		@Override
		public double interpolate(double bottom, double top, double mu)
		{
			mu = (1 - FastMath.cos(mu * Math.PI)) / 2;
			return (bottom * (1 - mu)) + (top * mu);
		}

		@Override public double interpolate(double past, double bottom, double top, double future, double mu){return interpolate(bottom, top, mu);}
		@Override public double interpolate(double past, double bottom, double top, double future, double mu, double tension, double bias){return interpolate(bottom, top, mu);}
	};
	
	/**
	 * Smoothed interpolation.
	 * Requires extra samples.
	 * Continuous, and derivative too.
	 */
	public static final Interpolation CUBIC = new Interpolation()
	{
		@Override
		public boolean extended()
		{
			return true;
		}

		@Override
		public double interpolate(double past, double bottom, double top, double future, double mu)
		{
			double muSq = mu * mu;
			double a0 = (future - top) - (past - bottom);
			double a1 = (past - bottom) - a0;
			double a2 = (top - past);
			double a3 = bottom;
			return (a0 * mu * muSq) + (a1 * muSq) + (a2 * mu) + a3;
		}
		
		@Override public double interpolate(double bottom, double top, double mu){return interpolate(bottom, bottom, top, top, mu);}
		@Override public double interpolate(double past, double bottom, double top, double future, double mu, double tension, double bias){return interpolate(past, bottom, top, future, mu);}
	}; 
	
	/**
	 * Smoothed interpolation.
	 * Requires extra samples.
	 * Continuous, and derivative too.
	 * Less lax than cubic.
	 */
	public static final Interpolation CATMULL_ROM = new Interpolation()
	{
		@Override
		public boolean extended()
		{
			return true;
		}

		@Override
		public double interpolate(double past, double bottom, double top, double future, double mu)
		{
			double muSq = mu * mu;
			double a0 = ((future / 2) - (top * 3 / 2)) - ((past / 2) - (bottom * 3 / 2));
			double a1 = (past - (bottom * 5 / 2)) - ((future / 2) - (top * 2));
			double a2 = (top - past) / 2;
			double a3 = bottom;
			return (a0 * mu * muSq) + (a1 * muSq) + (a2 * mu) + a3;
		}
		
		@Override public double interpolate(double bottom, double top, double mu){return interpolate(bottom, bottom, top, top, mu);}
		@Override public double interpolate(double past, double bottom, double top, double future, double mu, double tension, double bias){return interpolate(past, bottom, top, future, mu);}
	}; 
	
	/**
	 * Smoothed interpolation.
	 * Requires extra samples.
	 * Continuous, and derivative too.
	 * Allows extra parameters.
	 */
	public static final Interpolation HERMITE = new Interpolation()
	{
		@Override
		public boolean extended()
		{
			return true;
		}

		@Override
		public double interpolate(double past, double bottom, double top, double future, double mu, double tension, double bias)
		{
			double muSq = mu * mu;
			double muCu = muSq * mu;
			double tenMulti = (1 - tension) / 2;
			
			double m0 = (bottom - past) * (1 + bias) * tenMulti;
			      m0 += (top -  bottom) * (1 - bias) * tenMulti;
			double m1 = (top -  bottom) * (1 + bias) * tenMulti;
			      m1 += (future -  top) * (1 - bias) * tenMulti;
			      
			double a0 =  (2 * muCu) - (3 * muSq) + 1;
			double a1 =      (muCu) - (2 * muSq) + mu;
			double a2 =      (muCu) -     (muSq);
			double a3 = -(2 * muCu) + (3 * muSq);
			
			return (a0 * bottom) + (a1 * m0) + (a2 * m1) + (a3 * top);
		}
		
		@Override public double interpolate(double bottom, double top, double mu){return interpolate(bottom, bottom, top, top, mu, 0, 0);}
		@Override public double interpolate(double past, double bottom, double top, double future, double mu){return interpolate(past, bottom, top, future, mu, 0, 0);}
	};
}
