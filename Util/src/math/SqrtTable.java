package math;

/**
 * 
 * Allows blazing-fast calculation of sqrts in a certain interval.
 * 
 * @author F4113nb34st
 *
 */
public class SqrtTable
{
	private final double min;
	private final double max;
	private final double inc;
	//list of sqrt values, all other functions can be calculated off these
	private final double[] sqrt;
	
	public SqrtTable(double mi, double ma, int samples)
	{
		min = mi;
		max = ma;
		inc = (max - min) / (samples - 1);
		sqrt = new double[samples];
		for(int i = 0; i < sqrt.length; i++)
		{
			sqrt[i] = Math.sqrt(min + (i * inc));
		}
	}
	
	public double sqrt(double value)
	{
		if(value < min || value > max)
		{
			return Math.sqrt(value);
		}
		int index = (int)((value - min) / inc);
		return sqrt[index];
	}
}
