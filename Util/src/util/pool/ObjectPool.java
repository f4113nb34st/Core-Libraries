package util.pool;

import java.lang.reflect.Method;
import java.util.LinkedList;
import util.Copyable;

/**
 * 
 * A pool of objects that are used temporarily. May be reclaimed once no longer needed.
 * 
 * @author F4113nb34st
 */
@SuppressWarnings("unchecked")
public final class ObjectPool<E extends Copyable>
{
	private static Method clone;
	static
	{
		try
		{
			clone = Object.class.getDeclaredMethod("clone");
		}catch(NoSuchMethodException | SecurityException ex)
		{
			ex.printStackTrace();
		}
		clone.setAccessible(true);
	}
	
	private final LinkedList<E> pool = new LinkedList<E>();
	private final E master;
	
	public ObjectPool(E masterInstance)
	{
		master = masterInstance;
	}
	
	public synchronized E get()
	{
		E val = pool.poll();
		if(val == null)
		{
			val = (E)master.copy();
		}
		return val;
	}
	
	public synchronized void release(E val)
	{
		pool.add(val);
	}
}
