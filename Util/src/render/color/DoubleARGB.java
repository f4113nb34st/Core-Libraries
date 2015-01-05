package render.color;

import util.pool.ObjectPool;
import math.vector.DoubleVector4D;
import math.vector.Vector4D;

public final class DoubleARGB extends DoubleVector4D implements Color
{
	public static final ObjectPool<DoubleARGB> pool = new ObjectPool<DoubleARGB>(new DoubleARGB());
	static
	{
		Color.pool_map.put(DoubleARGB.class, pool);
	}
	
	public DoubleARGB()
	{
		set(0, 0, 0, 0);
	}
	
	public DoubleARGB(double i, double j, double k, double l)
	{
		set(i, j, k, l);
	}
	
	public DoubleARGB(Vector4D vec)
	{
		set(vec);
	}
	
	public DoubleARGB(Color color)
	{
		set(color);
	}
	
	@Override
	public boolean hasAlpha()
	{
		return w != 1;
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
		set(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		return this;
	}
	
	@Override
	public void setRed(double r)
	{
		x = r;
	}

	@Override
	public void setGreen(double g)
	{
		y = g;
	}

	@Override
	public void setBlue(double b)
	{
		z = b;
	}
	
	@Override 
	public void setAlpha(double a)
	{
		w = a;
	}
	
	@Override
	public void setGrey(double g)
	{
		x = y = z = g;
	}

	@Override
	public double getRed()
	{
		return x;
	}

	@Override
	public double getGreen()
	{
		return y;
	}

	@Override
	public double getBlue()
	{
		return z;
	}
	
	@Override 
	public double getAlpha()
	{
		return w;
	}
	
	@Override
	public double getGrey()
	{
		return (x + y + z) / 3D;
	}
	
	/**
	 * Returns a copy of this vector.
	 * @return The copy of this vector.
	 */
	@Override
	public DoubleARGB copy()
	{
		return new DoubleARGB((Vector4D)this);
	}

	@Override
	public void dispose()
	{
		pool.release(this);
	}
	
	@Override public void setRed(byte r){setRed((r & 0xFF) / 255D);}
	@Override public byte getRedB(){return (byte)(getRed() * 255);}
	
	@Override public void setGreen(byte g){setGreen((g & 0xFF) / 255D);}
	@Override public byte getGreenB(){return (byte)(getGreen() * 255);}
	
	@Override public void setBlue(byte b){setBlue((b & 0xFF) / 255D);}
	@Override public byte getBlueB(){return (byte)(getBlue() * 255);}
	
	@Override public void setAlpha(byte a){setAlpha((a & 0xFF) / 255D);}
	@Override public byte getAlphaB(){return (byte)(getAlpha() * 255);}
	
	@Override public void setGrey(byte g){setGrey((g & 0xFF) / 255D);}
	@Override public byte getGreyB(){return (byte)(getGrey() * 255);}
}
