package util.concurrent;

import java.util.Iterator;

public abstract class ListTask<E> implements Runnable
{
	/**
	 * The current index. Incremented on getLoc() calls.
	 */
	private volatile Iterator<E> iter;
	
	public ListTask(Iterable<E> list)
	{
		iter = list.iterator();
	}
	
	public synchronized E getObj()
	{
		if(iter.hasNext())
		{
			return iter.next();
		}else
		{
			return null;
		}
	}
	
	@Override
	public void run()
	{
		try
		{
			E obj;
			//loop until done (x == -1)
			while(true)
			{
				obj = getObj();
				if(obj == null)
				{
					break;
				}
				run(obj);
			}
		}catch(ArrayIndexOutOfBoundsException ex)
		{
			//rarely we go over, if we do, don't worry about it
		}
	}
	
	public abstract void run(E obj);
}
