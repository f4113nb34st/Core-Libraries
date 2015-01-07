package noise;

import noise.voronoi.CombineFunction;
import noise.voronoi.DefaultCombineFunctions;
import noise.voronoi.DefaultDistanceFunctions;
import noise.voronoi.DistanceEntry;
import noise.voronoi.DistanceFunction;

/**
 * 
 * Stores static methods for filling NoiseArrays with simple (point srcs only) Voronoi noise.
 * 
 * @author F4113nb34st
 *
 */
public class VoronoiNoise extends PeriodicNoise
{
	/**
	 * Array of 16 dots.
	 */
	private static final int dotNum = 16;
	private static final double[][] dots = new double[dotNum * dotNum][2];
	private static final int dotMask = dots.length - 1;
	static
	{
		double inc = 1 / (double)(dotNum - 1);
		int i = 0;
		for(double x = 0; x <= 1; x += inc)
		{
			for(double y = 0; y <= 1; y += inc)
			{
				dots[i] = new double[]{x, y};
				i++;
			}
		}
	}
	/**
	 * The NoiseGenerator instance for this VoronoiNoise.
	 */
	private IntNoiseGenerator gen = new IntNoiseGenerator();
	/**
	 * The distance function to use for the Voronoi generation.
	 */
	public DistanceFunction disFunc = DefaultDistanceFunctions.Euclid;
	/**
	 * The combine function to use for the Voronoi generation.
	 */
	public CombineFunction comFunc = DefaultCombineFunctions.F1;
	
	public VoronoiNoise()
	{
		setSeed(0);
	}
	
	@Override
	public SeededNoise setSeed(long s)
	{
		super.setSeed(s);
		gen.setSeed(s);
		
		return this;
	}
	
	/**
	 * Sets the DistanceFunction for this noise.
	 * @param dis The DistanceFunction.
	 * @return this
	 */
	public VoronoiNoise setDisFunc(DistanceFunction dis)
	{
		disFunc = dis;
		return this;
	}
	
	/**
	 * Sets the CombineFunction for this noise.
	 * @param com The CombineFunction.
	 * @return this
	 */
	public VoronoiNoise setComFunc(CombineFunction com)
	{
		comFunc = com;
		return this;
	}

	@Override
	public double getValue(double x, double y)
	{
		//get x cell
		int cellX = (int)(x / periodX);
		//get fractional x part
		double fracX = (x % periodX) / periodX;
		
		//get y cell
		int cellY = (int)(y / periodY);
		//get fractional y part
		double fracY = (y % periodY) / periodY;
		
		//create minDis array
		DistanceEntry[] minDis = new DistanceEntry[comFunc.getNumDistances()];
		
		//the distance we will check for points
		int checkDis = 1;
		//Mink. and anything with F3 needs more range
		if(disFunc == DefaultDistanceFunctions.Minkowski0_5 || minDis.length > 2)
		{
			checkDis = 2;
		}
		
		//check cell and neighbors
		for(int i = -checkDis; i <= checkDis; i++)
		{
			for(int j = -checkDis; j <= checkDis; j++)
			{
				int dotX = cellX + i;
				int dotY = cellY + j;
				//get dot for current cell
				double[] dot = dots[gen.noise_gen(dotX, dotY) & dotMask];
				//get distance to src point
				double disTo = disFunc.distanceFunc(fracX, fracY, dot[0] + i, dot[1] + j);
				//insert it into the sorted distance array
				insert(minDis, new DistanceEntry(disTo, dotX + (dotY * Math.PI)));
			}
		}
		
		//perform the Euclid sqrts
		if(disFunc == DefaultDistanceFunctions.Euclid)
		{
			for(int i = 0; i < minDis.length; i++)
			{
				minDis[i].dis = Math.sqrt(minDis[i].dis);
			}
		}
		//get value from distances
		double value = comFunc.combineFunc(minDis);
		//set value
		return value;
	}
	
	/**
     * Inserts a value into an array so that the array is sorted from least to greatest.
     * If the value is greater than the max value, it is not added.
     */
    private void insert(DistanceEntry[] array, DistanceEntry value)
    {
    	DistanceEntry temp;
        for(int i = array.length - 1; i >= 0; i--)
        {
            if(array[i] != null && value.dis > array[i].dis) 
            {
           	 	break;
            }
            temp = array[i];
            array[i] = value;
            if (i + 1 < array.length) 
            {
           	 	array[i + 1] = temp;
            }
        }
    }
}
