package util.concurrent;

/**
 * 
 * Class that represents a task that will be executed simultaneously on multiple threads over an array.
 * 
 * @author F4113nb34st
 *
 */
public abstract class ArrayTask implements Runnable
{
	/**
	 * The maximum value. The task stops when we go over this value.
	 */
	private final int max;
	/**
	 * The increment size.
	 */
	private final int increment;
	/**
	 * The current index. Incremented on getX() calls.
	 */
	private volatile int index;
	
	/**
	 * Creates a new ArrayTask with the given min and max values.
	 * @param mi The min value.
	 * @param ma The max value.
	 */
	public ArrayTask(int mi, int ma)
	{
		this(mi, ma, 1);
	}
	
	/**
	 * Creates a new ArrayTask with the given min, max, and increment values.
	 * @param mi The min value.
	 * @param ma The max value.
	 * @param inc The increment value.
	 */
	public ArrayTask(int mi, int ma, int inc)
	{
		max = ma;
		increment = inc;
		//since index is incremented before returned, start increment less than min so first value is min
		index = mi - increment;
	}
	
	/**
	 * Gets the next index value.
	 * @return The index value or -1 if we are done.
	 */
	private synchronized int getX()
	{
		index += increment;
		if(index <= max)
		{
			return index;
		}else
		{
			return -1;
		}
	}
	
	@Override
	public void run()
	{
		try
		{
			int x;
			//loop until done (x == -1)
			while(true)
			{
				x = getX();
				if(x == -1)
				{
					break;
				}
				run(x);
			}
		}catch(ArrayIndexOutOfBoundsException ex)
		{
			//rarely we go over, if we do, don't worry about it
		}
	}
	
	/**
	 * Performs the required operation on the given x value.
	 * @param x The index value to operate on.
	 */
	public abstract void run(int x);
}
