package util.pool;

import java.util.LinkedList;
import util.Copyable;

/**
 * 
 * A pool of objects that are used frequently and briefly. All objects are recycled once per tick.
 * 
 * @author F4113nb34st
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class ReferencePool<E extends Copyable>
{
	private static final LinkedList<ReferencePool> pools = new LinkedList<ReferencePool>();
	
	public static void clearAll()
	{
		for(ReferencePool pool : pools)
		{
			pool.clear();
		}
	}
	
	private int index = 0;
	private final LinkedList<E> pool = new LinkedList<E>();
	private final E master;
	
	public ReferencePool(E masterInstance)
	{
		pools.add(this);
		master = masterInstance;
	}
	
	public E get()
	{
		E value;
		if(index >= pool.size())
		{
			value = (E)master.copy();
			pool.add(value);
		}else
		{
			value = pool.get(index);
		}
		index++;
		return value;
	}
	
	public void clear()
	{
		index = 0;
	}
}
