package render.color;

import java.util.HashMap;
import util.Copyable;
import util.pool.ObjectPool;

public interface Color extends Copyable
{
	public static final HashMap<Class<? extends Color>, ObjectPool<? extends Color>> pool_map = new HashMap<Class<? extends Color>, ObjectPool<? extends Color>>();
	
	public static final Color BLACK = new ByteARGB(0, 0, 0, 255);
	public static final Color GREY = new ByteARGB(128, 128, 128, 255);
	public static final Color WHITE = new ByteARGB(255, 255, 255, 255);
	public static final Color RED = new ByteARGB(255, 0, 0, 255);
	public static final Color YELLOW = new ByteARGB(255, 255, 0, 255);
	public static final Color GREEN = new ByteARGB(0, 255, 0, 255);
	public static final Color TEAL = new ByteARGB(0, 255, 255, 255);
	public static final Color BLUE = new ByteARGB(0, 0, 255, 255);
	public static final Color MAGENTA = new ByteARGB(255, 0, 255, 255);
	
	public boolean hasAlpha();
	public boolean hasColor();
	public boolean hasGrey();
	
	public void setAlpha(double a);
	public void setAlpha(byte a);
	public void setRed(double r);
	public void setRed(byte r);
	public void setGreen(double g);
	public void setGreen(byte g);
	public void setBlue(double b);
	public void setBlue(byte b);
	
	public void setGrey(double g);
	public void setGrey(byte g);
	
	public double getAlpha();
	public byte getAlphaB();
	public double getRed();
	public byte getRedB();
	public double getGreen();
	public byte getGreenB();
	public double getBlue();
	public byte getBlueB();
	
	public double getGrey();
	public byte getGreyB();
	
	public Color set(Color color);
	public void dispose();
}
