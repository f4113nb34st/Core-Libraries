package render.color;

import util.pool.ObjectPool;
import math.vector.IntVector1D;
import math.vector.Vector1D;

public final class IntARGB extends IntVector1D implements Color
{
	public static final ObjectPool<IntARGB> pool = new ObjectPool<IntARGB>(new IntARGB());
	static
	{
		Color.pool_map.put(IntARGB.class, pool);
	}
	private static final int aInvMask = 0x00FFFFFF;
	private static final int rInvMask = 0xFF00FFFF;
	private static final int gInvMask = 0xFFFF00FF;
	private static final int bInvMask = 0xFFFFFF00;
	
	public IntARGB()
	{
		set(0);
	}
	
	public IntARGB(int i)
	{
		set(i);
	}
	
	public IntARGB(Vector1D vec)
	{
		set(vec);
	}
	
	public IntARGB(Color color)
	{
		set(color);
	}
	
	@Override
	public boolean hasAlpha()
	{
		return (x & 0xFF000000) != 0xFF000000;
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
	public Color set(Color color)
	{
		setRed(color.getRedB());
		setGreen(color.getGreenB());
		setBlue(color.getBlueB());
		setAlpha(color.getAlphaB());
		return this;
	}
	
	@Override
	public void setRed(byte r)
	{
		x &= rInvMask;
		x |= (r & 0xFF) << 16;
	}

	@Override
	public void setGreen(byte g)
	{
		x &= gInvMask;
		x |= (g & 0xFF) << 8;
	}

	@Override
	public void setBlue(byte b)
	{
		x &= bInvMask;
		x |= (b & 0xFF);
	}
	
	@Override 
	public void setAlpha(byte a)
	{
		x &= aInvMask;
		x |= (a & 0xFF) << 24;
	}
	
	@Override
	public void setGrey(byte g)
	{
		setRed(g);
		setGreen(g);
		setBlue(g);
	}

	@Override
	public byte getRedB()
	{
		return (byte)((x >>> 16) & 0xFF);
	}

	@Override
	public byte getGreenB()
	{
		return (byte)((x >>> 8) & 0xFF);
	}

	@Override
	public byte getBlueB()
	{
		return (byte)(x & 0xFF);
	}
	
	@Override 
	public byte getAlphaB()
	{
		return (byte)((x >>> 24) & 0xFF);
	}
	
	@Override
	public byte getGreyB()
	{
		return (byte)((((x >>> 16) & 0xFF) + ((x >>> 8) & 0xFF) + (x & 0xFF)) / 3);
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public IntARGB copy()
	{
		return new IntARGB((Vector1D)this);
	}

	@Override
	public void dispose()
	{
		pool.release(this);
	}
	
	@Override public void setRed(double r){setRed((byte)(r * 255));}
	@Override public double getRed(){return (getRedB() & 0xFF) / 255D;}
	
	@Override public void setGreen(double g){setGreen((byte)(g * 255));}
	@Override public double getGreen(){return (getGreenB() & 0xFF) / 255D;}
	
	@Override public void setBlue(double b){setBlue((byte)(b * 255));}
	@Override public double getBlue(){return (getBlueB() & 0xFF) / 255D;}
	
	@Override public void setAlpha(double a){setAlpha((byte)(a * 255));}
	@Override public double getAlpha(){return (getAlphaB() & 0xFF) / 255D;}
	
	@Override public void setGrey(double g){setGrey((byte)(g * 255));}
	@Override public double getGrey(){return (getGreyB() & 0xFF) / 255D;}
}
