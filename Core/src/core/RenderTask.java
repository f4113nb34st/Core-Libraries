package core;

public class RenderTask implements Runnable
{
	@Override
	public void run()
	{
		try
		{
			while(!Thread.currentThread().isInterrupted())
			{
				Core.instance.corePaint();
				Core.instance.barrier.waitFor();
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
