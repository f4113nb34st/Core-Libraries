package render.color;

import util.pool.ObjectPool;
import math.vector.ByteVector4D;
import math.vector.Vector4D;

public final class ByteARGB extends ByteVector4D implements Color
{
	public static final ObjectPool<ByteARGB> pool = new ObjectPool<ByteARGB>(new ByteARGB());
	static
	{
		Color.pool_map.put(ByteARGB.class, pool);
	}
	
	public ByteARGB()
	{
		set(0, 0, 0, 0);
	}
	
	public ByteARGB(int i, int j, int k, int l)
	{
		set(i, j, k, l);
	}
	
	public ByteARGB(Vector4D vec)
	{
		set(vec);
	}
	
	public ByteARGB(Color color)
	{
		set(color);
	}
	
	@Override
	public boolean hasAlpha()
	{
		return w != 255;
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
		set(color.getRedB(), color.getGreenB(), color.getBlueB(), color.getAlphaB());
		return this;
	}
	
	@Override
	public void setRed(byte r)
	{
		x = r;
	}

	@Override
	public void setGreen(byte g)
	{
		y = g;
	}

	@Override
	public void setBlue(byte b)
	{
		z = b;
	}
	
	@Override 
	public void setAlpha(byte a)
	{
		w = a;
	}
	
	@Override
	public void setGrey(byte g)
	{
		x = y = z = g;
	}

	@Override
	public byte getRedB()
	{
		return x;
	}

	@Override
	public byte getGreenB()
	{
		return y;
	}

	@Override
	public byte getBlueB()
	{
		return z;
	}
	
	@Override 
	public byte getAlphaB()
	{
		return w;
	}
	
	@Override
	public byte getGreyB()
	{
		return (byte)((x + y + z) / 3);
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public ByteARGB copy()
	{
		return new ByteARGB((Vector4D)this);
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
	
	
	//gamma correction code storage
//	/**
//	 * The gamma exponent.
//	 */
//	private static final double gamma = 2.2;
//	/**
//	 * The gamma correction exponent.
//	 */
//	private static final double inv_gamma = 1 / gamma;
//	private static final byte[] correction_table = new byte[256];
//	private static final byte[] inv_correction_table = new byte[256];
//	static
//	{
//		for(int i = 0; i < 256; i++)
//		{
//			correction_table[i] = (byte)Math.round(Math.pow(i / 255D, inv_gamma) * 255);
//			inv_correction_table[i] = (byte)Math.round(Math.pow(i / 255D, gamma) * 255);
//		}
//	}
}
