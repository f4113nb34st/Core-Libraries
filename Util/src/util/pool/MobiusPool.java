package util.pool;

import java.util.LinkedList;
import util.Copyable;

/**
 * 
 * A pool of objects that are used frequently and briefly. 
 * The distribution of objects means that objects will not be handed out again until all have been used.
 * 
 * @author F4113nb34st
 */
@SuppressWarnings({"unchecked"})
public final class MobiusPool<E extends Copyable>
{
	private int size;
	private int index = 0;
	private final LinkedList<E> pool = new LinkedList<E>();
	private final E master;
	
	public MobiusPool(E masterInstance, int s)
	{
		master = masterInstance;
		size = s;
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
		if(index >= size)
		{
			index = 0;
		}
		return value;
	}
}
