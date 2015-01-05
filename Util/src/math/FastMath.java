package math;

/**
 * 
 * Stores functions for fast sin, cos, and tan approximations.
 * 
 * @author F4113nb34st
 *
 */
public class FastMath
{
	//one revolution
	private static final double rev = Math.PI * 2;
	//half rev
	private static final double hrev = rev / 2;
	//quarter rev
	private static final double qrev = rev / 4;
	//the sample size to use (higher uses more memory but is more precise)
	private static final int SAMPLES = 512;
	//the resolution of the samples
	private static final double RESOLUTION = qrev / (SAMPLES - 1);
	//inverse resolution
	private static final double INVRES = 1 / RESOLUTION;
	//list of cos values, all other functions can be calculated off these
	private static final float[] cos = new float[SAMPLES];
	//populate the cos array
	static
	{
		for(int i = 0; i < SAMPLES; i++)
		{
			cos[i] = (float)Math.cos(RESOLUTION * i);
		}
	}
	
	/**
	 * Returns the cos value for the given radian measure.
	 * @param rads The radian measure.
	 * @return The cos value.
	 */
	public static final double cos(double rads)
	{
		if(rads < 0)//flip to >0
		{
			rads = -rads;
		}
		rads %= rev;//mod by 360
		if(rads > hrev)//if >180, map 360->0 and 180->180
		{
			rads = rev - rads;
		}
		if(rads <= qrev)//if <90
		{
			return cos[(int)(rads * INVRES)];
		}else
		{
			return -cos[(int)((hrev - rads) * INVRES)];
		}
	}
	
	/**
	 * Returns the sin value for the given radian measure.
	 * @param rads The radian measure.
	 * @return The sin value.
	 */
	public static final double sin(double rads)
	{
		//sin = cos - 90
		return cos(rads - qrev);
	}
	
	/**
	 * Returns the tan value for the given radian measure.
	 * @param rads The radian measure.
	 * @return The tan value.
	 */
	public static final double tan(double rads)
	{
		//tan = sin / cos
		return sin(rads) / cos(rads);
	}
	
	//atan2 stuff
	private static final int ATAN2_BITS = 7;

    private static final int ATAN2_BITS2 = ATAN2_BITS << 1;
    private static final int ATAN2_MASK = ~(-1 << ATAN2_BITS2);
    private static final int ATAN2_COUNT = ATAN2_MASK + 1;
    private static final int ATAN2_DIM = (int) Math.sqrt(ATAN2_COUNT);

    private static final float INV_ATAN2_DIM_MINUS_1 = 1.0f / (ATAN2_DIM - 1);

    private static final float[] atan2 = new float[ATAN2_COUNT];

	static
	{
		for(int i = 0; i < ATAN2_DIM; i++)
		{
			for(int j = 0; j < ATAN2_DIM; j++)
			{
				float x0 = (float)i / ATAN2_DIM;
				float y0 = (float)j / ATAN2_DIM;
				atan2[j * ATAN2_DIM + i] = (float)Math.atan2(y0, x0);
			}
		}
	}
	
	/**
	 * Returns the atan2 value for the given coords.
	 * @param y The y coord.
	 * @param x The x coord.
	 * @return The tan value.
	 */
	public static final double atan2(double y, double x)
	{
		float add, mul;
		if(x < 0.0f)
		{
			if(y < 0.0f)
			{
				x = -x;
				y = -y;
				mul = 1.0f;
			}else
			{
				x = -x;
				mul = -1.0f;
			}
			add = (float)-Math.PI;
		}else
		{
			if(y < 0.0f)
			{
				y = -y;
				mul = -1.0f;
			}else
			{
				mul = 1.0f;
			}
			add = 0.0f;
		}
		double invDiv = 1.0f / (((x < y) ? y : x) * INV_ATAN2_DIM_MINUS_1);
		int xi = (int)(x * invDiv);
		int yi = (int)(y * invDiv);
		return (atan2[yi * ATAN2_DIM + xi] + add) * mul;
	}
}
