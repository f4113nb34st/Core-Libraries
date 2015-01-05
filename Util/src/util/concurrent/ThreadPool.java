package util.concurrent;

import java.util.LinkedList;

/**
 * 
 * Object that manages a variable sized pool of threads.
 * 
 * @author F4113nb34st
 *
 */
public class ThreadPool
{
	/**
	 * List of available tasks.
	 */
	private volatile LinkedList<Runnable> taskQueue;
	
	/**
	 * Array of the threads in this ThreadPool.
	 */
	private Thread[] threads;
	
	/**
	 * The hibernation lock to suspend the child threads until they are needed.
	 */
	private Object hibernate = new Object();
	
	/**
	 * The wait lock to suspend the main thread until the tasks are completed.
	 */
	private Object waitLock = new Object();
	
	/**
	 * The number of hibernating threads.
	 */
	private volatile int numHibernating;
	
	/**
	 * True if this ThreadPool should finish.
	 */
	private volatile boolean isClosed = false;
	
	/**
	 * Creates a new ThreadPool with the given number of child threads.
	 * @param size The number of child threads.
	 */
	public ThreadPool(int size)
	{
		taskQueue = new LinkedList<Runnable>();
		threads = new Thread[size];
		for(int i = 0; i < threads.length; i++)
		{
			//create and start all child threads
			threads[i] = new PoolThread();
			threads[i].start();
		}
	}
	
	/**
	 * Returns the number of child threads in this pool.
	 * @return The number of child threads.
	 */
	public int poolSize()
	{
		return threads.length;
	}
	
	/**
	 * Adds a new task that will be run once by each thread.
	 * @param runner The task.
	 */
	public synchronized void addGlobalTask(Runnable runner)
	{
		//equivalent to adding the runner poolSize() times to the taskQueue
		for(int i = 0; i < threads.length; i++)
		{
			taskQueue.add(runner);
		}
	}
	
	/**
	 * Adds the given task to the taskQueue of this ThreadPool.
	 * @param runner The task.
	 */
	public synchronized void addTask(Runnable runner)
	{
		taskQueue.add(runner);
	}
	
	/**
	 * Starts all of the child threads to begin executing the available tasks.
	 */
	public void start()
	{
		//signal all hibernating threads to resume
		synchronized(hibernate)
		{
			hibernate.notifyAll();
		}
	}
	
	/**
	 * Starts all of the child threads to begin executing the available tasks and waits for all tasks to be completed.
	 */
	public void startAndWait()
	{
		//start
		start();
		if(!isClosed)
		{
			//wait til all tasks done
			synchronized(waitLock)
			{
				while(moreTasks())
				{
					try
					{
						waitLock.wait();
					} catch(InterruptedException ex)
					{
						//ex.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * Closes this ThreadPool.
	 */
	public void close()
	{
		isClosed = true;
		start();
	}
	
	/**
	 * Hibernates the current thread if there are no more tasks to be completed. Also alerts the main thread if it is waiting.
	 * Called by child threads.
	 */
	private void hibernate()
	{
		synchronized(hibernate)
		{
			//if more tasks, don't hibernate silly
			while(!moreTasks())
			{
				//if the last thread to finish
				if(numHibernating == poolSize() - 1)
				{
					//notify the sleeping main thread
					synchronized(waitLock)
					{
						waitLock.notifyAll();
					}
				}
				//increase hibernating count
				numHibernating++;
				try
				{
					//hibernate
					hibernate.wait();
				} catch(InterruptedException ex)
				{
					//ex.printStackTrace();
				}
				//we're done hibernating, reduce hibernating count and return
				numHibernating--;
			}
		}
	}
	
	/**
	 * Returns true if there are more tasks to be completed.
	 * @return True if more tasks left.
	 */
	private synchronized boolean moreTasks()
	{
		return !taskQueue.isEmpty();
	}
	
	/**
	 * Returns a new task from the queue.
	 * @return A task from the queue.
	 */
	private synchronized Runnable getTask()
	{
		return taskQueue.pollFirst();
	}
	
	/**
	 * The child thread's class
	 */
	public class PoolThread extends Thread
	{
		public PoolThread()
		{
			//all pool threads are daemon
			setDaemon(true);
		}
		
		@Override
		public void run()
		{
			try
			{
				//infinitely loop, if we need to die, program will kill us since we are daemon
				while(!isClosed)
				{
					Runnable task;
					//while more tasks
					while(true)
					{
						//get a task
						task = getTask();
						if(task == null)
						{
							break;
						}
						//perform it
						task.run();
					}
					//hibernate til more tasks
					hibernate();
				}
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}finally
			{
				close();
				//notify the sleeping main thread
				synchronized(waitLock)
				{
					waitLock.notifyAll();
				}
			}
		}
	}
}