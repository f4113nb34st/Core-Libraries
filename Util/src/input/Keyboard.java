package input;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;

public class Keyboard implements AWTEventListener
{
	static
	{
		Toolkit.getDefaultToolkit().addAWTEventListener(new Keyboard(), AWTEvent.KEY_EVENT_MASK);
	}
	
	private static KListener[] listeners = new KListener[0];
	private static final HashSet<Integer> down_buttons = new HashSet<Integer>();
	
	public static void addListener(KListener listener)
	{
		KListener[] newA = new KListener[listeners.length + 1];
		System.arraycopy(listeners, 0, newA, 0, listeners.length);
		newA[newA.length - 1] = listener;
		listeners = newA;
	}
	
	public static void addPriorityListener(KListener listener)
	{
		KListener[] newA = new KListener[listeners.length + 1];
		System.arraycopy(listeners, 0, newA, 1, listeners.length);
		newA[0] = listener;
		listeners = newA;
	}
	
	public static void removeListener(KListener listener)
	{
		int index = -1;
		for(int i = 0; i < listeners.length; i++)
		{
			if(listeners[i] == listener)
			{
				index = i;
				break;
			}
		}
		if(index != -1)
		{
			
			KListener[] newA = new KListener[listeners.length - 1];
			System.arraycopy(listeners, 0, newA, 0, index);
			System.arraycopy(listeners, index + 1, newA, index, newA.length - index);
			listeners = newA;
		}
	}

	@Override
	public void eventDispatched(AWTEvent e)
	{
		if(e instanceof KeyEvent)
		{
			int button = ((KeyEvent)e).getKeyCode();
			
			if(e.getID() != KeyEvent.KEY_PRESSED && e.getID() != KeyEvent.KEY_RELEASED && e.getID() != KeyEvent.KEY_TYPED)
			{
				return;
			}
			
			switch(e.getID())
			{
				case KeyEvent.KEY_PRESSED:
				{
					setButton(button, true);
					for(KListener lis : listeners)
					{
						if(lis.keyPress(button))
						{
							break;
						}
					}
					break;
				}
				case KeyEvent.KEY_RELEASED:
				{
					setButton(button, false);
					for(KListener lis : listeners)
					{
						if(lis.keyRelease(button))
						{
							break;
						}
					}
					break;
				}
				case KeyEvent.KEY_TYPED:
				{
					for(KListener lis : listeners)
					{
						if(lis.keyTyped(((KeyEvent)e).getKeyChar()))
						{
							break;
						}
					}
					break;
				}
			}
		}
	}
	
	private void setButton(int button, boolean down)
	{
		if(down)
		{
			down_buttons.add(button);
		}else
		{
			down_buttons.remove(button);
		}
	}
	
	public interface KListener
	{
		public boolean keyPress(int button);
		
		public boolean keyRelease(int button);
		
		public boolean keyTyped(char c);
	}
	
	public static boolean buttonDown(int button)
	{
		return down_buttons.contains(button);
	}
}
