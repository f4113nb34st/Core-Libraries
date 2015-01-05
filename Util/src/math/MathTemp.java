package math;

public class MathTemp
{
	public static void main(String[] args)
	{
		double[] array = new double[1000000];
		long time = System.currentTimeMillis();
		for(int i = 0; i < array.length; i++)
		{
			array[i] = Math.sqrt(i);
		}
		System.out.println(System.currentTimeMillis() - time);
		
		time = System.currentTimeMillis();
		for(int i = 0; i < array.length; i++)
		{
			array[i] = newton_method(i);
		}
		System.out.println(System.currentTimeMillis() - time);
	}
	
	private static final double mu = 1 / 100D;
	
	private static final double newton_method(double orig)
	{
		double old;
		double current = 1;
		
		do
		{
			old = current;
			current = old - (((old * old) - orig) / (2 * old));
		}while(old - current > mu || old - current < -mu);
		
		return current;
	}
}
