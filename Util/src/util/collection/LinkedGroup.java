
package util.collection;

import java.util.Iterator;

/**
 * 
 * A group is just a simple collection of objects that allows basic add/remove/clear/size/iterate functions with concurrent access.
 * 
 * @author F4113nb34st
 *
 */
public class LinkedGroup<E> implements Iterable<E>
{
	private static class Entry<E>
	{
		E value;
		Entry<E> next;
		Entry<E> prev;
		
		private Entry(E v, Entry<E> p, Entry<E> n)
		{
			value = v;
			prev = p;
			next = n;
		}
	}
	
	private Entry<E> header = new Entry<E>(null, null, null);
	private int size;
	
	public LinkedGroup()
	{
		header.prev = header.next = header;
	}
	
	public int size()
	{
		return size;
	}
	
	public void add(E value)
	{
		if(value == null)
		{
			return;
		}
		Entry<E> entry = new Entry<E>(value, header.prev, header);
		header.prev.next = header.prev = entry;
		size++;
	}
	
	public boolean remove(E value)
	{
		for(Entry<E> entry = header.next; entry != header; entry = entry.next)
		{
			if(entry.equals(value))
			{
				remove(entry);
				return true;
			}
		}
		return false;
	}
	
	public void clear()
	{
		//short circuit all iterators
		for(Entry<E> entry = header.next; entry != header; entry = entry.next)
		{
			entry.next = header;
		}
		header.next = header;
		header.prev = header;
		size = 0;
	}
	
	private void remove(Entry<E> entry)
	{
		entry.next.prev = entry.prev;
		entry.prev.next = entry.next;
		size--;
	}

	@Override
	public Iterator<E> iterator()
	{
		return new GroupIterator();
	}
	
	private class GroupIterator implements Iterator<E>
	{
		private Entry<E> current = header;
		
		@Override
		public boolean hasNext()
		{
			return current.next != header;
		}

		@Override
		public E next()
		{
			current = current.next;
			return current.value;
		}

		@Override
		public void remove()
		{
			LinkedGroup.this.remove(current);
		}
	}
}