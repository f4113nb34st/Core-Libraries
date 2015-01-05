package render.color;

import util.pool.ObjectPool;
import math.vector.ByteVector1D;
import math.vector.Vector1D;

public class ByteGrey extends ByteVector1D implements Color
{
	public static final ObjectPool<ByteGrey> pool = new ObjectPool<ByteGrey>(new ByteGrey());
	static
	{
		Color.pool_map.put(ByteGrey.class, pool);
	}
	
	public ByteGrey()
	{
		set(0);
	}
	
	public ByteGrey(byte i)
	{
		set(i);
	}
	
	public ByteGrey(Vector1D vec)
	{
		set(vec);
	}
	
	public ByteGrey(Color color)
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
		set(color.getGreyB());
		return this;
	}
	
	@Override
	public void setGrey(byte g)
	{
		x = g;
	}

	@Override
	public byte getGreyB()
	{
		return x;
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public ByteGrey copy()
	{
		return new ByteGrey((Vector1D)this);
	}

	@Override
	public void dispose()
	{
		pool.release(this);
	}
	
	@Override public final void setGrey(double g){setGrey((byte)(g * 255));}
	@Override public final double getGrey(){return (getGreyB() & 0xFF) / 255D;}

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
