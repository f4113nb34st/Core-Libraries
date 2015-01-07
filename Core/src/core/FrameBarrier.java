package core;

import java.util.concurrent.CyclicBarrier;

/**
 * 
 * Limits the number of frames that the Renderer can perform so as to not swamp the CPU.
 * 
 * @author F4113nb34st
 *
 */
public class FrameBarrier extends CyclicBarrier
{
	private boolean freeReign = false;
	
	public FrameBarrier()
	{
		super(2);
	}
	
	/**
	 * Sets the free reign of this FrameBarrier.
	 * If it has free reign, it will not stop threads in waitFor();
	 * @param free Whether or not to allow free reign.
	 */
	public void setFreeReign(boolean free)
	{
		freeReign = free;
		if(freeReign)//if we are getting free reign, trip the barrier.
		{
			trip();
		}
	}
	
	/**
	 * Waits for the Barrier to be tripped.
	 */
	public void waitFor()
	{
		if(freeReign)
		{
			return;
		}
		try
		{
			await();
		} catch(Exception ex)
		{
			//if we get a problem, print it out
			ex.printStackTrace();
		}
	}
	
	/**
	 * Trips this Barrier.
	 */
	public void trip()
	{
		if(getNumberWaiting() > 0)
		{
			waitFor();
		}
	}
}
