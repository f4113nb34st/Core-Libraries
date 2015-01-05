package render.color;

import util.pool.ObjectPool;
import math.vector.DoubleVector1D;
import math.vector.Vector1D;

public final class DoubleAlpha extends DoubleVector1D implements Color
{
	public static final ObjectPool<DoubleAlpha> pool = new ObjectPool<DoubleAlpha>(new DoubleAlpha());
	static
	{
		Color.pool_map.put(DoubleAlpha.class, pool);
	}
	
	public DoubleAlpha()
	{
		set(0);
	}
	
	public DoubleAlpha(double i)
	{
		set(i);
	}
	
	public DoubleAlpha(Vector1D vec)
	{
		set(vec);
	}
	
	public DoubleAlpha(Color color)
	{
		set(color);
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
	
	@Override
	public Color set(Color color)
	{
		set(color.getAlpha());
		return this;
	}
	
	@Override 
	public final void setAlpha(double a)
	{
		x = a;
	}
	
	@Override 
	public final double getAlpha()
	{
		return x;
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public DoubleAlpha copy()
	{
		return new DoubleAlpha((Vector1D)this);
	}

	@Override
	public void dispose()
	{
		pool.release(this);
	}
	
	@Override public final void setAlpha(byte a){setAlpha((a & 0xFF) / 255D);}
	@Override public final byte getAlphaB(){return (byte)(getAlpha() * 255);}
	
	@Override public void setGrey(double g){}
	@Override public final void setGrey(byte g){}
	@Override public double getGrey(){return 1;}
	@Override public final byte getGreyB(){return (byte)255;}
	
	@Override public final void setRed(double r){setGrey(r);}
	@Override public final void setRed(byte r){setGrey(r);}
	@Override public final double getRed(){return getGrey();}
	@Override public final byte getRedB(){return getGreyB();}
	
	@Override public final void setGreen(double g){setGrey(g);}
	@Override public final void setGreen(byte g){setGrey(g);}
	@Override public final double getGreen(){return getGrey();}
	@Override public final byte getGreenB(){return getGreyB();}
	
	@Override public final void setBlue(double b){setGrey(b);}
	@Override public final void setBlue(byte b){setGrey(b);}
	@Override public final double getBlue(){return getGrey();}
	@Override public final byte getBlueB(){return getGreyB();}
}
