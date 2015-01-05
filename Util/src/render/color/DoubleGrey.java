package render.color;

import util.pool.ObjectPool;
import math.vector.DoubleVector1D;
import math.vector.Vector1D;

public final class DoubleGrey extends DoubleVector1D implements Color
{
	public static final ObjectPool<DoubleGrey> pool = new ObjectPool<DoubleGrey>(new DoubleGrey());
	static
	{
		Color.pool_map.put(DoubleGrey.class, pool);
	}
	
	public DoubleGrey()
	{
		set(0);
	}
	
	public DoubleGrey(double i)
	{
		set(i);
	}
	
	public DoubleGrey(Vector1D vec)
	{
		set(vec);
	}
	
	public DoubleGrey(Color color)
	{
		set(color);
	}
	
	@Override
	public boolean hasAlpha()
	{
		return false;
	}

	@Override
	public boolean hasColor()
	{
		return false;
	}

	@Override
	public boolean hasGrey()
	{
		return true;
	}
	
	@Override
	public Color set(Color color)
	{
		set(color.getGrey());
		return this;
	}
	
	@Override
	public void setGrey(double g)
	{
		x = g;
	}
	
	@Override
	public double getGrey()
	{
		return x;
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public DoubleGrey copy()
	{
		return new DoubleGrey((Vector1D)this);
	}

	@Override
	public void dispose()
	{
		pool.release(this);
	}
	
	@Override public final void setGrey(byte g){setGrey((g & 0xFF) / 255D);}
	@Override public final byte getGreyB(){return (byte)(getGrey() * 255);}

	@Override public final void setAlpha(double a){}
	@Override public final void setAlpha(byte a){}
	@Override public final double getAlpha(){return 1;}
	@Override public final byte getAlphaB(){return (byte)255;}
	
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
